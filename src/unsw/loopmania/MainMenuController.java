package unsw.loopmania;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * controller for the main menu.
 * you could extend this, for example with a settings menu, or a menu to load particular maps.
 */
public class MainMenuController {
    /**
     * facilitates switching to main game
     */
    private MenuSwitcher gameSwitcher;
    private LoopManiaWorld world;

    @FXML
    private Button survival;
    @FXML
    private Button berserker;
    @FXML
    private Button confusing;

    public MainMenuController(LoopManiaWorld world) {
        this.world = world;
        world.setMode(Mode.STANDARD);
    }

    public void setGameSwitcher(MenuSwitcher gameSwitcher){
        this.gameSwitcher = gameSwitcher;
    }

    /**
     * facilitates switching to main game upon button click
     * @throws IOException
     */
    @FXML
    private void switchToGame() throws IOException {
        gameSwitcher.switchMenu();
    }

    /**
     * onAction method for survival mode selection
     */
    @FXML
    public void survivalButton() {
        world.setMode(Mode.SURVIVAL);
        berserker.setDisable(true);
        confusing.setDisable(true);
    }

    /**
     * onAction method for berserker mode selection
     */
    @FXML
    public void berserkerButton() {
        world.setMode(Mode.BERSERKER);
        survival.setDisable(true);
        confusing.setDisable(true);
    }

    /**
     * onAction method for confusing mode selection
     */
    @FXML
    public void confusingButton() {
        world.setMode(Mode.CONFUSING);
        survival.setDisable(true);
        berserker.setDisable(true);
    }
}
