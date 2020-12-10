import javafx.scene.image.Image;

public class Bird extends GameObject {
    Image birdImage1 = new Image("assets/textures/birdSprite1.png");
    Image birdImage2 = new Image("assets/textures/birdSprite2.png");
    Image birdImage3 = new Image("assets/textures/birdSprite3.png");
    private final static double BIRD_XPOS = 50;
    private final static double GRAVITY = 1.25;
    private double yVelocity;

    public Bird (Image image, double yPos, double width, double height, double yVelocity) {
        super(image, yPos, BIRD_XPOS, width, height);

        this.yVelocity = yVelocity;
    }

    public double getyVelocity () {
        return yVelocity;
    }

    public void setYVelocity (double yVelocity) {
        this.yVelocity = yVelocity;
    }

    public void applyGravity (double time) {
        yVelocity += GRAVITY*0.5*Math.pow(time, 2);
    }

    public void jump () {
        setYVelocity(-2.5);
        update(yVelocity);
    }

    public void animate () {
        if(this.getImage() == birdImage1) {
            this.setImage(birdImage2);
        }
        else if (this.getImage() == birdImage2) {
            this.setImage(birdImage3);
        }
        else {
            this.setImage(birdImage1);
        }
    }

    @Override
    public void update (double yVelocity) {
        setyPos(this.getyPos() + yVelocity);
    }
}
