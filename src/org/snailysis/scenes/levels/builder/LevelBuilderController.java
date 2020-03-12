package org.snailysis.scenes.levels.builder;

import org.snailysis.scenes.View;
import org.snailysis.scenes.gameplay.GameSceneController;

/**
 *
 */
public interface LevelBuilderController extends GameSceneController {

    /**
     * Gets the main view of the game.
     * 
     * @return
     *          the main view of the game
     */
    View getView();

    /**
     * Restart the building of the solution repositioning the snail at the beginning.
     */
    void resetSnail();

    /**
     * Enables walls positioning to the user.
     */
    void startPuttingWalls();

    /**
     * Checks if a wall can be added or not.
     * 
     * @param x
     *          the x-coordinate of the point you want to know if a new wall can be centered in
     * @return
     *          whenever a wall in this position can be added or not
     */
    boolean checkWallAddable(double x);

    /**
     * Creates a wall centered in the specified position.
     * 
     * @param x
     *          x-coordinate of the center of the wall the user wants to put
     */
    void createWall(double x);

    /**
     * Removes a wall if the specified point is contained in its width range.
     * 
     * @param x
     *          x-coordinate of the center of the wall the user wants to remove
     */
    void removeWall(double x);

    /**
     * Removes all the walls currently present.
     */
    void removeAllWalls();

    /**
     * Save the completed level to file.
     * 
     * @param lvlName
     *          the name of the level to save
     */
    void saveLevel(String lvlName);

}
