package engine.bpmn.process.activities;

import engine.Action;
import engine.State;
import engine.core.JavaDelegate;
import engine.service.impl.MemRuntimeService;
import org.springframework.util.Assert;

public class UserTask extends Task {
    private String className;
    @Override
    public void act(int action) throws Exception {
        if(action == Action.INVOKE){
            super.act(action);


        }else if(action == Action.PRE_INVOKE){
            setSTATE(State.PRE_INVOKED);
        }
    }






}
