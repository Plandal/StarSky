package specifications.ennemy;

import tools.Bullet;
import tools.Position;

/**
 * Created by senorihl on 14/03/16.
 */
public interface ShootableEnemy extends Enemy {

    Bullet shoot(Position enemyPosition);
    void setShootFrom(Position position);
    Position getShootFrom();
    int getShootSpacing();
}
