package markup;

import java.util.List;
public class Paragraph extends AbstractMarkup implements Itemable{

    public Paragraph(List <Texts> texts) {
        super(texts);
    }

    @Override
    public void toBBCode(StringBuilder sb) {
        super.toBBCode(sb, "", "");
    }

    public void toMarkdown(StringBuilder sb) {
        super.toMarkdown(sb, "");
    }
}