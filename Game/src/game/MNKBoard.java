package game;

import java.util.Arrays;
import java.util.Map;

public class MNKBoard implements Board{
    protected final Cell[][] field;
    public static final Map<Cell, String> CELL_STRING_MAP =  Map.of(
            Cell.X, "X",
            Cell.E, ".",
            Cell.O, "0"
    );
    protected Cell turn;
    protected int emptyCells;
    protected final int m;
    protected final int n;
    protected final int k;
    public MNKBoard(int m, int n, int k) {

        field = new Cell[m][n];
        fillWithE(field);
        turn = Cell.X;
        emptyCells = m * n;
        this.m = m;
        this.n = n;
        this.k = k;
    }

    public GameResult makeMove(Move move) {
        if (!isValid(move)) {
            return GameResult.LOOSE;
        }
        field[move.getRow()][move.getCol()] = move.getValue();
        emptyCells--;
        if (emptyCells == 0) {
            return GameResult.DRAW;
        }

        if (winCheck(move)) {
            return GameResult.WIN;
        }
        turn = turn == Cell.X ? Cell.O : Cell.X;
        return GameResult.UNKNOWN;
    }

    private boolean winCheck(Move move) {
        int loverBoundRow = Math.max(0, move.getRow() - k + 1);
        int upperBoundRow = Math.min(m - 1, move.getRow() + k - 1);
        int loverBoundColumn = Math.max(0, move.getCol() - k + 1);
        int upperBoundColumn = Math.min(n - 1, move.getCol() + k - 1);
        int count = 0;
        for (int i = loverBoundRow;  i <= upperBoundRow; i++) {
            if (field[i][move.getCol()] == turn) {
                count++;
                if (count == k) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        count = 0;
        for (int i = loverBoundColumn; i <= upperBoundColumn; i++) {
            if (field[move.getRow()][i] == turn) {
                count++;
                if (count == k) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        count = 0;
        int leftDif = Math.max(loverBoundColumn - move.getCol(), loverBoundRow - move.getRow());
        int rigthDif = Math.min(upperBoundColumn - move.getCol(), upperBoundRow - move.getRow());
        for (int i = leftDif; i <= rigthDif; i++) {
            if (field[move.getRow() + i][move.getCol() + i] == turn ) {
                count++;
                if (count == k) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        count = 0;
        leftDif = Math.max(loverBoundColumn - move.getCol(), move.getRow() - upperBoundRow);
        rigthDif = Math.min(upperBoundColumn - move.getCol(), move.getRow() - loverBoundRow);
        for (int i = leftDif; i <= rigthDif; i++) {
            if (field[move.getRow() - i][move.getCol() + i] == turn) {
                count++;
                if (count == k) {
                    return  true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }

    @Override
    public Position getPosition() {
        return new BoardPosition(turn, m, n, k, field);
    }



    public boolean isValid(Move move) {
        return  0 <= move.getRow() && move.getRow() < m
                && 0 <= move.getCol() && move.getCol() < n
                && field[move.getRow()][move.getCol()] == Cell.E
                && turn == move.getValue();

    }

    public void clear() {
        fillWithE(field);
        turn = Cell.X;
        emptyCells = m*n;
    }

    private void fillWithE(Cell[][] field) {
        for (Cell[] row: field) {
            Arrays.fill(row, Cell.E);
        }
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int numMaxLength = Integer.toString(n).length();
        numStringIncreaser(sb, numMaxLength, 0);
        for (int i = 0; i < n; i++) {
            numStringIncreaser(sb, numMaxLength, Integer.toString(i + 1).length());
            sb.append(i + 1).append(" ");
        }

        sb.append(System.lineSeparator());
        for (int i = 0; i < m; i++) {
            numStringIncreaser(sb, numMaxLength, Integer.toString(i + 1).length());
            sb.append(i + 1);
            for (int j = 0; j < n; j++) {
                numStringIncreaser(sb, numMaxLength, 1);
                sb.append(CELL_STRING_MAP.get(field[i][j])).append(" ");
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    private void numStringIncreaser(StringBuilder sb, int numMaxLength, int numLength) {
        sb.append(" ".repeat(Math.max(0, numMaxLength - numLength)));

    }
}
