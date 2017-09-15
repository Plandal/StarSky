package data.ia;

import javafx.scene.image.Image;
import tools.Position;

import java.util.Random;

/**
 * Created by Panda on 14/03/2016.
 */
public class EnemyToad extends AbstractEnemy {
    int count, up, range;
    Random gen;

    public EnemyToad(Position position){
        super(position);
        count = 0;
        life = 50;
        image = new Image("file:src/images/enemy/enemyToad.png");
        height = 35;
        width = 45;
        gen = new Random();
        up = 20;
        range = 0;
        shootSpacing = 50;
        super.setOriginLife(life);
    }

    public int getCount(){
        return count;
    }

    public void move(){

        position.x = position.x - 2;



        if(lifeTime%2 == 0) {

            if (range > 9 && range < 15) {
                position.y = position.y - up;
                up = up - 4;
            }else if(range > 14 && range < 21){
                position.y = position.y + up;
                up = up + 4;

            }else if(range == 21){
                range = 0;

                up = 20;
            }
            range++;
        }
        if(timeHit > 0){
            if(timeHit > 5){
                image = new Image("file:src/images/enemy/enemyToad.png");
                timeHit = 0;
            }else{
                timeHit = timeHit +1;
            }
        }
        lifeTime++;
    }

    @Override
    public void hitLife() {

        if(life > 0){
            image =  new Image("file:src/images/hitEnemy/iEnemyToad.png");
            timeHit = 1;
        }
    }
}