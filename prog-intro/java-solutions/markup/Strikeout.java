package markup;

import java.util.List;

public class Strikeout extends AbstractMarkup implements Texts{
    public Strikeout(List<Texts> texts) {
        super(texts);
    }

    public void toBBCode(StringBuilder sb) {
        super.toBBCode(sb, "[s]", "[/s]");
    }

    public void toMarkdown(StringBuilder sb) {
        super.toMarkdown(sb, "~");
    }

    public void toHtml(StringBuilder sb) {
        toHtml(sb, "<s>", "</s>");
    }
}
