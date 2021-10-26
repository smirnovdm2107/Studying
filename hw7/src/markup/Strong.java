package markup;


import java.util.List;

public class Strong extends AbstractMarkup implements Texts{

    public Strong(List<Texts> texts) {
       super(texts);
    }

    @Override
   public void toMarkdown(StringBuilder sb) {
        super.toMarkdown(sb, "__");
    }


}
