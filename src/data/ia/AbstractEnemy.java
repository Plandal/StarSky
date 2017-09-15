package data.ia;

import data.StageSet;
import javafx.scene.image.Image;
import specifications.ennemy.Enemy;
import specifications.ennemy.ShootableEnemy;
import tools.Bullet;
import tools.HardCodedParameters;
import tools.Position;

/**
 * Created by senorihl on 14/03/16.
 */
public abstract class AbstractEnemy implements ShootableEnemy {

    /**
     * The relative position of weapon.
     */
    protected Position shootFrom;
    /**
     * The Scrolling speed.
     */
    protected Position scrollingSpeed = new Position(0,0);
    /**
     * The Position.
     */
    protected Position position;
    /**
     * The Vector.
     *
     * Vitesse du vaisseau.
     * Attention c'est bien la VITESSE, c'est pas le déplacement par tour
     */
    protected Position vector;
    /**
     * The Image.
     */
    protected Image image;
    /**
     * The Height.
     */
    protected int height;
    /**
     * The Width.
     */
    protected int width;
    /**
     * The Life time.
     */
    protected int lifeTime;
    /**
     * The Life.
     */
    protected int life;


    protected  int timeHit;

    /**
     * The Life.
     */
    protected int originLife;
    protected int shootSpacing;

    /**
     * Instantiates a new Abstract enemy.
     */
    public AbstractEnemy() {
        this.lifeTime = 0;
        this.shootFrom = new Position(0,0);
        this.timeHit = 0;
        this.life = 1;
        this.originLife = 1;
        shootSpacing = 15;
    }

    public AbstractEnemy(Position position) {
        this();
        this.position   = position;
        this.timeHit = 0;
    }

    public AbstractEnemy(Position position,int life) {
        this(position);
        this.life = life;
        this.originLife = life;
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getShootSpacing() {
        return this.shootSpacing;
    }

    @Override
    public Bullet shoot(Position enemyPosition) {

        if (this.lifeTime % getShootSpacing() > 0) return null;

        double x   = this.position.x - enemyPosition.x;
        double y   = this.position.y - enemyPosition.y;
        double hyp = Math.sqrt( x*x + y*y );

        Position bulletVector = new Position(  x / hyp * 10, y / hyp * 10 );
        return new Bullet(bulletVector, this.shootFrom.add(this.position));
    }

    @Override
    public int getLifeTime() {
        return this.lifeTime;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Position position) {
        int limitLeft = (int) (HardCodedParameters.defaultWidth - this.getWidth() *.9);
        int limitRight = -100;
        int limitTop = (int) (this.getHeight() *.1 )-30;
        int limitBottom = (int) ((-.2 * HardCodedParameters.defaultHeight + HardCodedParameters.defaultHeight) - this.getHeight() * .6);
        double x = position.x;
        double y = position.y;
        if (limitLeft < x || limitRight > x){
            x = limitLeft < x ? limitLeft : limitRight;
        }
        if (limitTop > y || limitBottom < y){
            y = limitTop > y ? limitTop : limitBottom;
            x = -100;
        }
        this.position = new Position(x, y);;

    }

    @Override
    public Position getVector() {
        return this.vector;
    }

    @Override
    public void setVector(Position vector) {
        this.vector = vector;
    }

    @Override
    public void move() {

       /* this.position = new Position(
                this.position.x - this.vector.x + this.scrollingSpeed.x,
                this.position.y - this.vector.y
        );
*/
        this.setPosition(new Position(
                this.position.x - this.vector.x /3 + this.scrollingSpeed.x,
                this.position.y - this.vector.y /3
        ));
//        this.position = this.getPosition();
        lifeTime++;
    }

    @Override
    public String toString() {
        return "AbstractEnemy{" +
                "shootFrom=" + shootFrom +
                ", scrollingSpeed=" + scrollingSpeed +
                ", position=" + position +
                ", vector=" + vector +
                ", image=" + image +
                ", height=" + height +
                ", width=" + width +
                ", lifeTime=" + lifeTime +
                '}';
    }

    @Override
    public void setShootFrom(Position position) {
        this.shootFrom = position;
    }

    @Override
    public Position getShootFrom() {
        return this.position;
    }

    @Override
    public int getLife() {
        return this.life;
    }

    @Override
    public int getOriginLife() {
        return this.originLife;
    }

    @Override
    public void setLife(int life) {
        hitLife();
        this.life = life;
    }

    @Override
    public void setOriginLife(int originLife) {
        this.originLife = originLife;
    }

    @Override
    public boolean isOut() {
        double left  = position.x - width / 2;
        double right = position.x + width / 2;
        double up    = position.y - height / 2;
        double down  = position.y + height / 2;

        return (right < 0 || left > HardCodedParameters.defaultWidth || down < 0 || up > HardCodedParameters.defaultHeight);
    }

}
