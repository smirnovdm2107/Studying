package game;

public interface Position {
    Cell getTurn();
    int getColCount();
    int getRowCount();
    int getK();
    boolean isValid(Move move);
}
