package org.snailysis.scenes.entities.snail;

/**
 * Controller interface of the Snail character.
 */
public interface SnailController {

    /**
     * Scale the snail speed by a scale factor.
     * 
     * @param scale
     *          the value of the scale
     * @throws
     *          IllegalArgumentException if scale is zero
     */
    void scaleSpeed(double scale);

    /**
     * Starts the drawing process from instant zero. To be called when the game scene is loaded.
     */
    void startDrawing();
}
