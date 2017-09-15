/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: alpha/Main.java 2015-03-11 buixuan.
 * ******************************************************/
package alpha;

import data.MenuData;
import data.StageSet;
import engine.Menu;
import specifications.*;
import tools.EnemyFactory;
import tools.HardCodedParameters;
import tools.User;
import tools.Sound;

import java.nio.file.Paths;
import java.util.Arrays;

import data.Data;
import engine.Engine;
import userInterface.Viewer;
//import algorithm.RandomWalker;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import userInterface.ViewerGameOver;
import userInterface.ViewerMenu;

public class Main extends Application{
    //---HARD-CODED-PARAMETERS---//
    private static String fileName = HardCodedParameters.defaultParamFileName;

    //---VARIABLES---//
    private static DataService data;
    private static EngineService engine;
    private static ViewerService viewer;
    private static AnimationTimer timer;
    private static StageSet stageSet;
    private static EnemyFactory enemyFactory;
    private static ViewerService menu;
    private static MenuData menuData;
    private static ViewerGameOver gameOver;
    private static EngineService menuEngine;

    //---EXECUTABLE---//
    public static void main(String[] args) {
        //readArguments(args);

        /** Data */
        data = new Data();
        menuData = new MenuData();

        /** Engines */
        engine = new Engine();
        menuEngine = new Menu();

        /** Viewers */
        viewer = new Viewer();
        menu = new ViewerMenu();
        gameOver = new ViewerGameOver();
        stageSet = new StageSet();

        /** Other */
        enemyFactory = EnemyFactory.getInstance();

        ((Engine) engine).bindDataService(data);
        ((Engine) engine).bindStageSet(stageSet);
        ((Engine) engine).bindEnemyFactory(enemyFactory);
        ((Viewer) viewer).bindReadService(data);
        ((Viewer) viewer).bindReadService(data);
        ((Viewer) viewer).bindStageSet(stageSet);
        ((ViewerMenu) menu).bindReadService(data);
        ((ViewerMenu) menu).bindMenuDataService(menuData);
        gameOver.bindDataService(data);

        data.init();
        engine.init();
        viewer.init();
        stageSet.init();
        menu.init();
        gameOver.init();

        data.setGameStep(ReadService.GAME_STEP.MENU);

        launch(args);
    }

