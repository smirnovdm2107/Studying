package expression;


public class Negate extends AbstractUnaryOperation implements BasicExpression {


    public Negate(BasicExpression op) {
        super(op);
    }


    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public String getSign() {
        return "-";
    }

    protected int getOperationResult(int x) {
        return -x;
    }
    

}
