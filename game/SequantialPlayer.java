package game;

public class SequantialPlayer implements Player {
    @Override
    public Move makeMove(Position position) {
        for (int i = 0; i < position.getRowCount(); i++) {
            for (int j = 0; j < position.getColCount(); j++) {
                Move move = new Move(
                        position.getTurn(),
                        i,
                        j
                );
                if (position.isValid(move)) {
                    return move;
                }
            }
        }
        throw new AssertionError("There is no empty cells");
    }
}
