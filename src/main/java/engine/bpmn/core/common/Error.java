package engine.bpmn.core.common;

import engine.bpmn.core.foundation.RootElement;

public class Error extends RootElement {
    private String name;
    private String errorCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
