package engine.core;

import engine.bpmn.core.common.FlowNode;

public interface Execution {
    String getId();
    boolean isSuspended();
    boolean isEnded();
    String getParentId();
    String getName();
}
