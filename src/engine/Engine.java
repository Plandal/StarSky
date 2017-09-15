/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: engine/Engine.java 2015-03-11 buixuan.
 * ******************************************************/
package engine;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import specifications.*;
import specifications.ennemy.Enemy;
import specifications.ennemy.ShootableEnemy;
import tools.*;
import data.StageSet;

import java.nio.file.Paths;
import java.util.*;

public class Engine extends AbstractMusicService implements EngineService, RequireDataService {
    private static final double friction=HardCodedParameters.friction,
            heroesStep=HardCodedParameters.heroesStep,
            phantomStep=HardCodedParameters.phantomStep;
    private Timer engineClock,shootClock,bonusUpgradeWeaponClock;
    private DataService data;
    private User.COMMAND command;
    private Random gen;
    private boolean moveLeft,moveRight,moveUp,moveDown,shootPrincipal,allowShootPrincipal,shootBomb,allowShootBomb;
    private double heroesVX,heroesVY;
    private StageSet stageSet;
    private ArrayList<ShootService> shoots_destroy;
    private EnemyFactory enemyFactory;
    private Collision collision;
    private ArrayList<IBonus> bonuses;
    private ArrayList<IBonus> listBonus;
    private Random r = new Random();
    private ArrayList<Weapon> weapons;
    private TimerTask shootTask;

    public Engine(){}

    @Override
    public void bindDataService(DataService service){
        data=service;
    }

    @Override
    public void init() {
        engineClock = new Timer();
        shootClock = new Timer();
        bonusUpgradeWeaponClock = new Timer();
        command = User.COMMAND.NONE;
        gen = new Random();
        moveLeft = false;
        moveRight = false;
        moveUp = false;
        moveDown = false;
        shootPrincipal = false;
        allowShootPrincipal = true;
        heroesVX = 0;
        heroesVY = 0;
        collision = new Collision();
        weapons = (ArrayList<Weapon>) data.getWeapons();
        shootTask = new TimerTask() {
            @Override
            public void run() {
                allowShootPrincipal = true;
            }
        };

        listBonus = new ArrayList<IBonus>();
        bonuses = new ArrayList<IBonus>();

        listBonus.add(0,new tools.Bonus(20,0,0,0,0,"2_Items"));      // vie
        listBonus.add(1,new tools.Bonus(0,5,0,0,0,"3_Items"));       // armor
        listBonus.add(2,new tools.Bonus(0,0,1.6,1.6,0,"4_Items"));    // upgrade
        listBonus.add(3,new tools.Bonus(0,0,0,0,1,"5_Items"));      // missile


        bonuses.add((IBonus)listBonus.get(0));
        data.setBonuses(bonuses);


    }

