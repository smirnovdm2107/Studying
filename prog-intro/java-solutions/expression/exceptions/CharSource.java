package expression.exceptions;

public interface CharSource {
    char next();
    boolean hasNext();
    int getPos();
    ExpressionSyntaxExpression error(String message);
}
