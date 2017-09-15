package data.ia;

import javafx.scene.image.Image;
import tools.Position;

/**
 * Created by Panda on 14/03/2016.
 */
public class EnemyTank extends AbstractEnemy {

    public EnemyTank(Position position){
        super(position);
        life = 200;
        shootSpacing = 70;
        image = new Image("file:src/images/enemy/enemyTank.png");
        vector = new Position(5,0);
        height = 50;
        width = 60;
        super.setOriginLife(life);

    }

    public void move(){

        if(lifeTime < 20) {
            vector.x = 7;
        }else if(lifeTime < 90){
            vector.x = 2;
        }else{
            vector.x = 2;
        }
        if(timeHit > 0){
            if(timeHit > 5){
                image = new Image("file:src/images/enemy/enemyTank.png");
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
            image =  new Image("file:src/images/hitEnemy/iEnemyTank.png");
            timeHit = 1;
        }
    }
}
