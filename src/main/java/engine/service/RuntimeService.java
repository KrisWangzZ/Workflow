package engine.service;

import engine.bpmn.core.common.ConditionExpression;
import engine.core.ProcessInstance;

public interface RuntimeService {
    ProcessInstance startProcessInstanceById(String id) throws Exception;
    void setVariable(String varName, Object value, Class valueClass) throws Exception;
    Object getVariable(String varName);
    boolean getExpressionValue(ConditionExpression conditionExpression);

}
