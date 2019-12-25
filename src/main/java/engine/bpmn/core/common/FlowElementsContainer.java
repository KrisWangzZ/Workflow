package engine.bpmn.core.common;

import engine.bpmn.core.foundation.BaseElement;
import engine.bpmn.process.LaneSet;

public abstract class FlowElementsContainer extends BaseElement {
    protected  String name;
    private FlowElement[] flowElements;
    private LaneSet[] laneSets;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FlowElement[] getFlowElements() {
        return flowElements;
    }

    public void setFlowElements(FlowElement[] flowElements) {
        this.flowElements = flowElements;
    }

    public LaneSet[] getLaneSets() {
        return laneSets;
    }

    public void setLaneSets(LaneSet[] laneSets) {
        this.laneSets = laneSets;
    }
}
