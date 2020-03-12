package org.snailysis.model.levels;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.snailysis.model.utilities.Pair;
import org.snailysis.model.entities.snail.InitialTrajectory;
import org.snailysis.model.entities.snail.Operation;
import org.snailysis.model.entities.wall.Wall;
import org.snailysis.model.levels.builder.Buildable;

/**
 * Public interface describing a level in the game.
 */
public interface Level extends Buildable, Serializable {

    /**
     * Gets the trajectory of the snail at the beginning of the level. 
     * 
     * @return
     *          the trajectory the snail has to move according to at the beginning of the level
     */
    InitialTrajectory getInitTrajectory();

    /**
     * Gets a set containing all the walls present in this level.
     * 
     * @return
     *          the set of walls contained in this level
     */
    Set<Wall> getObstacles();

    /**
     * Gets a set containing all the operations which can be used to modify the trajectory of the snail in this level.
     * 
     * @return
     *          the set of operations available in this level
     */
    Set<Operation> getAvailableOperations();

    /**
     * Gets a list of operations which performed in the given order at the right points can solve the level.
     * 
     * @return
     *          the sequence of operations performed on the snail to solve the level
     */
    List<Operation> getSolvingOperations();

    /**
     * Gets a set of x and y coordinates where the snail pass through to solve the level.
     * 
     * @return
     *          a set of pair representing some coordinates where the snail pass through to solve the level 
     */
    Set<Pair<Double, Double>> guideLinePoints();

    /**
     * Gets the first builder of the process, the only LevelBuilder which is not created by another LevelBuilder.
     * 
     * @param trajectory
     *          the initial trajectory of the snail in the level the process aims to build
     * @return
     *          the first builder of the process
     */
    static OperationsBuilder startBuildProcess(final InitialTrajectory trajectory) {
        return new OperationsBuilder(trajectory);
    }

}
