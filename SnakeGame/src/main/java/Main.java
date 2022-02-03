import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int rows = 20;
    private static final int cols = 20;
    private static final Image background1 = new Image("/images/polePattern1.jpg");
    private static final Image background2 = new Image("/images/polePattern2.jpg");
    private static final Image cherryImage = new Image("/images/cherry.jpg");
    private static final Image bodyImage = new Image("/images/snakeBody.jpg");
    private static final Image headLeftImage = new Image("/images/headLeft.jpg");
    private static final Image headRightImage = new Image("/images/headRight.jpg");
    private static final Image headUpImage = new Image("/images/headUp.jpg");
    private static final Image headDownImage = new Image("/images/headDown.jpg");
    public static void main(String args[]) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {

        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);


        stage.setScene(scene);
        stage.show();

        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if ((i + j) % 2 == 0) {
                    gc.drawImage(background1, WIDTH*i/20, HEIGHT*j/20,  WIDTH/20, HEIGHT/20);
                } else {
                    gc.drawImage(background2, WIDTH*i/20, HEIGHT*j/20, WIDTH/20, HEIGHT/20);
                }
            }
        }
    }
}
