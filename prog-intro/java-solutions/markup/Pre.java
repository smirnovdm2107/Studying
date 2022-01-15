package markup;

public class Pre implements Texts {
    String text;
    public Pre(String text){
        this.text = text;
    }

    public void toMarkdown(StringBuilder sb) {
        sb.append("```");
        sb.append(text);
        sb.append("```");
    }
    public void toHtml(StringBuilder sb){
        sb.append("<pre>");
        sb.append(text);
        sb.append("</pre>");
    }

    public void toBBCode(StringBuilder sb) {
        sb.append(text);
    }

}
