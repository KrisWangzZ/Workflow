package engine.service.impl;

import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;
import engine.bpmn.core.common.ConditionExpression;
import engine.bpmn.core.common.FlowNode;
import engine.bpmn.process.events.EndEvent;
import engine.bpmn.process.gateways.GateWay;
import engine.core.*;
import engine.hibernate.HibernateUtils;
import engine.service.RuntimeService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.util.Assert;

import javax.el.ValueExpression;
import javax.el.VariableMapper;
import java.util.concurrent.ConcurrentHashMap;

public class MemRuntimeService implements RuntimeService {

    private ExpressionFactoryImpl juelExpressionFactory = new ExpressionFactoryImpl();
    private SimpleContext juelContext = new SimpleContext();

    public static ConcurrentHashMap<String, Object> getCachedJavaDelegate() {
        return CACHED_JAVA_DELEGATE;
    }

    public static Object getLOCK() {
        return LOCK;
    }

    // 整个JVM共用的部分
    // 可能涉及到类的加载/卸载
    private static final ConcurrentHashMap<String, Object> CACHED_JAVA_DELEGATE = new ConcurrentHashMap<String, Object>();
    private static final Object LOCK = new Object();

    public ProcessInstance startProcessInstanceById(String id) throws Exception {
        Deployment deployment = WorkFlowEngine.getWorkFlowEngine().getRepositoryService().getDeployment(id);
        ProcessInstance processInstance =  new ProcessEntity(deployment);
        WorkFlowEngine.getWorkFlowEngine().getRepositoryService().add2ProcessManager(processInstance);
        processInstance.addCandidate(deployment.getProcessDefinition().getStartEvent());
        processInstance.run();
        return processInstance;
    }

    @SuppressWarnings("rawtypes")
    public void setVariable(String varName, Object value, Class valueClass) throws Exception {
        Assert.hasText(varName, "var name must be valid");
        // 先判断是否已经有这个varName对应的,否则造成覆盖就不好
        // 经过讨论,可以覆盖老的变量
        // Assert.isTrue(null == variables.put(varName, OBJ), "dupliate var defined ->"
        // + varName);
        // 现在可以放心的放入到context里面去
        // 可覆盖老的变量,注意
        juelContext.setVariable(varName, juelExpressionFactory.createValueExpression(value, valueClass));

    }

    public Object getVariable(String varName) {

        VariableMapper variableMapper = juelContext.getVariableMapper();
        // 判断是否为空
        if (null == variableMapper) {
            return null;
        }
        // 继续查找
        ValueExpression valueExpression = variableMapper.resolveVariable(varName);
        if (null == valueExpression) {
            return null;
        }
        // 继续查找
        return valueExpression.getValue(juelContext);

    }

    public boolean getExpressionValue(ConditionExpression conditionExpression) {
        Assert.isTrue(null != conditionExpression.getExpression(), "not valid expression");
        // 解析表达式
        ValueExpression e = juelExpressionFactory.createValueExpression(juelContext,
                conditionExpression.getExpression(), java.lang.Boolean.class);
        boolean result = (boolean) e.getValue(juelContext);
        return result;
    }

    public static void generateRecords(FlowNode flowNode, int action){

        if(flowNode instanceof GateWay) return;

        try{

            Session session = HibernateUtils.getSession();

            //使用Hibernate操作数据库，都要开启事务,得到事务对象
            Transaction transaction = session.getTransaction();

            //开启事务
            transaction.begin();

            switch (action){

                case 0:
                    //把对象添加到数据库中
                    session.save(flowNode);
                    break;
                case 1:
                    session.update(flowNode);
                    break;
                default:
                    break;
            }

            //提交事务
            transaction.commit();

        }catch (Exception e){
            e.printStackTrace();
        }finally {

            HibernateUtils.closeSession();

        }

    }

}
