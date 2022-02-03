public interface Pole {
    Position getPosition();
    void makeMove(Move move);
    Ceil getCeil(int x, int y);
}
