import javafx.scene.image.Image;

public class Floor extends GameObject {
    private final static int FLOOR_YPOS = 600;
    public Floor (Image image, double xPos, double width, double height) {
        super(image, FLOOR_YPOS, xPos, width, height);
    }

    public void update (double xVelocity) {
        setxPos(this.getxPos() + xVelocity);

        if (this.getxPos() < -200) {
            this.setxPos(0);
        }
    }
}
