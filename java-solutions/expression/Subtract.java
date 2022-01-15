package expression;

import java.math.BigInteger;

public class Subtract extends AbstractBinaryOperation implements ExtendedExpression {

    @Override
    public int getPriority() {
        return 30;
    }

    @Override
    public String getSign() {
        return "-";
    }

    @Override
    public Commutability getCommutability() {
        return Commutability.OUTER;
    }

    public Subtract(BasicExpression op1, BasicExpression op2) {
        super(op1, op2);
    }

    protected int getOperationResult(int x, int y) {
        return x - y;
    }

    public BigInteger evaluate(BigInteger x) {
        return ((ExtendedExpression) op1).evaluate(x).subtract(((ExtendedExpression) op2).evaluate(x));
    }


}
