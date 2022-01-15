package expression;


import expression.exceptions.OverflowException;

public class CheckedAdd extends Add implements BasicExpression {

    public CheckedAdd(BasicExpression op1, BasicExpression op2) {
        super(op1, op2);
    }


    @Override
    protected int getOperationResult(int x, int y) {
        if (x > 0 && y > 0) {
            if (Integer.MAX_VALUE - x < y) {
                throw new OverflowException(String.format("Too large value in adding: trying with %d and %d", x, y));
            }
        } else if (x < 0 && y < 0) {
            if (Integer.MIN_VALUE - x < y) {
                throw new OverflowException(String.format("Too low value in adding: trying with %d and %d", x, y));
            }
        }
        return x + y;
    }

}
