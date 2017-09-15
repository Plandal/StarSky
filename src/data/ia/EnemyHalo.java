package data.ia;

import javafx.scene.image.Image;
import specifications.EnemyService;
import tools.Bullet;
import tools.HardCodedParameters;
import tools.Position;

/**
 * Created by Panda on 14/03/2016.
 */
public class EnemyHalo extends AbstractEnemy {

    public EnemyHalo(Position position){
        super(position);
        life = 50;
        image = new Image("file:src/images/enemy/enemyHalo.png");
        vector = new Position(5, 0);
        height = 40;
        width = 50;
        super.setOriginLife(life);

    }

    public void move(){
        int temp = lifeTime % 40;


        if(temp < 15) {
            vector.y = -5;

        }else if(temp <20){
            vector.y = -7;

        }else if(temp <35){
            vector.y = 5;

        }else{
            vector.y = 7;
        }

        if(timeHit > 0){
            if(timeHit > 5){
                image = new Image("file:src/images/enemy/enemyHalo.png");
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
            image =  new Image("file:src/images/hitEnemy/iEnemyHalo.png");
            timeHit = 1;
        }
    }

    @Override
    public Bullet shoot(Position enemyPosition) {
        return null;
    }
}
