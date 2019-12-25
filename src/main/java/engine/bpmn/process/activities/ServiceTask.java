package engine.bpmn.process.activities;

import engine.Action;
import engine.core.JavaDelegate;
import engine.service.impl.MemRuntimeService;
import org.springframework.util.Assert;

public class ServiceTask extends Task {

    private String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public void act(int action) throws Exception {
        super.act(action);
        Assert.hasText(className, "class attribute not valid in Task");
        // 确保是JavaDelegate类
        Class businessClass = Class.forName(className);
        Assert.isAssignable(JavaDelegate.class, businessClass,
                "wrong class,It must be implementation of interface JavaDelegate - " + className);

        // action部分
        Object cachedObject = MemRuntimeService.getCachedJavaDelegate().get(className);
        if (null == cachedObject) {
            synchronized (MemRuntimeService.getLOCK()) {
                cachedObject = MemRuntimeService.getCachedJavaDelegate().get(className);
                if (null == cachedObject) {
                    cachedObject = Class.forName(className).newInstance();
                    MemRuntimeService.getCachedJavaDelegate().put(className, cachedObject);
                }
            }
        }

        Assert.isTrue(cachedObject instanceof JavaDelegate, "bean obj must be JavaDelegate type for " + className);
        JavaDelegate delegate = (JavaDelegate) cachedObject;

        //执行服务任务
        delegate.execute();
    }
}
