package expression;

import expression.BasicExpression;

import java.math.BigInteger;

public interface BigIntegerExpression extends BasicExpression {
    BigInteger evaluate(BigInteger x);
}
