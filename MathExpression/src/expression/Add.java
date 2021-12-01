package expression;

public class Add extends AbstractOperation implements Expression, ToMiniString{

    public Add(Expression op1, Expression op2) {
        super(op1, op2);
    }

    @Override
    public String toString() {
        return toString("+");
    }

    @Override
    public int evaluate(int var) {
        return evaluate(var,"+");
    }

    @Override
    public String toMiniString() {
        return toMiniString("+");
    }
}
