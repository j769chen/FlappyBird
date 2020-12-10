import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class GameObject {
    private Image image;
    private double yPos;
    private double xPos;
    private double width;
    private double height;

    public GameObject (Image image, double yPos, double xPos, double width, double height) {
        this.image = image;
        this.yPos = yPos;
        this.xPos = xPos;
        this.width = width;
        this.height = height;
    }
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setyPos (double yPos) {
        this.yPos = yPos;
    }
    public void setxPos (double xPos) { this.xPos = xPos; }

    public double getyPos () {
        return yPos;
    }
    public double getxPos () {
        return xPos;
    }

    public double getHeight () {
        return height;
    }

    public double getWidth () {
        return width;
    }

    public Rectangle2D getBoundary () {
        return new Rectangle2D(xPos, yPos, width, height);
    }

    public boolean intersects (GameObject o) {
        return o.getBoundary().intersects(this.getBoundary());
    }

    public void render (GraphicsContext gc) {
        gc.drawImage(image, xPos, yPos);
    }

    abstract public void update (double velocity); // Bird only changes yPos, pipes only xPos
}
