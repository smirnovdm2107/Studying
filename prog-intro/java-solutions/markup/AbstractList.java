package markup;

import java.util.List;

public class AbstractList {

    List <ListItem> texts;
    public AbstractList(List<ListItem> texts) {
        this.texts = texts;
    }

    public void toBBCode(StringBuilder sb, String extraOpen, String extraClose) {
        sb.append(extraOpen);
        for (ListItem text: texts) {
            text.toBBCode(sb);
        }
        sb.append(extraClose);
    }


}
