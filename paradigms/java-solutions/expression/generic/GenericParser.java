package expression.generic;


import expression.generic.evaluator.BasicEvaluator;
import expression.generic.expression.*;

import expression.generic.expression.exceptions.BaseParser;
import expression.generic.expression.exceptions.CharSource;
import expression.generic.expression.exceptions.ExpressionSyntaxException;
import expression.generic.expression.exceptions.OverflowException;
import expression.generic.expression.exceptions.StringCharSource;

import java.util.List;
import java.util.Map;

public final class GenericParser {
    public <T extends Number> BasicExpression<T> parse(final CharSource source, BasicEvaluator<T> evaluator) {
        return new StringBaseParser<>(source, evaluator).parse();

    }


    public <T extends Number> BasicExpression<T> parse(final String string, BasicEvaluator<T> evaluator) throws ExpressionSyntaxException, OverflowException {
        return parse(new StringCharSource(string), evaluator);
    }


    private static final class StringBaseParser<T extends Number> extends BaseParser {

        private final BasicEvaluator<T> evaluator;

        private final Map<Token, Integer> TOKEN_TO_PRIORITY = Map.of(
                Token.PLUS, 30,
                Token.MINUS, 30,
                Token.MUL, 20,
                Token.DIV, 20,
                Token.EXP, 10,
                Token.LOG, 10,
                Token.MIN, 40,
                Token.MAX, 40
        );

        final List<String> VARIABLES = List.of(
                "x",
                "y",
                "z"
        );

        private enum Token {
            MINUS, PLUS, MUL, DIV, EXP, LOG, MIN, MAX,
            VAR, LP, RP, NUM, END, ABS, COUNT
        }

        public BasicExpression<T> parse() {
            int MAX_PRIORITY = 40;
            final BasicExpression<T> value = parseExpression(MAX_PRIORITY, true);
            if (token != Token.END) {
                throw syntaxError("expression end expected");
            }
            return value;
        }

        private Token token;
        private String numValue;
        private String varValue;

        public StringBaseParser(final CharSource source, BasicEvaluator<T> evaluator) {
            super(source);
            this.evaluator = evaluator;
        }

        public BasicExpression<T> parseExpression(final int priority, final boolean get) {
            if (priority == 0) {
                final BasicExpression<T> value = parseValue(get);
                getToken();
                return value;
            } else {
                BasicExpression<T> value = parseExpression(priority - 10, get);
                while (TOKEN_TO_PRIORITY.getOrDefault(token, -1) == priority) {
                    switch (token) {
                        case PLUS -> value = new Add<>(value, nextOp(priority), evaluator);
                        case MINUS -> value = new Subtract<>(value, nextOp(priority), evaluator);
                        case MUL -> value = new Multiply<>(value, nextOp(priority), evaluator);
                        case DIV -> value = new Divide<>(value, nextOp(priority), evaluator);
                        case MIN -> value = new Min<>(value, nextOp(priority), evaluator);
                        case MAX -> value = new Max<>(value, nextOp(priority), evaluator);
                    }
                }
                return value;
            }
        }

        private BasicExpression<T> nextOp(final int priority) {
            return parseExpression(priority - 10, true);
        }

        private void getToken() {
            skipWhitespaces();
            if (test(END)) {
                token = Token.END;
            } else if (take('+')) {
                token = Token.PLUS;
            } else if (take('(')) {
                token = Token.LP;
            } else if (take(')')) {
                token = Token.RP;
            } else if (take('-')) {
                token = Token.MINUS;
            } else if (take('m')) {
                if (take('i')) {
                    expect('n');
                    if (between('0', '9')) {
                        throw syntaxError("impossible syntax in min");
                    }
                    token = Token.MIN;
                } else {
                    expect("ax");
                    if (between('0', '9')) {
                        throw syntaxError("impossible syntax in max");
                    }
                    token = Token.MAX;
                }
            } else if (take('*')) {
                if (take('*')) {
                    token = Token.EXP;
                } else {
                    token = Token.MUL;
                }
            } else if (take('/')) {
                if (take('/')) {
                    token = Token.LOG;
                } else {
                    token = Token.DIV;
                }
            } else if (take('a')) {
                expect("bs");
                if (!isWhiteSpace() && !test('(')) {
                    throw syntaxError("impossible syntax in abs");
                }
                token = Token.ABS;
            } else if (take('c')) {
                expect("ount");
                token = Token.COUNT;
            } else if (between('0', '9')) {
                numValue = parseNumber(false);
                token = Token.NUM;
            } else {
                StringBuilder variable = new StringBuilder();
                token = Token.VAR;
                outerLoop:
                while (!isWhiteSpace()) {
                    boolean isPrefix = false;
                    variable.append(take());
                    for (final String var : VARIABLES) {
                        if (var.startsWith(variable.toString())) {
                            isPrefix = true;
                            if (variable.length() == var.length()) {
                                varValue = variable.toString();
                                break outerLoop;
                            }
                        }
                    }
                    if (!isPrefix) {
                        throw syntaxError("impossible expression token");
                    }
                }
            }
        }

        private String parseNumber(final boolean isNegative) {

            final StringBuilder number = new StringBuilder();
            if (isNegative) {
                number.append('-');
            }
            while (take('0')) {
                //Do nothing...
            }
            while (between('0', '9')) {
                number.append(take());
            }
            if (take('.')) {
                assert between('0', '9');
                while (between('0', '9')) {
                    number.append(take());
                }
            }
            return number.isEmpty() ? "0" : number.toString();
        }

        private BasicExpression<T> parseValue(final boolean get) {
            if (get) {
                getToken();
            }
            switch (token) {
                case NUM -> {
                    return new Const<>(evaluator.parse(numValue));
                }
                case VAR -> {
                    return new Variable<>(varValue);
                }
                case LP -> {
                    getToken();
                    final BasicExpression<T> value = parseExpression(40, false);
                    if (token != Token.RP) {
                        throw syntaxError("expected ')'");
                    }
                    return value;
                }
                case MINUS -> {
                    if (between('0', '9')) {
                        return new Const<>(evaluator.parse(parseNumber(true)));
                    }
                    return new Negate<>(parseValue(true), evaluator);
                }
                case ABS -> {
                    return new Abs<>(parseValue(true), evaluator);
                }
                case COUNT -> {
                    return new Count<>(parseValue(true), evaluator);
                }
                default -> throw syntaxError("impossible expression argument");
            }
        }

        private void skipWhitespaces() {
            while (isWhiteSpace()) {
                take();
            }
        }

    }
}
