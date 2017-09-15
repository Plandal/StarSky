package data;

import tools.User;

/**
 * Created by senorihl on 29/03/16.
 */
public class MenuData {
    private BUTTON activeButton;

    public void changeButton(User.COMMAND command) {
        switch (command) {
            case DOWN:case UP: {
                if (getActiveButton() == BUTTON.QUIT) setActiveButton(BUTTON.PLAY);
                else setActiveButton(BUTTON.QUIT);
            }
        }
    }

    public enum BUTTON {PLAY, QUIT};

    public MenuData() {activeButton = BUTTON.PLAY; }

    public BUTTON getActiveButton() {
        return activeButton;
    }

    public void setActiveButton(BUTTON activeButton) {
        this.activeButton = activeButton;
    }
}
