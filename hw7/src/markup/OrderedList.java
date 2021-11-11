package markup;

import java.util.List;

public class OrderedList extends AbstractList implements Itemable{

    public OrderedList(List<ListItem> texts) {
        super(texts);
    }

    public void toBBCode(StringBuilder sb) {
        super.toBBCode(sb, "[list=1]", "[/list]");
    }

}
