package engine.service.impl;

import engine.bpmn.process.activities.Task;
import engine.core.Deployment;
import engine.core.ProcessInstance;
import engine.core.WorkFlowEngine;
import engine.service.FormService;
import engine.service.RuntimeService;

import java.util.Map;

public class MemFormService implements FormService {
    @Override
    public void submitTaskFormData(String taskId, Map<String, ?> data) throws Exception {

        RuntimeService runtimeService = WorkFlowEngine.getWorkFlowEngine().getRuntimeService();

        for(String key : data.keySet()){

            runtimeService.setVariable(key,data.get(key), data.get(key).getClass());

        }



    }
}
