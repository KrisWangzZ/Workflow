package engine.core;

import engine.service.RepositoryService;
import engine.service.RuntimeService;
import engine.service.TaskService;
import engine.service.XmlParserService;

public interface Engine {
    RuntimeService getRuntimeService();
    RepositoryService getRepositoryService();
    TaskService getTaskService();
    XmlParserService getXmlParerService();
}
