/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: tools/Position.java 2015-03-11 buixuan.
 * ******************************************************/
package tools;

public class Weapon {
    public String name;
    public double fireRate;
    public double fireSpeed;
    public int hitDommage, ammo;

    /*public Weapon(String name, double fireRate, double fireSpeed, int hitDommage) {
        this.name = name;
        this.fireRate = fireRate;
        this.hitDommage = hitDommage;
        this.ammo = 999999;
    }*/

    public Weapon(String name, double fireRate, double fireSpeed, int hitDommage, int ammo) {
        this.name = name;
        this.fireRate = fireRate;
        this.hitDommage = hitDommage;
        this.ammo = ammo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFireRate() {
        return fireRate;
    }

    public double getFireSpeed() {
        return fireSpeed;
    }

    public void setFireRate(double fireRate) {
        this.fireRate = fireRate;
    }

    public void setFireSpeed(double fireSpeed) {
        this.fireSpeed = fireSpeed;
    }

    public int getHitDommage() {
        return hitDommage;
    }

    public void setHitDommage(int hitDommage) {
        this.hitDommage = hitDommage;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = this.ammo != 999999 ? ammo : 999999;
    }
}
