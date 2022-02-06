import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DefaultSnake implements Snake{
    private final List<Pair> snakeBody;
    public DefaultSnake(int width, int height, int length) {
        snakeBody = new ArrayList<>();
        for (int i = length - 1; i >= 0; i--) {
            snakeBody.add(new Pair(width / 2, height / 2 + length / 2 - i));
        }
    }

    @Override
    public Pair getHeadPosition() {
        return snakeBody.get(snakeBody.size() - 1);
    }

    @Override
    public Pair getTailPosition() {
        return snakeBody.get(0);
    }

    @Override
    public Pair[] getSnakePosition() {
        return snakeBody.toArray(new Pair[snakeBody.size()]);
    }

    @Override
    public void makeMove(Move move) {

        Pair deltaResult = delta(move);
        int lastIndex = snakeBody.size() - 1;
        Pair last = snakeBody.get(lastIndex);

        for (int i = 0; i < lastIndex; i++) {
            snakeBody.get(i).setX(snakeBody.get(i + 1).getX());
            snakeBody.get(i).setY(snakeBody.get(i + 1).getY());
        }
        int lastX = snakeBody.get(lastIndex).getX();
        int lastY = snakeBody.get(lastIndex).getY();
        snakeBody.get(lastIndex).setX(lastX + deltaResult.getX());
        snakeBody.get(lastIndex).setY(lastY + deltaResult.getY());

 ;
    }

    @Override
    public void increase(Move move) {
        Pair deltaResult = delta(move);
        snakeBody.add(
                new Pair(
                        snakeBody.get(snakeBody.size() - 1).getX() + deltaResult.getX(),
                        snakeBody.get(snakeBody.size() - 1).getY() + deltaResult.getY()
                )
        );
    }

    public Pair delta(Move move) {
        int dx = 0;
        int dy = 0;
        switch(move) {
            case LEFT -> {
                dx = -1;
                dy = 0;
            } case DOWN -> {
                dx = 0;
                dy = 1;
            } case RIGHT -> {
                dx = 1;
                dy = 0;
            } case UP -> {
                dx = 0;
                dy = -1;
            }
        }
        return new Pair(dx, dy);
    }



}