    @Override
    public void start(){
        playMusic("src/sound/level1.wav");

        shootTimer();

        engineClock.schedule(new TimerTask(){

            @Override
            public boolean cancel() {
                return super.cancel();
            }

            public void run() {
//                    System.out.println("Game step #"+data.getStepNumber()+": checked.");

                if (!data.isPause()) {
                    Sparkle sparkle = null;
                    Asteroid asteroid = null;
                    Enemy enemy = null;

                    if (gen.nextInt(10) < 3) sparkle = addSparkle();
//                if (gen.nextInt(10) < 1) asteroid = addAsteroid();
                    if (data.getStepNumber() % 20 == 0) enemy = spawnEnemy();

                    updateSpeedHeroes();

                    updatePositionHeroes();

                    ArrayList<Sparkle> sparkles = new ArrayList<Sparkle>();
                    ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
                    ArrayList<Enemy> enemies_destroy = new ArrayList<Enemy>();
                    List<Bullet> bullets = new ArrayList<Bullet>();
                    List<Bullet> bullets_destroy = new ArrayList<Bullet>();
                    shoots_destroy = new ArrayList<ShootService>();
                    ArrayList<ShootService> shoots = new ArrayList<ShootService>();
                    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
                    ArrayList<Position> explosions = new ArrayList<Position>();
                    shoots = (ArrayList<ShootService>) data.getShoot();
                    bullets = data.getBullets();
                    enemies = (ArrayList<Enemy>) data.getEnemies();
                    ArrayList<IBonus> bonuses = new ArrayList<IBonus>();
                    ArrayList<IBonus> bonuses_destroy = new ArrayList<IBonus>();
                    bonuses = data.getBonuses();
                    int score = 0;

                    updateCommandHeroes();
//                data.setSoundEffect(Sound.SOUND.None);


                    for (ShootService s : data.getShoot()) {
                        s.setPosition(new Position(s.getPosition().x + HardCodedParameters.weaponShotGunFireSpeed, s.getPosition().y));
                    }

                    for (Sparkle s : stageSet.getSparkles()) {
                        moveLeft(s);
                        if (s.getPosition().x <= 0) continue;
                        sparkles.add(s);
                    }

                    for (IBonus b : bonuses) {
                        b.setPosition(new Position(b.getPosition().x - 1, b.getPosition().y));

                   /* Rectangle hitbox = new Rectangle(
                            b.getPosition().x * shrink + xModifier * shrink,
                            b.getPosition().y * shrink + yShrink * shrink + 2 * shrink,
                            20 * shrink,
                            20 * shrink
                    );*/
                        if (collision.collisionShape(data.getHeroesPosition().x, data.getHeroesPosition().y, data.getHeroesWidth()
                                , b.getPosition().x, b.getPosition().y, 40)) {
                            bonuses_destroy.add(b);
                            data.setHeroesLife(data.getHeroesLife() + b.getLife() > HardCodedParameters.heroesLife ? HardCodedParameters.heroesLife : data.getHeroesLife() + b.getLife(), 1);
                            data.setGrenadeAmmo(data.getGrenadeAmmo() + b.getAmmo());
                            data.setHeroesArmor(data.getHeroesArmor() + b.getArmor() > HardCodedParameters.heroesArmor ? HardCodedParameters.heroesArmor : data.getHeroesArmor() + b.getArmor());
                            if (b.getFireSpeed() != 0) {
                                weapons.get(0).setFireRate(weapons.get(0).getFireRate() / b.getFireSpeed());
                                bonusUpgradeWeaponClock = new Timer();

                                bonusUpgradeWeaponClock.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        weapons.get(0).setFireRate(HardCodedParameters.weaponShotGunFireRate);
                                        shootTimer();
                                        //bonusUpgradeWeaponClock.cancel();
                                    }
                                }, 4000);
                                shootTimer();
                            }
                            System.out.println(b);
                        }
                        if (b.getPosition().x < 0) bonuses_destroy.add(b);
                    }

                    for (Enemy e : data.getEnemies()) {
                        e.move();
//                        System.out.println(data.getStepNumber());
                        if (e instanceof ShootableEnemy ) {
                            Bullet bullet = ((ShootableEnemy) e).shoot(data.getHeroesCenter());
                            if (bullet != null) {
                                bullets.add(bullet);
                            }
                        }
                        if (e.isOut()) continue;

                        if (collision.collisionShape(data.getHeroesPosition().x
                                , data.getHeroesPosition().y
                                , HardCodedParameters.heroesWidth
                                , e.getPosition().x
                                , e.getPosition().y
                                , e.getWidth())) {


                            data.setHeroesLife(data.getHeroesLife() - 5, 0);
                            score++;
                            enemies_destroy.add(e);
                        }

                        if (e.getPosition().x < 0) enemies_destroy.add(e);

                        for (ShootService s : shoots) {
                            if (collision.collisionShoot(s.getPosition().x
                                    , s.getPosition().y
                                    , 25
                                    , e.getPosition().x
                                    , e.getPosition().y, e.getWidth())) {
                                shoots_destroy.add(s);
                                e.setLife(e.getLife() - weapons.get(0).getHitDommage());
                                if (e.getLife() <= 0) {
                                    enemies_destroy.add(e);
                                    explosions.add(e.getPosition());
                                }
                                score++;
                            }
                        }


                    }


                    for (Asteroid a : stageSet.getAsteroids()) {
                        a.move();
                        if (a.getPosition().x <= -10 || a.getPosition().x >= HardCodedParameters.defaultWidth + 10)
                            continue;
                        if (a.getPosition().y <= -10 || a.getPosition().y >= HardCodedParameters.defaultHeight * .8 + 10)
                            continue;
                        asteroids.add(a);
                    }

                    for (Bullet b : data.getBullets()) {
                        b.move();
                        if (b.isOut())
                            bullets_destroy.add(b);
                        if (collision.collisionShootEnemy(data.getHeroesPosition().x
                                , data.getHeroesPosition().y
                                , data.getHeroesWidth()
                                , b.getPosition().x
                                , b.getPosition().y
                                , b.getWidth())) {
                            bullets_destroy.add(b);
                            data.setHeroesLife(data.getHeroesLife() - 5, 0);
                        }
                    }
                    bullets.removeAll(bullets_destroy);
                    data.setBullets(bullets);

                    if (asteroid != null) asteroids.add(asteroid);

                    if (sparkle != null) sparkles.add(sparkle);

                    if (enemy != null) enemies.add(enemy);

                    stageSet.setSparkles(sparkles);
                    stageSet.setAsteroids(asteroids);
                    shoots.removeAll(shoots_destroy);

                    enemies_destroy = updateBomb(enemies,enemies_destroy);

                    enemies.removeAll(enemies_destroy);

                    if (new Random().nextInt(1000) + 1 > 999 && bonuses.size() < 2) {
                        int itemBonus = r.nextInt(4);
                        listBonus.get(itemBonus).randomPosition();
                        bonuses.add(listBonus.get(itemBonus));
                    }

                    data.setShoots(shoots);
                    data.setEnemies(enemies);
                    data.addScore(score);
                    bonuses.removeAll(bonuses_destroy);
                    data.setBonuses(bonuses);
                    data.setExplosions(explosions);
                    data.setStepNumber(data.getStepNumber() + 1);

                    if (data.getHeroesLife() <= 0) {
                        data.setGameStep(ReadService.GAME_STEP.GAME_OVER);
                        if (isMusicPlaying()) stopMusic();
                        playsoundgameover();
                        this.cancel();
                        stop();
                    }

                }
            }
        },0,HardCodedParameters.enginePaceMillis);

    }

    @Override
    public void stop(){
        if (isMusicPlaying()) stopMusic();
        engineClock.cancel();
    }

    @Override
    public void setHeroesCommand(User.COMMAND c){
        if (c==User.COMMAND.LEFT) moveLeft=true;
        if (c==User.COMMAND.RIGHT) moveRight=true;
        if (c==User.COMMAND.UP) moveUp=true;
        if (c==User.COMMAND.DOWN) moveDown=true;
        if (c==User.COMMAND.SHOOT) shootPrincipal=true;
        if (c==User.COMMAND.BOMB) shootBomb=true;
    }

    @Override
    public void setPause(){
        data.changePause();
    }


    @Override
    public void releaseHeroesCommand(User.COMMAND c){
        if (c==User.COMMAND.LEFT) moveLeft=false;
        if (c==User.COMMAND.RIGHT) moveRight=false;
        if (c==User.COMMAND.UP) moveUp=false;
        if (c==User.COMMAND.DOWN) moveDown=false;
        if (c==User.COMMAND.SHOOT) shootPrincipal=false;
        if (c==User.COMMAND.BOMB) shootBomb=false;
    }

    private void updateSpeedHeroes(){
        heroesVX*=friction;
        heroesVY*=friction;
    }

    private void updateCommandHeroes(){
        if (moveLeft) heroesVX-=heroesStep;
        if (moveRight) heroesVX+=heroesStep;
        if (moveUp) heroesVY-=heroesStep;
        if (moveDown) heroesVY+=heroesStep;
        if (shootPrincipal && allowShootPrincipal) {
            data.addShoot(new Position(data.getHeroesPosition().x,data.getHeroesPosition().y +HardCodedParameters.heroesHeight/2));
            playsoundshoot();
            allowShootPrincipal=false;

        };




    }

    private void updatePositionHeroes(){
        data.setHeroesPosition(new Position(data.getHeroesPosition().x+heroesVX,data.getHeroesPosition().y+heroesVY));
        //if (data.getHeroesPosition().x<0) data.setHeroesPosition(new Position(0,data.getHeroesPosition().y));
        //etc...
    }

    MediaPlayer mediaPlayer2;
    public void playsoundshoot(){

        try {
            String bip2 = "src/sound/laser.wav";
            Media hit2 = new Media(Paths.get(bip2).toUri().toString());
            mediaPlayer2 = new MediaPlayer(hit2);
            mediaPlayer2.play();
            mediaPlayer2.setVolume(0.4);


        } catch (Exception e) {}


    }

    MediaPlayer mediaPlayer3;
    public void playsoundgameover(){

        try {
            String bip3 = "src/sound/gameover.wav";
            Media hit3 = new Media(Paths.get(bip3).toUri().toString());
            mediaPlayer2 = new MediaPlayer(hit3);
            mediaPlayer2.play();


        } catch (Exception e) {}


    }

    public void bindStageSet(StageSet stageSet) {
        this.stageSet = stageSet;
    }

    public void bindEnemyFactory(EnemyFactory enemyFactory) {
        this.enemyFactory = enemyFactory;
    }

    private Enemy spawnEnemy() {
        return enemyFactory.spawnEnemy();
    }

    private Asteroid addAsteroid() {
        return StageSet.randomAsteroid();
    }

    private void moveLeft(Sparkle sparkle) {

        switch (sparkle.getSpeed()) {
            case MEDIUM: {
                sparkle.setPosition( new Position( sparkle.getPosition().x - Math.pow(StageSet.baseSparkleSpeed, 1) , sparkle.getPosition().y ) );
                break;
            }
            case LOW: {
                sparkle.setPosition( new Position( sparkle.getPosition().x - Math.pow(StageSet.baseSparkleSpeed, .5) , sparkle.getPosition().y ) );
                break;
            }
            default: sparkle.setPosition( new Position( sparkle.getPosition().x - Math.pow(StageSet.baseSparkleSpeed, 2) , sparkle.getPosition().y ) );
        }
    }

    private Sparkle addSparkle() {
        return StageSet.randomSparkle(true);
    }


    private void shootTimer(){
        // shootClock = new Timer();
        try{
            shootTask.cancel();
            shootTask = new TimerTask() {
                @Override
                public void run() {
                    allowShootPrincipal = true;
                    allowShootBomb = true;
                }
            };
        }catch (Exception e){}
        shootClock.schedule(shootTask,0,(int)weapons.get(0).getFireRate());
        System.out.println((int)weapons.get(0).getFireRate());
    }

    private ArrayList<Enemy> updateBomb(ArrayList<Enemy> enemy,ArrayList<Enemy> enemy_destroy){
        Weapon bomb = data.getWeapons().get(1);
        if (shootBomb && allowShootBomb && bomb.getAmmo() !=0) {
            bomb.setAmmo( bomb.getAmmo()-1);
            for (Enemy e : enemy){
                e.setLife(e.getLife()-bomb.getHitDommage());
                if(e.getLife()<=0){
                    enemy_destroy.add(e);
                }
            }
            allowShootBomb=false;

        };

        return enemy_destroy;
    }
}
