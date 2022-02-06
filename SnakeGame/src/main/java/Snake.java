public interface Snake {
    Pair getHeadPosition();
    Pair getTailPosition();
    Pair[] getSnakePosition();
    void makeMove(Move move);
    void increase(Move move);
    Pair delta(Move move);
}