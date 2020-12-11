
import java.util.ArrayList;

//ArrayList Implementation of queue for the pipes
public class Queue implements QueueInterface {
    private ArrayList<Object> items;

    public ArrayList<Object> getItems() {
        return items;
    }

    public Queue() {
        items = new ArrayList<>();
    }

    public void add (Object o) {
        items.add(0, o);
    }

    public Object remove () {
        return items.remove(items.size()-1);
    }

    public Object peek () {
        return items.get(items.size()-1);
    }

    public int size () {
        return items.size();
    }

    public boolean isEmpty () {
        return size() == 0;
    }

    public void clear () {
        items.clear();
    }

    public void printAll () {
        for (int i = 0; i < 5; i++) {
            System.out.println(items.get(i));
        }
    }
    public void updateAll () { // Special method for Pipes to update every pipe's location
        for (Object o: items) {
            ((Pipe)o).update(Game.OBSTACLE_VELOCITY);
        }
    }
}
