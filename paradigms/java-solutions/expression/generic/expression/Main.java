package expression.generic.expression;

import expression.exceptions.CharSource;
import expression.exceptions.ExpressionParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Scanner;

public class Main {

    public static void main(String args[]) {

        System.out.println(new ExpressionParser().parse("5min5").toMiniString());
    }

}
