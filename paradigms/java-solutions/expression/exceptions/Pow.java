package expression.exceptions;

import expression.AbstractBinaryOperation;
import expression.BasicExpression;
import expression.Commutability;

public class Pow extends AbstractBinaryOperation {
    public Pow(BasicExpression op1, BasicExpression op2) {
        super(op1, op2);
    }



    @Override
    protected int getOperationResult(int x, int y) {
        if (y < 0) {
            throw new ExpressionSyntaxException(
                    String.format("impossible operation value in pow: trying with %d, %d", x, y)
            );
        } else if (y == 0) {
            if (x == 0) {
                throw new ExpressionSyntaxException(
                        String.format("undefined operation result in pow: trying with %d, %d", x, y)
                );
            }
            return 1;
        } else if (y == 1) {
            return x;
        }
        if (x < 0 && Integer.MAX_VALUE / x > x || x > 0 && Integer.MAX_VALUE / x < x) {
            throw new ExpressionSyntaxException("overflow in pow operation");
        }
        if (y % 2 == 0) {
            return getOperationResult(x * x, y / 2);
        } else {
            int result = getOperationResult(x * x, (y - 1) / 2);
            if (x != -1 && (x < 0 && (Integer.MIN_VALUE / x < result || Integer.MAX_VALUE / x > result)
                || (x > 0 && (Integer.MIN_VALUE / x > result  || Integer.MAX_VALUE / x < result)))
            ) {
               throw new ExpressionSyntaxException("overflow in pow operation");
            }
            return result * x;
        }
    }

    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    public String getSign() {
        return "**";
    }

    @Override
    public Commutability getCommutability() {
        return Commutability.NONE;
    }
}
