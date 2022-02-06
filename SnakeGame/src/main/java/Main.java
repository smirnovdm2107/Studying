import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class Main extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int rows = 20;
    private static final int cols = 20;
    private static final int SQUARE_WIDTH = WIDTH / rows;
    private static final int SQUARE_HEIGHT = HEIGHT / cols;

    private static final Image cherryImage = new Image("/images/cherry.png");
    private static final Image bodyImage = new Image("/images/snakeBody.png");
    private static final Image headLeftImage = new Image("/images/headLeft.png");
    private static final Image headRightImage = new Image("/images/headRight.png");
    private static final Image headUpImage = new Image("/images/headUp.png");
    private static final Image headDownImage = new Image("/images/headDown.png");

    private Move direction = Move.valueOf("DOWN");
    private static Snake snake = new DefaultSnake(WIDTH / SQUARE_WIDTH, HEIGHT / SQUARE_HEIGHT, 4);
    private static final Pair food = new Pair(1, 1);

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        final Snake snake = new DefaultSnake(WIDTH, HEIGHT, 4);

        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        drawBackground(gc);
        drawSnake(gc);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.D) {
                    if (direction != Move.LEFT) {
                        direction = Move.RIGHT;
                    }
                } else if (event.getCode() == KeyCode.A) {
                    if (direction != Move.RIGHT) {
                        direction = Move.LEFT;
                    }
                } else if (event.getCode() == KeyCode.W) {
                    if (direction != Move.DOWN) {
                        direction = Move.UP;
                    }
                } else if (event.getCode() == KeyCode.S) {
                    if (direction != Move.UP) {
                        direction = Move.DOWN;
                    }
                }
            }
        });
        changeFoodPosition();
        drawFood(gc);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(140), e -> updateGame(gc)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void drawBackground(GraphicsContext gc) {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if ((i + j) % 2 == 0) {
                    gc.setFill(Color.GREEN);
                } else {
                    gc.setFill(Color.LIMEGREEN);

                }
                gc.fillRect(SQUARE_WIDTH * i, SQUARE_HEIGHT * j, SQUARE_WIDTH, SQUARE_HEIGHT);
            }
        }
    }

    private void updateGame(GraphicsContext gc) {
        if (!isValid()) {
            return;
        }
        drawBackground(gc);
        if (snake.getHeadPosition().getY() + snake.delta(direction).getY() == food.getY()
                && snake.getHeadPosition().getX() + snake.delta(direction).getX() == food.getX()) {
            snake.increase(direction);
            changeFoodPosition();
        } else {
            snake.makeMove(direction);
        }
        drawFood(gc);
        drawSnake(gc);

    }

    private void drawSnake(GraphicsContext gc) {
        Pair headCoordinates = snake.getHeadPosition();
        Image headNow = null;
        switch (direction) {
            case UP -> headNow = headUpImage;
            case DOWN -> headNow = headDownImage;
            case LEFT -> headNow = headLeftImage;
            case RIGHT -> headNow = headRightImage;
        }
        gc.drawImage(
                headNow,headCoordinates.getX()*SQUARE_WIDTH, headCoordinates.getY()*SQUARE_HEIGHT, SQUARE_WIDTH, SQUARE_HEIGHT
        );

        Pair[] tailCoordinates = snake.getSnakePosition();
        for (int i = 0; i < tailCoordinates.length - 1; i++) {
            gc.drawImage(bodyImage, tailCoordinates[i].getX()*SQUARE_WIDTH, tailCoordinates[i].getY()*SQUARE_HEIGHT, SQUARE_WIDTH, SQUARE_HEIGHT);
        }
    }

    private void drawFood(GraphicsContext gc) {
        gc.drawImage(cherryImage, food.getX() * SQUARE_WIDTH, food.getY() * SQUARE_HEIGHT, SQUARE_WIDTH, SQUARE_HEIGHT);
    }

    private void changeFoodPosition() {
        Random random = new Random();
        int foodX = random.nextInt(rows);
        int foodY = random.nextInt(cols);
        food.setX(foodX);
        food.setY(foodY);
        Pair[] snakeBody = snake.getSnakePosition();
        while(isIn(snakeBody, foodX, foodY)) {
            foodX = random.nextInt(rows);
            foodY = random.nextInt(cols);
        }
        food.setX(foodX);
        food.setY(foodY);
    }

    private boolean isIn(Pair[] snakeBody, int x, int y) {
        boolean answer = false;
        for (int i = 0; i < snakeBody.length; i++) {
            if (snakeBody[i].getX() == x && snakeBody[i].getY() == y) {
                answer = true;
                break;
            }
        }
        return answer;
    }

    boolean isValid() {
        Pair headPosition = snake.getHeadPosition();
        Pair delta = snake.delta(direction);
        int nextX = headPosition.getX() + delta.getX();
        int nextY =  headPosition.getY() + delta.getY();
        if (
                nextY < 0
                        || nextY >= cols
                        || nextX < 0
                        || nextX >= rows
        ) {
            return false;
        }

        Pair[] snakeBody = snake.getSnakePosition();
        for (int i = 0; i < snakeBody.length - 1; i++) {
            if (snakeBody[i].getX() == nextX && snakeBody[i].getY() == nextY) {
                return false;
            }
        }
        return true;
    }

}
