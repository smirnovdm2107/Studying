package expression.generic.expression;


public class Variable<T extends Number> implements BasicExpression<T> {
    final String ch;

    @Override
    public String getSign() {
        return ch;
    }

    @Override
    public Commutability getCommutability() {
        return Commutability.FULL;
    }

    public Variable(String ch) {
        this.ch = ch;
    }

    @Override
    public String toString() {
        return ch;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    public String toMiniString() {
        return ch;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Variable) {
            return obj.toString().equals(this.toString());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ch.hashCode() * 691;
    }

    public T evaluate(T x, T y, T z) {
        switch(ch) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
            default:
                throw new AssertionError("There is no such var");

        }
    }
}
