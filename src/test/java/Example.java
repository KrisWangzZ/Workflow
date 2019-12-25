import engine.core.Deployment;
import engine.core.EngineConfiguration;
import engine.core.ProcessInstance;
import engine.core.WorkFlowEngine;
import engine.service.RuntimeService;
import engine.service.TaskService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *startEvent0 -> invoke
 * flow -> invoke
 * serviceTask -> invoke
 * flow1 -> invoke
 * serviceTask1 -> invoke
 * flow2 -> invoke
 * exclu0 -> invoke
 * flow3 -> invoke
 * flow3 : true
 * flow4 -> ignore
 * endEvent0 -> invoke
 */

public class Example {

    public static void main(String[] args) throws Exception {

        //配置工作流引擎
        WorkFlowEngine engine = EngineConfiguration.creatEngineConfigurationFromResource("cfg.xml").buildProcessEngine();

        //启动引擎

        //部署流程
        Deployment deployment = engine.getRepositoryService()
                .creatDeployment()
                .setName("example")
                .addClasspathResource("bpmn.xml")
                .deploy();

        RuntimeService runtimeService = engine.getRuntimeService();

        //启动流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(deployment.getProcessDefinition().getProcess().getId());

        // processInstance.run() -> ProcessEntity.run() -> 拓扑图输出

        TaskService taskService = engine.getTaskService();

        taskService.complete("third:userTask");

        Map<String, Boolean> t= new HashMap<>();

        t.put("approved", false);

        engine.getFormService().submitTaskFormData("userTask",t);

        taskService.complete("third:userTask1");


    }

}
