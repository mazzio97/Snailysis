package org.snailysis.model.levels;

import java.util.stream.Stream;

import org.snailysis.model.ModelImpl;
import org.snailysis.model.entities.snail.InitialTrajectory;
import org.snailysis.model.entities.snail.Operation;

/**
* The first LevelBuilder, responsible for setting the initial trajectory and the available operations of the level.
*/
public final class OperationsBuilder extends AbstractLevelBuilder<Operation, SolutionBuilder> {

    /**
     * The maximum number of operations available in a level.
     */
    public static final int MAX_NUM_OPERATIONS = 4;

    /**
     * Package protected constructor.
     * 
     * @param initTrajectory
     *          the initial trajectory of the snail in the level
     */
    OperationsBuilder(final InitialTrajectory initTrajectory) {
        super(new LevelProperties().setInitTrajectory(initTrajectory));
    }

    /**
     * Sets the trajectory of the snail at the beginning of the level.
     * 
     * @param traj
     *          the trajectory of the snail at the beginning of the level
     */
    public void setInitTrajectory(final InitialTrajectory traj) {
        getProperties().setInitTrajectory(traj);
    }

    /**
     * Gets the trajectory of the snail at the beginning of the level.
     * 
     * @return
     *          the trajectory of the snail at the beginning of the level
     */
    public InitialTrajectory getInitTrajectory() {
        return getProperties().getInitTrajectory();
    }

    @Override
    public boolean canBeAdded(final Operation elem) {
        return getElems().count() < MAX_NUM_OPERATIONS;
    }

    @Override
    public Stream<Operation> getElems() {
        return this.getRawElems().stream();
    }

    @Override
    protected SolutionBuilder nextBuildable() {
        ModelImpl.getInstance().setSnail(getProperties().getInitTrajectory());
        return new SolutionBuilder(this);
    }

}
