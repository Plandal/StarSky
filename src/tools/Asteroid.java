package tools;

/**
 * Created by senorihl on 14/03/16.
 */
public class Asteroid {

    private String imagePath;
    private Position vector;
    private Position position;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Position getVector() {
        return vector;
    }

    public void setVector(Position vector) {
        this.vector = vector;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Asteroid{" +
                "imagePath='" + imagePath + '\'' +
                ", vector=" + vector +
                ", position=" + position +
                '}';
    }

    public void move() {
        this.position = new Position( this.position.x + this.vector.x, this.position.y + this.vector.y );
    }
}
