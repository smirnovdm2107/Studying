package game;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter m, n and k values with spaces:");
        int m;
        int n;
        int k;
        while(true) {
            try {
                Scanner subIn = new Scanner(in.nextLine());
                m = subIn.nextInt();
                n = subIn.nextInt();
                k = subIn.nextInt();
                if (m <= 0 || n <= 0 || k <= 0) {
                    throw new InputMismatchException("Game parameters must be positive");
                }
                subIn.close();
                break;
            }catch(NoSuchElementException e) {
                System.out.println("Invalid parameters, please try again:");
            }
        }
        int result = new TwoPlayerGame(new HexBoard(n, k),new RandomPlayer (), new RandomPlayer ()).play(true);

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
