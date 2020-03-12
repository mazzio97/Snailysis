package org.snailysis.model.levels;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.snailysis.model.utilities.Pair;
import org.snailysis.model.entities.snail.InitialTrajectory;
import org.snailysis.model.entities.snail.Operation;
import org.snailysis.model.entities.wall.Wall;

/**
 * Class modeling a generic level of the game based on Level interface. 
 */
public final class LevelImpl implements Level {

    private static final long serialVersionUID = 2614273468070728740L;

    private final InitialTrajectory initTrajectory;
    private final Set<Operation> availableOperations;
    private final Map<Pair<Double, Double>, Operation> solution;
    private final Set<Wall> obstacles;

    /**
     * A level can be built only following the build process specified in LevelBuilder interface
     * so its constructor must be package private.
     * 
     * @param traj
     *          initial trajectory of the snail in the level
     * @param ops
     *          set of available operations in the level
     * @param sol
     *          map of (x,y) -> operation entries corresponding to
     *          the points where to perform an operation to finish the level
     * @param obs
     *          set of all the obstacles in the level
     */
    LevelImpl(final InitialTrajectory traj, final Set<Operation> ops, final Map<Pair<Double, Double>, Operation> sol, final Set<Wall> obs) {
        this.initTrajectory = traj;
        this.availableOperations = ops;
        this.solution = sol;
        this.obstacles = obs;
    }

    @Override
    public InitialTrajectory getInitTrajectory() {
        return initTrajectory;
    }

    @Override
    public Set<Wall> getObstacles() {
        return Collections.unmodifiableSet(obstacles);
    }

    @Override
    public Set<Operation> getAvailableOperations() {
        return Collections.unmodifiableSet(availableOperations);
    }

    @Override
    public List<Operation> getSolvingOperations() {
        return Collections.unmodifiableList(solution.keySet()
                                                    .stream()
                                                    .sorted((p1, p2) -> p1.getKey().compareTo(p2.getKey()))
                                                    .map(solution::get)
                                                    .collect(Collectors.toList()));
    }

    @Override
    public Set<Pair<Double, Double>> guideLinePoints() {
        return solution.keySet();
    }

    @Override
    public int hashCode() {
        return Objects.hash(initTrajectory, availableOperations, solution, obstacles);
    }

    @Override
    public boolean equals(final Object obj) {
        return Optional.ofNullable(obj)
                       .filter(o -> getClass().equals(o.getClass()))
                       .map(o -> (LevelImpl) o)
                       .filter(o -> o.getInitTrajectory().equals(getInitTrajectory()))
                       .filter(o -> o.getAvailableOperations().equals(getAvailableOperations()))
                       .filter(o -> o.getObstacles().equals(getObstacles()))
                       .isPresent();
    }

}
