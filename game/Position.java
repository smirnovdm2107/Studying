package game;

public interface Position {
    Cell getTurn();
    int getColCount();
    int getRowCount();
    int getK();
    String getStringBoard();
    boolean isValid(Move move);
}
