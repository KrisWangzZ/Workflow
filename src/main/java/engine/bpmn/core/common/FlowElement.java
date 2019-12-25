package engine.bpmn.core.common;

import engine.Action;
import engine.State;
import engine.bpmn.core.foundation.BaseElement;

public abstract class FlowElement extends BaseElement {
    private String name;
    private volatile int STATE = State.INIT;

    public int getSTATE() {
        return STATE;
    }

    public void setSTATE(int STATE) {
        this.STATE = STATE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void act(int action) throws Exception{

        System.out.println(getId()+" -> " + (action == Action.INVOKE ? "invoke":"ignore" ));
    };

}
