package expression;

public abstract class AbstractOperation {
    private final Expression op1;
    private final Expression op2;

    protected AbstractOperation(Expression op1, Expression op2) {
        this.op1 = op1;
        this.op2 = op2;
    }

    protected String toString(String sign) {
        return "(" + op1.toString() + " " + sign + " " + op2.toString() + ")";
    }

    protected int evaluate(int x, String sign) {
        return evaluate(x, 0, 0, sign);
    }
    protected int evaluate(int x, int y, int z, String sign) {
        switch (sign) {
            case "*":
                return op1.evaluate(x, y, z) * op2.evaluate(x, y, z);
            case "/":
                return op1.evaluate(x, y, z) / op2.evaluate(x, y, z);
            case "+":
                return op1.evaluate(var) + op2.evaluate(var);
            case "-":
                return op1.evaluate(var) - op2.evaluate(var);
            default:
                throw new AssertionError("Impossible operand");
        }
    }

    protected String toMiniString(String sign) {
        StringBuilder sb = new StringBuilder();
        if (sign.equals("+") || sign.equals("-")) {
            sb.append(op1.toMiniString());
            sb.append(" ");
            sb.append(sign);
            sb.append(" ");
            if (sign.equals("-") && (op2.getClass() == Add.class || op2.getClass() == Subtract.class)) {
                sb.append("(");
                sb.append(op2.toMiniString());
                sb.append(")");
            } else {
                sb.append(op2.toMiniString());
            }
            return sb.toString();
        } else {
            if (op1.getClass() == Subtract.class || op1.getClass() == Add.class) {
                sb.append("(");
                sb.append(op1.toMiniString());
                sb.append(")");
            } else {
                sb.append(op1.toMiniString());
            }
            sb.append(" ");
            sb.append(sign);
            sb.append(" ");
            if (op2.getClass() == Subtract.class || op2.getClass() == Add.class || op2.getClass() == Divide.class ||
                    (sign.equals("/") && op2.getClass() == Multiply.class)) {
                sb.append("(");
                sb.append(op2.toMiniString());
                sb.append(")");
            } else {
                sb.append(op2.toMiniString());
            }
            return sb.toString();
        }


    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Expression) {
            return obj.toString().equals(this.toString());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }


}
