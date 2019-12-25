import engine.core.JavaDelegate;
import engine.core.WorkFlowEngine;

public class ServiceTaskExample implements JavaDelegate {
    @Override
    public void execute() throws Exception {

        //设置变量，模拟输出
        WorkFlowEngine.getWorkFlowEngine().getRuntimeService().setVariable("securityStatus", "abcffg", String.class);

        //System.out.println(WorkFlowEngine.getWorkFlowEngine().getRuntimeService().getVariable("securityStatus"));
    }
}
