/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: data/Data.java 2015-03-11 buixuan.
 * ******************************************************/
package data;

import data.ia.ShootRight;
import specifications.*;
import specifications.ennemy.Enemy;
import tools.*;

import data.ia.MoveLeftPhantom;

import java.util.ArrayList;
import java.util.List;

public class Data implements DataService{
    //private Heroes hercules;
    private Position heroesPosition;
    private int stepNumber, score, heroesLife,heroesArmor;
    private List<PhantomService> phantoms;
    private List<Enemy> enemies;
    private ArrayList<ShootService> shoots;
    private ArrayList<Weapon> weapons;
    private double heroesWidth,heroesHeight,phantomWidth,phantomHeight;
    private Sound.SOUND sound;
    private int weaponShotgunAmmo, weaponGrenadeAmmo;
    private Weapon weaponSchotgun;
    private Weapon weaponGrenade;
    private List<Bullet> bullets;
    private Position map;
    private ArrayList<IBonus> bonuses;
    private ArrayList<IBonus> listBonus;
    private GAME_STEP gameStep;
    private boolean pause;
    private List<EnemyExplosion> enemyExplosions;

    public Data(){}

    @Override
    public void init() {
        // hercules = new Heroes;
        heroesPosition = new Position(HardCodedParameters.heroesStartX, HardCodedParameters.heroesStartY);
        phantoms = new ArrayList<PhantomService>();
        shoots = new ArrayList<ShootService>();
        weapons = new ArrayList<Weapon>();
        enemies = new ArrayList<Enemy>();
        enemyExplosions = new ArrayList<EnemyExplosion>();
        bullets = new ArrayList<Bullet>();
        bonuses = new ArrayList<IBonus>();
        listBonus = new ArrayList<IBonus>();

        stepNumber = 0;
        score = 0;
        heroesWidth = HardCodedParameters.heroesWidth;
        heroesHeight = HardCodedParameters.heroesHeight;
        phantomWidth = HardCodedParameters.phantomWidth;
        phantomHeight = HardCodedParameters.phantomHeight;
        heroesLife = HardCodedParameters.heroesLife;
        heroesArmor = HardCodedParameters.heroesArmor;
        sound = Sound.SOUND.None;
        weaponShotgunAmmo = HardCodedParameters.weaponShotGunAmmo;
        weaponGrenadeAmmo = HardCodedParameters.weaponGrenadeAmmo;
        weaponSchotgun = new Weapon(HardCodedParameters.weaponShotGunName,HardCodedParameters.weaponShotGunFireRate,HardCodedParameters.weaponShotGunFireSpeed,HardCodedParameters.weaponShotGunHitDommage,HardCodedParameters.weaponShotGunAmmo);
        weaponGrenade = new Weapon(HardCodedParameters.weaponGrenadeName,HardCodedParameters.weaponGrenadeFireRate,HardCodedParameters.weaponGrenadeFireSpeed,HardCodedParameters.weaponGrenadeHitDommage,HardCodedParameters.weaponGrenadeAmmo);
        weapons = new ArrayList<Weapon>();
        weapons.add(weaponSchotgun);
        weapons.add(weaponGrenade);
        pause = false;
    }

    @Override
    public Position getHeroesPosition(){ return heroesPosition; }

    @Override
    public double getHeroesWidth(){ return heroesWidth; }

    @Override
    public double getHeroesHeight(){ return heroesHeight; }

    @Override
    public Position getHeroesCenter() { return new Position( this.heroesPosition.x + heroesHeight / 2, this.heroesPosition.y + heroesWidth / 2); }

    @Override
    public double getPhantomWidth(){ return phantomWidth; }

    @Override
    public double getPhantomHeight(){ return phantomHeight; }

    @Override
    public int getStepNumber(){ return stepNumber; }

    @Override
    public int getScore(){ return score; }

    @Override
    public int getHeroesLife() { return heroesLife; }

    @Override
    public GAME_STEP getGameStep() {
        return this.gameStep;
    }

    @Override
    public int getHeroesArmor() {
        return heroesArmor;
    }

    @Override
    public ArrayList<IBonus> getBonuses() {
        return this.bonuses;
    }

