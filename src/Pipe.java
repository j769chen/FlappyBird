import javafx.scene.image.Image;

public class Pipe extends GameObject  {
    private double xVelocity;
    public Pipe(Image image, double yPos, double xPos, double width, double height) {
        super(image, yPos, xPos , width, height);
    }

    public void update(double xVelocity) {
        setxPos(this.getxPos() + xVelocity);
    }
}
