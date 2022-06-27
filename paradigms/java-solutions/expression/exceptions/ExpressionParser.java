package expression.exceptions;

import expression.*;


import java.util.List;
import java.util.Map;

public final class ExpressionParser implements TripleParser {
    public BasicExpression parse(final CharSource source) {
        return new StringBaseParser(source).parse();

    }


    public TripleExpression parse(final String string) throws ExpressionSyntaxException, OverflowException {
        return parse(new StringCharSource(string));
    }

    private static final class StringBaseParser extends BaseParser {

        final Map<Token, Integer> TOKEN_TO_PRIORITY = Map.of(
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
            VAR, LP, RP, NUM, END, ABS
        }

        public BasicExpression parse() {
            // :NOTE: Магическая константа
            final BasicExpression value = parseExpression(40, true);
            if (cur_token != Token.END) {
                throw syntaxError("expression end expected");
            }
            return value;
        }

        private Token cur_token;
        private int num_value;
        private String var_value;

        public StringBaseParser(final CharSource source) {
            super(source);
        }

        public BasicExpression parseExpression(final int priority, final boolean get) {
            if (priority == 0) {
                final BasicExpression value = parseValue(get);
                getToken();
                return value;
            } else {
                BasicExpression value = parseExpression(priority - 10, get);
                while (TOKEN_TO_PRIORITY.getOrDefault(cur_token, -1) == priority) {
                    // :NOTE: Map<Token, BinaryOperator<BasicExpression>>
                    switch (cur_token) {
                        case PLUS -> value = new CheckedAdd(value, nextOp(priority));
                        case MINUS -> value = new CheckedSubtract(value, nextOp(priority));
                        case MUL -> value = new CheckedMultiply(value, nextOp(priority));
                        case DIV -> value = new CheckedDivide(value, nextOp(priority));
                        case MIN -> value = new Min(value, nextOp(priority));
                        case MAX -> value = new Max(value, nextOp(priority));
                        case EXP -> value = new Pow(value, nextOp(priority));
                        case LOG -> value = new Log(value, nextOp(priority));
                    }
                }
                return value;
            }
        }

        private BasicExpression nextOp(final int priority) {
            return parseExpression(priority - 10, true);
        }

        private void getToken() {
            skipWhitespaces();
            if (test(END)) {
                cur_token = Token.END;
            } else if (take('+')) {
                cur_token = Token.PLUS;
            } else if (take('(')) {
                cur_token = Token.LP;
            } else if (take(')')) {
                cur_token = Token.RP;
            } else if (take('-')) {
                cur_token = Token.MINUS;
            } else if (take('m')) {
                if (take('i')) {
                    expect('n');
                    if (between('0', '9')) {
                        throw syntaxError("impossible syntax in min");
                    }
                    cur_token = Token.MIN;
                } else {
                    expect("ax");
                    if (between('0', '9')) {
                        throw syntaxError("impossible syntax in max");
                    }
                    cur_token = Token.MAX;
                }
            } else if (take('*')) {
                if (take('*')) {
                    cur_token = Token.EXP;
                } else {
                    cur_token = Token.MUL;
                }
            } else if (take('/')) {
                if (take('/')) {
                    cur_token = Token.LOG;
                } else {
                    cur_token = Token.DIV;
                }
            } else if (take('a')) {
                expect("bs");
                if (!isWhiteSpace() && !test('(')) {
                    throw syntaxError("impossible syntax in abs");
                }
                cur_token = Token.ABS;
            } else if (between('0', '9')) {
                num_value = parseNumber(false);
                cur_token = Token.NUM;
            } else {
                String variable = "";
                cur_token = Token.VAR;
                outerLoop:
                while (!isWhiteSpace()) {
                    boolean isPrefix = false;
                    // :NOTE: Конкатенация строк в цикле
                    variable += take();
                    for (final String var : VARIABLES) {
                        if (var.startsWith(variable)) {
                            isPrefix = true;
                            if (variable.length() == var.length()) {
                                var_value = variable;
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

        private int parseNumber(final boolean isNegative) {
            if (take('0')) {
                return 0;
            }
            final StringBuilder number = new StringBuilder();
            if (isNegative) {
                number.append('-');
            }
            // :NOTE: 11
            // :NOTE: 000000000000000000000001
            while (between('0', '9') && number.length() < 11) {
                number.append(take());
            }
            try {
                return Integer.parseInt(number.toString());
            } catch (final NumberFormatException e) {
                throw overflowError("overflow in const value");
            }
        }

        private BasicExpression parseValue(final boolean get) {
            if (get) {
                getToken();
            }
            switch (cur_token) {
                case NUM -> {
                    return new Const(num_value);
                }
                case VAR -> {
                    return new Variable(var_value);
                }
                case LP -> {
                    getToken();
                    final BasicExpression value = parseExpression(40, false);
                    if (cur_token != Token.RP) {
                        throw syntaxError("expected ')'");
                    }
                    return value;
                }
                case MINUS -> {
                    if (between('0', '9')) {
                        return new Const(parseNumber(true));
                    }
                    return new CheckedNegate(parseValue(true));
                }
                case ABS -> {
                    return new Abs(parseValue(true));
                }
                default -> throw syntaxError("impossible expression argument");
            }
        }

        private void skipWhitespaces() {
            while (isWhiteSpace()) {
                take();
            }
        }

        private OverflowException overflowError(final String message) {
            return new OverflowException(sourse.error(message).getMessage());
        }
        
    }
}
