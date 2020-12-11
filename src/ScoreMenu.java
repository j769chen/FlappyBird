import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ScoreMenu extends Pane {
    private int MENU_WIDTH = 150;
    private int MENU_HEIGHT = 250;


    private Label scoreLabel, score1, score2, score3, score4, score5;

    private Label[] scores;

    private Canvas canvas;
    private GraphicsContext gc;

    private Button resetButton;

    public Button getResetButton() {
        return resetButton;
    }

    public void setScoreboard(ArrayList<Integer> scores) {
        for (int i = 0; i < this.scores.length; i++) {
            this.scores[i].setText(Integer.toString(scores.get(i)));
        }
    }

    public ScoreMenu () {
        Pane scorePane = new Pane();

        String labelStyle = "-fx-font: 30 Impact; -fx-font-weight: bold;  -fx-text-fill: rgb(255,145,0)";
        String numStyle = "-fx-font: 30 Impact; -fx-font-weight: bold;  -fx-text-fill: rgb(255,255,255)";
        String buttonStyle = "-fx-font: 14 Impact; -fx-font-weight: bold; -fx-base: rgb(255,145,0); -fx-text-fill: " +
                "rgb(255,255,255); -fx-border-width: 1; -fx-border-color: rgb(255,255,255)";

        canvas = new Canvas(MENU_WIDTH, MENU_HEIGHT);
        gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.rgb(238,224,168));
        gc.fillRect(0, 0, MENU_WIDTH, MENU_HEIGHT);

        scoreLabel = new Label("TOP SCORES");
        scoreLabel.setStyle(labelStyle);
        scoreLabel.relocate(5, 20);

        score1 = new Label("0");
        score1.setStyle(numStyle);
        score1.relocate(65, 60);

        score2 = new Label("0");
        score2.setStyle(numStyle);
        score2.relocate(65, 95);

        score3 = new Label("0");
        score3.setStyle(numStyle);
        score3.relocate(65, 130);

        score4 = new Label("0");
        score4.setStyle(numStyle);
        score4.relocate(65, 165);

        score5 = new Label("0");
        score5.setStyle(numStyle);
        score5.relocate(65, 200);

        scores = new Label[]{score1, score2, score3, score4, score5};

        resetButton = new Button("BACK");
        resetButton.setStyle(buttonStyle);
        resetButton.relocate(30, 270);
        resetButton.setPrefSize(90,30);

        scorePane.getChildren().addAll(canvas, scoreLabel, score1, score2, score3, score4, score5, resetButton);

        getChildren().addAll(scorePane);
    }
}
