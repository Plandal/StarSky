package data.ia;

import javafx.scene.image.Image;
import specifications.ennemy.Enemy;
import specifications.ennemy.TurretEnemy;
import tools.Bullet;
import tools.HardCodedParameters;
import tools.Position;

import java.util.Random;

/**
 * Created by senorihl on 14/03/16.
 */
public class Turret extends AbstractEnemy implements TurretEnemy {

    private boolean isUp;

    public Turret() {
        super();
        height  = 30;
        width   = 30;
        life    = 10;
        shootSpacing = 15;
        super.setOriginLife(10);
        image   = new Image("file:src/images/enemy/turret.png");
        scrollingSpeed = new Position( - Math.pow(HardCodedParameters.scrollingSpeed, 2), 0 );
    }

    @Override
    public boolean isUp() {
        return isUp;
    }

    @Override
    public void setUp(boolean isUp) {
        this.isUp = isUp;
        if (isUp) {
            shootFrom = new Position(
                    this.width /2,
                    this.height
            );
        } else {
            shootFrom = new Position( this.width /2, 0 );
        }

    }

    @Override
    public String toString() {
        return "Turret{" +
                "isUp=" + isUp +
                "} " + super.toString();
    }

    @Override
    public void hitLife() {

    }
}
