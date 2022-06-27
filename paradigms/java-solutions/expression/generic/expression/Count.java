package expression.generic.expression;

import expression.generic.evaluator.BasicEvaluator;

public class Count<T extends Number> extends AbstractUnaryOperation<T> implements BasicExpression<T>{
    public Count(BasicExpression<T> op, BasicEvaluator<T> evaluator) {
        super(op, evaluator);
    }

    @Override
    protected T getOperationResult(T x) {
        return evaluator.evaluateCount(x);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public String getSign() {
        return "count";
    }
}
