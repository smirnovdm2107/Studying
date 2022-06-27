package expression.generic.expression;


import expression.generic.evaluator.BasicEvaluator;

public abstract class AbstractUnaryOperation<T extends Number> implements BasicExpression<T> {
    protected final BasicExpression<T> op;
    protected final BasicEvaluator<T> evaluator;

    public AbstractUnaryOperation(BasicExpression<T> op, BasicEvaluator<T> evaluator) {
        this.op = op;
        this.evaluator = evaluator;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getSign()).append('(').append(op.toString()).append(')');
        return sb.toString();
    }


    public String toMiniString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getSign());
        if (getPriority() < op.getPriority() || op.getPriority() == getPriority() && !isCommutable(op)) {
            sb.append('(').append(op.toMiniString()).append(')');
        } else {
            sb.append(' ').append(op.toMiniString());
        }
        return sb.toString();
    }

    private boolean isCommutable(BasicExpression<T> op) {
        return op.getCommutability() == Commutability.FULL;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return getOperationResult(op.evaluate(x, y, z));
    }

    protected abstract T getOperationResult(T x);

    public Commutability getCommutability() {
        return Commutability.FULL;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == this.getClass()) {
            AbstractUnaryOperation<?> that = (AbstractUnaryOperation<?>) obj;
            return (that.op.equals(op));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return op.hashCode() * 349 + getSign().hashCode() * 103;
    }

}
