package markup;

public class Text implements Texts{
    private final String text;

    public Text(String str) {
        this.text = str;
    }
    
    public void toMarkdown(StringBuilder sb) {
        sb.append(text);
    }

    public void toBBCode(StringBuilder sb) {
        toMarkdown(sb);
    }
}
