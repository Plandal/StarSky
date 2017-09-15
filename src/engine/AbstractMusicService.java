package engine;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import specifications.MusicService;

import java.nio.file.Paths;

/**
 * Created by senorihl on 30/03/16.
 */
public abstract class AbstractMusicService implements MusicService {


    protected MediaPlayer musicPlayer = null;

    @Override
    public void playMusic(String route) {
        try {
            Media play = new Media(Paths.get(route).toUri().toString());
            musicPlayer = new MediaPlayer(play);
            musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            musicPlayer.play();
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }

    }

    @Override
    public void stopMusic() {
        if (musicPlayer != null)
        {
            musicPlayer.stop();
            musicPlayer = null;
        }
    }

    @Override
    public boolean isMusicPlaying() {
        return (musicPlayer != null);
    }
}
