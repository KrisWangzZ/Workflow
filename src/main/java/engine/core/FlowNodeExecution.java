package engine.core;

import engine.State;
import engine.bpmn.core.common.FlowElement;
import engine.bpmn.core.common.FlowNode;

public class FlowNodeExecution implements Execution {

    private FlowNode flowNode;

    private boolean suspended = true;

    private boolean ended = true;

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    private int action;

    private State state;

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public void setGetParentId(String getParentId) {
        this.getParentId = getParentId;
    }

    private String getParentId;

    public String getId() {
        return flowNode.getId();
    }

    public boolean isSuspended() {
        return ended;
    }

    public FlowNode getFlowNode() {
        return flowNode;
    }

    public void setFlowNode(FlowNode flowNode) {
        this.flowNode = flowNode;
    }

    public String getGetParentId() {
        return getParentId;
    }

    public boolean isEnded() {
        return suspended;
    }

    public String getParentId() {
        return this.getParentId;
    }

    public String getName() {
        return flowNode.getName();
    }
}
