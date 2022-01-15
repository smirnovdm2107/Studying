package expression;


public class TZeroes extends AbstractUnaryOperation implements BasicExpression{

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public String getSign() {
        return "t0";
    }

    public TZeroes(BasicExpression op) {
        super(op);
    }


    protected int getOperationResult(int x) {
        return Integer.numberOfTrailingZeros(x);
    }

}
