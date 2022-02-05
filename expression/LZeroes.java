package expression;



public class LZeroes extends AbstractUnaryOperation implements BasicExpression{
    public LZeroes(BasicExpression op) {
        super(op);
    }


    protected int getOperationResult(int x) {
        return Integer.numberOfLeadingZeros(x);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public String getSign() {
        return "l0";
    }

    @Override
    public Commutability getCommutability() {
        return Commutability.FULL;
    }

}
