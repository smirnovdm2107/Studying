package expression;

import java.math.BigInteger;

public class Const implements ExtendedExpression {
    private final Number number;

    @Override
    public StringBuilder buildMiniString(StringBuilder sb) {
        sb.append(number.toString());
        return sb;
    }

    @Override
    public StringBuilder buildString(StringBuilder sb) {
        sb.append(number.toString());
        return sb;
    }

    @Override
    public Commutability getCommutability() {
        return Commutability.FULL;
    }

    public Const(int number) {
        this.number = number;
    }

    public Const(BigInteger number) {
        this.number = number;
    }


    @Override
    public String toString() {
        return number.toString();
    }


    public int evaluate(int x, int y, int z) {
        return number.intValue();
    }

    public int evaluate(int x) {
        return number.intValue();
    }

    public String toMiniString() {
        return number.toString();
    }

    public BigInteger evaluate(BigInteger x) {
        return (BigInteger) number;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Const) {
            return obj.hashCode() == this.hashCode();
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
