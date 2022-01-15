package markup;

import java.util.List;
public class Paragraph extends AbstractMarkup implements Itemable{

    public Paragraph(List <Texts> texts) {
        super(texts);
    }


    public void toBBCode(StringBuilder sb) {
        toBBCode(sb, "", "");
    }

    public void toMarkdown(StringBuilder sb) {
        toMarkdown(sb, "");
    }

    public void toHtml(StringBuilder sb) {
        toHtml(sb, "<p>", "</p>");
    }
}