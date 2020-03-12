package org.snailysis.scenes.levels.builder;

import java.io.File;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.snailysis.model.utilities.Pair;
import org.snailysis.model.entities.snail.Operation;
import org.snailysis.model.entities.snail.Snail;
import org.snailysis.model.entities.wall.Wall;
import org.snailysis.model.levels.Level;
import org.snailysis.model.levels.ObstaclesBuilder;
import org.snailysis.model.levels.OperationsBuilder;
import org.snailysis.model.levels.SolutionBuilder;
import org.snailysis.scenes.Controller;
import org.snailysis.scenes.View;
import org.snailysis.scenes.gameplay.AbstractGameSceneController;
import org.snailysis.scenes.gameplay.GameLoopImpl;
import org.snailysis.scenes.levels.manager.LevelManagerController;
import org.snailysis.scenes.utilities.save.SaveCustomBuild;
import org.snailysis.scenes.utilities.save.SaveCustomBuildimpl;

/**
 *
 */
public final class LevelBuilderControllerImpl extends AbstractGameSceneController implements LevelBuilderController {

    private final OperationsBuilder opBld;
    private final SolutionBuilder solBld;
    private Optional<ObstaclesBuilder> obsBld;
    private final Snail snail;

    /**
     * 
     * @param opBld
     *          the OperationsBuilder from the previous scene
     * @param ctrl
     *          the main controller of the game
     */
    public LevelBuilderControllerImpl(final OperationsBuilder opBld, final Controller ctrl) {
        super(ctrl);
        this.opBld = opBld;
        this.solBld = opBld.build();
        this.obsBld = Optional.empty();
        this.snail = getMainController().getModel().getSnail();
        getMainController().setGameLoop(new GameLoopImpl(ctrl));
        Optional.of(new File(LevelManagerController.SAVE_PATH)).filter(f -> !f.exists()).filter(f -> f.mkdir());
        Optional.of(new File(LevelManagerController.CUSTOM_LEVELS_PATH)).filter(f -> !f.exists()).filter(f -> f.mkdir());
    }

    @Override
    public View getView() {
        return getMainController().getView();
    }

    @Override
    public Set<Operation> getOperations() {
        return opBld.getElems().sorted().collect(Collectors.toSet());
    }

    @Override
    public void performOperationOnSnail(final Operation op) {
        super.performOperationOnSnail(op);
        solBld.add(new Pair<>(snail.getCurrentX(), op));
    }

    @Override
    public void resetSnail() {
        stopLoop();
        snail.restart();
        getMainController().setGameLoop(new GameLoopImpl(getMainController()));
        startLoop();
        solBld.clear();
    }

    @Override
    public void startPuttingWalls() {
        this.obsBld = Optional.of(solBld.build());
    }

    @Override
    public boolean checkWallAddable(final double x) {
        return obsBld.get().canBeAdded(x);
    }

    @Override
    public void createWall(final double x) {
        obsBld.ifPresent(bld -> bld.add(x));
    }

    @Override
    public void removeWall(final double x) {
        obsBld.ifPresent(bld -> bld.remove(bld.getElems()
                                              .map(w -> w.getGapX())
                                              .filter(wx -> wx == x)
                                              .findFirst().orElse(null)));
    }

    @Override
    public void removeAllWalls() {
        this.obsBld.ifPresent(bld -> bld.clear());
    }

    @Override
    public Set<Wall> getWalls() {
        return obsBld.isPresent() ? obsBld.get().getElems().collect(Collectors.toSet()) : new HashSet<>();
    }

    @Override
    public void saveLevel(final String lvlName) {
        final Level lvl = obsBld.get().build();
        final SaveCustomBuild<Level> scb = new SaveCustomBuildimpl<>();
        scb.save(lvl, LevelManagerController.CUSTOM_LEVELS_PATH + lvlName + LevelManagerController.LEVEL_EXTENSION);
    }

}
