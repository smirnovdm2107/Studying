package expression;

import expression.exceptions.CharSource;
import expression.exceptions.ExpressionParser;
import expression.generic.GenericParser;
import expression.generic.evaluator.FloatEvaluator;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int x = 10;
        x *= x -= x *= 5;
        System.out.println(x);
    }

}
