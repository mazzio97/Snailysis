package org.snailysis.scenes.levels.manager;

import java.io.File;
import java.util.List;

import org.snailysis.model.levels.Level;
import org.snailysis.scenes.gameplay.PlayLevelController;

/**
 * Interface for the controller of LevelManagerView.
 */
public interface LevelManagerController {

    /**
     * The path where are the game saves.
     */
    String SAVE_PATH = System.getProperty("user.home") + File.separator + ".snailysis" + File.separator;
    /**
     * The file where is saves the default level manager.
     */
    String DEFAULT_SAVE_FILE = SAVE_PATH + "progress.sav";
    /**
     * The path where the default levels of the game are saved.
     */
    String DEFAULT_LEVELS_PATH = SAVE_PATH + "default" + File.separator;
    /**
     * The path where the custom levels of the user are saved by default.
     */
    String CUSTOM_LEVELS_PATH = SAVE_PATH + "custom" + File.separator;
    /**
     * The extension of a file containing a level.
     */
    String LEVEL_EXTENSION = ".lvl";

    /**
     * Gets the list of default levels.
     * 
     * @return
     *          a list containing all the default levels of the game.
     */
    List<Level> getDefaultLevels();

    /**
     * Gets the list of custom levels.
     * 
     * @return
     *          a list containing all the levels created by the user in the game.
     */
    List<Level> getCustomLevels();

    /**
     * Creates the controller of the level to play scene.
     * 
     * @param lvl
     *          the level to play
     * @return
     *          the controller of the scene containing the level to play
     */
    PlayLevelController selectLevelToPlay(Level lvl);

    /**
     * Gets the name of a given level.
     * 
     * @param lvl
     *          the level you want to know the name
     * @return
     *          the name of the given level
     */
    String getLevelName(Level lvl);

    /**
     * Returns the unlocked state of the level.
     * 
     * @param lvl
     *          the level you want to know if it's unlocked
     * @return
     *          true if the level is unlocked false otherwise
     */
    boolean isUnlocked(Level lvl);

}
