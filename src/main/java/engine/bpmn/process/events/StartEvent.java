package engine.bpmn.process.events;

import engine.Action;
import engine.State;
import engine.bpmn.core.common.SequenceFlow;
import org.springframework.util.Assert;

import java.util.List;

public class StartEvent extends CatchEvent {
    private boolean isInterrupting = true;

    @Override
    public void act(int action) throws Exception {
        super.act(action);
        Assert.isTrue(action == Action.INVOKE, "Error");
        setSTATE(State.INVOKED);

        List<SequenceFlow> edges = this.outgoing;

        for(SequenceFlow e : edges){

            e.act(Action.INVOKE);

        }
    }
}
