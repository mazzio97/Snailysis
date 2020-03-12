package org.snailysis.model.levels;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.snailysis.model.utilities.Pair;
import org.snailysis.model.entities.snail.InitialTrajectory;
import org.snailysis.model.entities.snail.Operation;
import org.snailysis.model.entities.wall.Wall;
import org.snailysis.model.levels.builder.LevelBuilder;
import org.snailysis.model.utilities.exceptions.ExceptionUtilities;

/**
 * A class containing all the properties of the level during the build process.
 */
public final class LevelProperties implements LevelBuilder.Properties {

    private static final String ALREADY_SET_EXCEPTION_MESSAGE = "This property has already been set";
    private static final String NOT_ALREADY_SET_EXCEPTION_MESSAGE = "This property has not been set yet";

    private Optional<InitialTrajectory> initTrajectory = Optional.empty();
    private Optional<Set<Operation>> availableOperations = Optional.empty();
    private Optional<Map<Pair<Double, Double>, Operation>> solution = Optional.empty();
    private Optional<Set<Wall>> obstacles = Optional.empty();

    /**
     * Package protected constructor to force the creation 
     * of an object of this type only from a class of this package.
     */
    LevelProperties() {
        super();
    }

    @Override
    public LevelProperties setInitTrajectory(final InitialTrajectory trajectory) {
        /*
         * Since you must specify the initial trajectory in the OperationsBuilder
         * you can modify it only if the operations are not already set
         */
        checkAlreadySet(this.availableOperations, false);
        ExceptionUtilities.checkNotNull(trajectory, "Trajectory must be a Function2D");
        this.initTrajectory = Optional.of(trajectory);
        return this;
    }

    @Override
    public InitialTrajectory getInitTrajectory() {
        return this.initTrajectory
                   .orElseThrow(() -> new IllegalStateException(NOT_ALREADY_SET_EXCEPTION_MESSAGE));
    }

    @Override
    public LevelProperties setAvailableOperations(final Set<Operation> operations) {
        checkAlreadySet(this.availableOperations, false);
        this.availableOperations = Optional.of(Collections.unmodifiableSet(operations));
        return this;
    }

    @Override
    public Set<Operation> getAvailableOperations() {
        return this.availableOperations
                   .orElseThrow(() -> new IllegalStateException(NOT_ALREADY_SET_EXCEPTION_MESSAGE));
    }

    @Override
    public LevelProperties setSolutionSteps(final Map<Pair<Double, Double>, Operation> solution) {
        checkAlreadySet(this.initTrajectory, true);
        checkAlreadySet(this.availableOperations, true);
        checkAlreadySet(this.solution, false);
        this.solution = Optional.of(Collections.unmodifiableMap(solution));
        return this;
    }

    @Override
    public Map<Pair<Double, Double>, Operation> getSolutionSteps() {
        return this.solution
                   .orElseThrow(() -> new IllegalStateException(NOT_ALREADY_SET_EXCEPTION_MESSAGE));
    }

    @Override
    public LevelProperties setObstacles(final Set<Wall> obstacles) {
        checkAlreadySet(this.solution, true);
        checkAlreadySet(this.obstacles, false);
        this.obstacles = Optional.of(Collections.unmodifiableSet(obstacles));
        return this;
    }

    @Override
    public Set<Wall> getObstacles() {
        return this.obstacles
                   .orElseThrow(() -> new IllegalStateException(NOT_ALREADY_SET_EXCEPTION_MESSAGE));
    }

    private void checkAlreadySet(final Optional<?> opt, final boolean expected) {
        ExceptionUtilities.throwExceptionIf(opt.isPresent() != expected, 
                                            new IllegalStateException(expected ? ALREADY_SET_EXCEPTION_MESSAGE
                                                                               : NOT_ALREADY_SET_EXCEPTION_MESSAGE));
    }

}
