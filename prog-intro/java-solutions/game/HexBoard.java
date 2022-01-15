package game;

public class HexBoard extends MNKBoard {
    public HexBoard(int n, int k) {
        super(n, n, k);
    }

    @Override
    protected boolean winCheck(Move move) {
       return checkDirection("row", move) ||
        checkDirection("col", move) ||
        checkDirection("diagRight", move);
    }

}

