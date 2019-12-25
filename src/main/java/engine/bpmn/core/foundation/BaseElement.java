package engine.bpmn.core.foundation;

import java.util.Date;

public abstract class BaseElement {

    private String id;

    protected Date time;

    private Documentation[] documentation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Documentation[] getDocumentation() {
        return documentation;
    }

    public void setDocumentation(Documentation[] documentation) {
        this.documentation = documentation;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
