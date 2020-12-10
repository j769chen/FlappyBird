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

    public void setScoreNum (int score) {
        scoreNum.setText(Integer.toString(score));
    }
    public void setBestScoreNum (int bestScore) { bestScoreNum.setText(Integer.toString(bestScore));}

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
        int scoreCardWidth = 90;
        int scoreCardHeight = 150;

        gameOver = new Image("assets/textures/game_over.png");

        String buttonStyle = "-fx-font: 14 Impact; -fx-font-weight: bold; -fx-base: rgb(255,145,0); -fx-text-fill: " +
                "rgb(255,255,255); -fx-border-width: 1; -fx-border-color: rgb(255,255,255)";
        String labelStyle = "-fx-font: 16 Impact; -fx-font-weight: bold;  -fx-text-fill: rgb(255,145,0)";
        String numStyle = "-fx-font: 30 Impact; -fx-font-weight: bold;  -fx-text-fill: rgb(255,255,255)";

        canvas = new Canvas(gameOver.getWidth(), gameOver.getHeight() + 50 + scoreCardHeight);
        gc = canvas.getGraphicsContext2D();

        gc.drawImage(gameOver, 0, 0);
        canvas.relocate(50, 0);

        gc.setFill(Color.rgb(238,224,168));
        gc.fillRect(55, 100, scoreCardWidth, scoreCardHeight);

        scoreLabel = new Label("SCORE");
        scoreLabel.setStyle(labelStyle);
        scoreLabel.relocate(130, 110);

        scoreNum = new Label("0");
        scoreNum.setStyle(numStyle);
        scoreNum.relocate(140, 130);

        bestScoreLabel = new Label("BEST");
        bestScoreLabel.setStyle(labelStyle);
        bestScoreLabel.relocate(135, 170);

        bestScoreNum = new Label("0");
        bestScoreNum.setStyle(numStyle);
        bestScoreNum.relocate(140, 190);

        resetButton = new Button("RESTART");
        resetButton.setStyle(buttonStyle);
        resetButton.relocate(50, 300);
        resetButton.setPrefSize(90,30);

        scoreButton = new Button("SCORE");
        scoreButton.setStyle(buttonStyle);
        scoreButton.relocate(160, 300);
        scoreButton.setPrefSize(90, 30);

        gameOverPane.getChildren().addAll(canvas, scoreLabel, scoreNum, bestScoreLabel, bestScoreNum, resetButton,
                scoreButton);

        getChildren().addAll(gameOverPane);
    }
}
