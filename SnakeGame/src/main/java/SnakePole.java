public class SnakePole implements Pole{
    private final Ceil[][] field;
    private final Snake snake;
    public SnakePole(Snake snake) {
        this.field = new Ceil[10][20];
        for (Ceil[] row: field) {
            for (Ceil ceil: row) {
                ceil = Ceil.NONE;
            }
        }
        this.snake = snake;

    }
    @Override
    public Position getPosition() {
        Position position = () -> {
          return null;
        };
        return position;
    }
    @Override
    public void makeMove(Move move) {
        int dx;
        int dy;
    }

    @Override
    public Ceil getCeil(int x, int y) {
        return field[x][y];
    }
}
