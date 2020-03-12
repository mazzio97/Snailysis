package org.snailysis.model.collisions;

import javafx.scene.shape.Shape;

/**
 * Interface describing a region in the game.
 * A region is a geometric area that describe the hitbox of any entity in the game.
 */
public interface Region {
    /**
     * Getter of the region Shape.
     * @return
     *      the region shape
     */
    Shape getShape();
    /**
     * Check collision between regions.
     * @param r
     *      the other region
     * @return
     *      true if region collide, false vice versa
     */
    boolean collide(Region r);
    /**
     * Check if region r is entirely contained in this region.
     * @param r
     *      the other region
     * @return
     *      true if region is  entirely contained, false vice versa
     */
    boolean contains(Region r);
    /**
     * Rotate the region.
     * @param angle
     *          angle of rotation
     */
    void rotate(double angle);

}
