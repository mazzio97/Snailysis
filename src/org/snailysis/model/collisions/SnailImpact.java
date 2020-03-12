package org.snailysis.model.collisions;
/**
 * Enum for all possible impacts.
 */
public enum SnailImpact {
    /**
     * Represent the snail impact with Walls.
     */
    WALL,
    /**
     * Represent the snail impact with play area.
     */
    PLAYAREA,
    /**
     * Represent the snail impact of the end of the level.
     */
    END_LEVEL,
    /**
     * Represent the missing impact of the snail.
     */
    NOONE;
}
