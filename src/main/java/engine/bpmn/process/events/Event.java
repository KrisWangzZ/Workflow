package engine.bpmn.process.events;

import engine.bpmn.core.common.FlowNode;

import java.util.ArrayList;
import java.util.List;

public abstract class Event extends FlowNode {
    protected List<EventDefinition> eventDefinitions = new ArrayList<EventDefinition>();

    public List<EventDefinition> getEventDefinitions() {
        return eventDefinitions;
    }

    public void setEventDefinitions(List<EventDefinition> eventDefinitions) {
        this.eventDefinitions = eventDefinitions;
    }

    public void addEventDefinition(EventDefinition eventDefinition) {
        eventDefinitions.add(eventDefinition);
    }
}
