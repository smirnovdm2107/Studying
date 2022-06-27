package expression.generic.expression;

import expression.generic.evaluator.BasicEvaluator;

public class Min<T extends Number> extends AbstractBinaryOperation<T> implements BasicExpression<T> {
    public Min(BasicExpression<T> op1, BasicExpression<T> op2, BasicEvaluator<T> evaluator) {
        super(op1, op2, evaluator);
    }

    @Override
    public int getPriority() {
        return 40;
    }

    @Override
    public String getSign() {
        return "min";
    }

    @Override
    public Commutability getCommutability() {
        return Commutability.INNER;
    }

    @Override
    protected T getOperationResult(T x, T y) {
       return evaluator.evaluateMin(x, y);
    }
}
