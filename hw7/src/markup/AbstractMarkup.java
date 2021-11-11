package markup;
import java.util.List;

public abstract class AbstractMarkup  {
    protected List<Texts> texts;
    public AbstractMarkup(List <Texts> texts) {
        this.texts = texts;
    }

    public void toBBCode(StringBuilder sb, String extraOpen, String extraClose) {
        sb.append(extraOpen);
        for (Texts text: texts) {
            text.toBBCode(sb);
        }
        sb.append(extraClose);
    }

    public void toMarkdown(StringBuilder sb, String extra) {
        sb.append(extra);
        for (Texts text: texts) {
            text.toMarkdown(sb);
        }
        sb.append(extra);
    }

}
