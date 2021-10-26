package markup;
import java.util.List;

public abstract class AbstractMarkup  {
    protected List<Texts> texts;
    public AbstractMarkup(List <Texts> texts) {
        this.texts = texts;
    }

    public void toMarkdown(StringBuilder sb, String extra) {
        sb.append(extra);
        for (Texts text: texts) {
            text.toMarkdown(sb);
        }
        sb.append(extra);
    }

}
