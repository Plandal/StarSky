/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: tools/Position.java 2015-03-11 buixuan.
 * ******************************************************/
package tools;
import javafx.scene.image.Image;
import specifications.IBonus;

import java.util.Random;


public class Bonus implements IBonus {

    private int life;
    private int armor;
    private double fireSpeed;
    private double fireDommage;
    private int ammo;
    private Image img;
    private Position p;


    public Bonus(){
        this.life = 0;
        this.armor = 0;
        this.fireSpeed = 0;
        this.fireDommage = 0;
        this.ammo = 0;
        this.randomPosition();
    }

    public Bonus(int life,int armor, double fireSpeed, double fireDommage, int ammo, String fileName){
        this();
        this.life = life;
        this.armor = armor;
        this.fireSpeed = fireSpeed;
        this.fireDommage = fireDommage;
        this.ammo = ammo;
        this.img = new Image("file:src/images/bonus/"+fileName+".png");
    }

    @Override
    public void setLife(int life) {
        this.life = life;
    }

    @Override
    public int getLife() {
        return  this.life;
    }

    @Override
    public Image getImage() {
        return this.img;
    }

    @Override
    public void setArmor(int armor) {
        this.armor = armor;
    }

    @Override
    public int getArmor() {
        return this.armor;
    }

    @Override
    public void setUpgrade(int fireSpeed, int fireDommage) {
        this.fireSpeed = fireSpeed;
        this.fireDommage = fireDommage;
    }

    @Override
    public void addAmmo(Weapon w) {
        w.setAmmo(w.getAmmo()+1);
    }

    @Override
    public int getAmmo() {
        return this.ammo;
    }


    @Override
    public void setPosition(Position p) {
        this.p = p;
    }

    @Override
    public Position getPosition() {
        return this.p;
    }

    @Override
    public double getFireSpeed() {
        return this.fireSpeed;
    }

    @Override
    public double getfireDommage() {
        return this.fireDommage;
    }

    @Override
    public void randomPosition() {
        this.p = new Position(HardCodedParameters.defaultWidth-40,new Random().nextInt((int) (HardCodedParameters.defaultHeight*0.8)));
    }


    @Override
    public String toString() {
        return "IBonus{" +
                "life=" + life +
                ", armor=" + armor +
                ", fireSpeed=" + fireSpeed +
                ", fireDommage=" + fireDommage +
                ", ammo=" + ammo +
                ", img=" + img +
                ", p=" + p +
                '}';
    }
}
