package expression.exceptions;

import expression.BasicExpression;
import expression.Divide;

public class CheckedDivide extends Divide implements BasicExpression {

    public CheckedDivide(BasicExpression op1, BasicExpression op2) {
        super(op1, op2);
    }

    @Override
    protected int getOperationResult(int x, int y) {
        if (y == 0) {
            throw new DivideByZeroException("Division by zero");
        }
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowException(String.format("Overflow in Divide: trying with %d and %d", x, y));
        }
        return x / y;
    }


}
