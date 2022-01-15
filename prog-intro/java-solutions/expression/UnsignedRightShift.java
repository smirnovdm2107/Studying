package expression;

import java.math.BigInteger;

public class UnsignedRightShift extends AbstractBinaryOperation implements BasicExpression {
    @Override
    public String getSign() {
        return ">>>";
    }

    @Override
    public int getPriority() {
        return 40;
    }

    @Override
    public Commutability getCommutability() {
        return Commutability.NONE;
    }

    public UnsignedRightShift(BasicExpression op1, BasicExpression op2) {
        super(op1, op2);
    }


    @Override
    protected int getOperationResult(int x, int y) {
        return x >>> y;
    }
}
