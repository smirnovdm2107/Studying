package markup;

import java.util.List;

public abstract class AbstractAbstractMarkup {

    protected List<Texts> texts;

    public void toMarkdown(StringBuilder sb) {
        for (Texts text : texts) {
            text.toMarkdown(sb);
        }
    }
}
