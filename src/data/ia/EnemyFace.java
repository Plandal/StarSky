package data.ia;

import javafx.scene.image.Image;
import tools.Bullet;
import tools.Position;

/**
 * Created by Panda on 14/03/2016.
 */
public class EnemyFace extends AbstractEnemy {

    public EnemyFace(Position position){
        super(position);
        shootSpacing = 30;
        life = 50;
        image = new Image("file:src/images/enemy/enemyFace.png");
        vector = new Position(5,0);
        height = 40;
        width = 50;
        super.setOriginLife(life);
    }

    @Override
    public Bullet shoot(Position enemyPosition) {
        return null;
    }

    public void move() {

        if (lifeTime >= 40) {
            vector.x = (lifeTime - 40)/2;
        }

        if(timeHit > 0){
            if(timeHit > 5){
                image = new Image("file:src/images/enemy/enemyFace.png");
                timeHit = 0;
            }else{
                timeHit = timeHit +1;
            }
        }
        super.move();
    }

    @Override
    public void hitLife() {

            if(life > 0){
                image =  new Image("file:src/images/hitEnemy/iEnemyFace.png");
                timeHit = 1;
        }
    }

}
