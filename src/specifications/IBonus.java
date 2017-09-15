/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/AlgorithmService.java 2015-03-11 buixuan.
 * ******************************************************/
package specifications;

import javafx.scene.image.Image;
import tools.Position;
import tools.Weapon;

import java.util.ArrayList;

public interface IBonus {
    public void setLife(int life);
    public int getLife();
    public Image getImage();
    public void setArmor(int armor);
    public int getArmor();
    public void setUpgrade(int fireSpeed,int fireDommage);
    public void addAmmo(Weapon w);
    public int getAmmo();
    public void setPosition(Position p);
    public Position getPosition();
    public double getFireSpeed();
    public double getfireDommage();
    public void randomPosition();

}
