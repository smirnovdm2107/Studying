package expression;

import expression.parser.ExpressionParser;

public class Main {

    public static void main(String args[]) {
        final Add example = new Add(new Add(new Multiply(new Variable("x"), new Variable("x")),
                new Multiply(new Variable("x"), new Const(2))), new Const(1));
        System.out.println(example.toMiniString());
         System.out.println(new ExpressionParser().parse("    l0   (0)").evaluate(0));
    }

}
