package expression;

public class Divide extends AbstractOperation implements Expression, ToMiniString{

    public Divide(Expression op1, Expression op2) {
        super(op1, op2);
    }


    public String toString() {
        return toString("/");
    }

    public int evaluate(int var) {
        return evaluate(var,"/");
    }

    public int evaluate(int x, int y, int z) {
        return evaluate(x, y, z, "/");
    }

    public String toMiniString() {
        return toMiniString("/");
    }
}
