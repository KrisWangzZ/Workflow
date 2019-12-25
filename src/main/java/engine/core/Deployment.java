package engine.core;

import engine.service.impl.Dom4JXmlParserService;
import org.springframework.core.io.DefaultResourceLoader;

public class Deployment{

    private String name;
    private String id;
    private String processDefinitionResourcePath;
    private ProcessDefinition processDefinition;

    public String getName() {
        return name;
    }

    public Deployment setName(String name) {
        this.name = name;
        return this;
    }

    public String getId() {
        return id;
    }

    public Deployment setId(String id) {
        this.id = id;
        return this;
    }

    public ProcessDefinition getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
    }

    public Deployment addClasspathResource(String id){
        this.processDefinitionResourcePath = id;
        return this;
    }

    public Deployment deploy() throws Exception {
        //解析文档返回ProcessDefinition
        Dom4JXmlParserService dom4JXmlParserService = (Dom4JXmlParserService) WorkFlowEngine.getWorkFlowEngine().getXmlParerService();
        this.processDefinition = dom4JXmlParserService.parse(new DefaultResourceLoader().getResource(this.processDefinitionResourcePath));
        this.setId(processDefinition.getProcess().getId());
        WorkFlowEngine.getWorkFlowEngine().getRepositoryService().add2Deployments(this);
        return this;
    }



}
