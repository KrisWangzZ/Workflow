package engine.core;

import engine.bpmn.core.common.FlowNode;
import engine.bpmn.core.common.SequenceFlow;
import engine.bpmn.process.Process;
import engine.bpmn.process.events.StartEvent;
import engine.bpmn.process.gateways.ExclusiveGateway;
import engine.bpmn.process.gateways.ParallelGateway;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Map;

public class ProcessDefinition {

    private Process process;
    private StartEvent startEvent;

    private final Map<String, FlowNode> nodeMap;

    private final Map<String, SequenceFlow> edgeMap;

    public Process getProcess() {
        return process;
    }

    public StartEvent getStartEvent() {
        return startEvent;
    }

    public Map<String, FlowNode> getNodeMap() {
        return nodeMap;
    }

    public Map<String, SequenceFlow> getEdgeMap() {
        return edgeMap;
    }

    public ProcessDefinition(Map<String, FlowNode> nodeMap, Map<String, SequenceFlow> edgeMap, Process process)
            throws Exception {

        Assert.isTrue(null != nodeMap, "not valid node map");
        Assert.isTrue(null != edgeMap, "not valid edge map");
        this.nodeMap = nodeMap;
        this.edgeMap = edgeMap;
        this.process = process;

        buildConnection();

    }

    private void buildConnection() {

        Collection<SequenceFlow> c = edgeMap.values();

        for(SequenceFlow e : c ){

            String srcNodeId = e.getSourceRef();
            String tgtNodeId = e.getTargetRef();

            FlowNode srcNode = nodeMap.get(srcNodeId);
            FlowNode tgtNode = nodeMap.get(tgtNodeId);

            Assert.isTrue(null != srcNode, "find not node , not valid src node id -> " + srcNodeId);
            Assert.isTrue(null != tgtNode, "find not node , not valid target node id -> " + tgtNodeId);

            //对跳转和回退进行处理
            srcNode.addOutcoming(e);
            if(e.getAction().equals("NORMAL")) tgtNode.addIncoming(e);

            e.setSourceNode(srcNode);
            e.setTargetNode(tgtNode);

            if(srcNode instanceof ExclusiveGateway){

                Assert.isTrue(null != e.getConditionExpression(), "ConditionExpression is null");

                Assert.isTrue(0 != e.getConditionExpression().getExpression().length() | null != e.getConditionExpression().getExpression(), "Expression is null");

            }
            else if( srcNode instanceof ParallelGateway){

                Assert.isTrue(null == e.getConditionExpression(), "The ParallelGateway ConditionalExpression must be null");

            }

        }

        Collection<FlowNode> cn = nodeMap.values();

        for(FlowNode n : cn){

            Assert.isTrue(0 != n.getIncoming().size() | 0 != n.getOutgoing().size(), "There is a node not used" + ":"+ n.getId());

            if(n instanceof StartEvent){

                Assert.isTrue(null == startEvent, "There must be only 1 StartEvent");

                this.startEvent = (StartEvent) n;

            }

        }

        Assert.isTrue(null != startEvent, "There must be only 1 StartEvent");

    }
}
