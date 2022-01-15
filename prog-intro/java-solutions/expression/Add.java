package expression;

import java.math.BigInteger;

public class Add extends AbstractBinaryOperation implements ExtendedExpression {
    public Add(BasicExpression op1, BasicExpression op2) {
        super(op1, op2);
    }

    protected int getOperationResult(int x, int y) {
        return x + y;
    }

    @Override
    public int getPriority() {
        return 30;
    }

    public BigInteger evaluate(BigInteger x) {
        return ((ExtendedExpression) op1).evaluate(x).add(((ExtendedExpression) op2).evaluate(x));
    }

    @Override
    public Commutability getCommutability() {
        return Commutability.FULL;
    }

    @Override
    public String getSign() {
        return "+";
    }
}
