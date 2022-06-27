package expression.parser;

import expression.*;
import expression.exceptions.ExpressionSyntaxException;


public final class ExpressionParser implements TripleParser {


    public BasicExpression parse(CharSource source) {
        return new StringParser(source).parse();
    }

    public BasicExpression parse(String string) {
        return parse(new StringCharSource(string));
    }


    private static final class StringParser extends BaseParser {


        public StringParser(CharSource source) {
            super(source);
        }


        public BasicExpression parse() {
            skipWhitespaces();
            if (take(END)) {
                throw new ExpressionSyntaxException("No parameters in expression");
            }
            return parseSubExpression(new StringHolder(),  Integer.MAX_VALUE,END);
        }

        private class StringHolder {
            String string;
            String get() {
                return string;
            }
            void set(String string) {
                this.string = string;
            }

        }

        private BasicExpression parseSubExpression(StringHolder op, int prevPriority, char stopper) {
            skipWhitespaces();
            BasicExpression value = parseValue();
            skipWhitespaces();
            if (!test(stopper)) {
                op.set(parseOperation());
                skipWhitespaces();
                while(!test(stopper) && prevPriority > getOperationPriority(op.get())) {
                    value = makeExpression(
                            op.get() , value, parseSubExpression(op, getOperationPriority(op.get()), stopper)
                    );

                }
            }
            return value;
        }




        private int getOperationPriority(String sign) {
            return makeExpression(sign, null, null).getPriority();
        }

        private BasicExpression makeExpression(String sign, BasicExpression op1, BasicExpression op2) {
            switch (sign) {
                case "*":
                    return new Multiply(op1, op2);
                case "/":
                    return new Divide(op1, op2);
                case "+":
                    return new Add(op1, op2);
                case "-":
                    return new Subtract(op1, op2);
                case "min":
                    return new Min(op1, op2);
                case "max":
                    return new Max(op1, op2);
                case "<<":
                    return new LeftShift(op1, op2);
                case ">>":
                    return new RightShift(op1, op2);
                case ">>>":
                    return new UnsignedRightShift(op1, op2);
                default:
                    throw new AssertionError("There is no such operation sign!");
            }
        }


        public BasicExpression parseValue() {
            if (take('x')) {
                return new Variable("x");
            } else if (take('y')) {
                return new Variable("y");
            } else if (take('z')) {
                return new Variable("z");
            } else if (take('0')) {
                return new Const(0);
            } else if (between('1', '9')) {
                return new Const(Integer.parseInt(parseStringNumber()));
            } else if (take('-')) {
                if (between('0', '9')) {
                    return new Const(Integer.parseInt("-" + parseStringNumber()));
                }
                skipWhitespaces();
                return new Negate(parseValue());
            } else if (take('(')) {
                return parseBrackets();
            } else if (take('l')) {
                expect('0');
                skipWhitespaces();
                return new LZeroes(parseValue());
            } else if (take('t')) {
                expect('0');
                skipWhitespaces();
                return new TZeroes(parseValue());
            }
            throw new IllegalArgumentException(error(String.format("Impossible expression value: '%s'!", take())));
        }

        private String parseStringNumber() {
            if (take('0')) {
                return "0";
            }
            StringBuilder number = new StringBuilder();
            while (between('0', '9')) {
                number.append(take());
            }
            return number.toString();
        }

        private BasicExpression parseBrackets() {
            BasicExpression ans = parseSubExpression(new StringHolder(), Integer.MAX_VALUE,')');
            expect(')');
            return ans;
        }

        public String parseOperation() {
            if (take('+')) {
                return "+";
            } else if (take('-')) {
                return "-";
            } else if (take('*')) {
                return "*";
            } else if (take('/')) {
                return "/";
            } else if (take('>')) {
                expect('>');
                if (take('>')) {
                    return ">>>";
                }
                return ">>";
            } else if (take('<')) {
                expect('<');
                return "<<";
            } else if (take('m')) {
                if (take('i')) {
                    expect('n');
                    return "min";
                }
                expect("ax");
                return "max";
            }
            throw error(String.format("expected operation, found '%s'", take()));
        }


        public void skipWhitespaces() {
            while (isWhiteSpace()) {
                take();
            }
        }

    }


}