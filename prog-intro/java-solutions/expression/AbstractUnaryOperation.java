package expression;


public abstract class AbstractUnaryOperation implements BasicExpression {
    protected final BasicExpression op;

    public AbstractUnaryOperation(BasicExpression op) {
        this.op = op;
    }

    @Override
    public String toString() {
        return buildString(new StringBuilder()).toString();
    }


    public String toMiniString() {
        return buildMiniString(new StringBuilder()).toString();
    }

    @Override
    public StringBuilder buildMiniString(StringBuilder sb) {
        sb.append(getSign());
        if (getPriority() < op.getPriority() || op.getPriority() == getPriority() && !isCommutable(op)) {
            sb.append('(');
            op.buildMiniString(sb);
            sb.append(')');
        } else {
            sb.append(' ');
            op.buildMiniString(sb);
        }
        return sb;
    }

    @Override
    public StringBuilder buildString(StringBuilder sb) {
        sb.append(getSign());
        sb.append('(');
        op.buildString(sb);
        sb.append(')');
        return sb;
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
