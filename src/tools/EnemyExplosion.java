package tools;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;

/**
 * Created by Panda on 14/03/2016.
 */
public class EnemyExplosion {

    Position position;
    int step,height, width;
    Image image;

    public EnemyExplosion(Position position){
        step = 0;
        image = new Image("file:src/images/explosion-1.png");
        height = 40;
        width = 50;
        this.position = position;
        playsoundexplosion();
    }


    public void action() {
        if(step <5){
            this.image = new Image("file:src/images/explosion-2.png");
        }else if (step < 10){
            this.image = new Image("file:src/images/explosion-3.png");
        }else if (step < 15){
            this.image = new Image("file:src/images/explosion-4.png");
        }else if (step < 20){
            this.image = new Image("file:src/images/explosion-5.png");
        }else{
            image = null;
        }
        step = step + 1;
    }

    MediaPlayer mediaPlayer;
    public void playsoundexplosion(){

        try {
           String sound = "src/sound/explosion.wav";
           Media play = new Media(Paths.get(sound).toUri().toString());
           mediaPlayer = new MediaPlayer(play);
           mediaPlayer.setVolume(0.4);
           mediaPlayer.play();
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }


    }

    public Position getPosition() {
        return this.position;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Image getImage(){ return image;    }
}
