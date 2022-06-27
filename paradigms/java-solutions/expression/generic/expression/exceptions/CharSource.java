package expression.generic.expression.exceptions;

public interface CharSource {
    char next();
    boolean hasNext();
    int getPos();
    IllegalArgumentException error(String message);
}
