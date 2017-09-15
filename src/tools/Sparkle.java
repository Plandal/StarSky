package tools;

import data.StageSet;

/**
 * Created by senorihl on 14/03/16.
 */
public class Sparkle {

    private Position position;
    private StageSet.SPARKLE_SPEED speed;

    public Sparkle(Position position) {
        this.position = position;
        this.speed = StageSet.SPARKLE_SPEED.MEDIUM;
    }

    public Sparkle(Position position, StageSet.SPARKLE_SPEED speed) {
        this.position = position;
        this.speed = speed;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public StageSet.SPARKLE_SPEED getSpeed() {
        return speed;
    }

    public void setSpeed(StageSet.SPARKLE_SPEED speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Sparkle{" +
                "position=" + position +
                ", speed=" + speed +
                '}';
    }
}
