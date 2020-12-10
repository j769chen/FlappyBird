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
    GameOverMenu endMenu;
    private Label scoreLabel;

    Image birdImage = new Image("assets/textures/birdSprite1.png");
    Image floorImage = new Image("assets/textures/floor.png");
    Image background = setBackground();

    ArrayList<Image> topPipes = loadPipes(true);
    ArrayList<Image> bottomPipes = loadPipes(false);


    public GameView () {
        scoreLabel = new Label("0");
        scoreLabel.relocate(175, 60);
        scoreLabel.setMinSize(100, 100);
        scoreLabel.setStyle("-fx-max-width: 50; -fx-max-height: 50; -fx-font: 50 Impact; -fx-font-weight: bold; " +
                "-fx-text-fill: rgb(255,255,255);");
        scoreLabel.setVisible(false);

        canvas = new Canvas(APP_WIDTH, APP_HEIGHT);

        gc = canvas.getGraphicsContext2D();
        gc.drawImage(background, 0, 0);
        setPrefSize(APP_WIDTH, APP_HEIGHT);

        menu = new StartMenu();
        menu.relocate(50, 150);

        endMenu = new GameOverMenu();
        endMenu.relocate(50, 150);

        getChildren().addAll(canvas, menu, scoreLabel);
    }

    public Label getScoreLabel () {
        return  scoreLabel;
    }

    public void updateScoreLabel (int score) {
        scoreLabel.setText(Integer.toString(score));
    }

    public Image returnBackground () {
        return background;
    }

    public void initialRender (Bird bird, Floor floor) { // Draws all static items once for start menu
        gc.drawImage(floorImage, floor.getxPos(), floor.getyPos());
        gc.drawImage(birdImage, bird.getxPos(), bird.getyPos());
    }

    public void menuRender (Bird bird, Floor floor) { // render loop for the start menu
        bird.animate();
        gc.drawImage(background, 0, 0);
        gc.drawImage(floor.getImage(), floor.getxPos(), floor.getyPos());
        gc.drawImage(bird.getImage(), 50, bird.getyPos());
    }

    public void render (Bird bird, Floor floor, PipeQueue top, PipeQueue bottom) { // Function to render during gameplay, updates locations of all GameObjects

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


    public Image setBackground () { // Randomly sets the background to daytime or nighttime on load
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

    public ArrayList<Image> loadPipes (boolean top) { // Loads the possible pipe images from the textures folder
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

    public void startGame () {
        getChildren().remove(menu);
        scoreLabel.setVisible(true);
    }

    public void showGameOver () {
       getChildren().remove(scoreLabel);
       getChildren().addAll(endMenu);
    }
}