    @Override public void start(Stage stage) {
        final Scene scene = new Scene(((Viewer)viewer).getPanel());

        System.err.println("Loading CSS ... : " + Paths.get("src/userInterface/style.css").toUri().toString());
        System.err.println("Available fonts :  " + Arrays.toString(javafx.scene.text.Font.getFamilies().toArray()));
        scene.getStylesheets().add(Paths.get("src/userInterface/style.css").toUri().toString());

        playmusic();
        scene.setFill(Color.BLACK);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {

                switch (data.getGameStep()) {
                    case PLAYING: {
                        if (event.getCode()==KeyCode.LEFT) engine.setHeroesCommand(User.COMMAND.LEFT);
                        if (event.getCode()==KeyCode.RIGHT) engine.setHeroesCommand(User.COMMAND.RIGHT);
                        if (event.getCode()==KeyCode.UP) engine.setHeroesCommand(User.COMMAND.UP);
                        if (event.getCode()==KeyCode.DOWN) engine.setHeroesCommand(User.COMMAND.DOWN);
                        if (event.getCode() == KeyCode.SPACE) engine.setHeroesCommand(User.COMMAND.SHOOT);
                        if (event.getCode() == KeyCode.C) engine.setHeroesCommand(User.COMMAND.BOMB);
                        if(event.getCode() == KeyCode.P) engine.setPause();
                        if(event.getCode() == KeyCode.F) {
                            if (stage.isFullScreen()){
                                stage.setFullScreen(false);
                            }else{
                                stage.setFullScreen(true);
                            }
                        }

                        break;
                    }
                    case MENU: {
                        if (event.getCode()==KeyCode.UP) menuData.changeButton(User.COMMAND.UP);
                        if (event.getCode()==KeyCode.DOWN) menuData.changeButton(User.COMMAND.DOWN);
                        if (event.getCode() == KeyCode.ENTER) {
                            if (menuData.getActiveButton() == MenuData.BUTTON.PLAY) {
                                engine.init();
                                engine.start();
                                data.init();
                                enemyFactory.init();
                                //stageSet.init();
                                data.setGameStep(ReadService.GAME_STEP.PLAYING);
                                menuEngine.stop();
                            } else if (menuData.getActiveButton() == MenuData.BUTTON.QUIT) {
                                System.exit(0);
                            }
                        }
                        break;
                    }
                    case GAME_OVER: {
                        data.setGameStep(ReadService.GAME_STEP.MENU);
                        menuEngine.start();
                        break;
                    }

                }

                event.consume();
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()==KeyCode.LEFT) engine.releaseHeroesCommand(User.COMMAND.LEFT);
                if (event.getCode()==KeyCode.RIGHT) engine.releaseHeroesCommand(User.COMMAND.RIGHT);
                if (event.getCode()==KeyCode.UP) engine.releaseHeroesCommand(User.COMMAND.UP);
                if (event.getCode()==KeyCode.DOWN) engine.releaseHeroesCommand(User.COMMAND.DOWN);
                if (event.getCode()==KeyCode.SPACE) engine.releaseHeroesCommand(User.COMMAND.SHOOT);
                if (event.getCode()==KeyCode.C) engine.releaseHeroesCommand(User.COMMAND.BOMB);
                event.consume();
            }
        });

        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                viewer.setMainWindowWidth(newSceneWidth.doubleValue());
                menu.setMainWindowWidth(newSceneWidth.doubleValue());
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                viewer.setMainWindowHeight(newSceneHeight.doubleValue());
                menu.setMainWindowHeight(newSceneHeight.doubleValue());
            }
        });

        stage.setScene(scene);
        stage.setWidth(HardCodedParameters.defaultWidth);
        stage.setHeight(HardCodedParameters.defaultHeight);
        stage.setOnShown(new EventHandler<WindowEvent>() {
            @Override public void handle(WindowEvent event) {
                menuEngine.start();
            }
        });
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override public void handle(WindowEvent event) {
                engine.stop();
            }
        });
        stage.show();

        timer = new AnimationTimer() {
            @Override public void handle(long l) {
                switch (data.getGameStep()) {
                    case PLAYING: {
                        scene.setRoot(viewer.getPanel());
                        break;
                    }
                    case GAME_OVER: {
                        scene.setRoot(gameOver.getPanel());
                        break;
                    }
                    case MENU: {
                        scene.setRoot( menu.getPanel() );
                        break;
                    }
                }

            }
        };
        timer.start();
    }

    MediaPlayer mediaPlayer;
    public void playmusic(){

        try {
//            String sound = "src/sound/level1.wav";
//            Media play = new Media(Paths.get(sound).toUri().toString());
//            mediaPlayer = new MediaPlayer(play);
//            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
//            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }


    }


    //---ARGUMENTS---//
    private static void readArguments(String[] args){
        if (args.length>0 && args[0].charAt(0)!='-'){
            System.err.println("Syntax error: use option -h for help.");
            return;
        }
        for (int i=0;i<args.length;i++){
            if (args[i].charAt(0)=='-'){
                if (args[i+1].charAt(0)=='-'){
                    System.err.println("Option "+args[i]+" expects an argument but received none.");
                    return;
                }
	/*switch (args[i]){
	  case "-inFile":
	    fileName=args[i+1];
	    break;
	  case "-h":
	    System.out.println("Options:");
	    System.out.println(" -inFile FILENAME: (UNUSED AT THE MOMENT) set file name for input parameters. Default name is"+HardCodedParameters.defaultParamFileName+".");
	    break;
	  default:
	    System.err.println("Unknown option "+args[i]+".");
	    return;
	}*/
                i++;
            }
        }
    }
}
