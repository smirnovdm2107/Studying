package expression;

import expression.exceptions.OverflowException;

public class CheckedSubtract extends Subtract implements BasicExpression{

    public CheckedSubtract(BasicExpression op1, BasicExpression op2) {
        super(op1, op2);
    }


    @Override
    protected int getOperationResult(int x, int y) {
        if (x > 0 && y < 0) {
            if (Integer.MAX_VALUE + y > x) {
                throw new OverflowException(String.format("Too large value in subtract: trying with %d and %d", x, y));
            }
        } else if (x < 0 && y > 0){
            if (Integer.MIN_VALUE + y < x) {
                throw new OverflowException(String.format("Too low value in subtract: trying with %d and %d", x, y));
            }
        }
        return x - y;
    }
}
