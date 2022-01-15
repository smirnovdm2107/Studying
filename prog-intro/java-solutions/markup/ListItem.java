package markup;

import java.util.List;

public class ListItem {
    List<Itemable> texts;

    public ListItem(List <Itemable> texts) {
        this.texts = texts;
    }

    public void toBBCode(StringBuilder sb) {
        sb.append("[*]");
        for (Itemable text: texts) {
            text.toBBCode(sb);
        }
    }

}
