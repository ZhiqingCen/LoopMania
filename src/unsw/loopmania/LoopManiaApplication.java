package unsw.loopmania;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * the main application
 * run main method from this class
 */
public class LoopManiaApplication extends Application {

    /**
     * the controller for the game. Stored as a field so can terminate it when click exit button
     */
    private LoopManiaWorldController mainController;

    @Override
    public void start(Stage primaryStage) throws IOException {
        // set title on top of window bar
        primaryStage.setTitle("Loop Mania");

        // prevent human player resizing game window (since otherwise would see white space)
        // alternatively, you could allow rescaling of the game (you'd have to program resizing of the JavaFX nodes)
        primaryStage.setResizable(false);

        // load the main game
        LoopManiaWorldControllerLoader loopManiaLoader = new LoopManiaWorldControllerLoader("world17.json");
        mainController = loopManiaLoader.loadController();
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("LoopManiaView.fxml"));
        gameLoader.setController(mainController);
        Parent gameRoot = gameLoader.load();
        
        // load the main menu
        MainMenuController mainMenuController = new MainMenuController(mainController.getWorld());
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("MainMenuView.fxml"));
        menuLoader.setController(mainMenuController);
        Parent mainMenuRoot = menuLoader.load();

        // load the Hero's Castle shop page
        HerosCastleController herosCastleController = new HerosCastleController(mainController.getWorld());
        FXMLLoader shopLoader = new FXMLLoader(getClass().getResource("HerosCastleView.fxml"));
        shopLoader.setController(herosCastleController);
        Parent shopRoot = shopLoader.load();

        // create new scene with the main menu (so we start with the main menu)
        Scene scene = new Scene(mainMenuRoot);
        
        // set functions which are activated when button click to switch menu is pressed
        // e.g. from main menu to start the game, or from the game to return to main menu
        mainController.setMainMenuSwitcher(() -> {switchToRoot(scene, mainMenuRoot, primaryStage);});
        mainMenuController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
        });
        
        mainController.setShopSwitcher(() -> {
            herosCastleController.showButtons();
            switchToRoot(scene, shopRoot, primaryStage);
            mainController.pause();
        });
        herosCastleController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
        });

        // deploy the main onto the stage
        gameRoot.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * wrap up activities when exit program
     */
    @Override
    public void stop(){
        mainController.terminate();
    }

    /**
     * switch to a different Root
     */
    private void switchToRoot(Scene scene, Parent root, Stage stage){
        scene.setRoot(root);
        root.requestFocus();
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
