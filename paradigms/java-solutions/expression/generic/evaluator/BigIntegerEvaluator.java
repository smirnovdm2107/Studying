package expression.generic.evaluator;

import java.math.BigInteger;

public class BigIntegerEvaluator implements BasicEvaluator<BigInteger> {
    @Override
    public BigInteger parse(String x) {
        return new BigInteger(x);
    }

    @Override
    public BigInteger evaluateCount(BigInteger x) {
        return new BigInteger(Integer.toString(x.bitCount()));
    }

    @Override
    public BigInteger evaluateAdd(BigInteger x, BigInteger y) {
        return x.add(y);
    }

    @Override
    public BigInteger evaluateSubtract(BigInteger x, BigInteger y) {
        return x.subtract(y);
    }

    @Override
    public BigInteger evaluateMultiply(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }

    @Override
    public BigInteger evaluateDivide(BigInteger x, BigInteger y) {
        return x.divide(y);
    }

    @Override
    public BigInteger evaluateNegate(BigInteger x) {
        return x.negate();
    }

    @Override
    public BigInteger evaluateAbs(BigInteger x) {
        return x.abs();
    }

    @Override
    public BigInteger evaluateMin(BigInteger x, BigInteger y) {
        if (x.compareTo(y) < 0) {
            return x;
        } else {
            return y;
        }
    }

    @Override
    public BigInteger evaluateMax(BigInteger x, BigInteger y) {
        if (x.compareTo(y) > 0) {
            return x;
        } else {
            return y;
        }
    }
}
