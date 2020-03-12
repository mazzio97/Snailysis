package org.snailysis.scenes.levels.builder;

import org.snailysis.model.entities.snail.InitialTrajectory;
import org.snailysis.model.entities.snail.Operation;

/**
 * Interface for the OperationsSelectionView controller.
 */
public interface OperationsSelectionController {

    /**
     * The trajectory to use by default in the OperationsBuilder.
     */
    InitialTrajectory DEFAULT_INIT_TRAJECTORY = InitialTrajectory.POSITIVE_LINEAR_X;

    /**
     * Sets a new initial trajectory for the snail.
     * 
     * @param trajectory
     *          the trajectory to set as the initial one in the level
     */
    void modifyInitialTrajectory(InitialTrajectory trajectory);

    /**
     * Adds an operation in the level builder.
     * 
     * @param op
     *          the operation to add
     */
    void selectOperation(Operation op);

    /**
     * Removes an operation from the builder.
     * 
     * @param op
     *          the operation to remove
     */
    void removeOperation(Operation op);

    /**
     * Removes all the operations currently in the builder.
     */
    void removeAllOperations();

    /**
     * Checks if an operation can be added in the builder.
     * 
     * @param op
     *          the operation you aim to add
     * @return
     *          whenever the operation can be added or not
     */
    boolean checkIfAddable(Operation op);

    /**
     * Creates the controller of the LevelBuilderView scene.
     * 
     * @return
     *          the controller of the scene to build the level
     */
    LevelBuilderController startLevelCreation();

}
