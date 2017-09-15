package tools;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

/**
 * Created by Nenex on 17/03/2016.
 */
public class Collision {

    public boolean collisionShape(double obj1x,double obj1y, double obj1width,
                                  double obj2x,double obj2y, double obj2width){
        boolean collision = false;
        try{
            collision = Math.pow(obj1x-obj2x,2)+Math.pow(obj1y-obj2y,2) < 0.25 * Math.pow(obj1width+obj2width,2)
                        || Math.pow(obj1x+HardCodedParameters.defaultWidth- obj2x,2)+Math.pow(obj1y-obj2y,2) < 0.25 * Math.pow(obj1width+obj2width,2)
                        || Math.pow(obj1x+HardCodedParameters.defaultWidth-obj2x,2)+Math.pow(obj1y-HardCodedParameters.defaultHeight-obj2y,2) < 0.25 * Math.pow(obj1width+obj2width,2)
                        || Math.pow(obj1x-obj2x,2)+Math.pow(obj1y-HardCodedParameters.defaultHeight-obj2y,2) < 0.25 * Math.pow(obj1width+obj2width,2)
            ;
        }catch (Exception e1){

        }

        return collision;
    }

    public boolean collisionShoot(double obj1x,double obj1y, double obj1width,
                                  double obj2x,double obj2y, double obj2width){
        return Math.pow(obj1x-obj2x,2)+Math.pow(obj1y-obj2y,2) < .7 * Math.pow(obj1width+obj2width,2);
    }

    public boolean collisionShootEnemy(double obj1x,double obj1y, double obj1width,
                                  double obj2x,double obj2y, double obj2width){
        return Math.pow(obj1x-obj2x,2)+Math.pow(obj1y-obj2y,2) < .5 * Math.pow(obj1width+obj2width,2);
    }
}
