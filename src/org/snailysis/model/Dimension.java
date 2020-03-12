package org.snailysis.model;

/**
 * Standard Model Dimensions.
 */
public enum Dimension {
    /**
     * Cartesian game plane height scale.
     */
    PLANE_HEIGHT_SCALE(12),
    /**
     * Cartesian game plane width scale.
     */
    PLANE_WIDTH_SCALE(10),
    /**
     * Cartesian game plane height scale.
     */
    PLANE_HEIGHT(620),
    /**
     * Cartesian game plane width scale.
     */
    PLANE_WIDTH(1280),
    /**
     * Snail's width.
     */
    SNAIL_WIDTH(45),
    /**
     * Snail's heigth.
     */
    SNAIL_HEIGHT(40),
    /**
     * Snail's delta movement.
     */
    SNAIL_DELTA(5),
    /**
     * Wall's height multiplicative factor. 
     */
    WALL_GAP_PROPORTION(3);

    private final double value;

    Dimension(final double value) {
        this.value = value;
    }

    /**
     * Gets the value of the dimension.
     * 
     * @return
     *          the value
     */
    public double get() {
        return value;
    }
}
