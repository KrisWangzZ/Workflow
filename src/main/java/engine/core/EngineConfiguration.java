package engine.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class EngineConfiguration {
    public static EngineConfiguration creatEngineConfigurationFromResource(String id){
        ApplicationContext ctx= new ClassPathXmlApplicationContext(id);
        return (EngineConfiguration) ctx.getBean("engineConfiguration");
    }
    public abstract WorkFlowEngine buildProcessEngine();
}
