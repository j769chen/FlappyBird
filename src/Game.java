import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class Game extends Application {
    protected final static int OBSTACLE_VELOCITY = -1;
    private int score = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane aPane = new Pane();
        GameView root = new GameView();
        aPane.getChildren().add(root);

        final boolean hitSpace[] = {false};

        Bird bird = new Bird(root.birdImage, 300, root.birdImage.getWidth(), root.birdImage.getHeight(), 0);
        Floor floor = new Floor(root.floorImage, 0, root.floorImage.getWidth(), root.floorImage.getHeight());
        PipeQueue topPipes = new PipeQueue();
        PipeQueue bottomPipes = new PipeQueue();

        primaryStage.setTitle("Flappy Bird");
        Scene scene = new Scene(aPane);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        initialize(topPipes, bottomPipes, root);
        root.initialRender(bird, floor);

        AnimationTimer menuLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                floor.update(OBSTACLE_VELOCITY);
                root.menuRender(bird, root.returnBackground(), floor);
            }
        };

        menuLoop.start();

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
                // background image clears canvas
                root.render(bird, root.returnBackground(), floor, topPipes, bottomPipes);

                addScore(root, topPipes, bird);

                for (int i = 0; i < topPipes.size(); i ++) {
                    if (bird.intersects(topPipes.pipes.get(i)) || bird.intersects(bottomPipes.pipes.get(i))) {
                        this.stop();
                    }
                }

                if (bird.intersects(floor)) {
                    this.stop();
                }
            }
        };

//        gameTimer.start();


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
                root.getChildren().remove(root.menu);
                menuLoop.stop();
                root.getScoreLabel().setVisible(true);
                gameTimer.start();
            }
        });
    }
    public void addPipes (PipeQueue top, PipeQueue bottom, GameView root, int xPos, int pipeNum) {
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

    public void initialize(PipeQueue top, PipeQueue bottom, GameView root) { // Fills the pipe queue with 7 pipes to begin
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
