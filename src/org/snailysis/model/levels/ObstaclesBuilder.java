package org.snailysis.model.levels;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.snailysis.model.Dimension;
import org.snailysis.model.entities.wall.Wall;
import org.snailysis.model.entities.wall.WallImpl;

/**
 * The LevelBuilder created by SolutionBuilder, responsible for setting the obstacles of the level.
 */
public final class ObstaclesBuilder extends AbstractLevelBuilder<Double, Level> {

    private final SolutionBuilder solBld;

    /**
     * Package protected constructor.
     * 
     * @param bld
     *          the builder of the solution steps
     */
    ObstaclesBuilder(final SolutionBuilder bld) {
        super(bld.getProperties()
                 .setSolutionSteps(bld.getElems().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()))));
        this.solBld = bld;
    }

    @Override
    public boolean canBeAdded(final Double elem) {
        return !getElems().map(w -> w.gapWidthRange())
                          .filter(r -> !r.isAfterRange(Wall.widthRange(elem)))
                          .filter(r -> !r.isBeforeRange(Wall.widthRange(elem)))
                          .findFirst()
                          .isPresent();
    }

    @Override
    public Stream<Wall> getElems() {
        return getRawElems().stream()
                            .map(x -> new WallImpl(x, 
                                                   solBld.computeSolutionFunctionAt(x), 
                                                   computeWallHeight(x)));
    }

    @Override
    protected Level nextBuildable() {
        return new LevelImpl(getProperties().getInitTrajectory(), 
                             Collections.unmodifiableSet(getProperties().getAvailableOperations()), 
                             Collections.unmodifiableMap(getProperties().getSolutionSteps()),
                             Collections.unmodifiableSet(getProperties().setObstacles(getElems().collect(Collectors.toSet()))
                                                                        .getObstacles()));
    }

    private double computeWallHeight(final double wX) {
        return ((Math.abs(solBld.computeSolutionFunctionAt(wX + Wall.WALL_WIDTH / 2) 
                          - solBld.computeSolutionFunctionAt(wX - Wall.WALL_WIDTH / 2)) 
                 + Dimension.SNAIL_HEIGHT.get()) * Dimension.WALL_GAP_PROPORTION.get());
    }

}
