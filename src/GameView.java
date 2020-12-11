import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends Pane {
    private final int APP_WIDTH = 400;
    private final int APP_HEIGHT = 700;
    private Canvas canvas;
    private GraphicsContext gc;
    private StartMenu menu;
    private GameOverMenu endMenu;
    private ScoreMenu scoreMenu;
    private Label scoreLabel;

    private Image birdImage = new Image("assets/textures/birdSprite1.png");
    private Image floorImage = new Image("assets/textures/floor.png");
    private Image background = setBackground();

    private ArrayList<Image> topPipes = loadPipes(true);
    private ArrayList<Image> bottomPipes = loadPipes(false);

    public Image getBirdImage () {
        return birdImage;
    }

    public Image getFloorImage () {
        return floorImage;
    }

    public ArrayList<Image> getTopPipes() {
        return topPipes;
    }

    public ArrayList<Image> getBottomPipes() {
        return bottomPipes;
    }

    public StartMenu getStartMenu () {
        return menu;
    }

    public GameOverMenu getEndMenu() {
        return endMenu;
    }

    public ScoreMenu getScoreMenu() {
        return scoreMenu;
    }

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

        scoreMenu = new ScoreMenu();
        scoreMenu.relocate(125, 170);
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

    public void menuRender (Bird bird, Floor floor) { // render loop for the start menu, not rendering pipes
        bird.animate();
        gc.drawImage(background, 0, 0);
        gc.drawImage(floorImage, floor.getxPos(), floor.getyPos());
        gc.drawImage(bird.getImage(), 50, bird.getyPos());
    }

    public void render (Bird bird, Floor floor, Queue top, Queue bottom) { // Function to render during gameplay, updates locations of all GameObjects
        bird.animate();
        gc.drawImage(background, 0, 0);
        gc.drawImage(floor.getImage(), floor.getxPos(), floor.getyPos());
        for (Object o: top.getItems()) {
            Pipe p = (Pipe) o;
            gc.drawImage(p.getImage(), p.getxPos(), p.getyPos());
        }

        for (Object o: bottom.getItems()) {
            Pipe p = (Pipe) o;
            gc.drawImage(p.getImage(), p.getxPos(), p.getyPos());
        }
        gc.drawImage(bird.getImage(), 50, bird.getyPos());
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
        scoreLabel.setText("0");
        scoreLabel.setVisible(true);
    }

    public void showGameOver (int score, int bestScore) {
       scoreLabel.setVisible(false);
       endMenu.setScoreNum(score);
       endMenu.setBestScoreNum(bestScore);
       getChildren().addAll(endMenu);
    }

    public void resetGameView () {
        getChildren().remove(endMenu);
        getChildren().remove(scoreMenu);
        getChildren().addAll(menu);
    }

    public void goToScoreMenu () {
        if (getChildren().contains(endMenu)) {
            getChildren().remove(endMenu);
        }
        if (getChildren().contains(menu)) {
            getChildren().remove(menu);
        }

        getChildren().addAll(scoreMenu);
    }
}
