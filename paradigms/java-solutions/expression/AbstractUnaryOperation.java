package expression;


public abstract class AbstractUnaryOperation implements BasicExpression {
    protected final BasicExpression op;

    public AbstractUnaryOperation(BasicExpression op) {
        this.op = op;
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

    private boolean isCommutable(BasicExpression op) {
        return op.getCommutability() == Commutability.FULL;
    }


    @Override
    public int evaluate(int x) {
        return getOperationResult(op.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return getOperationResult(op.evaluate(x, y, z));
    }

    protected abstract int getOperationResult(int x);

    public Commutability getCommutability() {
        return Commutability.FULL;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == this.getClass()) {
            AbstractUnaryOperation that = (AbstractUnaryOperation) obj;
            return (that.op.equals(op));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return op.hashCode() * 349 + getSign().hashCode() * 103;
    }

}
