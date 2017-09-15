package userInterface;

import data.MenuData;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import specifications.ReadService;
import specifications.RequireMenuService;
import specifications.RequireReadService;
import specifications.ViewerService;
import tools.HardCodedParameters;

/**
 * Created by senorihl on 29/03/16.
 */
public class ViewerMenu implements ViewerService, RequireReadService, RequireMenuService {
    private ReadService data;
    private MenuData menu;
    private static final double defaultMainWidth= HardCodedParameters.defaultWidth,
            defaultMainHeight=HardCodedParameters.defaultHeight;
    private double xShrink,yShrink, shrink;

    @Override
    public void bindReadService(ReadService service) {
        this.data = service;
    }

    @Override
    public void init() {
        xShrink = 1;
        yShrink = 1;
    }

    @Override
    public Parent getPanel() {
        shrink = Math.min(xShrink, yShrink);
        Group panel = new Group();
        Rectangle background = new Rectangle(defaultMainWidth * xShrink, defaultMainHeight * yShrink);
        background.setFill(Color.BLACK);

        Text title = initText("Star Sky", 80);
        title.setId("game-title");
        title.setTranslateX( (defaultMainWidth - title.getLayoutBounds().getWidth()) * xShrink  /2 );
        title.setTranslateY( (50 + title.getLayoutBounds().getHeight() ) * yShrink  );
        title.setFill(Color.RED);
        Reflection r = new Reflection();
        r.setFraction(0.7);

        title.setEffect(r);

        Text play = initText("Play");
        if (menu.getActiveButton() == MenuData.BUTTON.PLAY) {
            play = initText("> Play <");
            play.setFill(Color.RED);
        }
        play.applyCss();
        play.setTranslateX( (defaultMainWidth - play.getLayoutBounds().getWidth()) * xShrink  /2 );
        play.setTranslateY( (150 + title.getLayoutBounds().getHeight() * 2 ) * yShrink  );

        Text quit = initText("Quit");
        if (menu.getActiveButton() == MenuData.BUTTON.QUIT) {
            quit = initText("> Quit <");
            quit.setFill(Color.RED);
        }
        quit.setTranslateX( (defaultMainWidth - quit.getLayoutBounds().getWidth()) * xShrink  /2 );
        quit.setTranslateY( (170 + play.getLayoutBounds().getHeight() + title.getLayoutBounds().getHeight() * 2 ) * yShrink  );


        panel.getChildren().addAll(background, title, play, quit);
        panel.applyCss();
        return panel;
    }

    private Text initText(String text) {
        return initText(text, 30 * yShrink);
    }

    private Text initText(String text, double height) {
        Text textView = new Text(text);
        textView.setFill(Color.WHITE);
        textView.setFont(Font.font("Monospaced", height));
        return textView;
    }

    @Override
    public void setMainWindowWidth(double width){
        xShrink=width/defaultMainWidth;
    }

    @Override
    public void setMainWindowHeight(double height){
        yShrink=height/defaultMainHeight;
    }

    @Override
    public void bindMenuDataService(MenuData service) {
        this.menu = service;
    }
}
