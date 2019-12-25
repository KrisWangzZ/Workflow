package engine.core;

import engine.bpmn.core.common.FlowNode;
import engine.service.RepositoryService;
import engine.service.RuntimeService;
import engine.service.TaskService;
import engine.service.XmlParserService;

import java.util.Map;

public interface ProcessInstance extends Execution {
    String getDeploymentId();
    Map<String, FlowNode> getCandidates();
    FlowNode removeCandidate(FlowNode flowNode);
    void addCandidate(FlowNode flowNode);
    void run() throws Exception;

}
