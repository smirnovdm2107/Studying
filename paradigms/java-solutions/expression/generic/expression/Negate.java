package expression.generic.expression;

import expression.generic.evaluator.BasicEvaluator;

public class Negate<T extends Number> extends AbstractUnaryOperation<T> implements BasicExpression<T> {


    public Negate(BasicExpression<T> op, BasicEvaluator<T> evaluator) {
        super(op, evaluator);
    }


    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public String getSign() {
        return "-";
    }

    protected T getOperationResult(T x) {
        return evaluator.evaluateNegate(x);
    }
    

}
