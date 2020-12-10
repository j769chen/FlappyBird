import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends Pane {
    private int APP_WIDTH = 400;
    private int APP_HEIGHT = 700;
    Canvas canvas;
    GraphicsContext gc;
    StartMenu menu;
    private Label scoreLabel;

    Image birdImage = new Image("assets/textures/birdSprite1.png");
    Image floorImage = new Image("assets/textures/floor.png");
    Image background = setBackground();

    ArrayList<Image> topPipes = loadPipes(true);
    ArrayList<Image> bottomPipes = loadPipes(false);


    public GameView () {
        System.out.println(javafx.scene.text.Font.getFamilies());
        scoreLabel = new Label();
        scoreLabel.setText("0");
        scoreLabel.relocate(190, 60);
        scoreLabel.setStyle("-fx-max-width: 50; -fx-max-height: 50; -fx-font: 50 Impact; -fx-font-weight: bold; " +
                "-fx-text-fill: rgb(255,255,255);");
        scoreLabel.setVisible(false);

        canvas = new Canvas(APP_WIDTH, APP_HEIGHT);

        gc = canvas.getGraphicsContext2D();
        gc.drawImage(background, 0, 0);
        setPrefSize(APP_WIDTH, APP_HEIGHT);

        menu = new StartMenu();
        menu.relocate(50, 150);

        getChildren().addAll(canvas, menu, scoreLabel);
    }

    public Label getScoreLabel () {
        return  scoreLabel;
    }
    public Image returnBackground () {
        return background;
    }

    public void initialRender (Bird bird, Floor floor) {
        gc.drawImage(floorImage, floor.getxPos(), floor.getyPos());
        gc.drawImage(birdImage, bird.getxPos(), bird.getyPos());
    }

    public void menuRender (Bird bird, Image background, Floor floor) {
        bird.animate();
        gc.drawImage(background, 0, 0);
        gc.drawImage(floor.getImage(), floor.getxPos(), floor.getyPos());
        gc.drawImage(bird.getImage(), 50, bird.getyPos());
    }

    public void render (Bird bird, Image background, Floor floor, PipeQueue top, PipeQueue bottom) {

        bird.animate();
        gc.drawImage(background, 0, 0);
        gc.drawImage(floor.getImage(), floor.getxPos(), floor.getyPos());
        gc.drawImage(bird.getImage(), 50, bird.getyPos());
        for (Pipe p: top.pipes) {
            gc.drawImage(p.getImage(), p.getxPos(), p.getyPos());
        }

        for (Pipe p: bottom.pipes) {
            gc.drawImage(p.getImage(), p.getxPos(), p.getyPos());
        }
    }


    public Image setBackground () {
        Random random = new Random();
        int randomBG = random.nextInt(2);
        String filePath;
        if (randomBG == 0) {
            filePath = "assets/textures/background.png";
        }
        else {
            filePath = "assets/textures/background_night.png";
        }

        Image background = new Image(filePath);

        return background;
    }

    public ArrayList<Image> loadPipes (boolean top) {
        ArrayList<Image> images = new ArrayList<>();
        String pipeType = "bottom";

        if (top) {
           pipeType = "top";
        }

        for (int i = 1; i <= 5; i++) {
            images.add(new Image("assets/textures/" + pipeType + "Pipe" + i + ".png"));
        }

        return images;
    }

    public void updateScoreLabel (int score) {
        scoreLabel.setText(Integer.toString(score));
    }
}
