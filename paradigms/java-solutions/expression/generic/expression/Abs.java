package expression.generic.expression;


import expression.generic.evaluator.BasicEvaluator;

public class Abs<T extends Number> extends AbstractUnaryOperation<T> {

    public Abs(BasicExpression<T> op, BasicEvaluator<T> evaluator) {
        super(op, evaluator);
    }

    @Override
    public String getSign() {
        return "abs";
    }

    @Override
    protected T getOperationResult(T x) {
       return evaluator.evaluateAbs(x);
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
