package userInterface;

import data.MenuData;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import specifications.DataService;
import specifications.ReadService;
import specifications.RequireDataService;
import tools.HardCodedParameters;

/**
 * Created by senorihl on 29/03/16.
 */
public class ViewerGameOver implements specifications.ViewerService, RequireDataService {

    private static final double defaultMainWidth= HardCodedParameters.defaultWidth,
            defaultMainHeight=HardCodedParameters.defaultHeight;
    private double xShrink,yShrink, shrink;
    private DataService data;

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

        Text gameOver = initText("GAME OVER", 90);
        gameOver.setTranslateX( (defaultMainWidth - gameOver.getLayoutBounds().getWidth()) * xShrink  /2 );
        gameOver.setTranslateY( (100 + gameOver.getLayoutBounds().getHeight() ) * yShrink  );
        Text score = initText("Score : " + data.getScore(), 20);
        score.setTranslateX( (defaultMainWidth - score.getLayoutBounds().getWidth()) * xShrink  /2 );
        score.setTranslateY( (140 + gameOver.getLayoutBounds().getHeight() + score.getLayoutBounds().getHeight() ) * yShrink  );
        Text goToMenu = initText("Press space to continue", 20);
        goToMenu.setTranslateX( (defaultMainWidth - goToMenu.getLayoutBounds().getWidth()) * xShrink  /2 );
        goToMenu.setTranslateY( (180 + gameOver.getLayoutBounds().getHeight() + score.getLayoutBounds().getHeight() + goToMenu.getLayoutBounds().getHeight() ) * yShrink  );

        StackPane sp = new StackPane();
        Image img = new Image("file:src/images/rose.png");
        ImageView imgView = new ImageView(img);
        imgView.setX(650);
        imgView.setY(120);
        panel.getChildren().addAll(background, gameOver, score, goToMenu, imgView);
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
    public void bindDataService(DataService service) {
        this.data = service;
    }
}

