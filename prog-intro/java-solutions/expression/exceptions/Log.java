package expression.exceptions;

import expression.AbstractBinaryOperation;
import expression.BasicExpression;
import expression.Commutability;

public class Log extends AbstractBinaryOperation {
    public Log(BasicExpression op1, BasicExpression op2) {
        super(op1, op2);
    }

    @Override
    protected int getOperationResult(int x, int y) {
        if (y <= 0 || x <= 0 || y == 1) {
            throw new ArithmeticException(
                    String.format("impossible values in log operation: trying with %d, %d", x, y)
            );
        } else {
            int result = 0;
            int temp = 1;
            while(temp <= x / y) {
                temp *= y;
                result++;
            }
            return result;
        }
    }

    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    public String getSign() {
        return "//";
    }

    @Override
    public Commutability getCommutability() {
        return Commutability.NONE;
    }
}
