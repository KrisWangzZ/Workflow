package engine.bpmn.process.events;

import engine.Action;
import engine.State;
import org.springframework.util.Assert;

public class EndEvent extends ThrowEvent {
    @Override
    public void act(int action) throws Exception {
        super.act(action);
        Assert.isTrue(action == Action.INVOKE, "Error");
        setSTATE(State.INVOKED);
    }
}
