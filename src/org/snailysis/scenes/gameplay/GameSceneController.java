package org.snailysis.scenes.gameplay;

import java.util.Set;

import org.snailysis.model.collisions.SnailImpact;
import org.snailysis.model.entities.snail.Operation;
import org.snailysis.model.entities.wall.Wall;

/**
 * Interface describing a generic controller for game scenes.
 */
public interface GameSceneController {

    /**
     * Getter for walls in the current level.
     * 
     * @return
     *      walls of the level
     */
    Set<Wall> getWalls();

    /**
     * Getter for available operations.
     * 
     * @return
     *      available operations
     */
    Set<Operation> getOperations();

    /**
     * Getter for number of available operations.
     * 
     * @return
     *      number of available operations
     */
    int getOperationsNumber();

    /**
     * Getter for snail's current trajectory.
     * 
     * @return
     *      initial trajectory
     */
    String getSnailCurrentTrajectory();

    /**
     * Method that starts the loop.
     */
    void startLoop();

    /**
     * Method that starts the loop.
     */
    void stopLoop();

    /**
     * Method that allows to know if loop is running.
     * 
     * @return
     *      true if loop is running, false vice versa
     */
    boolean isLoopRunning();

    /**
     * Method that performs operation on current snail's function.
     * 
     * @param op
     *      operation to perform
     */
    void performOperationOnSnail(Operation op);

    /**
     * Keeps track of (time, observer) and notifies the observer when right time is spotted.
     * 
     * @param time
     *          time
     * @param r
     *           the observer whose method run has to be called when right time is spotted
     */
    void registerControlsObserver(long time, Runnable r);

    /**
     * Keeps track of (impact, observer) and notifies the observer when right impact is spotted.
     * 
     * @param impact
     *          the type of impact the snail has done
     * @param r
     *          the action to perform when the impact occurs
     */
    void registerImpactObserver(SnailImpact impact, Runnable r);
    /**
     * Compute time needed to reset controls.
     * @return
     *      reset time
     */
    long computeControlsResetTime();
}
