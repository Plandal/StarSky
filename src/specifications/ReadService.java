/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/ReadService.java 2015-03-11 buixuan.
 * ******************************************************/
package specifications;

import specifications.ennemy.Enemy;
import tools.*;
import java.util.ArrayList;
import java.util.List;

public interface ReadService {
    enum GAME_STEP { PLAYING, MENU, GAME_OVER };
    Position getHeroesPosition();
    Position getHeroesCenter();
    double getHeroesWidth();
    double getHeroesHeight();
    double getPhantomWidth();
    double getPhantomHeight();
    int getStepNumber();
    int getScore();
    List<PhantomService> getPhantoms();
    Sound.SOUND getSoundEffect();
    List<Enemy> getEnemies();
    List<Bullet> getBullets();
    List<EnemyExplosion> getEnemyExplosions();
    Position getMap();
    public List<Weapon> getWeapons();
    public int getShotgunAmmo();
    public int getGrenadeAmmo();
    public List<ShootService> getShoot();
    public int getHeroesLife();
    public boolean isPause();
    GAME_STEP getGameStep();
    public int getHeroesArmor();
    public ArrayList<IBonus> getBonuses();
    public ArrayList<IBonus> getListBonus();
}
