package expression;

import java.math.BigInteger;

public class Variable implements ExtendedExpression {
    final String ch;

    @Override
    public String getSign() {
        return ch;
    }

    @Override
    public Commutability getCommutability() {
        return Commutability.FULL;
    }

    @Override
    public StringBuilder buildMiniString(StringBuilder sb) {
        sb.append(ch);
        return sb;
    }

    @Override
    public StringBuilder buildString(StringBuilder sb) {
        sb.append(ch);
        return sb;
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

    public int evaluate(int x) {
        return x;
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

    public BigInteger evaluate(BigInteger x) {
        return x;
    }

    public int evaluate(int x, int y, int z) {
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
