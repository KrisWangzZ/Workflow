package engine.bpmn.core.common;

import engine.bpmn.core.foundation.RootElement;

public abstract class Resource extends RootElement {
    private String name;
    private ResourceParameter[] resourceParameters;
}
