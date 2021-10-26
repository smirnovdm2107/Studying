package markup;

import java.util.List;
public class Paragraph extends AbstractMarkup{

    public Paragraph(List <Texts> texts) {
        super(texts);
    }

    public void toMarkdown(StringBuilder sb) {
        super.toMarkdown(sb, "");
    }

}