package org.snailysis.model;

import org.snailysis.model.entities.snail.InitialTrajectory;
import org.snailysis.model.entities.snail.Snail;
import org.snailysis.model.levels.Level;
import org.snailysis.model.levels.manager.LevelManager;

import org.snailysis.model.collisions.SnailImpact;

/**
 * Interface of the Model part of MVC architecture pattern.
 */
public interface Model {

    /**
     * Sets the default level manager of the game.
     * 
     * @param mgr
     *          the manager to set as default
     */
    void setDefaultManager(LevelManager mgr);

    /**
     * Gets the default level manager of the game.
     * 
     * @return
     *          the manager of default levels
     */
    LevelManager getDefaultManager();

    /**
     * Gets the custom level manager of the game.
     * 
     * @return
     *          the manager of custom levels
     */
    LevelManager getCustomManager();

    /**
     * Sets a specific level to be played.
     * 
     * @param lvl
     *        the level to be played
     */
    void selectLevel(Level lvl);

    /**
     * Ends the level that is currently being played.
     * 
     * @throws
     *          IllegalStateException if no level is set.
     */
    void endLevel();

    /**
     * Gets the level currently being played, null if none.
     * 
     * @return
     *        the current level
     */
    Level getCurrentLevel();

    /**
     * Unlocks the level after the current level.
     */
    void unlockNextDefaultLevel();

    /**
     * Sets the snail to use to play.
     * @param trajectory
     *          the initial trajectory of the player
     */
    void setSnail(InitialTrajectory trajectory);

    /**
     * Gets the player of the game, null if not present.
     * @return
     *          the snail player
     */
    Snail getSnail();

    /**
     * Checks if the snail is colliding with something.
     * @return
     *          the type of the collision
     */
    SnailImpact checkCollisions();

    /**
     * Method that allow to know if the player reached the end of the level.
     * @return
     *      true if snail reached the end of the level, false vice versa
     */
    boolean isLevelComplete();

    /**
     * Sets the difficulty of the game.
     * 
     * @param difficulty
     *          the difficulty of the game
     */
    void setDifficulty(DifficultGame difficulty);

    /**
     * Gets the difficulty of the game.
     * 
     * @return difficulty
     *          the difficulty of the game
     */
    DifficultGame getDifficulty();
}
