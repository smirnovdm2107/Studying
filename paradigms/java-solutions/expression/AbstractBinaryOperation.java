package expression;

public abstract class AbstractBinaryOperation implements BasicExpression {
    protected final BasicExpression op1;
    protected final BasicExpression op2;

    public AbstractBinaryOperation(BasicExpression op1, BasicExpression op2)  {
        this.op1 = op1;
        this.op2 = op2;
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

    private void append(final boolean condition, final StringBuilder sb, final BasicExpression op) {
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

    private boolean isCommutable(BasicExpression op) {
        return getCommutability() == Commutability.FULL && (op.getCommutability() == Commutability.OUTER
                || op.getCommutability() == Commutability.FULL)
                || op.getCommutability() == Commutability.INNER && this.getClass() == op.getClass();
    }


    public int evaluate(int x) {
        return getOperationResult(op1.evaluate(x), op2.evaluate(x));
    }

    public int evaluate(int x, int y, int z) {
        return getOperationResult(op1.evaluate(x, y, z), op2.evaluate(x, y, z));
    }

    protected abstract int getOperationResult(int x, int y);

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
