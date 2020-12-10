import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GameOverMenu extends Pane {
    private int MENU_WIDTH = 300;
    private int MENU_HEIGHT = 500;

    private Label scoreLabel, scoreNum, bestScoreLabel, bestScoreNum;

    public Label getScoreLabel() {
        return scoreLabel;
    }

    Canvas canvas;
    GraphicsContext gc;
    private Image gameOver;
    private Button resetButton, scoreButton;

    public Button getResetButton () {
        return resetButton;
    }

    public Button getScoreButton () {
        return scoreButton;
    }

    public GameOverMenu () {
        Pane gameOverPane = new Pane();
        gameOverPane.setPrefSize(MENU_WIDTH, MENU_HEIGHT);
        int scoreCardWidth = 90;
        int scoreCardHeight = 150;

        gameOver = new Image("assets/textures/game_over.png");
        String buttonStyle = "-fx-font: 14 Impact; -fx-font-weight: bold; -fx-base: rgb(255,145,0); -fx-text-fill: " +
                "rgb(255,255,255); -fx-border-width: 1; -fx-border-color: rgb(255,255,255)";

        String labelStyle = "-fx-font: 16 Impact; -fx-font-weight: bold;  -fx-text-fill: rgb(255,145,0)";

        canvas = new Canvas(gameOver.getWidth(), gameOver.getHeight() + 50 + scoreCardHeight);
        gc = canvas.getGraphicsContext2D();

        gc.drawImage(gameOver, 0, 0);
        canvas.relocate(50, 0);

        gc.setFill(Color.rgb(238,224,168));
        gc.fillRect(55, 100, scoreCardWidth, scoreCardHeight);

        scoreLabel = new Label("SCORE");
        scoreLabel.setStyle(labelStyle);

        scoreLabel.relocate(130, 110);

        gameOverPane.getChildren().addAll(canvas, scoreLabel);

        getChildren().addAll(gameOverPane);
    }
}