    @Override
    public ArrayList<IBonus> getListBonus() {
        return this.listBonus;
    }

    @Override
    public List<PhantomService> getPhantoms(){ return phantoms; }

    @Override
    public Sound.SOUND getSoundEffect() { return sound; }

    @Override
    public ArrayList<ShootService> getShoot() { return shoots;}

    @Override
    public List<EnemyExplosion> getEnemyExplosions() { return enemyExplosions; }

    @Override
    public void setHeroesPosition(Position p) {
        int limitLeft = (int) (HardCodedParameters.defaultWidth - HardCodedParameters.heroesWidth * .9);
        int limitRight = (int) (HardCodedParameters.heroesWidth * .6);
        int limitTop = (int) (HardCodedParameters.heroesHeight *.6);
        int limitBottom = (int) ((-.2 * HardCodedParameters.defaultHeight + HardCodedParameters.defaultHeight) - HardCodedParameters.heroesHeight * .6);
        double x = p.x;
        double y = p.y;
        if (limitLeft < x || limitRight > x){
            x = limitLeft < x ? limitLeft : limitRight;
        }
        if (limitTop > y || limitBottom < y){
            y = limitTop > y ? limitTop : limitBottom;
        }
        heroesPosition = new Position(x, y);
    }

    @Override
    public void setStepNumber(int n){ stepNumber=n; }

    @Override
    public void addScore(int score){ this.score+=score; }

    @Override
    public void addPhantom(Position p) { phantoms.add(new MoveLeftPhantom(p)); }

    @Override
    public void setPhantoms(ArrayList<PhantomService> phantoms) { this.phantoms=phantoms; }

    @Override
    public void setSoundEffect(Sound.SOUND s) { sound=s; }

    @Override
    public void addShoot(Position p) {
        shoots.add(new ShootRight(p));
    }

    @Override
    public void setHeroesLife(int l, int force) {
        if (force != 1) {
            int life = this.getHeroesLife();
            int armor = this.getHeroesArmor();
            int dommage = life - l;
            int rest = armor - dommage;
            this.heroesArmor = rest <= 0 ? 0 : rest;
            this.heroesLife = rest >= 0 ? life : life + rest;
        }else{
            this.heroesLife =l;
        }
    }

    @Override
    public void setHeroesArmor(int a) {
        this.heroesArmor = a;
    }

    @Override
    public void setShoots(ArrayList<ShootService> shoots) {
        this.shoots = shoots;
    }

    @Override
    public void setShotgunAmmo(int a) {
    }

    @Override
    public void setGrenadeAmmo(int a) {
        weaponGrenade.setAmmo(a);
    }

    @Override
    public int getShotgunAmmo() {
        return weaponSchotgun.getAmmo();
    }

    @Override
    public int getGrenadeAmmo() {
        return weaponGrenade.getAmmo();
    }

    @Override
    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    @Override
    public void addWeapons(Weapon w) {
        weapons.add(w);
    }

    @Override
    public List<Enemy> getEnemies() {
        return enemies;
    }

    @Override
    public List<Bullet> getBullets() {
        return this.bullets;
    }

    @Override
    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    @Override
    public void addEnemy(Enemy enemy) {
        this.enemies.add(enemy);
    }

    @Override
    public void setBullets(List<Bullet> bullets) {
        this.bullets = bullets;
    }

    public Position getMap() {
        return map;
    }

    public void setMap(Position map) {
        this.map = map;
    }

    @Override
    public void setGameStep(GAME_STEP step) {
        this.gameStep = step;
    }

    public boolean isPause() {
        return pause;
    }

    public void changePause(){
        if(pause){
            pause = false;
        }else{
            pause = true;
        }
    }

    @Override
    public void setExplosions(ArrayList<Position> explosions) {

        for (Position p : explosions) {
            enemyExplosions.add(new EnemyExplosion(p));
        }
    }

    @Override
    public void setBonuses(ArrayList<IBonus> bonuses) {this.bonuses = bonuses;}

    @Override
    public void setListBonus(ArrayList<IBonus> listBonus) {
        this.listBonus = listBonus;
    }
}
