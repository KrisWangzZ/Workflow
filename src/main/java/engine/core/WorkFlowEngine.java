package engine.core;

import engine.service.*;
import engine.service.impl.*;

public class WorkFlowEngine implements Engine {

    WorkFlowEngine(EngineConfiguration cfg) {
        this.cfg = cfg;
        this.xmlParserService = new Dom4JXmlParserService();
        this.runtimeService = new MemRuntimeService();
        this.repositoryService = new MemRepositoryService();
        this.taskService = new MemTaskService();
        this.formService = new MemFormService();
    }

    private static WorkFlowEngine workFlowEngine;

    private static volatile boolean onRunning = false;

    private EngineConfiguration cfg;

    private XmlParserService xmlParserService;

    private TaskService taskService;

    private RuntimeService runtimeService;

    private RepositoryService repositoryService;

    private FormService formService;

    public FormService getFormService() {
        return formService;
    }

    public void setFormService(FormService formService) {
        this.formService = formService;
    }

    public static WorkFlowEngine getWorkFlowEngine() {
        return workFlowEngine;
    }

    public static void setWorkFlowEngine(WorkFlowEngine workFlowEngine) {
        WorkFlowEngine.workFlowEngine = workFlowEngine;
    }

    private void setCfg(EngineConfiguration cfg) {
        this.cfg = cfg;
    }

    public RepositoryService getRepositoryService() {
        return this.repositoryService;
    }

    public RuntimeService getRuntimeService() {
        return this.runtimeService;
    }

    public TaskService getTaskService() {
        return this.taskService;
    }

    public XmlParserService getXmlParerService() {
        return this.xmlParserService;
    }


}
