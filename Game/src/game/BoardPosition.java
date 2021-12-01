package game;

public class BoardPosition  implements Position {
    final int m;
    final int n;
    final int k;
    final Cell turn;
    final Cell[][] field;
    public BoardPosition(Cell turn, int m, int n, int k,Cell[][] field) {
       this.m = m;
       this.n = n;
       this.k = k;
       this.turn = turn;
       this.field = field;
    }

    @Override
    public Cell getTurn() {
        return turn;
    }

    @Override
    public int getColCount() {
        return n;
    }

    @Override
    public int getRowCount() {
        return m;
    }

    @Override
    public int getK() {
        return k;
    }

    public boolean isValid(Move move) {
        return  0 <= move.getRow() && move.getRow() < m
                && 0 <= move.getCol() && move.getCol() < n
                && field[move.getRow()][move.getCol()] == Cell.E
                && turn == move.getValue();

    }

}
