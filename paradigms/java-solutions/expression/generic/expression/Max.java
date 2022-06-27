package expression.generic.expression;

import expression.generic.evaluator.BasicEvaluator;

public class Max<T extends Number> extends AbstractBinaryOperation<T> implements BasicExpression<T> {

    public Max(BasicExpression<T> op1, BasicExpression<T> op2, BasicEvaluator<T> evaluator) {
        super(op1, op2, evaluator);
    }

    @Override
    public int getPriority() {
        return 40;
    }

    @Override
    public String getSign() {
        return "max";
    }

    @Override
    public Commutability getCommutability() {
        return Commutability.INNER;
    }



    @Override
    protected T getOperationResult(T x, T y) {
        return evaluator.evaluateMax(x, y);
    }
}
