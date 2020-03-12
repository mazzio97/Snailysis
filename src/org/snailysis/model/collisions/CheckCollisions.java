package org.snailysis.model.collisions;

import java.util.Set;

import org.snailysis.model.entities.snail.Snail;
import org.snailysis.model.entities.wall.Wall;

/**
 * Interface that model the computation of collisions in the game.
 */
public interface CheckCollisions {
    /**
     * Method that compute all possible collision of the snail with entities.
     * @param snail
     *          current snail of the level
     * @param obstacles
     *          set that contains walls of the level
     * @param playAreaWidth
     *          width of play area
     * @param playAreaHeight
     *          height of play area
     * @return
     *      Enum represent the kind of impact, included the missing impact
     */
    SnailImpact computeCollision(Snail snail, Set<Wall> obstacles, double playAreaWidth, double playAreaHeight);
    /**
     * Method that allow to know if the player reached the end of the level.
     * @return
     *      true if snail reached the end of the level, false vice versa
     */
    boolean isLevelCompleted();
}
