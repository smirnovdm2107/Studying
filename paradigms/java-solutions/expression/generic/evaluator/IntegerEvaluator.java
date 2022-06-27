package expression.generic.evaluator;

public class IntegerEvaluator implements BasicEvaluator<Integer>{
    @Override
    public Integer parse(String x) {
        return Integer.parseInt(x);
    }

    @Override
    public Integer evaluateAdd(Integer x, Integer y) {
        return x + y;
    }

    @Override
    public Integer evaluateSubtract(Integer x, Integer y) {
        return x - y;
    }

    @Override
    public Integer evaluateMultiply(Integer x, Integer y) {
        return x * y;
    }

    @Override
    public Integer evaluateDivide(Integer x, Integer y) {
        return x / y;
    }

    @Override
    public Integer evaluateNegate(Integer x) {
        return -x;
    }

    @Override
    public Integer evaluateAbs(Integer x) {
        return x > 0 ? x : -x;
    }

    @Override
    public Integer evaluateMin(Integer x, Integer y) {
        return x > y ? y : x;
    }

    @Override
    public Integer evaluateMax(Integer x, Integer y) {
        return x > y ? x : y;
    }

    @Override
    public Integer evaluateCount(Integer x) {
        return Integer.bitCount(x);
    }
}
