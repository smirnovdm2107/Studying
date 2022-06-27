package expression.exceptions;

import expression.AbstractUnaryOperation;
import expression.BasicExpression;

public class Abs extends AbstractUnaryOperation {

    public Abs(BasicExpression op) {
        super(op);
    }

    @Override
    public String getSign() {
        return "abs";
    }

    @Override
    protected int getOperationResult(int x) {
        if (x == Integer.MIN_VALUE) {
            throw new ExpressionSyntaxException(String.format("too small value for abs: %x", x));
        }
        if (x > 0) {
            return x;
        } else {
            return -x;
        }
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
