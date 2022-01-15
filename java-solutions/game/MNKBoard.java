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

    protected boolean winCheck(Move move) {
       return checkDirection("row", move) ||
               checkDirection("col", move) ||
               checkDirection("diagLeft", move) ||
               checkDirection("diagRight", move);
    }

    @Override
    public Position getPosition() {
        return new BoardPosition(this, turn, m, n, k, field);
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
        numStringIncrease(sb, numMaxLength, 0);
        for (int i = 0; i < n; i++) {
            numStringIncrease(sb, numMaxLength, Integer.toString(i + 1).length());
            sb.append(i + 1).append(" ");
        }

        sb.append(System.lineSeparator());
        for (int i = 0; i < m; i++) {
            numStringIncrease(sb, numMaxLength, Integer.toString(i + 1).length());
            sb.append(i + 1);
            for (int j = 0; j < n; j++) {
                numStringIncrease(sb, numMaxLength, 1);
                sb.append(CELL_STRING_MAP.get(field[i][j])).append(" ");
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    protected boolean checkDirection(String direction, Move move) {
        int difX;
        int difY;
        switch(direction){
            case "row":
                difX = 0;
                difY = 1;
                break;
            case "col":
                difX = 1;
                difY = 0;
                break;
            case "diagRight":
                difX = 1;
                difY = 1;
                break;
            case "diagLeft":
                difX = 1;
                difY = -1;
                break;
            default:
                throw new AssertionError("There is no such direction!");
        }
        int x = move.getRow() - k * difX;
        int y = move.getCol() - k * difY;
        int count = 0;
        while(x <= move.getRow() + k && y <= move.getCol() + k) {
            if (0 <= x && x < field.length && 0 <= y && y < field[x].length) {
                if (field[x][y] == turn) {
                    count++;
                    if (count == k) {
                        return true;
                    }
                } else {
                    count = 0;
                }
            }
            x += difX;
            y += difY;
        }
        return false;
    }

    private void numStringIncrease(StringBuilder sb, int numMaxLength, int numLength) {
        sb.append(" ".repeat(Math.max(0, numMaxLength - numLength)));

    }
}
