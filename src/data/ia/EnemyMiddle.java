package data.ia;

import javafx.scene.image.Image;
import tools.Position;

import java.util.Random;

/**
 * Created by Panda on 14/03/2016.
 */
public class EnemyMiddle extends AbstractEnemy {
    boolean direction;
    int range;
    Random gen;

    public EnemyMiddle(Position position){
        super(position);
        life = 50;
        image = new Image("file:src/images/enemy/enemyMiddle.png");
        scrollingSpeed = new Position(0,0);
        shootSpacing = 40;
        vector = new Position(0,0);
        height = 30;
        width = 40;
        gen = new Random();
        direction = gen.nextBoolean();
        range = gen.nextInt(30)+10;
        super.setOriginLife(life);

    }

    public void move(){

        vector = new Position(0,0);

        if(lifeTime < range){
            vector.x = 20;
        }else{
            if(direction){
                vector.y = 5;
            }else{
                vector.y = -5;
            }

        }

        if(timeHit > 0){
            if(timeHit > 5){
                image = new Image("file:src/images/enemy/enemyMiddle.png");
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
            image =  new Image("file:src/images/hitEnemy/iEnemyMiddle.png");
            timeHit = 1;
        }
    }
}
