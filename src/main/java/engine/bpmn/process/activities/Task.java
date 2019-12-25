package engine.bpmn.process.activities;

import engine.Action;
import engine.State;
import engine.bpmn.core.common.SequenceFlow;

import java.util.Date;
import java.util.List;

public abstract class Task extends Activity {

    protected String assignee;
    protected String owner;
    protected String skipExpression;
    protected String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSkipExpression() {
        return skipExpression;
    }

    public void setSkipExpression(String skipExpression) {
        this.skipExpression = skipExpression;
    }

    public void act(int action) throws Exception {
        super.act(action);
        setSTATE(State.INVOKED);

        // 再处理各个边
        List<SequenceFlow> edges = this.getOutgoing();

        if (null == edges) {
            return;
        }

        for (SequenceFlow edge : edges) {

            if (edge != null) {
                edge.act(Action.INVOKE);
            }
        }
    }
}
