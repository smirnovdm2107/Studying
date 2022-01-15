package markup;

import java.util.List;

public class Head extends AbstractMarkup implements Itemable{
    private int level = 0;

    public Head(List<Texts> texts, int level) {
        super(texts);
        this.level = level;
    }


    public void toBBCode(StringBuilder sb) {
        toBBCode(sb, "", "");
    }

    public void toMarkdown(StringBuilder sb) {
        toMarkdown(sb, "");
    }

    public void toHtml(StringBuilder sb) {
        toHtml(sb, "<h" + level + ">", "</h" + level + ">");
    }
}
