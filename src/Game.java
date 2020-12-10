import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Random;

public class Game extends Application {
    protected final static int OBSTACLE_VELOCITY = -1;
    private int score = 0;
    Bird bird;
    Floor floor;
    PipeQueue topPipes;
    PipeQueue bottomPipes;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane aPane = new Pane();
        GameView root = new GameView();
        aPane.getChildren().add(root);

        final boolean hitSpace[] = {false};
        final boolean[] inGame = {false};

        topPipes = new PipeQueue();
        bottomPipes = new PipeQueue();

        primaryStage.setTitle("Flappy Bird");
        Scene scene = new Scene(aPane);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, k -> { // Prevents user from accidentally starting game using spacebar in menus
            if ( k.getCode() == KeyCode.SPACE && !inGame[0]){
                k.consume();
            }
        });

        AnimationTimer menuLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                floor.update(OBSTACLE_VELOCITY);
                root.menuRender(bird, floor);
            }
        };

        AnimationTimer onGameOver = new AnimationTimer() {
            @Override
            public void handle(long l) {
                double t = 0.3;
                bird.applyGravity(t);
                bird.update(bird.getyVelocity());
                root.render(bird, floor, topPipes, bottomPipes);
                if (bird.intersects(floor)) {
                    this.stop();
                }
            }
        };

        menuLoop.start();
        resetGameObjects(root);

        AnimationTimer gameTimer = new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                double t = 0.3;
                if (hitSpace[0]) {
                    bird.jump();
                    t = 0.3;
                    hitSpace[0] = false;
                }

                bird.applyGravity(t);
                bird.update(bird.getyVelocity());
                floor.update(OBSTACLE_VELOCITY);
                if (topPipes.peek().getxPos() == -360) {
                    generatePipes(topPipes, bottomPipes, root);
                }
                topPipes.updateAll();
                bottomPipes.updateAll();
                root.render(bird, floor, topPipes, bottomPipes);

                addScore(root, topPipes, bird);

                for (int i = 0; i < topPipes.size(); i ++) {
                    if (bird.intersects(topPipes.pipes.get(i)) || bird.intersects(bottomPipes.pipes.get(i))) {
                        this.stop();
                        inGame[0] = false;
                        onGameOver.start();
                        root.showGameOver();
                    }
                }

                if (bird.intersects(floor)) {
                    this.stop();
                    inGame[0] = false;
                    root.showGameOver();
                }
                else if (bird.getyPos() < 0) {
                    this.stop();
                    inGame[0] = false;
                    onGameOver.start();
                    root.showGameOver();
                }
            }
        };

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.SPACE) {
                    hitSpace[0] = true;
                }
            }
        });

        root.menu.getStartButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                root.startGame();
                menuLoop.stop();
                gameTimer.start();
                inGame[0] = true;
            }
        });

        root.endMenu.getResetButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                root.resetGameView();
                resetGameObjects(root);
                menuLoop.start();
            }
        });
    }

    public void resetGameObjects (GameView root) { //sets all game objects to starting game state
        bird = new Bird(root.birdImage, 300, root.birdImage.getWidth(), root.birdImage.getHeight(), 0);
        floor = new Floor(root.floorImage, 0, root.floorImage.getWidth(), root.floorImage.getHeight());
        score = 0;

        if (!topPipes.isEmpty()) {
            topPipes.clear();
        }
        if (!bottomPipes.isEmpty()) {
            bottomPipes.clear();
        }

        initializePipes(topPipes, bottomPipes, root);
    }

    public void addPipes (PipeQueue top, PipeQueue bottom, GameView root, int xPos, int pipeNum) { // Helper function to add pipes to both top and bottom queues
        top.add(new Pipe(root.topPipes.get(pipeNum), 0, xPos, root.topPipes.get(pipeNum).getWidth(),
                root.topPipes.get(pipeNum).getHeight()));
        bottom.add(new Pipe(root.bottomPipes.get(pipeNum), 600 - root.bottomPipes.get
                (pipeNum).getHeight(), xPos, root.bottomPipes.get(pipeNum).getWidth(),
                root.bottomPipes.get(pipeNum).getHeight()));
    }

    public void generatePipes (PipeQueue top, PipeQueue bottom, GameView root) { // When first pipe in queue gets too far, it is removed and then a new pipe is added at the back of the queue
        Random generator = new Random();
        int pipeToGenerate = generator.nextInt(5);

        addPipes(top, bottom, root, 1000, pipeToGenerate);

        top.remove();
        bottom.remove();
    }

    public void initializePipes(PipeQueue top, PipeQueue bottom, GameView root) { // Fills the pipe queue with 7 pipes to begin
        Random randInt = new Random();
        int pipeToGenerate;

        for (int initialX = 400; initialX <= 1600; initialX += 200) {
            pipeToGenerate = randInt.nextInt(5);
            addPipes(top, bottom, root, initialX, pipeToGenerate);
        }
    }

    public void addScore(GameView root, PipeQueue top, Bird bird) {
        for (int i = 0; i < top.pipes.size(); i++) {
            if (top.pipes.get(i).getxPos() + top.pipes.get(i).getWidth() == bird.getxPos()) {
                score++;
                root.updateScoreLabel(score);
            }
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
