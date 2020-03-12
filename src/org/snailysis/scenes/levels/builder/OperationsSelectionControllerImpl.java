package org.snailysis.scenes.levels.builder;

import java.util.stream.Collectors;

import org.snailysis.model.entities.snail.InitialTrajectory;
import org.snailysis.model.entities.snail.Operation;
import org.snailysis.model.levels.Level;
import org.snailysis.model.levels.OperationsBuilder;
import org.snailysis.scenes.Controller;

/**
 * Controller of the level builder managing the operations.
 */
public final class OperationsSelectionControllerImpl implements OperationsSelectionController {

    private final Controller mainCtrl;
    private final OperationsBuilder opBld = Level.startBuildProcess(OperationsSelectionController.DEFAULT_INIT_TRAJECTORY);

    /**
     * 
     * @param ctrl
     *          the main controller of the game
     */
    public OperationsSelectionControllerImpl(final Controller ctrl) {
        this.mainCtrl = ctrl;
    }

    @Override
    public void modifyInitialTrajectory(final InitialTrajectory trajectory) {
        opBld.setInitTrajectory(trajectory);
    }

    @Override
    public boolean checkIfAddable(final Operation op) {
        return opBld.canBeAdded(op);
    }

    @Override
    public void selectOperation(final Operation op) {
        opBld.add(op);
    }

    @Override
    public void removeOperation(final Operation op) {
        opBld.remove(op);
    }

    @Override
    public void removeAllOperations() {
        opBld.clear();
    }

    @Override
    public LevelBuilderController startLevelCreation() {
        final OperationsBuilder copy = Level.startBuildProcess(opBld.getInitTrajectory());
        copy.addAll(opBld.getElems().collect(Collectors.toList()));
        return new LevelBuilderControllerImpl(copy, mainCtrl);
    }

}
