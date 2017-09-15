/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/WriteService.java 2015-03-11 buixuan.
 * ******************************************************/
package specifications;

import specifications.ennemy.Enemy;
import tools.Bullet;
import tools.Position;
import tools.Sound;
import tools.Weapon;

import java.util.ArrayList;
import java.util.List;

public interface WriteService {
    public void setHeroesPosition(Position p);
    public void setStepNumber(int n);
    public void addPhantom(Position p);
    public void setPhantoms(ArrayList<PhantomService> phantoms);
    public void setSoundEffect(Sound.SOUND s);
    public void addScore(int score);
    public void addShoot(Position p);
    public void setHeroesLife(int l,int force);
    public void setHeroesArmor(int a);
    public void setShoots(ArrayList<ShootService> shoots);
    public void addWeapons(Weapon w);
    public void setShotgunAmmo(int a);
    public void setGrenadeAmmo(int a);
    void setEnemies(List<Enemy> enemies);
    void addEnemy(Enemy enemy);
    void setBullets(List<Bullet> bullets);
    void setMap(Position p);

    public void changePause();

    public void setExplosions(ArrayList<Position> explosions);
    void setGameStep(ReadService.GAME_STEP step);
    void setBonuses(ArrayList<IBonus> bonuses);
    void setListBonus(ArrayList<IBonus> listBonus);
}
