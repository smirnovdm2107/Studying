package markup;


import java.util.List;

public class Strong extends AbstractMarkup implements Texts{

    public Strong(List<Texts> texts) {
       super(texts);
    }

    public void toBBCode(StringBuilder sb) {
        super.toBBCode(sb, "[b]", "[/b]");
    }

    public void toMarkdown(StringBuilder sb) {
        super.toMarkdown(sb, "__");
    }

    public void toHtml(StringBuilder sb) {
        toHtml(sb, "<strong>", "</strong>");
    }
}
