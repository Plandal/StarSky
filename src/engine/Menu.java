package engine;

import specifications.DataService;
import specifications.EngineService;
import specifications.RequireDataService;
import tools.User;

/**
 * Created by senorihl on 29/03/16.
 */
public class Menu extends AbstractMusicService implements RequireDataService, EngineService {

    private DataService data;

    @Override
    public void bindDataService(DataService service) {
        this.data = service;
    }

    public void init() {}

    @Override
    public void start() { playMusic("src/sound/menu.wav"); }

    @Override
    public void stop() { if (isMusicPlaying()) stopMusic(); }

    @Override
    public void setHeroesCommand(User.COMMAND c) {

    }

    @Override
    public void releaseHeroesCommand(User.COMMAND c) {

    }

    @Override
    public void setPause() {

    }
}
