package expression.generic.evaluator;

import expression.exceptions.DivideByZeroException;
import expression.exceptions.ExpressionSyntaxException;
import expression.exceptions.OverflowException;

public class CheckedIntegerEvaluator implements BasicEvaluator<Integer> {

    @Override
    public Integer evaluateCount(Integer x) {
        return Integer.bitCount(x);
    }

    @Override
    public Integer parse(String x) {
        try {
            return Integer.parseInt(x);
        } catch(NumberFormatException e) {
            throw new OverflowException("overflow in const");
        }
    }

    @Override
    public Integer evaluateAdd(Integer x, Integer y) {
        if (x > 0 && y > 0) {
            if (Integer.MAX_VALUE - x < y) {
                throw new OverflowException(String.format("Too large value in adding: trying with %d and %d", x, y));
            }
        } else if (x < 0 && y < 0) {
            if (Integer.MIN_VALUE - x > y) {
                throw new OverflowException(String.format("Too low value in adding: trying with %d and %d", x, y));
            }
        }
        return x + y;
    }

    @Override
    public Integer evaluateSubtract(Integer x, Integer y) {
        if (x >= 0 && y < 0) {
            if (Integer.MAX_VALUE + y < x) {
                throw new OverflowException(String.format("Too large value in subtract result: trying with %d and %d", x, y));
            }
        } else if (x < 0 && y > 0){
            if (Integer.MIN_VALUE + y > x) {
                throw new OverflowException(String.format("Too small value in subtract result: trying with %d and %d", x, y));
            }
        }
        return x - y;
    }

    @Override
    public Integer evaluateMultiply(Integer x, Integer y) {
        if (x == 0 || y == 0) {
            return 0;
        }
        if (x == -1 && y == Integer.MIN_VALUE || x == Integer.MIN_VALUE && y == -1
                || x < -1 && (Integer.MAX_VALUE / x > y || Integer.MIN_VALUE / x < y)
                || x > 0 && (Integer.MAX_VALUE / x < y || Integer.MIN_VALUE / x > y)) {
            throw new OverflowException(String.format("Overflow in Multiply: trying with %d and %d", x, y));
        }
        return x * y;
    }

    @Override
    public Integer evaluateDivide(Integer x, Integer y) {
        if (y == 0) {
            throw new DivideByZeroException("Division by zero");
        }
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowException(String.format("Overflow in Divide: trying with %d and %d", x, y));
        }
        return x / y;
    }

    @Override
    public Integer evaluateNegate(Integer x) {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException("too small value for negate");
        }
        return -x;
    }

    @Override
    public Integer evaluateAbs(Integer x) {
        if (x == Integer.MIN_VALUE) {
            throw new ExpressionSyntaxException(String.format("too small value for abs: %x", x));
        }
        if (x > 0) {
            return x;
        } else {
            return -x;
        }
    }

    @Override
    public Integer evaluateMin(Integer x, Integer y) {
        if (x < y) {
            return x;
        } else {
            return y;
        }
    }

    @Override
    public Integer evaluateMax(Integer x, Integer y) {
        if (x > y) {
            return x;
        } else {
            return y;
        }
    }
}
