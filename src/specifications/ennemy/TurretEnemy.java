package specifications.ennemy;

/**
 * Created by senorihl on 14/03/16.
 */
public interface TurretEnemy extends ShootableEnemy {
    boolean isUp();
    void setUp(boolean isUp);
}
