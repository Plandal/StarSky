package tools;

import data.Data;
import data.ia.*;
import specifications.DataService;
import specifications.ennemy.Enemy;

import java.util.Random;

/**
 * Created by senorihl on 15/03/16.
 */
public class EnemyFactory{
    private int nbEnemies, boss;
    private static EnemyFactory FACTORY = null;
    private final Random gen;
    private DataService data;

    /** Constructeur privé */
    private EnemyFactory()
    { this.gen = new Random(); init(); }

    public void init() {
        nbEnemies = 0;
        boss = 0;
    }

    /** Point d'accès pour l'instance unique du singleton */
    public static synchronized EnemyFactory getInstance()
    {
        if (FACTORY == null) FACTORY = new EnemyFactory();
        return FACTORY;
    }


    public Enemy spawnEnemy() {
        Enemy enemy = null;
        int rand = gen.nextInt(10);
        if (rand <= 3){
            enemy = spawnTurret();
            nbEnemies = nbEnemies +1;
        }else if (nbEnemies < 5){
            enemy = spawnFace();
        }else if (nbEnemies < 10){
            enemy = spawnToad();
        }else if (nbEnemies < 15){
            enemy = spawnMiddle();
        }else if (nbEnemies < 20){
            enemy = spawnTank();
        } else if (nbEnemies < 25) {
            enemy = spawnHalo();
        } else if (nbEnemies < 30){
            enemy = spawnSchtroumpf();
        }else if(nbEnemies <40 && boss == 0){
                enemy = spawnBossBig();
            boss++;
        }

        return enemy;
    }

    private Enemy spawnMiddle() {
        return new EnemyMiddle(new Position(HardCodedParameters.defaultWidth -60,gen.nextInt((int) (HardCodedParameters.defaultHeight*0.8))));
    }

    private Enemy spawnFace() {
        return new EnemyFace(new Position(HardCodedParameters.defaultWidth -60,gen.nextInt((int) (HardCodedParameters.defaultHeight*0.8))));
    }

    private Enemy spawnHalo() {
        return new EnemyHalo(new Position(HardCodedParameters.defaultWidth -60,gen.nextInt((int) (HardCodedParameters.defaultHeight * .8)) - 50));
    }

    private Enemy spawnTank() {
        return new EnemyTank(new Position(HardCodedParameters.defaultWidth -60,gen.nextInt((int) (HardCodedParameters.defaultHeight * .8)) - 50));
    }

    private Enemy spawnToad() {
        return new EnemyToad(new Position(HardCodedParameters.defaultWidth -60,gen.nextInt((int) (HardCodedParameters.defaultHeight * .8)) - 50));
    }

    private Enemy spawnSchtroumpf() {
        return new EnemySchtroumpf(new Position(HardCodedParameters.defaultWidth -60,gen.nextInt((int) (HardCodedParameters.defaultHeight * .8)) - 50));
    }

    private Enemy spawnBossBig(){
        return new BossBig(new Position(HardCodedParameters.defaultWidth - 200,(HardCodedParameters.defaultHeight)/10));
    }

    private Enemy spawnTurret() {
        Turret enemy = new Turret();
        boolean isUp = gen.nextBoolean();
        enemy.setVector(new Position(0,0));
        enemy.setPosition( new Position(HardCodedParameters.defaultWidth - enemy.getWidth(), isUp ? 0 : HardCodedParameters.defaultHeight * .8 - enemy.getHeight() ) );
        enemy.setUp(isUp);
        return enemy;
    }
}
