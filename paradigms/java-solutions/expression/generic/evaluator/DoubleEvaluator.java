package expression.generic.evaluator;

public class DoubleEvaluator implements BasicEvaluator<Double>{
    @Override
    public Double evaluateAdd(Double x, Double y) {
        return x + y;
    }

    @Override
    public Double evaluateSubtract(Double x, Double y) {
        return x - y;
    }

    @Override
    public Double evaluateMultiply(Double x, Double y) {
        return x * y;
    }

    @Override
    public Double evaluateDivide(Double x, Double y) {
        return x / y;
    }

    @Override
    public Double evaluateNegate(Double x) {
        return -x;
    }

    @Override
    public Double evaluateCount(Double x) {
        return (double) Long.bitCount(Double.doubleToLongBits(x));
    }

    @Override
    public Double parse(String x) {
        return Double.parseDouble(x);
    }

    @Override
    public Double evaluateAbs(Double x) {
        return Math.abs(x);
    }

    @Override
    public Double evaluateMin(Double x, Double y) {
        return Math.min(x, y);
    }

    @Override
    public Double evaluateMax(Double x, Double y) {
        return Math.max(x, y);
    }

}
