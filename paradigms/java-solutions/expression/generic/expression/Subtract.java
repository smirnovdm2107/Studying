package expression.generic.expression;

import expression.generic.evaluator.BasicEvaluator;

public class Subtract<T extends Number> extends AbstractBinaryOperation<T> implements BasicExpression<T> {

    @Override
    public int getPriority() {
        return 30;
    }

    @Override
    public String getSign() {
        return "-";
    }

    @Override
    public Commutability getCommutability() {
        return Commutability.OUTER;
    }

    public Subtract(BasicExpression<T> op1, BasicExpression<T> op2, BasicEvaluator<T> evaluator) {
        super(op1, op2, evaluator);
    }

    protected T getOperationResult(T x, T y) {
        return evaluator.evaluateSubtract(x, y);
    }

}
