package expression.exceptions;

import expression.BasicExpression;
import expression.Negate;

public class CheckedNegate extends Negate {
    public CheckedNegate(BasicExpression op) {
        super(op);
    }

    @Override
    public int getOperationResult(int x) {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException("too small value for negate");
        }
        return -x;
    }

}
