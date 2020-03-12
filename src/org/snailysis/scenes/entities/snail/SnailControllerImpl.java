package org.snailysis.scenes.entities.snail;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import org.snailysis.model.utilities.Pair;
import org.snailysis.model.collisions.SnailImpact;
import org.snailysis.model.entities.snail.Snail;
import org.snailysis.model.entities.snail.SnailObserver;
import org.snailysis.model.entities.snail.SnailObserver.SnailNotificationType;
import org.snailysis.model.utilities.exceptions.ExceptionUtilities;
import org.snailysis.scenes.Controller;
import org.snailysis.scenes.gameplay.GameLoop;

/**
 * Basic implementation of the SnailController interface.
 */
public final class SnailControllerImpl implements SnailController {

    private static final int NEXT_POINTS = 5;
    private static final int NEXT_POINTS_DELTA_SCALE = 15;

    private final Controller controller;
    private final List<Pair<Double, Double>> previousPositions = new LinkedList<>();
    private final Map<SnailObserver.SnailNotificationType, Runnable> routines = new LinkedHashMap<>();
    private Pair<Double, Double> lastChangingPoint;

    /**
     * Basic constructor for the Snail Controller.
     * 
     * @param controller
     *          the general controller
     */
    public SnailControllerImpl(final Controller controller) {
        this.controller = controller;
        routines.put(SnailNotificationType.OPERATION_PERFORMED, 
                () -> lastChangingPoint = controller.getModel().getSnail().getCurrentPosition());
        routines.put(SnailNotificationType.RESTART, this::clearController);
    }

    @Override
    public void scaleSpeed(final double scale) {
        ExceptionUtilities.checkPositive(scale, "Invalid Scale");
        controller.getModel().getSnail().setDelta(controller.getModel().getSnail().getDelta() * scale);
    }

    @Override
    public void startDrawing() {
        /*
         * Get initial values and attach Snail Observers
         */
        final Snail snail = controller.getModel().getSnail();
        snail.attachObserver(n -> routines.get(n).run());
        previousPositions.add(snail.getCurrentPosition());
        lastChangingPoint = snail.getCurrentPosition();
        /*
         * Attach Game Loop Observers
         */
        final GameLoop loop = controller.getGameLoop();
        loop.registerCollisionsObserver(SnailImpact.NOONE, this::refresh);
        loop.registerCollisionsObserver(SnailImpact.PLAYAREA, this::endOnCollision);
        loop.registerCollisionsObserver(SnailImpact.WALL, this::endOnCollision);
        loop.registerCollisionsObserver(SnailImpact.END_LEVEL, this::endLevel);
    }

    private void endLevel() {
        updateValues();
        updateView(null, Collections.emptyList());
    }

    private void endOnCollision() {
        updateValues();
        updateView(null, Collections.emptyList());
    }

    private void refresh() {
        updateValues();
        /*
         * next positions calculated by the current trajectory
         */
        final Snail snail = controller.getModel().getSnail();
        final List<Pair<Double, Double>> nextPositions = DoubleStream.iterate(
                snail.getCurrentX() + snail.getWidth() + snail.getDelta() * NEXT_POINTS_DELTA_SCALE / 2,
                x -> x + snail.getDelta() * NEXT_POINTS_DELTA_SCALE)
                .mapToObj(x -> new Pair<>(x, snail.getPositionAt(x)))
                .limit(NEXT_POINTS)
                .collect(Collectors.toList());
        updateView(lastChangingPoint, nextPositions);
    }

    private void updateValues() {
        previousPositions.add(controller.getModel().getSnail().moveAndGetPosition());
    }

    private void updateView(final Pair<Double, Double> changingPoint, final List<Pair<Double, Double>> nextPositions) {
        final Snail snail = controller.getModel().getSnail();
        controller.getView().getSnailDrawer().draw(snail.getCurrentPosition(), snail.getAngleDeg(),
                                                   changingPoint,
                                                   Collections.unmodifiableList(previousPositions),
                                                   Collections.unmodifiableList(nextPositions));
    }

    private void clearController() {
        previousPositions.clear();
        lastChangingPoint = controller.getModel().getSnail().getCurrentPosition();
    }
}
