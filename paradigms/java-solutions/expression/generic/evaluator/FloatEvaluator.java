package expression.generic.evaluator;

public class FloatEvaluator implements BasicEvaluator<Float>{
    @Override
    public Float parse(String x) {
        return Float.parseFloat(x);
    }

    @Override
    public Float evaluateAdd(Float x, Float y) {
        return x + y;
    }

    @Override
    public Float evaluateSubtract(Float x, Float y) {
        return x - y;
    }

    @Override
    public Float evaluateMultiply(Float x, Float y) {
        return x * y;
    }

    @Override
    public Float evaluateDivide(Float x, Float y) {
        return x / y;
    }

    @Override
    public Float evaluateNegate(Float x) {
        return -x;
    }

    @Override
    public Float evaluateAbs(Float x) {
        return x > 0 ? x : -x;
    }

    @Override
    public Float evaluateMin(Float x, Float y) {
        return Math.min(x, y);
    }

    @Override
    public Float evaluateMax(Float x, Float y) {
        return Math.max(x, y);
    }

    @Override
    public Float evaluateCount(Float x) {
        return (float) Integer.bitCount(Float.floatToIntBits(x));
    }

}
