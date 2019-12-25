package engine.service;

import engine.core.Deployment;
import engine.core.ProcessInstance;

public interface RepositoryService {
    Deployment creatDeployment();
    Deployment getDeployment(String id);
    ProcessInstance getProcessInstance(String id);
    void add2ProcessManager(ProcessInstance process);
    void add2Deployments(Deployment deployment);

}
