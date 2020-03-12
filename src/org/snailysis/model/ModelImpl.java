package org.snailysis.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;

import org.snailysis.model.entities.snail.InitialTrajectory;
import org.snailysis.model.entities.snail.Snail;
import org.snailysis.model.entities.snail.SnailBuilder;
import org.snailysis.model.levels.Level;
import org.snailysis.model.collisions.CheckCollisionImpl;
import org.snailysis.model.collisions.CheckCollisions;
import org.snailysis.model.collisions.SnailImpact;
import org.snailysis.model.levels.manager.LevelManager;
import org.snailysis.model.levels.manager.LevelManagerImpl;
import org.snailysis.model.utilities.exceptions.ExceptionUtilities;

/**
 * Basic implementation of the Model interface.
 * The class is not instantiable, as it is a Singleton.
 */
public final class ModelImpl implements Model {

    private static final Model SINGLETON = new ModelImpl();
    private static final String NO_SNAIL_TEXT = "No Snail Currently Running";
    private static final String NO_LEVEL_TEXT = "No Level Currently Running";
    private static final String ALREADY_SET_LEVEL_TEXT = "Another Level is Currently Running";

    private final LevelManager customLvlMgr = new LevelManagerImpl();
    private LevelManager defaultLvlMgr = new LevelManagerImpl();
    private CheckCollisions checker = new CheckCollisionImpl();
    private DifficultGame difficulty = DifficultGame.MEDIUM;
    private Optional<Level> level = Optional.empty();
    private Optional<Snail> snail = Optional.empty();

    /**
     * Gets an univocal instance of the model.
     * 
     * @return
     *          the instance of the model
     */
    public static Model getInstance() {
        return SINGLETON;
    }

    private ModelImpl() { }

    @Override
    public void selectLevel(final Level lvl) {
        checkLevelNotPresent();
        level = Optional.of(lvl);
    }

    @Override
    public void endLevel() {
        checkLevelPresent();
        level = Optional.empty();
        checker = new CheckCollisionImpl();
    }

    @Override
    public Level getCurrentLevel() {
        checkLevelPresent();
        return level.get();
    }

    @Override
    public Snail getSnail() {
        checkSnailPresent();
        return snail.get();
    }

    @Override
    public void setSnail(final InitialTrajectory trajectory) {
        this.snail = Optional.of(new SnailBuilder().setInitialTrajectory(trajectory)
                                                   .setDelta(difficulty.getDifficult() * Dimension.SNAIL_DELTA.get())
                                                   .build());
    }

    @Override
    public SnailImpact checkCollisions() {
        checkSnailPresent();
        return checker.computeCollision(snail.get(),
                                        level.map(l -> l.getObstacles()).orElse(Collections.emptySet()),
                                        Dimension.PLANE_WIDTH.get(),
                                        Dimension.PLANE_HEIGHT.get());
    }

    private void checkSnailPresent() {
        ExceptionUtilities.throwExceptionIf(!snail.isPresent(), new IllegalStateException(NO_SNAIL_TEXT));
    }

    private void checkLevelPresent() {
        ExceptionUtilities.throwExceptionIf(!level.isPresent(), new IllegalStateException(NO_LEVEL_TEXT));
    }

    private void checkLevelNotPresent() {
        ExceptionUtilities.throwExceptionIf(level.isPresent(), new IllegalStateException(ALREADY_SET_LEVEL_TEXT));
    }

    @Override
    public void setDefaultManager(final LevelManager mgr) {
        this.defaultLvlMgr = mgr;
    }

    @Override
    public void unlockNextDefaultLevel() {
        final Iterator<Level> lvlIterator = defaultLvlMgr.getAllLevels().iterator();
        while (lvlIterator.hasNext()) {
            if (lvlIterator.next().equals(level.get())) {
                break;
            }
        }
        if (lvlIterator.hasNext()) {
            defaultLvlMgr.unlock(lvlIterator.next());
        }
    }

    @Override
    public LevelManager getDefaultManager() {
        return this.defaultLvlMgr;
    }

    @Override
    public LevelManager getCustomManager() {
        return this.customLvlMgr;
    }

    @Override
    public boolean isLevelComplete() {
        return checker.isLevelCompleted();
    }

    @Override
    public void setDifficulty(final DifficultGame difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public DifficultGame getDifficulty() {
        return difficulty;
    }
}
