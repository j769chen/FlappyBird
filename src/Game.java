import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;
import java.io.*;

public class Game extends Application {
    protected final static int OBSTACLE_VELOCITY = -1;
    private final static int MAX_SCORES = 5;
    private int score = 0;
    private final String scoreFilePath = "src/assets/topScores.txt";

    private Bird bird;
    private Floor floor;
    private Queue topPipes;
    private Queue bottomPipes;
    private ArrayList<Integer> topScores;

    private String audioFilePath = "assets/audio/";

    private Sound fly, buttonClick, point, hit, die;
    private boolean hitSpace;
    private boolean inGame;

    @Override
    public void start (Stage primaryStage) throws Exception {
        Pane aPane = new Pane();
        GameView root = new GameView();
        aPane.getChildren().add(root);

        hitSpace = false;
        inGame = false;

        topPipes = new Queue();
        bottomPipes = new Queue();

        topScores = new ArrayList<>();
        readScores();

        setSounds();

        if (topScores.isEmpty()) {
            initializeEmptyScores();
        }

        primaryStage.setTitle("Flappy Bird");
        Scene scene = new Scene(aPane);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        resetGameObjects(root);

        AnimationTimer menuLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                root.menuRender(bird, floor);
                floor.update(OBSTACLE_VELOCITY);

            }
        };

        AnimationTimer onGameOver = new AnimationTimer() {
            @Override
            public void handle(long l) {
                double t = 0.3;
                bird.applyGravity(t);
                bird.update(bird.getyVelocity());
                root.render(bird, floor, topPipes, bottomPipes
                );
                if (bird.intersects(floor)) {
                    this.stop();
                }
            }
        };

        menuLoop.start();


        AnimationTimer gameTimer = new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                double t = 0.3;
                if (hitSpace) {
                    bird.jump();
                    t = 0.3;
                    hitSpace = false;
                }

                bird.applyGravity(t);
                bird.update(bird.getyVelocity());
                floor.update(OBSTACLE_VELOCITY);
                if (((Pipe)topPipes.peek()).getxPos() == -360) {
                    generatePipes(topPipes, bottomPipes, root);
                }
                topPipes.updateAll();
                bottomPipes.updateAll();
                root.render(bird, floor, topPipes, bottomPipes);

                addScore(root, topPipes, bird);

                for (int i = 0; i < topPipes.size(); i ++) {
                    if (bird.intersects((Pipe)topPipes.getItems().get(i)) || bird.intersects((Pipe)bottomPipes.getItems()
                            .get(i))) {
                        this.stop();
                        hit.play();
                        die.play();
                        inGame = false;
                        onGameOver.start();
                        updateTopScores();
                        root.showGameOver(score, topScores.get(0));
                    }
                }

                if (bird.intersects(floor)) {
                    this.stop();
                    hit.play();
                    inGame = false;
                    updateTopScores();
                    root.showGameOver(score, topScores.get(0));
                }
                else if (bird.getyPos() < 0) {
                    this.stop();
                    hit.play();
                    die.play();
                    inGame = false;
                    onGameOver.start();
                    updateTopScores();
                    root.showGameOver(score, topScores.get(0));
                }
            }
        };

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.SPACE) {
                    hitSpace = true;
                    fly.play();
                }
            }
        });

        root.getStartMenu().getStartButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                buttonClick.play();
                root.startGame();
                menuLoop.stop();
                gameTimer.start();
                inGame = true;
            }
        });

        root.getEndMenu().getResetButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                buttonClick.play();
                root.resetGameView();
                resetGameObjects(root);
                menuLoop.start();
            }
        });

        root.getStartMenu().getScoreButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                buttonClick.play();
                root.goToScoreMenu();
                setLeaderBoard(root);
            }
        });

        root.getEndMenu().getScoreButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                buttonClick.play();
                root.goToScoreMenu();
                setLeaderBoard(root);
            }
        });

        root.getScoreMenu().getResetButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                buttonClick.play();
                root.resetGameView();
                resetGameObjects(root);
                menuLoop.start();
            }
        });

        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, k -> { // Prevents user from accidentally starting game using spacebar in menus
            if (k.getCode() == KeyCode.SPACE && !inGame){
                k.consume();
            }
        });
    }

    public void resetGameObjects (GameView root) { //sets all game objects to starting game state
        bird = new Bird(root.getBirdImage(), 300, root.getBirdImage().getWidth(), root.getBirdImage().getHeight(),
                0);
        floor = new Floor(root.getFloorImage(), 0, root.getFloorImage().getWidth(),
                root.getFloorImage().getHeight());
        score = 0;

        if (!topPipes.isEmpty()) {
            topPipes.clear();
        }

        if (!bottomPipes.isEmpty()) {
            bottomPipes.clear();
        }

        initializePipes(topPipes, bottomPipes, root);
    }

    public void addPipes (Queue top, Queue bottom, GameView root, int xPos, int pipeNum) { // Helper function to add pipes to both top and bottom queues
        top.add(new Pipe(root.getTopPipes().get(pipeNum), 0, xPos, root.getTopPipes().get(pipeNum).getWidth(),
                root.getTopPipes().get(pipeNum).getHeight()));
        bottom.add(new Pipe(root.getBottomPipes().get(pipeNum), 600 - root.getBottomPipes().get
                (pipeNum).getHeight(), xPos, root.getBottomPipes().get(pipeNum).getWidth(),
                root.getBottomPipes().get(pipeNum).getHeight()));
    }

    public void generatePipes (Queue top, Queue bottom, GameView root) { // When first pipe in queue gets too far, it is removed and then a new pipe is added at the back of the queue
        Random generator = new Random();
        int pipeToGenerate = generator.nextInt(5);

        addPipes(top, bottom, root, 1000, pipeToGenerate);

        top.remove();
        bottom.remove();
    }

    public void initializePipes (Queue top, Queue bottom, GameView root) { // Fills the pipe queue with 7 pipes to begin
        Random randInt = new Random();
        int pipeToGenerate;

        for (int initialX = 400; initialX <= 1600; initialX += 200) {
            pipeToGenerate = randInt.nextInt(5);
            addPipes(top, bottom, root, initialX, pipeToGenerate);
        }
    }

    public void addScore (GameView root, Queue top, Bird bird) {  // Updates the score when bird passes through a pipe
        for (int i = 0; i < top.getItems().size(); i++) {
            if (((Pipe)top.getItems().get(i)).getxPos() + ((Pipe)top.getItems().get(i)).getWidth() == bird.getxPos()) {
                score++;
                root.updateScoreLabel(score);
                point.play();
            }
        }
    }

    public void initializeEmptyScores () { // If the score file is empty, initialize the array with 5 0 values
        for (int i = 0; i < 5; i ++) {
            topScores.add(0);
        }
    }

    public void readScores () { // Reads in scores from the score file
        try {
            BufferedReader in = new BufferedReader(new FileReader(scoreFilePath));

            while (in.ready()) {
                topScores.add(Integer.parseInt(in.readLine()));
            }

            in.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Score file could not be found");
        }
        catch (IOException e) {
            System.out.println("Error reading in scores");
        }
    }

    public void updateTopScoreFile () { //Helper function to write top scores to file
        try {
            PrintWriter out = new PrintWriter(new FileWriter(scoreFilePath));

            for (int i = 0; i < MAX_SCORES; i++) {
                out.println(topScores.get(i));
            }

            out.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Score file could not be found");
        }
        catch (IOException e) {
            System.out.println("Error writing scores to file");
        }
    }

    public void updateTopScores () { // Updates topScores ArrayList and file
        for (int i = 0; i < MAX_SCORES; i++) {
            if (score > topScores.get(i)) {
                topScores.add(i, score);
                topScores.remove(MAX_SCORES);
                break;
            }
        }
        updateTopScoreFile();
    }

    public void setLeaderBoard (GameView root) { //Sets values in leaderboard to corresponding values from Arraylist
        root.getScoreMenu().setScoreboard(topScores);
    }

    public void setSounds () {
        fly = new Sound(audioFilePath + "sfx_wing.wav");
        buttonClick = new Sound(audioFilePath + "sfx_swooshing.wav");
        point = new Sound(audioFilePath + "sfx_point.wav");
        hit = new Sound(audioFilePath + "sfx_hit.wav");
        die = new Sound(audioFilePath + "sfx_die.wav");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
