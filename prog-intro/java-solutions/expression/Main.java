package expression;

import expression.exceptions.ExpressionParser;

import java.util.Scanner;

public class Main {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int x = 0;
        int y = 0;
        int z = 0;
        try {
            System.out.println(
                    new ExpressionParser().parse(in.nextLine()).evaluate(in.nextInt(), in.nextInt(), in.nextInt())
            );
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
