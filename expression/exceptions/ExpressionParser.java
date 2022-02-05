package expression.exceptions;

import expression.*;


public final class ExpressionParser implements  Parser {


    public BasicExpression parse(CharSource source) {
        return new StringBaseParser(source).parse();
    }

    public TripleExpression parse(String string) throws Exception {
        return parse(new StringCharSource(string));
    }


    private static final class StringBaseParser extends BaseParser {

        public StringBaseParser(CharSource source) {
            super(source);
        }

        public BasicExpression parse() {
            skipWhitespaces();
            if (take(END)) {
                throw new ExpressionSyntaxExpression("No parameters in expression");
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
            boolean hasSpaces = skipWhitespaces();
            if (!test(stopper)) {
                op.set(parseOperation(hasSpaces));
                skipWhitespaces();
                boolean hasNext = false;
                while(!test(stopper) && prevPriority > getOperationPriority(op.get())) {
                    hasNext = true;
                    value = makeExpression(
                            op.get() , value, parseSubExpression(op, getOperationPriority(op.get()), stopper)
                    );
                }
                if (!hasNext && test(stopper)) throw new ExpressionSyntaxExpression("missing argument in expression");

            }
            return value;
        }




        private int getOperationPriority(String sign) {
            return makeExpression(sign, null, null).getPriority();
        }

        private BasicExpression makeExpression(String sign, BasicExpression op1, BasicExpression op2) {
            switch (sign) {
                case "*":
                    return new CheckedMultiply(op1, op2);
                case "/":
                    return new CheckedDivide(op1, op2);
                case "+":
                    return new CheckedAdd(op1, op2);
                case "-":
                    return new CheckedSubtract(op1, op2);
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
                case "**":
                    return new Pow(op1, op2);
                case "//":
                    return new Log(op1, op2);
                default:
                    throw new ExpressionSyntaxExpression(
                            String.format("There is no such operation sign: %s!", sign)
                    );
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
                return new CheckedNegate(parseValue());
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
            } else if (take('a')) {
                expect("bs");
                if (test(' ') || test('(')) {
                    skipWhitespaces();
                    return new Abs(parseValue());
                } else {
                    throw new ExpressionSyntaxExpression(
                            String.format("Impossible expression syntax in abs: '%s'", take())
                    );
                }
            }
            throw new ExpressionSyntaxExpression(String.format("Impossible expression value: '%s'!", take()));
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

        public String parseOperation(boolean hasSpaces) {
            if (take('+')) {
                return "+";
            } else if (take('-')) {
                return "-";
            } else if (take('*')) {
                if (take('*')) {
                    return "**";
                }
                return "*";
            } else if (take('/')) {
                if (take('/')) {
                    return "//";
                }
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
                    expect("n");
                    if (between('0', '9')) {
                        throw new ExpressionSyntaxExpression(
                                String.format("Syntax error in min operation trying with %s, %s", take())
                        );
                    }
                    return "min";

                }
                expect("ax");
                if (between('0', '9')) {
                    throw new ExpressionSyntaxExpression(
                            String.format("Syntax error in max operation trying with %s, %s", take())
                    );
                }
                return "max";

            }
            throw error(String.format("expected operation, found '%s'", take()));
        }


        public  boolean skipWhitespaces() {
            boolean hasSpaces = false;
            while (isWhiteSpace()) {
                take();
                hasSpaces = true;
            }
            return hasSpaces;
        }

    }
}