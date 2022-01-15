package expression;

public interface BasicExpression extends Expression, TripleExpression {
    int getPriority();
    String getSign();
    Commutability getCommutability();
    StringBuilder buildString(StringBuilder sb);
    StringBuilder buildMiniString(StringBuilder sb);
}
