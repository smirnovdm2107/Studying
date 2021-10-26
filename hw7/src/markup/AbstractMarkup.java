package markup;
import java.util.List;

public abstract class AbstractMarkup extends AbstractAbstractMarkup {

    public AbstractMarkup(List <Texts> texts) {
        super.texts = texts;
    }


    public void toMarkdown(StringBuilder sb, String extra) {
        sb.append(extra);
        super.toMarkdown(sb);
        sb.append(extra);
    }

}
