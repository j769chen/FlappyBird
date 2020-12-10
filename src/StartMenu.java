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
        Pane startPane = new Pane();
        startPane.setPrefSize(MENU_WIDTH, MENU_HEIGHT);

        logo = new Image("assets/textures/title.png");
        String buttonStyle = "-fx-font: 14 Impact; -fx-font-weight: bold; -fx-base: rgb(255,145,0); -fx-text-fill: " +
                "rgb(255,255,255); -fx-border-width: 1; -fx-border-color: rgb(255,255,255)";

        canvas = new Canvas(logo.getWidth(), logo.getHeight());
        gc = canvas.getGraphicsContext2D();
        gc.drawImage(logo, 0, 0);
        canvas.relocate(50, 0);

        startButton = new Button("START");
        startButton.setStyle(buttonStyle);
        startButton.relocate(50, 200);
        startButton.setPrefSize(90,30);

        scoreButton = new Button("SCORE");
        scoreButton.setStyle(buttonStyle);
        scoreButton.relocate(160, 200);
        scoreButton.setPrefSize(90, 30);

        startPane.getChildren().addAll(canvas, startButton, scoreButton);

        getChildren().addAll(startPane);
    }

}
