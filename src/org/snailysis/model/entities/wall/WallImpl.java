package org.snailysis.model.entities.wall;

import java.util.Objects;
import java.util.Optional;
import org.snailysis.model.utilities.Pair;

/**
 * Implementation of a generic Wall in the game.
 */
public final class WallImpl implements Wall {

    private static final long serialVersionUID = -1259165194481256698L;

    private final Pair<Double, Double> gapPos;
    private final double gapHeight;

    /**
     * Constructor of a wall specifying a pair representing the center of its gap and the gap height.
     * 
     * @param gapPos
     *          point of the wall where the gap is centered
     * @param gapHeight
     *          the height of the gap in the wall
     */
    public WallImpl(final Pair<Double, Double> gapPos, final double gapHeight) {
        this.gapPos = gapPos;
        this.gapHeight = gapHeight;
    }

    /**
     * Constructor of a wall given x and y coordinates of the center of its gap and specifying the gap height.
     * 
     * @param gapX
     *          x-coordinate of the center of the gap
     * @param gapY
     *          y-coordinate of the center of the gap
     * @param gapHeight
     *          the height of the gap in the wall
     */
    public WallImpl(final double gapX, final double gapY, final double gapHeight) {
        this(new Pair<>(gapX, gapY), gapHeight);
    }

    @Override
    public Pair<Double, Double> getGapPos() {
        return this.gapPos;
    }

    @Override
    public double getGapHeight() {
        return this.gapHeight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gapPos, gapHeight);
    }

    @Override
    public boolean equals(final Object obj) {
        return Optional.ofNullable(obj)
                       .filter(o -> getClass().equals(o.getClass()))
                       .map(o -> (WallImpl) o)
                       .filter(o -> o.getGapPos().equals(getGapPos()))
                       .filter(o -> o.getGapHeight() == getGapHeight())
                       .isPresent();
    }

}
