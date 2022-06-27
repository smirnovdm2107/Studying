package expression.generic.expression;

import expression.generic.evaluator.BasicEvaluator;

public class Add<T extends Number> extends AbstractBinaryOperation<T> implements BasicExpression<T> {
    public Add(BasicExpression<T> op1, BasicExpression<T> op2, BasicEvaluator<T> evaluator) {
        super(op1, op2, evaluator);
    }

    protected T getOperationResult(T x, T y) {
        return evaluator.evaluateAdd(x, y);
    }

    @Override
    public int getPriority() {
        return 30;
    }

    @Override
    public Commutability getCommutability() {
        return Commutability.FULL;
    }

    @Override
    public String getSign() {
        return "+";
    }
}
