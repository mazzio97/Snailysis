package org.snailysis.scenes.gameplay;

import org.snailysis.model.collisions.SnailImpact;

/**
 *  Interface describing a game loop.
 */
public interface GameLoop {
    /**
     * Starts the loop.
     */
    void start();
    /**
     * Stop the loop.
     */
    void finish();
    /**
     * Method that allow to know if the thread represent the loop is running.
     * @return
     *      true if the thread represent the loop is running, false vice versa
     */
    boolean isRunning();
    /**
     * Keeps track of (impact, observer) and notifies the observer when impact is spotted.
     * @param impact
     *          snail impact
     * @param observer
     *           the observer whose method run has to be called when impact is spotted
     */
    void registerCollisionsObserver(SnailImpact impact, Runnable observer);
    /**
     * Keeps track of (time, observer) and notifies the observer when right time is spotted.
     * @param time
     *          time
     * @param observer
     *           the observer whose method run has to be called when right time is spotted
     */
    void registerControlsObserver(long time, Runnable observer);
    /**
     * Checker for controls observers map.
     * @return
     *      true if controls observer map is empty, false vice versa
     */
    boolean isControlsObserversEmpty();
    /**
     * Checker for collisions observers map.
     * @return
     *      true if collisions observer map is empty, false vice versa
     */
    boolean isCollisionsObserversEmpty();
}
