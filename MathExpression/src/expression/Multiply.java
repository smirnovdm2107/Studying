package expression;

public class Multiply extends AbstractOperation implements Expression, ToMiniString{

     public Multiply(Expression op1, Expression op2) {
        super(op1, op2);
    }

    @Override
    public String toString() {
        return toString("*");
    }

    @Override
    public int evaluate(int var) {
        return evaluate(var,"*");
    }

    @Override
    public String toMiniString() {
        return toMiniString("*");
    }
}
