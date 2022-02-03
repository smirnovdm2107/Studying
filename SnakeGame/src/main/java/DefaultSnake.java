import java.util.LinkedList;

public class DefaultSnake implements Snake{
    private final LinkedList<Pair> snake;
    public DefaultSnake(int x, int y, int length) {
        snake = new LinkedList<>();
        for (int i = 0; i < length; i++) {
            snake.add(new Pair(x / 2, y / 2 + length / 2 - i));
        }
    }

    @Override
    public Pair getHeadPosition() {
        return snake.getLast();
    }

    @Override
    public Pair getTailPosition() {
        return snake.getFirst();
    }

    @Override
    public Pair[] getSnakePosition() {
        return snake.toArray(new Pair[snake.size()]);
    }

    @Override
    public void makeMove(Move move) {

    }

    private void moveSnakeBody() {

    }

}
