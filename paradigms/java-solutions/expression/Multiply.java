package expression;


import java.math.BigInteger;

public class Multiply extends AbstractBinaryOperation implements ExtendedExpression {

    public Multiply(BasicExpression op1, BasicExpression op2) {
        super(op1, op2);
    }
    @Override
    public int getPriority() {
        return 20;
    }

    @Override
    public String getSign() {
        return "*";
    }

    @Override
    public Commutability getCommutability() {
        return Commutability.FULL;
    }

    @Override
    public BigInteger evaluate(BigInteger x) {
        return ((ExtendedExpression) op1).evaluate(x).multiply(((ExtendedExpression) op2).evaluate(x));
    }

    @Override
    protected int getOperationResult(int x, int y) {
        return x * y;
    }
}
