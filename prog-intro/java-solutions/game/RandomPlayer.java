package game;

import java.util.Random;

public class RandomPlayer implements Player {
    private final Random random = new Random();

    @Override
    public Move makeMove(Position position) {
        while (true) {
            Move move = new Move(
                    position.getTurn(),
                    random.nextInt(position.getRowCount()),
                    random.nextInt(position.getColCount())
            );
            if (position.isValid(move)) {
                return move;
            }
        }
    }
}
