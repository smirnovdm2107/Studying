package expression;

import expression.BasicExpression;

import java.math.BigDecimal;

public interface BigDecimalExpression extends BasicExpression {
    BigDecimal evaluate(BigDecimal x);
}
