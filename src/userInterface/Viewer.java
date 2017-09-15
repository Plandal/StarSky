/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: userInterface/Viewer.java 2015-03-11 buixuan.
 * ******************************************************/
package userInterface;

import data.ia.BossBig;
import specifications.*;
import specifications.IBonus;
import specifications.ennemy.Enemy;
import specifications.ennemy.TurretEnemy;
import tools.*;

import data.StageSet;

import javafx.scene.effect.*;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;
import tools.Weapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Viewer implements ViewerService, RequireReadService{
    private static final int spriteSlowDownRate=HardCodedParameters.spriteSlowDownRate,
            heroesLife = HardCodedParameters.heroesLife,heroesArmor = HardCodedParameters.heroesArmor;
    private static final double defaultMainWidth=HardCodedParameters.defaultWidth,
            defaultMainHeight=HardCodedParameters.defaultHeight;
    private ReadService data;
    private StageSet stageSet;
    private ImageView heroesAvatar,heroesSchield;
    private ImageView enemiesAvatar;
    private Image enemiesSpriteSheet;
    private Image heroesSpriteSheet,shootImage,schield;
    private ArrayList<Rectangle2D> heroesAvatarViewports;
    private ArrayList<Integer> heroesAvatarXModifiers;
    private ArrayList<Integer> heroesAvatarYModifiers;
    private HashMap<String, Image> asteroids;
    private int heroesAvatarViewportIndex;
    private double xShrink,yShrink,shrink,xModifier,yModifier,heroesScale;
    private Rectangle lifeBar, ammoContener,ReloadBar,armorBar;
    private Position mapPosition;
    private ArrayList<IBonus> bonuses = new ArrayList<IBonus>();
    private ArrayList<IBonus> listBonus = new ArrayList<IBonus>();
    public Viewer(){}

    @Override
    public void bindReadService(ReadService service){
        data=service;
    }

    public void bindStageSet( StageSet stageSet ) {
        this.stageSet = stageSet;
    }

    @Override
    public void init(){
        xShrink=1;
        yShrink=1;
        xModifier=0;
        yModifier=0;

        //Yucky hard-conding
        heroesSpriteSheet = new Image("file:sprites/image/ship.png");
        schield = new  Image("file:sprites/image/schield.png");
        heroesAvatar = new ImageView(heroesSpriteSheet);
        heroesSchield = new ImageView(schield);
        shootImage = new Image("file:src/images/shoot.png");
        heroesAvatarViewports = new ArrayList<Rectangle2D>();
        heroesAvatarXModifiers = new ArrayList<Integer>();
        heroesAvatarYModifiers = new ArrayList<Integer>();
        Rectangle lifeBar = new Rectangle();
        Rectangle armorBar = new Rectangle();

        heroesAvatarViewportIndex=0;

        //TODO: replace the following with XML loader
        //heroesAvatarViewports.add(new Rectangle2D(301,386,95,192));
        heroesAvatarViewports.add(new Rectangle2D(25,10,184,71));
        heroesAvatarViewports.add(new Rectangle2D(218,10,184,71));
        heroesAvatarViewports.add(new Rectangle2D(411,10,184,71));
        heroesAvatarViewports.add(new Rectangle2D(604,10,184,71));
        //heroesAvatarViewports.add(new Rectangle2D(204,386,95,192));

        //heroesAvatarXModifiers.add(10);heroesAvatarYModifiers.add(-7);
        heroesAvatarXModifiers.add(3);heroesAvatarYModifiers.add(-10);
        heroesAvatarXModifiers.add(2);heroesAvatarYModifiers.add(-10);
        heroesAvatarXModifiers.add(1);heroesAvatarYModifiers.add(-10);
        heroesAvatarXModifiers.add(1);heroesAvatarYModifiers.add(-10);
        heroesAvatarXModifiers.add(3);heroesAvatarYModifiers.add(-10);
        heroesAvatarXModifiers.add(3);heroesAvatarYModifiers.add(-10);
        heroesAvatarXModifiers.add(1);heroesAvatarYModifiers.add(-10);
        heroesAvatarXModifiers.add(1);heroesAvatarYModifiers.add(-10);
        //heroesAvatarXModifiers.add(10);heroesAvatarYModifiers.add(-7);



    }

    @Override
    public Parent getPanel(){
        shrink=Math.min(xShrink,yShrink);
        xModifier=.01*shrink*defaultMainHeight;
        yModifier=.01*shrink*defaultMainHeight;

        //Yucky hard-conding
        Rectangle map = new Rectangle(xModifier,defaultMainHeight*shrink);
        map.setFill(Color.BLACK);
//        map.setStroke(Color.BLACK);
//        map.setStrokeWidth(.01*shrink*defaultMainHeight);
//        map.setArcWidth(.04*shrink*defaultMainHeight);
//        map.setArcHeight(.04*shrink*defaultMainHeight);
//        map.setTranslateX(xModifier);
//        map.setTranslateY(yModifier);

        Rectangle life = new Rectangle(200,20);
        life.setFill(Color.WHITE);
        life.setStroke(Color.RED);
        life.setX(xModifier+10);
        life.setY(-.2*shrink*defaultMainHeight+shrink*defaultMainHeight+10);
        life.setTranslateX(xModifier*shrink);
        life.setTranslateY(yModifier*shrink);

        lifeBar = new Rectangle(data.getHeroesLife()*100/heroesLife*2,20);
        lifeBar.setFill(data.getHeroesLife()*100/heroesLife < 20 ? Color.RED : Color.GREEN);
        lifeBar.setX(xModifier+10);
        lifeBar.setY(-.2*shrink*defaultMainHeight+shrink*defaultMainHeight+10);
        lifeBar.setTranslateX(xModifier*shrink);
        lifeBar.setTranslateY(yModifier*shrink);

        armorBar = new Rectangle(data.getHeroesArmor()*100/heroesArmor,20);
        armorBar.setFill(Color.BLUE);
        armorBar.setX(xModifier+130);
        armorBar.setY(-.2*shrink*defaultMainHeight+shrink*defaultMainHeight+50);
        armorBar.setTranslateX(xModifier*shrink);
        armorBar.setTranslateY(yModifier*shrink);

        Text greets = new Text(-0.1*shrink*defaultMainHeight+.5*shrink*defaultMainWidth,
                -0.1*shrink*defaultMainWidth+shrink*defaultMainHeight,
                "Round 1");
        greets.setFont(new Font(.05*shrink*defaultMainHeight));

        Text score = new Text(-0.1*shrink*defaultMainHeight+.5*shrink*defaultMainWidth,
                -0.05*shrink*defaultMainWidth+shrink*defaultMainHeight,
                "Score: "+data.getScore());
        score.setFont(new Font(.05*shrink*defaultMainHeight));

        int index=heroesAvatarViewportIndex/spriteSlowDownRate;
        heroesScale=data.getHeroesHeight()*shrink/heroesAvatarViewports.get(index).getHeight();
        heroesAvatar.setViewport(heroesAvatarViewports.get(index));
        heroesAvatar.setFitHeight(data.getHeroesHeight()*shrink);
        heroesAvatar.setPreserveRatio(true);
        heroesAvatar.setTranslateX(shrink*data.getHeroesPosition().x+
                shrink*xModifier+
                -heroesScale*0.5*heroesAvatarViewports.get(index).getWidth()+
                shrink*heroesScale*heroesAvatarXModifiers.get(index)
        );
        heroesAvatar.setTranslateY(shrink*data.getHeroesPosition().y+
                shrink*yModifier+
                -heroesScale*0.5*heroesAvatarViewports.get(index).getHeight()+
                shrink*heroesScale*heroesAvatarYModifiers.get(index)
        );
        heroesAvatarViewportIndex=(heroesAvatarViewportIndex+1)%(heroesAvatarViewports.size()*spriteSlowDownRate);

        Rectangle ammoContenerxx;
        ammoContenerxx = new Rectangle(20,10);
        ammoContenerxx.setFill(Color.WHITE);
        ammoContenerxx.setStroke(Color.GRAY);
        ammoContenerxx.setTranslateX(shrink*data.getHeroesPosition().x+
                shrink*xModifier+
                -heroesScale*0.5*heroesAvatarViewports.get(index).getWidth()+
                shrink*heroesScale*heroesAvatarXModifiers.get(index)
        );

        heroesSchield.setFitHeight(55*shrink);
        heroesSchield.setPreserveRatio(true);
        heroesSchield.setTranslateX(-15+shrink*data.getHeroesPosition().x+
                shrink*xModifier+
                -heroesScale*0.5*heroesAvatarViewports.get(index).getWidth()+
                shrink*heroesScale*heroesAvatarXModifiers.get(index)
        );
        heroesSchield.setTranslateY(-10+shrink*data.getHeroesPosition().y+
                shrink*yModifier+
                -heroesScale*0.5*heroesAvatarViewports.get(index).getHeight()+
                shrink*heroesScale*heroesAvatarYModifiers.get(index)
        );
        heroesSchield.setOpacity((double)(data.getHeroesArmor()*100/heroesArmor)/100);

        Group panel = new Group();
        panel.getChildren().addAll(map,score,life,lifeBar,armorBar,heroesSchield);
        panel = this.drawSparkles(panel);
        panel = this.drawAsteroids(panel);
        panel = this.drawExplosions(panel);
        panel = this.drawEnemies(panel);
        panel = this.drawBonus(panel);
        panel = this.drawBullets(panel);
        panel.getChildren().add(heroesAvatar);

        ArrayList<ShootService> shoots = (ArrayList<ShootService>) data.getShoot();
        ShootService s;

        for (ShootService shoot : shoots) {
            s = shoot;
            ImageView shootImgW = new ImageView(shootImage);
            shootImgW.setTranslateX(shrink * s.getPosition().x + shrink * xModifier);
            shootImgW.setTranslateY(shrink * s.getPosition().y + shrink * yModifier - 25);
            panel.getChildren().add(shootImgW);
        }



        ammoContener = new Rectangle(100,50);
        ammoContener.setFill(Color.WHITE);
        ammoContener.setStroke(Color.GRAY);
        ammoContener.setX(xModifier+10);
        ammoContener.setY(-.2*shrink*defaultMainHeight+shrink*defaultMainHeight+40);
        ammoContener.setTranslateX(xModifier*shrink);
        ammoContener.setTranslateY(yModifier*shrink);

        panel.getChildren().add(ammoContener);

        Text arms = new Text(4,15, "ARMS: ");
        arms.setX(xModifier+15);
        arms.setY(-.2*shrink*defaultMainHeight+shrink*defaultMainHeight+60);
        arms.setTranslateX(xModifier*shrink);
        arms.setTranslateY(yModifier*shrink);
        arms.setFont(new Font(14));
        panel.getChildren().add(arms);
/*
    double progress = System.currentTimeMillis()-data.getShootTime()<HardCodedParameters.weaponShotGunFireRate?System.currentTimeMillis()-data.getShootTime():HardCodedParameters.weaponShotGunFireRate;
    progress = (progress * 100) / HardCodedParameters.weaponShotGunFireRate;
    System.out.println(progress);
    ReloadBar = new Rectangle(progress,20);
    ReloadBar.setFill(progress==100 ? Color.RED : Color.GREEN);
   // ReloadBar.setX(xModifier+10);
  //  ReloadBar.setY(-.2*shrink*defaultMainHeight+shrink*defaultMainHeight+10);
    ReloadBar.setX(100);
    ReloadBar.setY(100);
    ReloadBar.setTranslateX(xModifier*shrink);
    ReloadBar.setTranslateY(yModifier*shrink);
    panel.getChildren().add(ReloadBar);*/

        Text arms2 = new Text(4,15, "BOMB: ");
        arms2.setX(xModifier+25);
        arms2.setY(-.2*shrink*defaultMainHeight+shrink*defaultMainHeight+60);
        arms2.setTranslateX(xModifier*shrink+40);
        arms2.setTranslateY(yModifier*shrink);
        arms2.setFont(new Font(14));
        panel.getChildren().add(arms2);

        ArrayList<Weapon> weapons = (ArrayList<Weapon>) data.getWeapons();
        Weapon w;

        w = weapons.get(0);
        Text weaponCounter = new Text(24,35,"\u221E"/*String.valueOf(/*==w.getAmmo()=)*/);
        weaponCounter.setX(xModifier+15);
        weaponCounter.setY(-.2*shrink*defaultMainHeight+shrink*defaultMainHeight+80);
        weaponCounter.setTranslateX(xModifier*shrink);
        weaponCounter.setTranslateY(yModifier*shrink);
        panel.getChildren().add(weaponCounter);

        w = weapons.get(1);
        Text weaponCounter2 = new Text(44,35,String.valueOf(w.getAmmo()));
        weaponCounter2.setX(xModifier+25);
        weaponCounter2.setY(-.2*shrink*defaultMainHeight+shrink*defaultMainHeight+80);
        weaponCounter2.setTranslateX(xModifier*shrink+40);
        weaponCounter2.setTranslateY(yModifier*shrink);
        panel.getChildren().add(weaponCounter2);

//        panel.getChildren().addAll(map);

        if(data.isPause()){
            Text pause = new Text(4,15, "PAUSE");
            pause.setX(200*shrink);
            pause.setY(200*shrink);
            pause.setTranslateX(xModifier*shrink+40);
            pause.setTranslateY(yModifier*shrink);
            pause.setFont(new Font(56));
            pause.setFill(Color.TURQUOISE);
            panel.getChildren().add(pause);
        }

        return panel;
    }

    @Override
    public void setMainWindowWidth(double width){
        xShrink=width/defaultMainWidth;
    }

    @Override
    public void setMainWindowHeight(double height){
        yShrink=height/defaultMainHeight;
    }

    private Group drawBullets(Group panel) {
        List<Bullet> bullets = data.getBullets();
        for (Bullet bullet : bullets) {
            Circle c = new Circle(bullet.getPosition().x * shrink + xModifier * -2 ,  bullet.getPosition().y * shrink + yModifier, bullet.getHeight() * shrink);
            c.setFill(Color.YELLOW);
            c.setStroke(Color.WHITE);
            panel.getChildren().add(c);

        }

        return panel;
    }

    private Group drawEnemies(Group panel) {

        for (Enemy enemy : data.getEnemies()) {

            ImageView e = new ImageView(enemy.getImage());

            Rectangle hitbox = new Rectangle(
                    enemy.getPosition().x * shrink + xModifier * shrink,
                    enemy.getPosition().y * shrink + yShrink * shrink + 2 * shrink,
                    enemy.getWidth() * shrink,
                    enemy.getHeight() * shrink
            );
            hitbox.setFill(Color.YELLOW);

            e.setFitHeight(enemy.getHeight()*shrink);
            e.setFitWidth(enemy.getWidth()*shrink);

           /* for (ShootService s: data.getShoot()){
                if (hitbox.intersects(s.getPosition().x,s.getPosition().y,20,20))
                    panel.getChildren().add(hitbox);
            }*/

            if (enemy instanceof TurretEnemy)
                if (((TurretEnemy) enemy).isUp())
                    e.setRotate(180);

            if (enemy instanceof BossBig) {
                e.setOpacity(((BossBig) enemy).getIsReady());
            }


            if(enemy.getLife()!=0){
                Rectangle enemyLifeBar = new Rectangle((enemy.getLife()*100/enemy.getOriginLife())* enemy.getWidth() * shrink /100,5);

                enemyLifeBar.setFill(enemy.getLife()*100/enemy.getOriginLife()*enemy.getWidth()/100 < 20 ? Color.RED : Color.GREEN);
                double test = +enemy.getPosition().y-((double)enemy.getHeight());
                enemyLifeBar.setX(enemy.getPosition().x * shrink + xModifier * shrink);
                enemyLifeBar.setY(enemy.getPosition().y * shrink + yShrink * shrink + 2 * shrink + enemy.getHeight() * shrink);
               panel.getChildren().add(enemyLifeBar);
            }


            e.setTranslateX(enemy.getPosition().x * shrink + xModifier * shrink);
            e.setTranslateY(enemy.getPosition().y * shrink + yShrink * shrink + 2 * shrink);

            panel.getChildren().add(e);


//            panel.getChildren().add(hitbox);
//
        }

        return panel;
    }

    private Group drawExplosions(Group panel) {

        for (EnemyExplosion e : data.getEnemyExplosions()) {
            e.action();
            ImageView image = new ImageView(e.getImage());

            image.setFitHeight(e.getHeight()*shrink);
            image.setFitWidth(e.getWidth()*shrink);


            image.setTranslateX(e.getPosition().x * shrink + xModifier * shrink);
            image.setTranslateY(e.getPosition().y * shrink + yShrink * shrink + 2 * shrink);

            panel.getChildren().add(image);
        }

        return panel;
    }

    private Group drawBonus(Group panel) {

        for (IBonus b : data.getBonuses()) {

            ImageView e = new ImageView(b.getImage());

           /* Rectangle hitbox = new Rectangle(
                    b.getPosition().x * shrink + xModifier * shrink,
                    b.getPosition().y * shrink + yShrink * shrink + 2 * shrink,
                    20 * shrink,
                    20 * shrink
            );
            hitbox.setFill(Color.YELLOW);*/
            e.setFitHeight(30*shrink);
            e.setFitWidth(30*shrink);
            e.setTranslateX(b.getPosition().x * shrink + xModifier * shrink);
            e.setTranslateY(b.getPosition().y * shrink + yShrink * shrink + 2 * shrink);

            panel.getChildren().add(e);
        }

        return panel;
    }

    private Group drawAsteroids(Group panel) {

        for (Asteroid asteroid : stageSet.getAsteroids()) {
            ImageView view = new ImageView(new Image(asteroid.getImagePath()));
            view.setTranslateX(asteroid.getPosition().x);
            view.setTranslateY(asteroid.getPosition().y);
            view.setFitHeight(50 * shrink);
            view.setPreserveRatio(true);
            view.setRotate(data.getStepNumber() * 4);
            view.setEffect(new ColorAdjust(0,0,-.7,0));
            panel.getChildren().addAll(view);
        }

        return panel;
    }

    private Group drawSparkles(Group panel) {

        for (Sparkle sparkle : stageSet.getSparkles()) {

            Circle point = new Circle();
            point.setRadius(1);
            switch (sparkle.getSpeed()) {
                case FAST: {
                    point.setFill(Color.LIGHTGRAY);
                    point.setStroke(Color.LIGHTGRAY);
                    break;
                }
                case MEDIUM: {
                    point.setFill(Color.GRAY);
                    point.setStroke(Color.GRAY);
                    break;
                }
                case LOW: {
                    point.setFill(Color.DARKGRAY);
                    point.setStroke(Color.DARKGRAY);
                    break;
                }
                default: {
                    point.setFill(Color.WHITE);
                    point.setStroke(Color.WHITE);
                }
            }
            point.setTranslateX( sparkle.getPosition().x * shrink - xModifier );
            point.setTranslateY( sparkle.getPosition().y * shrink - yModifier );
            panel.getChildren().add(point);
        }

        return panel;
    }
}
