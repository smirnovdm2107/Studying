package game;

public class BoardPosition  implements Position {
    final Board board;
    final int m;
    final int n;
    final int k;
    final Cell turn;
    final Cell[][] field;
    public BoardPosition(Board board, Cell turn, int m, int n, int k,Cell[][] field) {
       this.board = board;
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

    public String getStringBoard() {
        return board.toString();
    }

    public boolean isValid(Move move) {
        return  0 <= move.getRow() && move.getRow() < m
                && 0 <= move.getCol() && move.getCol() < n
                && field[move.getRow()][move.getCol()] == Cell.E
                && turn == move.getValue();

    }

}
