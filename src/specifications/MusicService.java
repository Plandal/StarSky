package specifications;

/**
 * Created by senorihl on 30/03/16.
 */
public interface MusicService {
    void playMusic(String route);
    void stopMusic();
    boolean isMusicPlaying();
}
