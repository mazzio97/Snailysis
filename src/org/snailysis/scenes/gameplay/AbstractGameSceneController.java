package org.snailysis.scenes.gameplay;

import java.util.Set;

import org.snailysis.model.collisions.SnailImpact;
import org.snailysis.model.entities.snail.Operation;
import org.snailysis.model.entities.wall.Wall;
import org.snailysis.scenes.Controller;

/**
 * Common controller of the views extended from AbstractGameSceneView.
 */
public abstract class AbstractGameSceneController implements GameSceneController {
    private static final long CONTROLS_RESET_TIME = 1500;
    private final Controller mainCtrl;

    /**
     * 
     * @param ctrl
     *          the main controller of the game
     */
    public AbstractGameSceneController(final Controller ctrl) {
        this.mainCtrl = ctrl;
    }

    @Override
    public abstract Set<Operation> getOperations();

    @Override
    public final int getOperationsNumber() {
        return getOperations().size();
    }

    @Override
    public final String getSnailCurrentTrajectory() {
        return mainCtrl.getModel().getSnail().getTrajectoryText();
    }

    @Override
    public final void startLoop() {
        mainCtrl.getGameLoop().start();
    }

    @Override
    public final void stopLoop() {
        mainCtrl.getGameLoop().finish();
    }

    @Override
    public final boolean isLoopRunning() {
        return mainCtrl.getGameLoop().isRunning();
    }

    /**
     * The level builder must keep track of the performed operation.
     */
    @Override
    public void performOperationOnSnail(final Operation op) {
        mainCtrl.getModel().getSnail().performOperation(op);
    }

    @Override
    public abstract Set<Wall> getWalls();

    @Override
    public final void registerControlsObserver(final long time, final Runnable r) {
        mainCtrl.getGameLoop().registerControlsObserver(time, r);
    }

    @Override
    public final void registerImpactObserver(final SnailImpact impact, final Runnable r) {
        mainCtrl.getGameLoop().registerCollisionsObserver(impact, r);
    }

    @Override
    public final long computeControlsResetTime() {
        return (long) (CONTROLS_RESET_TIME / mainCtrl.getModel().getDifficulty().getDifficult());
    }
    /**
     * Returns the main controller of the game.
     * 
     * @return
     *          the main controller of the game
     */
    protected final Controller getMainController() {
        return this.mainCtrl;
    }

}
