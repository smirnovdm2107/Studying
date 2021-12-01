package expression;

public class Main {

    public static void main(String args[]) {
        final Add example = new Add(new Variable("x"), new Variable("x"));
        System.out.println(example.toString());
    }

}
