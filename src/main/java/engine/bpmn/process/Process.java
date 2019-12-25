package engine.bpmn.process;

import engine.bpmn.core.common.FlowElementsContainer;

public class Process extends FlowElementsContainer {
    private ProcessType processType;
    private boolean isExecutable;
    private boolean isClosed;
    private String ProcessCreater;

    public String getProcessCreater() {
        return ProcessCreater;
    }

    public void setProcessCreater(String creater) {
        this.ProcessCreater = creater;
    }

    public ProcessType getProcessType() {
        return processType;
    }

    public void setProcessType(ProcessType processType) {
        this.processType = processType;
    }

    public boolean isExecutable() {
        return isExecutable;
    }

    public void setExecutable(boolean executable) {
        isExecutable = executable;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }
}
