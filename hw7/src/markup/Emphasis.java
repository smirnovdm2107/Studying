package markup;

import java.util.List;

public class Emphasis extends AbstractMarkup implements Texts{
    public Emphasis(List<Texts> texts) {
        super(texts);
    }

    public void toBBCode(StringBuilder sb) {
        super.toBBCode(sb, "[i]", "[/i]");
    }

    public void toMarkdown(StringBuilder sb) {
        super.toMarkdown(sb, "*");
    }
}
