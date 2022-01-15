package expression;


public class LeftShift extends AbstractBinaryOperation implements BasicExpression{

    public LeftShift(BasicExpression op1, BasicExpression op2) {
        super(op1, op2);
    }

    @Override
    public int getPriority() {
        return 40;
    }

    @Override
    public String getSign() {
        return "<<";
    }

    @Override
    public Commutability getCommutability() {
        return Commutability.NONE;
    }



    @Override
    protected int getOperationResult(int x, int y) {
        return x << y;
    }
}
