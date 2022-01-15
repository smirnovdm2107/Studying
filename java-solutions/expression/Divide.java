package expression;

import java.math.BigInteger;

public class Divide extends AbstractBinaryOperation implements ExtendedExpression {

    @Override
    public int getPriority() {
        return 20;
    }

    @Override
    public String getSign() {
        return "/";
    }

    @Override
    public Commutability getCommutability() {
        return Commutability.NONE;
    }

    public Divide(BasicExpression op1, BasicExpression op2) {
        super(op1, op2);
    }

    public BigInteger evaluate(BigInteger x) {
        return ((ExtendedExpression) op1).evaluate(x).divide(((ExtendedExpression) op2).evaluate(x));
    }

    @Override
    protected int getOperationResult(int x, int y) {
        return x / y;
    }
}
