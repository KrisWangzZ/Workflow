package engine.bpmn.core.foundation;

public class Documentation extends BaseElement {

    private String text;
    private String textFormat;
    public Documentation(String text, String textFormat) {
        this.text = text;
        this.textFormat = textFormat;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextFormat() {
        return textFormat;
    }

    public void setTextFormat(String textFormat) {
        this.textFormat = textFormat;
    }
}
