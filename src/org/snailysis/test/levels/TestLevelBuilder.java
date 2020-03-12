package org.snailysis.test.levels;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.snailysis.model.utilities.Pair;
import org.junit.jupiter.api.Test;
import org.snailysis.model.ModelImpl;
import org.snailysis.model.entities.snail.InitialTrajectory;
import org.snailysis.model.entities.snail.Operation;
import org.snailysis.model.entities.snail.Snail;
import org.snailysis.model.entities.wall.Wall;
import org.snailysis.model.entities.wall.WallImpl;
import org.snailysis.model.levels.Level;
import org.snailysis.model.levels.ObstaclesBuilder;
import org.snailysis.model.levels.OperationsBuilder;
import org.snailysis.model.levels.SolutionBuilder;

/**
 * Test class for the LevelBuilder.
 */
public class TestLevelBuilder {

    private Stream<Pair<Double, Double>> getObstaclesHoles(final Level lvl) {
        return lvl.getObstacles().stream().map(Wall::getGapPos);
    }

    /**
     * Creation of a level without anything.
     */
    @Test
    public void createEmptyLevel() {
        final InitialTrajectory line = InitialTrajectory.POSITIVE_DOUBLE_X; // y = 2x
        final Level lvl = Level.startBuildProcess(line).build().build().build();

        assertEquals(lvl.getInitTrajectory(), line);
        assertTrue(lvl.getAvailableOperations().isEmpty());
        assertTrue(lvl.getObstacles().size() == 0);
    }

    /**
     * Check if two levels made in the same way except by the building order are equal.
     */
    @Test
    public void checkLvlEquals() {
        final InitialTrajectory parabola = InitialTrajectory.POSITIVE_SQUARED_X; // y = x^2
        final List<Double> points = Arrays.asList(3.0, 7.0);
        final OperationsBuilder opBld1 = Level.startBuildProcess(parabola);
        opBld1.addAll(Arrays.asList(Operation.NEGATIVE_X, Operation.TWO_X));
        final SolutionBuilder solBld1 = opBld1.build();
        solBld1.add(new Pair<>(points.get(0), Operation.TWO_X));
        solBld1.add(new Pair<>(points.get(1), Operation.NEGATIVE_X));
        final ObstaclesBuilder obsBld1 = solBld1.build();
        obsBld1.addAll((Arrays.asList(points.get(0) + 1, points.get(1) + 1 + WallImpl.WALL_WIDTH)));

        final OperationsBuilder opBld2 = Level.startBuildProcess(parabola);
        opBld2.add(Operation.TWO_X);
        opBld2.add(Operation.NEGATIVE_X);
        final SolutionBuilder solBld2 = opBld2.build();
        solBld2.add(new Pair<>(points.get(1), Operation.NEGATIVE_X));
        solBld2.add(new Pair<>(points.get(0), Operation.TWO_X));
        final ObstaclesBuilder obsBld2 = solBld2.build();
        obsBld2.addAll((Arrays.asList(points.get(1) + 1 + WallImpl.WALL_WIDTH, points.get(0) + 1)));

        final Level lvl1 = obsBld1.build();
        final Level lvl2 = obsBld2.build();
        assertEquals(lvl1, lvl2);
    }

    /**
     * Check operations consistency and order inside the level.
     */
    @Test
    public void checkSolvingOperationsOrder() {
        final InitialTrajectory func = InitialTrajectory.POSITIVE_LINEAR_X; // y = x
        final OperationsBuilder opBld = Level.startBuildProcess(func);
        final List<Double> points = Arrays.asList(3.0, 7.0, 9.0);
        opBld.add(Operation.HALF_X);
        opBld.add(Operation.DIFFERENTIATE);
        opBld.add(Operation.NEGATIVE_X);
        final SolutionBuilder solBld = opBld.build();
        solBld.add(new Pair<>(points.get(1), Operation.HALF_X));
        solBld.add(new Pair<>(points.get(2), Operation.NEGATIVE_X));
        solBld.add(new Pair<>(points.get(0), Operation.DIFFERENTIATE));
        final ObstaclesBuilder obsBld = solBld.build();
        obsBld.addAll(Arrays.asList(points.get(0) + 1, points.get(1) + 1 + WallImpl.WALL_WIDTH, points.get(2) + 1 + WallImpl.WALL_WIDTH * 2));
        final Level lvl = obsBld.build();

        assertEquals(lvl.getInitTrajectory(), func);
        assertEquals(lvl.getSolvingOperations().get(0), Operation.DIFFERENTIATE);
        assertEquals(lvl.getSolvingOperations().get(1), Operation.HALF_X);
        assertEquals(lvl.getSolvingOperations().get(2), Operation.NEGATIVE_X);
    }

