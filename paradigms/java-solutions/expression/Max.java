package expression;

import java.math.BigInteger;

public class Max extends AbstractBinaryOperation implements BasicExpression{

    public Max(BasicExpression op1, BasicExpression op2) {
        super(op1, op2);
    }

    @Override
    public int getPriority() {
        return 40;
    }

    @Override
    public String getSign() {
        return "max";
    }

    @Override
    public Commutability getCommutability() {
        return Commutability.INNER;
    }



    @Override
    protected int getOperationResult(int x, int y) {
        if (x > y) {
            return x;
        } else {
            return y;
        }
    }
}
