package engine.bpmn.core.common;

import engine.bpmn.core.foundation.BaseElement;

public abstract class Expression extends BaseElement {
    private String type;

    private String expression;

    public void setType(String type) {
        this.type = type;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getType() {
        return type;
    }

    public String getExpression() {
        return expression;
    }
}
