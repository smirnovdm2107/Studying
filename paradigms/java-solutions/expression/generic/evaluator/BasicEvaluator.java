package expression.generic.evaluator;

public interface BasicEvaluator<T extends Number> {

    T parse(String x);

    T evaluateAdd(T x, T y);

    T evaluateSubtract(T x, T y);

    T evaluateMultiply(T x, T y);

    T evaluateDivide(T x, T y);

    T evaluateNegate(T x);

    T evaluateAbs(T x);

    T evaluateMin(T x, T y);

    T evaluateMax(T x, T y);

    T evaluateCount(T x);
}
