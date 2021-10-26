package markup;

import java.util.List;

public class Emphasis extends AbstractMarkup implements Texts{
    public Emphasis(List<Texts> texts) {
        super(texts);
    }
    @Override
    public void  toMarkdown(StringBuilder sb) {
        super.toMarkdown(sb, "*");
    }


}
