package expression.generic.expression;


import expression.generic.evaluator.BasicEvaluator;

public class TZeroes extends AbstractUnaryOperation<Integer> implements BasicExpression<Integer> {

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public String getSign() {
        return "t0";
    }

    public TZeroes(BasicExpression<Integer> op, BasicEvaluator<Integer> evaluator) {
        super(op, evaluator);
    }


    protected Integer getOperationResult(Integer x) {
        return Integer.numberOfTrailingZeros(x);
    }

}
