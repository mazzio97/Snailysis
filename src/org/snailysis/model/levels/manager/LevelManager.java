package org.snailysis.model.levels.manager;


import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.snailysis.model.levels.Level;

/**
 * Interface for managing all the playable levels in the game.
 */
public interface LevelManager extends Serializable {

    /**
     * Gets a map containing all the levels in this builder.
     * 
     * @return
     *          a list containing all the levels in this manager
     */
    List<Level> getAllLevels();

    /**
     * Insert a level in the manager, in addition to the level also its name must be specified.
     * 
     * @param lvlName
     *          the name of the level to add
     * @param lvl
     *          the level to add in the manager
     */
    void insert(Level lvl, String lvlName);

    /**
     * Removes a level from the manager.
     * 
     * @param lvl
     *          the level to remove from the manager
     */
    void remove(Level lvl);

    /**
     * Unlocks a specific level in the manager.
     * 
     * @param lvl
     *          the level inside the manager to unlock
     */
    void unlock(Level lvl);

    /**
     * Gets whenever the level is locked or not.
     * 
     * @param lvl
     *          the level you want to know if it's unlocked
     * @return
     *          whenever the level is unlocked or not
     */
    boolean isUnlocked(Level lvl);

    /**
     * Sets the score obtained for a specified level.
     * 
     * @param lvl
     *          the level you want to change the score
     * @param score
     *          the score obtained
     */
    void setScore(Level lvl, int score);

    /**
     * Gets the user's solution score for the specified level.
     * 
     * @param lvl
     *          the level you want to know which score the user's solution obtained
     * @return
     *          the score the best user's solution obtained
     */
    int getScore(Level lvl);

    /**
     * Gets the percentage of completed levels in relation with the score obtained in each one.
     * 
     * @return
     *          the percentage of completed levels
     */
    double getTotalScore();

    /**
     * Gets the actual number of levels in this manager.
     * 
     * @return
     *          the number of levels available in this manager
     */
    int getLevelsCount();

    /**
     * Gets the number of unlocked levels in the game.
     * 
     * @return
     *          the number of unlocked levels in this manager
     */
    int getUnlockedLevelsCount();

    /**
     * Gets the name of the level specified if present.
     * 
     * @param lvl
     *          the level you want to know the name
     * @return
     *          the name of the level if present
     */
    Optional<String> getLevelName(Level lvl);

    /**
     * Gets the level with the specified name if present.
     * 
     * @param lvlName
     *          the name of the level
     * @return
     *          the level with the specified name if present
     */
    Optional<Level> getLevelFromName(String lvlName);
}
