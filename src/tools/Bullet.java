package tools;

import data.ia.AbstractEnemy;
import specifications.DataService;
import specifications.ennemy.Enemy;

/**
 * Created by senorihl on 14/03/16.
 */
public class Bullet extends AbstractEnemy {

    public Bullet(Position vector, Position position) {
        super();
        this.vector = vector;
        this.position = position;
        this.height = 3;
        this.width = 3;
        scrollingSpeed = new Position(0,0);
    }

    public Bullet(Position vector, Enemy enemy) {
        super();
        this.vector = vector;
        this.height = 3;
        this.width = 3;
        this.position = enemy.getPosition();
        scrollingSpeed = new Position(0,0);
    }

    @Override
    public String toString() {
        return "Bullet{} " + super.toString();
    }

    @Override
    public void hitLife() {

    }
}
