package engine.bpmn.core.common;

import engine.Action;
import engine.State;
import engine.core.WorkFlowEngine;

public class SequenceFlow extends FlowElement {
    protected ConditionExpression conditionExpression;
    protected String sourceRef;
    protected String targetRef;

    protected String action;

    protected FlowNode sourceNode;
    protected FlowNode targetNode;

    protected String skipExpression;

    // Actual flow elements that match the source and target ref
    // Set during process definition parsing
    protected FlowElement sourceFlowElement;

    public SequenceFlow(String sourceRef, String targetRef) {
        this.sourceRef = sourceRef;
        this.targetRef = targetRef;
    }

    public SequenceFlow() {

    }

    public ConditionExpression getConditionExpression() {
        return conditionExpression;
    }

    public void setConditionExpression(ConditionExpression conditionExpression) {
        this.conditionExpression = conditionExpression;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSourceRef() {
        return sourceRef;
    }

    public void setSourceRef(String sourceRef) {
        this.sourceRef = sourceRef;
    }

    public String getTargetRef() {
        return targetRef;
    }

    public void setTargetRef(String targetRef) {
        this.targetRef = targetRef;
    }

    public FlowNode getSourceNode() {
        return sourceNode;
    }

    public void setSourceNode(FlowNode sourceNode) {
        this.sourceNode = sourceNode;
    }

    public FlowNode getTargetNode() {
        return targetNode;
    }

    public void setTargetNode(FlowNode targetNode) {
        this.targetNode = targetNode;
    }

    public String getSkipExpression() {
        return skipExpression;
    }

    public void setSkipExpression(String skipExpression) {
        this.skipExpression = skipExpression;
    }

    public FlowElement getSourceFlowElement() {
        return sourceFlowElement;
    }

    public void setSourceFlowElement(FlowElement sourceFlowElement) {
        this.sourceFlowElement = sourceFlowElement;
    }

    public FlowElement getTargetFlowElement() {
        return targetFlowElement;
    }

    public void setTargetFlowElement(FlowElement targetFlowElement) {
        this.targetFlowElement = targetFlowElement;
    }

    protected FlowElement targetFlowElement;

    @Override
    public void act(int action) throws Exception {
        super.act(action);
        if (action == Action.IGNORE) setSTATE(State.IGNORED);
        else if(action == Action.INVOKE){
            // 根据条件表达式来
            if (null == this.conditionExpression) {
                setSTATE(State.INVOKED);
                return;
            }

            // 下面表示有表达式,则需要构造引擎来判断
            // 条件表达式实时计算比较好,因为事先并不知道哪些表达式会真正触发计算
            // 根据计算结果来设置自己的状态
            boolean result = WorkFlowEngine.getWorkFlowEngine().getRuntimeService().getExpressionValue(conditionExpression);
            //System.out.println(getId()+" : "+result);
            setSTATE(result ? State.INVOKED : State.IGNORED);
        }
    }
}
