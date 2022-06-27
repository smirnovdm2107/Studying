package expression;

public interface BasicExpression extends Expression, TripleExpression {
    int getPriority();
    String getSign();
    Commutability getCommutability();
}
