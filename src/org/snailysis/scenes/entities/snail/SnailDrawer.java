package org.snailysis.scenes.entities.snail;

import java.util.List;
import org.snailysis.model.utilities.Pair;

/**
 * View interface that draws the Snail character.
 */
public interface SnailDrawer {

    /**
     * Draws the Snail character.
     * 
     * @param currentPosition
     *          current position of the snail
     * @param angle
     *          the inclination of the snail
     * @param previousPositions
     *          list of the snail's previous positions
     * @param changingPoints
     *          list of the points when the snail trajectory changed
     * @param nextPositions
     *          list of the snail's next positions
     */
    void draw(Pair<Double, Double> currentPosition, double angle, Pair<Double, Double> changingPoints,
            List<Pair<Double, Double>> previousPositions, List<Pair<Double, Double>> nextPositions);
}
