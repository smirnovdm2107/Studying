package expression.generic.expression;

import expression.generic.evaluator.BasicEvaluator;

public class Multiply<T extends Number> extends AbstractBinaryOperation<T> implements BasicExpression<T> {

    public Multiply(BasicExpression<T> op1, BasicExpression<T> op2, BasicEvaluator<T> evaluator) {
        super(op1, op2, evaluator);
    }
    @Override
    public int getPriority() {
        return 20;
    }

    @Override
    public String getSign() {
        return "*";
    }

    @Override
    public Commutability getCommutability() {
        return Commutability.FULL;
    }

    @Override
    protected T getOperationResult(T x, T y) {
        return evaluator.evaluateMultiply(x, y);
    }
}
