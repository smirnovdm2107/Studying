package game;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
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
                Scanner subIn = new Scanner(in.nextLine());
                x = subIn.nextInt();
                y = subIn.nextInt();
                if (!position.isValid(new Move(position.getTurn(), x - 1, y - 1))) {
                    throw new InputMismatchException("Invalid move");
                }
                subIn.close();
                break;
            } catch (NoSuchElementException e) {
                System.out.println("Invalid move, please try again:");
                System.out.println(position.getStringBoard());
            }
        }
        return new Move(position.getTurn(),x - 1, y - 1);
    }

}
