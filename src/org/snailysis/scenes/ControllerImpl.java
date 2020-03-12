package org.snailysis.scenes;

import org.snailysis.model.Model;
import org.snailysis.model.ModelImpl;
import org.snailysis.scenes.gameplay.GameLoop;

/**
 * Basic implementation of the controller of the game.
 */
public final class ControllerImpl implements Controller {

    private final Model model;
    private final View view;
    private GameLoop loop;

    /**
     * 
     */
    public ControllerImpl() {
        this.model = ModelImpl.getInstance();
        this.view = new ViewImpl();
    }

    @Override
    public Model getModel() {
        return this.model;
    }

    @Override
    public View getView() {
        return this.view;
    }

    @Override
    public void setGameLoop(final GameLoop loop) {
        this.loop = loop;
    }

    @Override
    public GameLoop getGameLoop() {
        return this.loop;
    }

}
