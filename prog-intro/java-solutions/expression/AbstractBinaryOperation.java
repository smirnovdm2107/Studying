package expression;

import java.util.Stack;

public abstract class AbstractBinaryOperation implements BasicExpression {
    protected final BasicExpression op1;
    protected final BasicExpression op2;
    // Убрать

    public AbstractBinaryOperation(BasicExpression op1, BasicExpression op2)  {
        this.op1 = op1;
        this.op2 = op2;
    }



    @Override
    public String toString() {
        return buildString(new StringBuilder()).toString();
    }


    public StringBuilder buildString(StringBuilder sb) {
        sb.append('(');
        op1.buildString(sb);
        sb.append(' ').append(getSign()).append(' ');
        op2.buildString(sb);
        sb.append(')');
        return sb;
    }

    public StringBuilder buildMiniString(StringBuilder sb) {
        append(op1.getPriority() > getPriority(), sb, op1);
        sb.append(' ');
        sb.append(getSign());
        sb.append(' ');
        final BasicExpression op = this.op2;
        append(op.getPriority() > getPriority() || op.getPriority() == getPriority() && !isCommutable(op), sb, op);
        return sb;
    }

    private void append(final boolean condition, final StringBuilder sb, final BasicExpression op) {
        if (condition) {
            sb.append('(');
            op.buildMiniString(sb);
            sb.append(')');
        } else {
            op.buildMiniString(sb);
        }
    }

    public String toMiniString() {
        return buildMiniString(new StringBuilder()).toString();
    }

    private boolean isCommutable(BasicExpression op) {
        return getCommutability() == Commutability.FULL && (op.getCommutability() == Commutability.OUTER
                || op.getCommutability() == Commutability.FULL);
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
