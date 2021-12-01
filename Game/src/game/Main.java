package game;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter m, n and k integer values with spaces:");
        int m;
        int n;
        int k;
        while(true) {
            try {
                m = in.nextInt();
                n = in.nextInt();
                k = in.nextInt();
                in.nextLine();
                break;
            }catch(InputMismatchException e) {
                System.out.println("Incorrect values, please try again:");
            }
        }
        int result = new TwoPlayerGame(new HexBoard(n, k),new HumanPlayer(in), new HumanPlayer(in)).play(true);

        switch(result) {
            case 1:
                System.out.println("First player won!");
                break;
            case 2:
                System.out.println("Second player won!");
                break;
            case 0:
                System.out.println("Draw!");
                break;
            default:
                throw new AssertionError("Impossible game result");
        }




        new Tournament(new MNKBoard(m, n , k), new RandomPlayer(),
                new RandomPlayer(), new RandomPlayer(), new RandomPlayer()).play(true);
        in.close();
    }
}
