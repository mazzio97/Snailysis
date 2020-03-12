package org.snailysis.scenes.gameplay;

import org.snailysis.scenes.View;

/**
 * Interface describing a controller for PlayLevelView.
 */
public interface PlayLevelController extends GameSceneController {
    /**
     * Getter for view.
     * @return
     *      view
     */
    View getView();
    /**
     * Method that set all PlayLevelObserver.
     */
    void setUpObservers();
    /**
     * Getter for current level name.
     * @return
     *      level name
     */
    String getCurrentLvlName();
    /**
     * Actions to do when press menu button in EndLevelView.
     */
    void backToMenu();
    /**
     * Actions to do when press again button in EndLevelView.
     */
    void restartLevel();
}
