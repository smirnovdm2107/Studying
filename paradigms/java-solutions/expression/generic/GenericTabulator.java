package expression.generic;

import expression.generic.evaluator.*;
import expression.generic.expression.BasicExpression;

import java.util.Map;

public class GenericTabulator implements Tabulator {

    Map<String, BasicEvaluator<? extends Number>> MODE_TO_EVALUATOR = Map.of(
            "i", new CheckedIntegerEvaluator(),
            "d", new DoubleEvaluator(),
            "bi", new BigIntegerEvaluator(),
            "u", new IntegerEvaluator(),
            "l", new LongEvaluator(),
            "f", new FloatEvaluator()
    );

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        int deltaX = x2 - x1;
        int deltaY = y2 - y1;
        int deltaZ = z2 - z1;
        if (deltaX < 0 || deltaY < 0 || deltaZ < 0) {
            throw new NegativeArraySizeException(
                    String.format("the right border should be to the right of the left:" +
                            "%d >= %d, %d >= %d, %d >= %d", x1, x2, y1, y2, z1, z2)
            );
        }
        BasicEvaluator<?> evaluator = MODE_TO_EVALUATOR.get(mode);
        Object[][][] results = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        fillWithEvaluations(
                results,
                x1, deltaX,
                y1, deltaY,
                z1, deltaZ,
                expression,
                evaluator

        );
        return results;
    }

    private <T extends Number> void fillWithEvaluations(
            Object[][][] results,
            int x, int deltaX,
            int y, int deltaY,
            int z, int deltaZ,
            String expression,
            BasicEvaluator<T> evaluator
    ) {
        BasicExpression<T> expr = new GenericParser().parse(expression, evaluator);
        for (int i = 0; i <= deltaX; i++) {
            for (int j = 0; j <= deltaY; j++) {
                for (int k = 0; k <= deltaZ; k++) {
                    try {
                        results[i][j][k] = expr.evaluate(
                                evaluator.parse(Integer.toString(x + i)),
                                evaluator.parse(Integer.toString(y + j)),
                                evaluator.parse(Integer.toString(z + k))
                        );
                    } catch(ArithmeticException e) {
                        results[i][j][k] = null;
                    }
                }
            }
        }

    }
}
