package engine.service.impl;

import com.google.common.collect.Sets;
import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;
import engine.bpmn.core.common.*;
import engine.bpmn.process.activities.ServiceTask;
import engine.bpmn.process.activities.SubProcess;
import engine.bpmn.process.Process;
import engine.bpmn.process.activities.Task;
import engine.bpmn.process.activities.UserTask;
import engine.bpmn.process.events.EndEvent;
import engine.bpmn.process.events.StartEvent;
import engine.bpmn.process.gateways.ExclusiveGateway;
import engine.bpmn.process.gateways.InclusiveGateway;
import engine.bpmn.process.gateways.ParallelGateway;
import engine.core.ProcessDefinition;
import engine.service.XmlParserService;
import lombok.extern.java.Log;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import javax.el.ValueExpression;
import javax.el.VariableMapper;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Dom4JXmlParserService implements XmlParserService {

    private Process process;

    public ProcessDefinition parse(Resource resource) throws Exception{
        InputStream inputStream = null;
        try{
            Assert.notNull(resource, "xml resource is null");
            inputStream = resource.getInputStream();
            Assert.notNull(inputStream, "xml input stream is null");
            Document document = new SAXReader().read(inputStream);
            return parseDocument(document);
        }catch (Exception e){
            throw e;
        }finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                }
            }
        }

    }

    private ProcessDefinition parseDocument(Document document) throws Exception {

        Assert.notNull(document, "document is null");

        // 获取definitions元素
        Element root = document.getRootElement();
        Assert.isTrue(null != root && null != root.getName() && root.getName().equals("definitions"),
                "root element must be definitions");
        Element definitions = root;

        // 获取process元素
        List<Element> processList = definitions.elements("process");
        Assert.isTrue(null != processList && 1 == processList.size(), "process list size must be 1");
        Element e = (Element) processList.get(0);
        process = new Process();
        process.setId(e.attribute("id").getText());

        List<Element> children = e.elements();
        Assert.isTrue(null != children && children.size() >= 1, "child element of process not valid");

        // 开始提取每个子元素
        Map<String, FlowNode> nodeMap = new HashMap<String, FlowNode>(64);
        Map<String, SequenceFlow> edgeMap = new HashMap<String, SequenceFlow>(64);
        //
        FlowNode previousNode = null;
        SequenceFlow previousSequenceFlow = null;

        for (Element child : children) {
            String name = child.getName();
            Assert.isTrue(null != name && name.length() > 0, "name of element is not valid");
            if ("startEvent".equals(name)) {
                StartEvent startEvent = new StartEvent();
                parseAttributes(child, startEvent);
                previousNode = nodeMap.put(startEvent.getId(), startEvent);
                Assert.isTrue(null == previousNode, "duplicate id -> " + startEvent.getId());
            } else if ("endEvent".equals(name)) {
                EndEvent endEvent = new EndEvent();
                parseAttributes(child, endEvent);
                previousNode = nodeMap.put(endEvent.getId(), endEvent);
                Assert.isTrue(null == previousNode, "duplicate id ->" + endEvent.getId());
            } else if ("sequenceFlow".equals(name)) {
                SequenceFlow sequenceFlow = new SequenceFlow();
                parseAttributes(child, sequenceFlow);
                previousSequenceFlow = edgeMap.put(sequenceFlow.getId(), sequenceFlow);
                Assert.isTrue(null == previousSequenceFlow, "duplicate id -> " + sequenceFlow.getId());
            } else if ("serviceTask".equals(name)) {
                ServiceTask serviceTask = new ServiceTask();
                parseAttributes(child, serviceTask);
                previousNode = nodeMap.put(serviceTask.getId(), serviceTask);
                Assert.isTrue(null == previousNode, "duplicate id -> " + serviceTask.getId());
            }else if("userTask".equals(name)){
                UserTask userTask = new UserTask();
                parseAttributes(child, userTask);
                previousNode = nodeMap.put(userTask.getId(), userTask);
                Assert.isTrue(null == previousNode, "duplicate id -> " + userTask.getId());
            }
            else if ("exclusiveGateway".equals(name)) {
                ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
                parseAttributes(child, exclusiveGateway);
                previousNode = nodeMap.put(exclusiveGateway.getId(), exclusiveGateway);
                Assert.isTrue(null == previousNode, "duplicate id -> " + exclusiveGateway.getId());
            } else if ("parallelGateway".equals(name)) {
                ParallelGateway parllelGateway = new ParallelGateway();
                parseAttributes(child, parllelGateway);
                previousNode = nodeMap.put(parllelGateway.getId(), parllelGateway);
                Assert.isTrue(null == previousNode, "duplicate id -> " + parllelGateway.getId());
            } else if ("inclusiveGateway".equals(name)) {
                InclusiveGateway inclusiveGateway = new InclusiveGateway();
                parseAttributes(child, inclusiveGateway);
                previousNode = nodeMap.put(inclusiveGateway.getId(), inclusiveGateway);
                Assert.isTrue(null == previousNode, "duplicate id -> " + inclusiveGateway.getId());
            } else if ("subProcess".equals(name)) {
                SubProcess subProcess = new SubProcess();
                parseAttributes(child, subProcess);
                previousNode = nodeMap.put(subProcess.getId(), subProcess);
                Assert.isTrue(null == previousNode, "duplicate id -> " + subProcess.getId());
            } else {
                String errorMsg = "not valid element [" + name + "]";
                throw new Exception(errorMsg);
            }

        }

        validate(nodeMap,edgeMap);

        return new ProcessDefinition(nodeMap, edgeMap,process);

    }

    private void validate(Map<String, FlowNode> nodeMap, Map<String, SequenceFlow> edgeMap) {

        Assert.isTrue(null != nodeMap && nodeMap.size() > 0, "node map must size >=1");
        Assert.isTrue(null != edgeMap && edgeMap.size() > 0, "edge Map must size >=1");

        Set<String> nodeKeySet = nodeMap.keySet();
        Set<String> edgeKeySet = edgeMap.keySet();

        // 1)2者的ID不应该有重复的
        // 交集
        Set<String> sameKeySet = Sets.intersection(nodeKeySet, edgeKeySet);
        Assert.isTrue(0 == sameKeySet.size(), "duplicate id -> " + sameKeySet);

    }

    private String parseAttribute(Element element, String name){
        Assert.isTrue(null != element, "element is null");

        String value = null;
        Attribute nameAttribute = element.attribute(name);
        if (null != nameAttribute) {
            value = nameAttribute.getText();
        }

        // 返回结果
        return value;
    }

    private void parseAttributes(Element element, FlowElement flowElement){

        flowElement.setId(process.getId() + ":" + parseAttribute(element,"id"));

        flowElement.setName(parseAttribute(element,"name"));

        if(parseAttribute(element,"class")!=null){
            ((ServiceTask) flowElement).setClassName(parseAttribute(element,"class"));
        }
        if(parseAttribute(element,"description")!=null){
            ((Task) flowElement).setDescription(parseAttribute(element,"description"));
        }
        if(parseAttribute(element,"assignee")!=null){
            ((Task) flowElement).setAssignee(parseAttribute(element,"assignee"));
        }

        if(parseAttribute(element,"sourceRef")!=null){
            ((SequenceFlow) flowElement).setSourceRef(process.getId() + ":" + parseAttribute(element,"sourceRef"));
        }

        if(parseAttribute(element,"targetRef")!=null){
            ((SequenceFlow) flowElement).setTargetRef(process.getId() + ":" + parseAttribute(element,"targetRef"));
        }

        if(parseAttribute(element,"action")!=null){
            ((SequenceFlow) flowElement).setAction(parseAttribute(element,"action"));
        }

        Element conditionElement = element.element("conditionExpression");
        if (null != conditionElement) {
            ((SequenceFlow) flowElement).setConditionExpression(new ConditionExpression());
            parseExpression(element.element("conditionExpression"),((SequenceFlow) flowElement).getConditionExpression());
        }

    }

    public void parseExpression(Element element, Expression expression){
        Assert.isTrue(element != null, "element is null");

        // type属性必须存在
        Attribute typeAttribute = element.attribute("type");
        Assert.isTrue(null != typeAttribute, "typeAttribute is null");
        String type = typeAttribute.getText();
        Assert.isTrue(null != type && type.equals("juel"), "type value must be juel");
        expression.setType(type);

        // expression必须存在
        String expressionStr = element.getText();
        Assert.hasText(expressionStr, "expression not valid");
        // 去掉\n\t
        expressionStr = expressionStr.replaceAll("\n", "");
        expressionStr = expressionStr.replaceAll("\t", "");
        // 执行trim
        expressionStr = expressionStr.trim();
        Assert.hasText(expressionStr, "expression not valid");
        expression.setExpression(expressionStr);
    }




}
