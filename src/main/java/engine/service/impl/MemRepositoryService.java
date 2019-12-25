package engine.service.impl;

import engine.core.Deployment;
import engine.core.ProcessInstance;
import engine.service.RepositoryService;

import java.util.HashMap;
import java.util.Map;

public class MemRepositoryService implements RepositoryService {
    private Map<String, Deployment> deployments = new HashMap<String, Deployment>();
    private Map<String, ProcessInstance> processManager = new HashMap<>();

    public Deployment creatDeployment() {
        Deployment deployment = new Deployment();
        return deployment;
    }

    public Deployment getDeployment(String id) {
        return deployments.get(id);
    }

    public ProcessInstance getProcessInstance(String id) {
        return processManager.get((id));
    }

    public void add2ProcessManager(ProcessInstance process) {
        this.processManager.put(process.getId(), process);
    }

    @Override
    public void add2Deployments(Deployment deployment) {
        deployments.put(deployment.getId(),deployment);
    }
}
