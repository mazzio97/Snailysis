package org.snailysis.model.entities.wall;

import java.io.Serializable;

import org.apache.commons.lang3.Range;
import org.snailysis.model.utilities.Pair;
import org.snailysis.model.Dimension;

/**
 * Public interface describing a wall with a gap the snail has to pass through.
 */
public interface Wall extends Serializable {

    /**
     * The width of any wall in the game.
     */
    double WALL_WIDTH = Dimension.SNAIL_WIDTH.get();

    /**
     * Gets the coordinates of the center of the gap in the wall.
     * 
     * @return
     *          a pair containing x and y coordinates of the center of the gap
     */
    Pair<Double, Double> getGapPos();

    /**
     * Gets the width of the wall.
     * 
     * @return
     *          the width of the wall
     */
    default double getWidth() {
        return WALL_WIDTH;
    }

    /**
     * Gets the height of the gap in the wall.
     * 
     * @return
     *          the height of the gap
     */
    double getGapHeight();

    /**
     * Gets only the x-coordinate of the center of the gap in the wall.
     * 
     * @return
     *          the x-coordinate of the center of the gap
     */
    default double getGapX() {
        return getGapPos().getFirst();
    };

    /**
     * Gets only the y-coordinate of the center of the gap in the wall.
     * 
     * @return
     *          the y-coordinate of the center of the gap
     */
    default double getGapY() {
        return getGapPos().getSecond();
    }

    /**
     * A range of x-coordinates representing the width of the wall 
     * from its initial to its last point horizontally.
     * 
     * @return
     *          the width-range of the wall
     */
    default Range<Double> gapWidthRange() {
        return Wall.widthRange(getGapX());
    }

    /**
     * A range of y-coordinates representing the height of the gap of the wall 
     * from its initial to its last point vertically.
     * 
     * @return
     *          the height-range of the gap in the wall
     */
    default Range<Double> gapHeightRange() {
        return Range.between(getGapY() - getGapHeight() / 2, getGapY() + getGapHeight() / 2);
    }

    /**
     * Returns the width-range of a not necessarily present wall 
     * given the x-coordinate of the center of its gap.
     * 
     * @param gapX
     *          the x-coordinate of the gap
     * @return
     *          the width-range of a wall centered in gapX 
     */
    static Range<Double> widthRange(final Double gapX) {
        return Range.between(gapX - Wall.WALL_WIDTH / 2, gapX + Wall.WALL_WIDTH / 2);
    }

}
