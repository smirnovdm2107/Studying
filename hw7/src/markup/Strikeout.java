package markup;

import java.util.List;

public class Strikeout extends AbstractMarkup implements Texts{
    public Strikeout(List<Texts> texts) {
        super(texts);
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        super.toMarkdown(sb, "~");
    }
}
