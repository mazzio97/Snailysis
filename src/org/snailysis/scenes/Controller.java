package org.snailysis.scenes;

import org.snailysis.model.Model;
import org.snailysis.scenes.gameplay.GameLoop;

/**
 * Interface for the main controller of the game.
 */
public interface Controller {

    /**
     * 
     * @return
     *          the model of the game
     */
    Model getModel();

    /**
     * 
     * @return
     *          the view of the game
     */
    View getView();

    /**
     * 
     * @param loop
     *          a game loop
     */
    void setGameLoop(GameLoop loop);

    /**
     * 
     * @return
     *          the game loop moving the snail in the game
     */
    GameLoop getGameLoop();

}
