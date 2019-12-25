package engine.bpmn.core.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class FlowNode extends FlowElement {

    protected List<SequenceFlow> incoming = new ArrayList<SequenceFlow>();
    protected List<SequenceFlow> outgoing = new ArrayList<SequenceFlow>();

    public List<SequenceFlow> getIncoming() {
        return incoming;
    }

    public List<SequenceFlow> getOutgoing() {
        return outgoing;
    }

    public void setIncoming(List<SequenceFlow> incoming) {
        this.incoming = incoming;
    }

    public void setOutgoing(List<SequenceFlow> outgoing) {
        this.outgoing = outgoing;
    }

    public void addIncoming(SequenceFlow e) {
        incoming.add(e);
    }

    public void addOutcoming(SequenceFlow e){
        outgoing.add(e);
    }

}
