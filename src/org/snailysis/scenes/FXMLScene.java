package org.snailysis.scenes;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;

import org.snailysis.scenes.gameplay.PlayLevelView;
import org.snailysis.scenes.gameplay.LevelCompleteView;
import org.snailysis.scenes.gameplay.LevelFailedView;
import org.snailysis.scenes.levels.builder.LevelBuilderView;
import org.snailysis.scenes.levels.builder.OperationsSelectionView;
import org.snailysis.scenes.levels.manager.LevelManagerView;
import org.snailysis.scenes.menu.MenuBaseController;
import org.snailysis.scenes.menu.SettingsMenuController;
import org.snailysis.scenes.menu.sNailysisHome;
import org.snailysis.scenes.menu.CreditMenuController;
/**
 * Something similar to an Abstract Factory that has different Views to be used into the Main class.
 */
public enum FXMLScene {

    /**
     * Scene to load while playing.
     */
    GAME("gameplay/GameScene", new PlayLevelView()), 

    /**
     * Scene to load when creating a level.
     */
    LEVEL_BUILDER("gameplay/GameScene", new LevelBuilderView()),

    /**
     * Scene to load to select the level to play.
     */
    LEVEL_SELECTION("levels/LevelManager", new LevelManagerView()),

    /**
     * Scene to load to select the operations you want to use in the LevelBuilder.
     */
    OPERATIONS_SELECTION("levels/OperationsSelection", new OperationsSelectionView()),
    /**
     * Setting menu.
     */
    SETTINGS("menu/SettingsMenu", new SettingsMenuController()),
    /**
     * Game menu.
     */
    MENU("menu/MenuBase", new MenuBaseController()),
    /**
     * Main menu immage.
     */
    SNAILYSISHOME("menu/SnailysisHome", new sNailysisHome()),
    /**
     * 
     */
    CREDIT("menu/CreditMenu", new CreditMenuController()),
    /**
     * 
     */
    LEVEL_COMPLETE("gameplay/EndLevel", new LevelCompleteView()),
    /**
     * 
     */
    LEVEL_FAILED("gameplay/EndLevel", new LevelFailedView());

    private static final String PACKAGE_PATH = "/org/snailysis/scenes/";
    private static final String EXTENSION = ".fxml";
    private FXMLLoader loader;
    private Scene scene;

    FXMLScene(final String fileName, final Initializable view) {
        this.loader = new FXMLLoader(this.getClass().getResource(PACKAGE_PATH + fileName + EXTENSION));
        this.loader.setController(view);
        try {
            this.scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return 
     *          the FXMLLoader associated with the fxml file .
     */
    public FXMLLoader getLoader() {
        return this.loader;
    }

    /**
     * 
     * @return
     *          the scene loaded from the FXMLLoader
     */
    public Scene getScene() {
        return this.scene;
    }
}
