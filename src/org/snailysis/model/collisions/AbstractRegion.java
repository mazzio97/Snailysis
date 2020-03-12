package org.snailysis.model.collisions;

import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
/**
 * Abstract class implementing Region.
 */
public abstract class AbstractRegion implements Region {
    /**
     * Shape represent the region.
     */
    private Shape shape;
    /**
     * Rotation transformation.
     */
    private Rotate rotation;

    @Override
    public final boolean collide(final Region r) {
        return this.shape.getBoundsInLocal().intersects(r.getShape().getBoundsInLocal());
    }
    @Override
    public final boolean contains(final Region r) {
        return this.shape.getBoundsInLocal().contains(r.getShape().getBoundsInLocal());
    }
    @Override
    public abstract void rotate(double angle);
    @Override
    public final Shape getShape() {
        return this.shape;
    }
    /**
     * Setter for the shape represent the geometric area.
     * @param shape
     *          shape that describe region
     */
    protected void setShape(final Shape shape) {
        this.shape = shape;
    }
    /**
     * Getter for rotation transformation.
     * @return
     *      rotation applicable of the region
     */
    protected Rotate getRotation() {
        return this.rotation;
    }
    /**
     * Setter for rotation transformation.
     * @param rotation
     *          rotation applicable of the region
     */
    protected void setRotation(final Rotate rotation) {
        this.rotation = rotation;
    }
}
