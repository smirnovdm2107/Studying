package game;

public class HexBoard extends MNKBoard {
    public HexBoard(int n, int k) {
        super(n, n, k);
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

        return false;
    }
}
