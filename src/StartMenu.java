import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;

public class StartMenu extends Pane {
    private int MENU_WIDTH = 300;
    private int MENU_HEIGHT = 400;

    Canvas canvas;
    GraphicsContext gc;
    private Image logo;
    private Button startButton, scoreButton;

    public Button getStartButton() {
        return startButton;
    }

    public Button getScoreButton() {
        return scoreButton;
    }

    public StartMenu () {
        Pane menuPane = new Pane();

        logo = new Image("assets/textures/title.png");
        canvas = new Canvas(logo.getWidth(), logo.getHeight());
        gc = canvas.getGraphicsContext2D();
        gc.drawImage(logo, 0, 0);
        canvas.relocate(50, 0);

        startButton = new Button("START");
        startButton.setStyle("-fx-font: 14 Impact; -fx-font-weight: bold; -fx-base: rgb(255,145,0); " +
                "-fx-text-fill: rgb(255,255,255); -fx-border-width: 1; -fx-border-color: rgb(255,255,255)");
        startButton.relocate(50, 200);
        startButton.setPrefSize(90,30);

        scoreButton = new Button("SCORE");
        scoreButton.setStyle("-fx-font: 14 Impact; -fx-font-weight: bold; -fx-base: rgb(255,145,0); " +
                "-fx-text-fill: rgb(255,255,255); -fx-border-width: 1; -fx-border-color: rgb(255,255,255)");
        scoreButton.relocate(160, 200);
        scoreButton.setPrefSize(90, 30);

        menuPane.getChildren().addAll(canvas, startButton, scoreButton);

        getChildren().addAll(menuPane);
    }

}
