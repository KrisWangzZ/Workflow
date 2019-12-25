package engine.service.impl;

import engine.Action;
import engine.bpmn.process.activities.Task;
import engine.bpmn.process.activities.UserTask;
import engine.core.Deployment;
import engine.core.ProcessInstance;
import engine.core.WorkFlowEngine;
import engine.service.TaskService;


public class MemTaskService implements TaskService {
    public void claim(String taskId) {

    }

    public void unclaim(String taskId) {

    }

    public void complete(String taskId) throws Exception {

        String processInstanceId = taskId.split(":")[0];

        ProcessInstance processInstance = WorkFlowEngine.getWorkFlowEngine().getRepositoryService().getProcessInstance(processInstanceId);

        Deployment deployment = WorkFlowEngine.getWorkFlowEngine().getRepositoryService().getDeployment(processInstanceId);

        Task task = (Task) deployment.getProcessDefinition().getNodeMap().get(taskId);

        if(task instanceof UserTask){

            task.act(Action.PRE_INVOKE);

            processInstance.run();

        }
    }
}
