package data.ia;

import javafx.scene.image.Image;
import tools.Bullet;
import tools.Position;

/**
 * Created by Panda on 14/03/2016.
 */
public class BossBig extends AbstractEnemy {

    private double isReady = 0;

    public BossBig(Position position){
        super(position,200);
        image = new Image("file:src/images/enemy/bossbig.png");
        vector = new Position(5,0);
        height = 120;
        width = 60;
        life = 1000;
        super.setOriginLife(life);

    }

    @Override
    public Bullet shoot(Position enemyPosition) {
        return null;
    }

    public void move() {

        if(height < 400){
            height = height +4;
        }
        if(width < 200){
            width = width +2;
        }

        if (lifeTime < 20 && isReady < 1)
            isReady += .05;

        if(timeHit > 0){
            if(timeHit > 5){
                image = new Image("file:src/images/enemy/bossbig.png");
                timeHit = 0;
            }else{
                timeHit = timeHit +1;
            }
        }


//        super.move();
    }

    @Override
    public void hitLife() {

        if(life > 0){
            image =  new Image("file:src/images/hitEnemy/iBossBig.png");
            timeHit = 1;
        }
    }

    public double getIsReady() {
        return isReady;
    }
}
