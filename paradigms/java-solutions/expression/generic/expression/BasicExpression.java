package expression.generic.expression;

import expression.ToMiniString;

public interface BasicExpression<T extends Number> extends ToMiniString {
    T evaluate(T x, T y, T z);
    int getPriority();
    String getSign();
    Commutability getCommutability();
}
