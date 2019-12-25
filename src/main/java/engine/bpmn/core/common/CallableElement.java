package engine.bpmn.core.common;

import engine.bpmn.core.foundation.RootElement;

public abstract class CallableElement extends RootElement {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
