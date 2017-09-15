package data;

import tools.Asteroid;
import tools.HardCodedParameters;
import tools.Position;
import tools.Sparkle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * Created by senorihl on 14/03/16.
 */
public class StageSet {

    private List<Sparkle> sparkles;
    private List<Asteroid> asteroids;
    public static int baseSparkleSpeed = HardCodedParameters.scrollingSpeed;
    private Random gen;

    public static Asteroid randomAsteroid() {
        Random rand = new Random();
        Asteroid asteroid = new Asteroid();
        asteroid.setImagePath("file:sprites/image/asteroid_" + (rand.nextInt(4) + 1) + ".png");

        boolean up = rand.nextBoolean();

        asteroid.setVector( new Position( rand.nextInt(3) * (rand.nextBoolean() ? 1 : -1) * 8 , (up ? rand.nextInt(4) : 0 - rand.nextInt(4)) * 4 ) );


        asteroid.setPosition( new Position(
                HardCodedParameters.defaultWidth * 2 / 3 + rand.nextInt(HardCodedParameters.defaultWidth / 3),
                up ? -10 : HardCodedParameters.defaultHeight + 10
        ) );

        double nextY = asteroid.getVector().y + asteroid.getPosition().y;

        if (nextY > HardCodedParameters.defaultHeight + 10 || nextY < -10)
            return randomAsteroid();

        return asteroid;

    }

    public List<Asteroid> getAsteroids() {
        return asteroids;
    }

    public void setAsteroids(List<Asteroid> asteroids) {
        this.asteroids = asteroids;
    }

    public void addAsteroid(Asteroid asteroid) {
        this.asteroids.add(asteroid);
    }

    public enum SPARKLE_SPEED { FAST, MEDIUM, LOW };

    public void init() {
        sparkles = new Vector<Sparkle>();
        asteroids = new Vector<Asteroid>();
        gen = new Random();
        initSparkles();
    }

    public List<Sparkle> getSparkles() {
        return sparkles;
    }

    public void addSparkle(Sparkle sparkle) {
        this.sparkles.add(sparkle);
    }

    public void removeSparkle(Sparkle sparkle) {
        this.sparkles.remove(sparkle);
    }

    private void initSparkles() {
        for (int i = 0 ; i <100 ; i++) {
            Sparkle sparkle = randomSparkle(false);
            addSparkle(sparkle);
        }

    }

    public static Sparkle randomSparkle(boolean left) {
        Random rand = new Random();
        Position position = new Position( left ? HardCodedParameters.defaultWidth : rand.nextInt(HardCodedParameters.defaultWidth), rand.nextInt((int) (HardCodedParameters.defaultHeight -.2 * HardCodedParameters.defaultHeight)) );
        Sparkle sparkle = new Sparkle(position);

        switch (rand.nextInt(3)) {
            case 0 : {
                sparkle.setSpeed(StageSet.SPARKLE_SPEED.LOW);
                break;
            }
            case 1 : {
                sparkle.setSpeed(StageSet.SPARKLE_SPEED.MEDIUM);
                break;
            }
            case 2 : {
                sparkle.setSpeed(StageSet.SPARKLE_SPEED.FAST);
                break;
            }
            default : {
                sparkle.setSpeed(StageSet.SPARKLE_SPEED.LOW);
                break;
            }

        }

        return sparkle;
    }

    public void setSparkles(ArrayList<Sparkle> sparkles) {
        this.sparkles = sparkles;
    }


}
