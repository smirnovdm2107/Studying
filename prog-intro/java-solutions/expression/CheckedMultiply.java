package expression;

import expression.exceptions.OverflowException;

public class CheckedMultiply extends Multiply implements BasicExpression{

    public CheckedMultiply(BasicExpression op1, BasicExpression op2) {
        super(op1, op2);
    }

    @Override
    protected int getOperationResult(int x, int y) {
        if (x == 0) {
            return 0;
        }
        if (Integer.MAX_VALUE / x < y || Integer.MIN_VALUE / x > y
                || x == -1 && y == Integer.MIN_VALUE || x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowException(String.format("Overflow in Multiply: trying with %d and %d", x, y));
        }
        return x * y;
    }
}
