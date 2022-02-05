package expression.exceptions;

public class StringCharSource implements CharSource {
    final String string;
    int pos;

    public StringCharSource(String string) {
        this.string = string;
        pos = 0;
    }

    @Override
    public boolean hasNext() {
        return pos < string.length();
    }

    @Override
    public char next() {
        return string.charAt(pos++);
    }


    @Override
    public ExpressionSyntaxExpression error(String message) {
        return new ExpressionSyntaxExpression(String.format("%Error on pos d : %s", pos, message));
    }

    public int getPos() {
        return pos;
    }
}
