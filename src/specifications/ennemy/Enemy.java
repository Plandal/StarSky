package specifications.ennemy;

import javafx.scene.image.Image;
import specifications.DataService;
import tools.Bullet;
import tools.Position;

/**
 * Created by senorihl on 14/03/16.
 */
public interface Enemy {
    /**
     * Gets position.
     *
     * @return the position
     */
    Position getPosition();

    /**
     * Gets vector.
     *
     * @return the vector
     */
    Position getVector();

    /**
     * Gets image.
     *
     * @return the image
     */
    Image getImage();

    /**
     * Gets life time.
     *
     * @return the life time
     */
    int getLifeTime();

    /**
     * Gets height.
     *
     * @return the height
     */
    int getHeight();

    /**
     * Gets width.
     *
     * @return the width
     */
    int getWidth();

    /**
     * Gets life.
     *
     * @return the life
     */
    int getLife();

    /**
     * Gets life.
     *
     * @return the Originlife
     */
    int getOriginLife();

    /**
     * Sets position.
     *
     * @param position the position
     */
    void setPosition( Position position );

    /**
     * Sets life.
     *
     * @param life the life
     */
    void setLife(int life);

    /**
     * Sets life.
     *
     * @param originLife the life
     */
    void setOriginLife(int originLife);

    /**
     * Sets vector.
     *
     * @param vector the vector
     */
    void setVector( Position vector );

    /**
     * Move.
     */
    void move();

    /**
     * Is out of screen.
     *
     * @return is out or not
     */
    boolean isOut();

    /**
     * Take damage.
     *
     */
    void hitLife();
}
