package game;

import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanPlayer implements Player {
    Scanner in;

    public HumanPlayer(Scanner in) {
        this.in = in;
    }

    @Override
    public Move makeMove(Position position) {
        System.out.println("Input your move:");
        int x;
        int y;
        while (true) {
            try {
                x = in.nextInt();
                y = in.nextInt();
                in.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Incorrect values, please try again:");
            }
        }
        return new Move(position.getTurn(),x - 1, y - 1);
    }

}
