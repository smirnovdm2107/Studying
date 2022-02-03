public interface Snake {
    Pair getHeadPosition();
    Pair getTailPosition();
    Pair[] getSnakePosition();
    void makeMove(Move move);
}
