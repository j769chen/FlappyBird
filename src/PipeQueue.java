//ArrayList Implementation of queue for the pipes
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.util.ArrayList;
import java.util.Random;

public class PipeQueue implements QueueInterface {
    ArrayList<Pipe> pipes;

    public PipeQueue() {
        pipes = new ArrayList<>();
    }

    public void add(Pipe p) {
        pipes.add(0, p);
    }

    public Pipe remove() {
        return pipes.remove(pipes.size()-1);
    }

    public Pipe peek() {
        return pipes.get(pipes.size()-1);
    }

    public int size() {
        return pipes.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void clear() {
        pipes.clear();
    }

    public void updateAll () {
        for (Pipe p: pipes) {
            p.update(Game.OBSTACLE_VELOCITY);
        }
    }
}
