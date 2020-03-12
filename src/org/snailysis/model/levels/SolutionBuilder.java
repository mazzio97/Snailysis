package org.snailysis.model.levels;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.snailysis.model.utilities.Pair;
import org.snailysis.model.ModelImpl;
import org.snailysis.model.entities.snail.Operation;
import org.snailysis.model.entities.snail.Snail;

/**
* The LevelBuilder created by OperationsBuilder, responsible for defining the solution steps of the level.
*/
public final class SolutionBuilder extends AbstractLevelBuilder<Pair<Double, Operation>, ObstaclesBuilder> {

    /**
     * Package protected constructor.
     * 
     * @param bld
     *          the builder of the available operations
     */
    SolutionBuilder(final OperationsBuilder bld) {
        super(bld.getProperties().setAvailableOperations(bld.getElems().collect(Collectors.toSet())));
    }

    @Override
    public boolean canBeAdded(final Pair<Double, Operation> elem) {
        return !getRawElems().stream()
                          .map(Pair::getFirst)
                          .anyMatch(c -> c.equals(elem.getFirst()))
               && getProperties().getAvailableOperations().contains(elem.getSecond());
    }

    @Override
    public Stream<Map.Entry<Pair<Double, Double>, Operation>> getElems() {
        return getRawElems().stream()
                .collect(Collectors.toMap(e -> new Pair<>(e.getFirst(), computeSolutionFunctionAt(e.getFirst())),
                                          e -> e.getValue()))
                .entrySet()
                .stream();
    }

    @Override
    protected ObstaclesBuilder nextBuildable() {
        return new ObstaclesBuilder(this);
    }

    /**
     * Gets the y-coordinate of the solution function in a given point x.
     * 
     * @param x
     *          the x-coordinate of the solution function 
     * @return
     *          the corresponding y-coordinate of the solution function
     */
    protected double computeSolutionFunctionAt(final double x) {
        final Snail copy = ModelImpl.getInstance().getSnail().getCopy();
        copy.restart();
        getRawElems().stream()
                     .filter(e -> e.getKey() <= x)
                     .sorted((p1, p2) -> p1.getKey().compareTo(p2.getKey()))
                     .forEach(p -> {
                         /*
                          * Move the snail until the next solution point before performing the operation
                          */
                         copy.setDelta(p.getKey() - copy.getCurrentX());
                         copy.move();
                         copy.performOperation(p.getValue());
                     });
        return copy.getPositionAt(x);
    }

}
