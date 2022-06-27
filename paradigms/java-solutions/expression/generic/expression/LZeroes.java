package expression.generic.expression;

import expression.generic.evaluator.BasicEvaluator;

public class LZeroes extends AbstractUnaryOperation<Integer> implements BasicExpression<Integer> {
    public LZeroes(BasicExpression<Integer> op, BasicEvaluator<Integer> evaluator) {
        super(op, evaluator);
    }


    protected Integer getOperationResult(Integer x) {
        return Integer.numberOfLeadingZeros(x);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public String getSign() {
        return "l0";
    }

    @Override
    public Commutability getCommutability() {
        return Commutability.FULL;
    }

}
