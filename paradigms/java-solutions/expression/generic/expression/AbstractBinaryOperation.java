package expression.generic.expression;

import expression.generic.evaluator.BasicEvaluator;

public abstract class AbstractBinaryOperation<T extends Number> implements BasicExpression<T> {
    protected final BasicExpression<T> op1;
    protected final BasicExpression<T> op2;
    protected final BasicEvaluator<T> evaluator;

    public AbstractBinaryOperation(BasicExpression<T> op1, BasicExpression<T> op2, BasicEvaluator<T> evaluator)  {
        this.op1 = op1;
        this.op2 = op2;
        this.evaluator = evaluator;
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(')
                .append(op1.toString())
                .append(' ')
                .append(getSign()).append(' ')
                .append(op2.toString())
                .append(')');
        return sb.toString();
    }

    private void append(final boolean condition, final StringBuilder sb, final BasicExpression<T> op) {
        if (condition) {
            sb.append('(')
                    .append(op.toMiniString())
                    .append(')');
        } else {
            sb.append(op.toMiniString());
        }
    }

    public String toMiniString() {
        StringBuilder sb = new StringBuilder();
        append(op1.getPriority() > getPriority(), sb, op1);
        sb.append(' ')
                .append(getSign())
                .append(' ');
        append(op2.getPriority() > getPriority() || op2.getPriority() == getPriority() && !isCommutable(op2),
                sb, op2
        );
        return sb.toString();
    }

    private boolean isCommutable(BasicExpression<T> op) {
        return getCommutability() == Commutability.FULL && (op.getCommutability() == Commutability.OUTER
                || op.getCommutability() == Commutability.FULL)
                || op.getCommutability() == Commutability.INNER && this.getClass() == op.getClass();
    }

    public T evaluate(T x, T y, T z) {
        return getOperationResult(op1.evaluate(x, y, z), op2.evaluate(x, y, z));
    }

    protected abstract T getOperationResult(T x, T y);

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == this.getClass()) {
            AbstractBinaryOperation that = (AbstractBinaryOperation) obj;
            return that.op1.equals(op1) && that.op2.equals(op2);
        }
        return false;
    }

    public abstract int getPriority();

    @Override
    public int hashCode() {
        return op1.hashCode() * 997 + getSign().hashCode() * 479 + op2.hashCode() * 67;
    }


}
