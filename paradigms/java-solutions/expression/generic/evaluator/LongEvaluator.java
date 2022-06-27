package expression.generic.evaluator;

public class LongEvaluator implements BasicEvaluator<Long>{
    @Override
    public Long parse(String x) {
        return Long.parseLong(x);
    }

    @Override
    public Long evaluateAdd(Long x, Long y) {
        return x + y;
    }

    @Override
    public Long evaluateSubtract(Long x, Long y) {
        return x - y;
    }

    @Override
    public Long evaluateMultiply(Long x, Long y) {
        return x * y;
    }

    @Override
    public Long evaluateDivide(Long x, Long y) {
        return x / y;
    }

    @Override
    public Long evaluateNegate(Long x) {
        return -x;
    }

    @Override
    public Long evaluateAbs(Long x) {
        return x > 0 ? x : -x;
    }

    @Override
    public Long evaluateMin(Long x, Long y) {
        return x > y ? y : x;
    }

    @Override
    public Long evaluateMax(Long x, Long y) {
        return x > y ? x : y;
    }

    @Override
    public Long evaluateCount(Long x) {
        return (long) Long.bitCount(x);
    }
}
