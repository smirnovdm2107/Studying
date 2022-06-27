package expression.generic.expression;


public class Const<T extends Number> implements BasicExpression<T> {
    private final T number;

    public Const(T number) {
        this.number = number;
    }

    @Override
    public Commutability getCommutability() {
        return Commutability.FULL;
    }

    @Override
    public String toString() {
        return number.toString();
    }


    public T evaluate(T x, T y, T z) {
        return number;
    }

    public String toMiniString() {
        return number.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Const<?>) {
            return number.equals(((Const<?>) obj).number);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return number.hashCode() * 739;
    }

    public int getPriority() {
        return 0;
    }

    @Override
    public String getSign() {
        return number.toString();
    }
}
