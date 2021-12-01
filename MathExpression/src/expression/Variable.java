package expression;

import java.math.BigInteger;

public class Variable implements Expression, ToMiniString{
    final String ch;


    public Variable(String ch) {
        this.ch = ch;
    }

    @Override
    public String toString() {
        return ch;
    }


    public int evaluate(int var) {
        return var;
    }

    @Override
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
        return this.toString().hashCode();
    }

    public BigInteger evaluate(BigInteger x) {

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
