package expression.exceptions;

public class DivideByZeroException extends ArithmeticException {
    public DivideByZeroException(final String message) {
        super(message);
    }
}