    /**
     * Check the impossibility to add more than the max number of operations in a level.
     */
    @Test
    public void checkMaxOperationsAdded() {
        final Set<Operation> ops = new HashSet<>(Arrays.asList(Operation.INTEGRATE, Operation.DIFFERENTIATE, 
                                                               Operation.SQUARED_X, Operation.HALF_X, Operation.TWO_X));
        final OperationsBuilder opBld = Level.startBuildProcess(InitialTrajectory.POSITIVE_SQUARED_X);
        try {
            opBld.addAll(ops);
            fail("Cannot add more than" + OperationsBuilder.MAX_NUM_OPERATIONS + " operations in the first step");
        } catch (RuntimeException ex) {
            assertEquals(opBld.getElems().count(), OperationsBuilder.MAX_NUM_OPERATIONS);
        }
    }

    /**
     * Check the possibility of adding an operation in the solution step 
     * only if it has been added in the operations step.
     */
    @Test
    public void checkSolutionPossibleOperations() {
        final OperationsBuilder opBld = Level.startBuildProcess(InitialTrajectory.CONSTANT);
        opBld.add(Operation.INTEGRATE);
        opBld.add(Operation.DIFFERENTIATE);
        final SolutionBuilder solBld = opBld.build();
        try {
            solBld.add(new Pair<>(2.0, Operation.SQUARED_X));
            fail("Cannot add a pair with an operation not added in the first step");
        } catch (RuntimeException ex) {
            assertEquals(solBld.getElems().count(), 0);
        }
    }

    /**
     * Check if the solution of a level passes through each obstacle's hole.
     */
    @Test
    public void checkSolutionConsistency() {
        final InitialTrajectory func = InitialTrajectory.POSITIVE_LINEAR_X;
        ModelImpl.getInstance().setSnail(func);
        final Snail snail = ModelImpl.getInstance().getSnail();
        final List<Double> obsPoints = Arrays.asList(2.0, 5.0 + WallImpl.WALL_WIDTH, 8.0 + WallImpl.WALL_WIDTH * 2);
        final OperationsBuilder opBld = Level.startBuildProcess(func);
        opBld.add(Operation.TWO_X);
        opBld.add(Operation.NEGATIVE_X);
        opBld.add(Operation.DIFFERENTIATE);
        final SolutionBuilder solBld = opBld.build();
        solBld.add(new Pair<>(obsPoints.get(0) + 1, Operation.NEGATIVE_X));
        solBld.add(new Pair<>(obsPoints.get(1) + 1, Operation.TWO_X));
        solBld.add(new Pair<>(obsPoints.get(2) + 1, Operation.DIFFERENTIATE));
        final ObstaclesBuilder obsBld = solBld.build();
        obsBld.addAll(obsPoints);
        final Level lvl = obsBld.build();

        snail.setDelta(obsPoints.get(0) + 1 - snail.getCurrentX());
        snail.move();
        snail.performOperation(lvl.getSolvingOperations().get(0));
        assertEquals(snail.getPositionAt(obsPoints.get(1)), 
                     getObstaclesHoles(lvl).filter(p -> p.getFirst().equals(obsPoints.get(1)))
                                           .findFirst().get().getSecond().doubleValue());
        snail.setDelta(obsPoints.get(1) + 1 - snail.getCurrentX());
        snail.move();
        snail.performOperation(lvl.getSolvingOperations().get(1));
        assertEquals(snail.getPositionAt(obsPoints.get(2)), 
                     getObstaclesHoles(lvl).filter(p -> p.getFirst().equals(obsPoints.get(2)))
                                           .findFirst().get().getSecond().doubleValue());
    }

    /**
     * Check the possibility of inserting obstacles in a level.
     */
    @Test
    public void checkObstaclesCreation() {
        final InitialTrajectory func = InitialTrajectory.POSITIVE_LINEAR_X;
        ModelImpl.getInstance().setSnail(func);
        final List<Double> obsPoints = Arrays.asList(2.0, 5.0 + WallImpl.WALL_WIDTH / 2, 8.0 + WallImpl.WALL_WIDTH * 2);
        final OperationsBuilder opBld = Level.startBuildProcess(func);
        opBld.add(Operation.INTEGRATE);
        opBld.add(Operation.NEGATIVE_X);
        opBld.add(Operation.DIFFERENTIATE);
        final SolutionBuilder solBld = opBld.build();
        solBld.add(new Pair<>(obsPoints.get(0) + 1, Operation.NEGATIVE_X));
        solBld.add(new Pair<>(obsPoints.get(1) + 1, Operation.INTEGRATE));
        solBld.add(new Pair<>(obsPoints.get(2) + 1, Operation.DIFFERENTIATE));
        final ObstaclesBuilder obsBld = solBld.build();
        try {
            obsBld.addAll(obsPoints);
            fail("Cannot add a wall if it overlaps another one");
        } catch (RuntimeException ex) {
            assertEquals(obsBld.getElems().count(), 1);
        }
    }
}
