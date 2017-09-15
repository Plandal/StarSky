package data.ia;

import javafx.scene.image.Image;
import tools.Position;

import java.util.Random;

/**
 * Created by Panda on 14/03/2016.
 */
public class EnemySchtroumpf extends AbstractEnemy {
    int count, up, range, max;
    Random gen;
    boolean bool;

    public EnemySchtroumpf(Position position){
        super(position);
        count = 0;
        life = 50;
        image = new Image("file:src/images/enemy/EnemySchtroumpf.png");
        height = 35;
        width = 45;
        gen = new Random();
        up = 50;
        max = (int) position.x - 10;
        range = gen.nextInt(30)+10;
        bool = true;
        super.setOriginLife(life);
    }

    public int getCount(){
        return count;
    }

    public void move() {

        if(lifeTime < range){
            position.x = position.x - 8;
        }else{
            if(position.x < max && bool) {
                position.x = position.x + 16;
            }else{
                bool = false;
                position.x = position.x - 32;
            }
        }
        if(timeHit > 0){
            if(timeHit > 5){
                image = new Image("file:src/images/enemy/enemySchtroumpf.png");
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
            image =  new Image("file:src/images/hitEnemy/iEnemySchtroumpf.png");
            timeHit = 1;
        }
    }
}
