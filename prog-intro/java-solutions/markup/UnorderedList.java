package markup;

import java.util.List;

public class UnorderedList extends AbstractList implements Itemable{

    public UnorderedList(List<ListItem> texts) {
        super(texts);
    }

    public void toBBCode(StringBuilder sb) {
        super.toBBCode(sb, "[list]", "[/list]");
    }

    public void toHtml(StringBuilder sb) {}
}
