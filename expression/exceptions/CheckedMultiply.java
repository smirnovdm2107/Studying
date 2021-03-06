package expression.exceptions;

import expression.BasicExpression;
import expression.Multiply;

public class CheckedMultiply extends Multiply implements BasicExpression {

    public CheckedMultiply(BasicExpression op1, BasicExpression op2) {
        super(op1, op2);
    }

    @Override
    protected int getOperationResult(int x, int y) {
        if (x == 0 || y == 0) {
            return 0;
        }
        if (x == -1 && y == Integer.MIN_VALUE || x == Integer.MIN_VALUE && y == -1
            || x < -1 && (Integer.MAX_VALUE / x > y || Integer.MIN_VALUE / x < y)
            || x > 0 && (Integer.MAX_VALUE / x < y || Integer.MIN_VALUE / x > y)) {
            throw new OverflowException(String.format("Overflow in Multiply: trying with %d and %d", x, y));
        }
        return x * y;
    }
}
