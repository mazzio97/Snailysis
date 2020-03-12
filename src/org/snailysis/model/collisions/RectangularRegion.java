package org.snailysis.model.collisions;

import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

/**
 * Class extending AbstractRegion that represent a region with rectangular form.
 */
public final class RectangularRegion extends AbstractRegion {

    /**
     * Basic constructor for a RectangularRegion.
     *
     * @param x
     *      x-coordinate of region's center
     * @param y
     *      x-coordinate of region's center
     * @param width
     *      width of the region
     * @param height
     *      height of the region
     */
    public RectangularRegion(final double x, final double y, final double width, final double height) {
        super();
        setShape(new Rectangle(x, y, width, height));
        setRotation(new Rotate());
        getRotation().setPivotX(x);
        getRotation().setPivotY(y + height);
    }
    @Override
    public void rotate(final double angle) {
        getRotation().setAngle(-angle);
        getShape().getTransforms().add(super.getRotation());
    }

}
