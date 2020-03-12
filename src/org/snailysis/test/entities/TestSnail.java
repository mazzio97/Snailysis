package org.snailysis.test.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import org.snailysis.model.utilities.Pair;
import org.junit.jupiter.api.Test;
import org.snailysis.model.entities.snail.InitialTrajectory;
import org.snailysis.model.entities.snail.Operation;
import org.snailysis.model.entities.snail.Snail;
import org.snailysis.model.entities.snail.SnailBuilder;
import org.snailysis.model.entities.snail.SnailObserver;
import org.snailysis.model.entities.snail.SnailObserver.SnailNotificationType;

class TestSnail {

    private static final String FUNCTION_ERROR = "Function Error";
    private static final String VALUE_ERROR = "Value Error";
    private static final String COPY_ERROR = "Copy Error";
    private static final String OBSERVER_ERROR = "Observer Error";
    private static final String EXCEPTION_ERROR = "Exception Error";

    private static final String NOT_POSITIVE_VALUE_TEXT = "Not Positive Values Are Not Acceptable";
    private static final String ALREADY_SET_TEXT = "This Field Has Already Been Set";
    private static final String ALREADY_BUILT_TEXT = "This Snail Has Already Been Built";
    private static final double INITIAL_POSITIVE_DEGREE = 45;
    private static final double ERROR = 0.01;

    private Snail defaultSnail(final InitialTrajectory traj) {
        return new SnailBuilder().setInitialTrajectory(traj).setDelta(1).setScaleX(1).setScaleY(1).build();
    }

    @Test
    public void builderExceptionsTest() {
        final Snail.Builder builder = new SnailBuilder();
        /*
         * NOT POSITIVE EXCEPTION
         */
        try {
            builder.setHeight(0);
            fail(EXCEPTION_ERROR);
        } catch (IllegalArgumentException exc) {
            assertEquals(EXCEPTION_ERROR, NOT_POSITIVE_VALUE_TEXT, exc.getMessage());
        }
        try {
            builder.setWidth(-1);
            fail(EXCEPTION_ERROR);
        } catch (IllegalArgumentException exc) {
            assertEquals(EXCEPTION_ERROR, NOT_POSITIVE_VALUE_TEXT, exc.getMessage());
        }
        /* 
         * ALREADY SET EXCEPTION
         */
        builder.setHeight(10).setWidth(10).setDelta(10).setScaleX(10).setScaleY(10);
        try {
            builder.setHeight(2);
            fail(EXCEPTION_ERROR);
        } catch (IllegalStateException exc) {
            assertEquals(EXCEPTION_ERROR, ALREADY_SET_TEXT, exc.getMessage());
        }
        try {
            builder.setScaleX(2);
            fail(EXCEPTION_ERROR);
        } catch (IllegalStateException exc) {
            assertEquals(EXCEPTION_ERROR, ALREADY_SET_TEXT, exc.getMessage());
        }
        try {
            builder.setScaleY(2);
            fail(EXCEPTION_ERROR);
        } catch (IllegalStateException exc) {
            assertEquals(EXCEPTION_ERROR, ALREADY_SET_TEXT, exc.getMessage());
        }
        /*
         *  ALREADY BUILT EXCEPTION
         */
        builder.build();
        try {
            builder.build();
            fail(EXCEPTION_ERROR);
        } catch (IllegalStateException exc) {
            assertEquals(EXCEPTION_ERROR, ALREADY_BUILT_TEXT, exc.getMessage());
        }
        try {
            builder.setScaleX(10);
            fail(EXCEPTION_ERROR);
        } catch (IllegalStateException exc) {
            assertEquals(EXCEPTION_ERROR, ALREADY_BUILT_TEXT, exc.getMessage());
        }
        try {
            builder.setScaleY(10);
            fail(EXCEPTION_ERROR);
        } catch (IllegalStateException exc) {
            assertEquals(EXCEPTION_ERROR, ALREADY_BUILT_TEXT, exc.getMessage());
        }
    }

    @Test
    public void basicTest() {
        /*
         *  CREATING Snail -> f(x) = x^2
         */
        final InitialTrajectory traj = InitialTrajectory.POSITIVE_SQUARED_X;
        final Snail snail = new SnailBuilder().setInitialTrajectory(traj)
                                              .setDelta(1)
                                               .setScaleX(1)
                                               .setScaleY(1)
                                               .build();
        assertEquals(FUNCTION_ERROR, "x²", snail.getTrajectoryText());
        assertEquals(FUNCTION_ERROR, "x² + o(x²)", snail.getTrajectoryCompactText());
        assertEquals(VALUE_ERROR, 1, snail.getDelta(), ERROR);
        assertEquals(VALUE_ERROR, 0, snail.getCurrentX(), ERROR);
        assertEquals(VALUE_ERROR, 0 + traj.getTranslationY(), snail.getCurrentY(), ERROR);
        /*
         * MOVING Snail -> x = 1
         */
        assertEquals(VALUE_ERROR, new Pair<>(1.0, 1.0 + traj.getTranslationY()), snail.moveAndGetPosition());
        /*
         * SETTING delta
         */
        try {
            snail.setDelta(0);
            fail(EXCEPTION_ERROR);
        } catch (IllegalArgumentException e) {
            assertEquals(EXCEPTION_ERROR, "Not Positive Value", e.getMessage());
        }
        try {
            snail.setDelta(-1);
            fail(EXCEPTION_ERROR);
        } catch (IllegalArgumentException e) {
            assertEquals(EXCEPTION_ERROR, "Not Positive Value", e.getMessage());
        }
        snail.setDelta(3);
        assertEquals(VALUE_ERROR, 3, snail.getDelta(), ERROR);
        /*
         * MOVING Snail -> x = 10
         */
        IntStream.range(0, 3).forEach(i -> snail.move());
        /*
         * SETTING x-scale to 10 -> y = (10/10)^2 = 1.0
         */
        snail.setScaleX(10);
        assertEquals(VALUE_ERROR, 10.0, snail.getScaleX(), ERROR);
        assertEquals(VALUE_ERROR, new Pair<>(10.0, 1.0 + traj.getTranslationY()), snail.getCurrentPosition());
        /*
         * SETTING y-scale to 10 -> y = (10/10)^2 * 10 = 10.0
         */
        snail.setScaleY(10);
        assertEquals(VALUE_ERROR, 10.0, snail.getScaleY(), ERROR);
        assertEquals(VALUE_ERROR, new Pair<>(10.0, 10.0 + traj.getTranslationY()), snail.getCurrentPosition());
        /*
         * GETTING position at 0 -> y = 0
         */
        assertEquals(VALUE_ERROR, 0 + traj.getTranslationY(), snail.getPositionAt(0), ERROR);
        /*
         * COPYING SNAIL
         */
        final Snail snailCopy = snail.getCopy();
        assertEquals(COPY_ERROR, snail.getDelta(), snailCopy.getDelta(), ERROR);
        assertEquals(COPY_ERROR, snail.getHeight(), snailCopy.getHeight(), ERROR);
        assertEquals(COPY_ERROR, snail.getWidth(), snailCopy.getWidth(), ERROR);
        assertEquals(COPY_ERROR, snail.getScaleX(), snailCopy.getScaleX(), ERROR);
        assertEquals(COPY_ERROR, snail.getScaleY(), snailCopy.getScaleY(), ERROR);
        assertEquals(COPY_ERROR, snail.getTrajectoryText(), snailCopy.getTrajectoryText());
        assertEquals(COPY_ERROR, snail.getAngleDeg(), snailCopy.getAngleDeg(), ERROR);
        assertEquals(COPY_ERROR, snail.getCurrentPosition(), snailCopy.getCurrentPosition());
        snail.move();
        snail.performOperation(Operation.DIFFERENTIATE);
        assertNotEquals(COPY_ERROR, snail.getTrajectoryText(), snailCopy.getTrajectoryText());
        assertNotEquals(COPY_ERROR, snail.getAngleDeg(), snailCopy.getAngleDeg(), ERROR);
        assertNotEquals(COPY_ERROR, snail.getCurrentPosition(), snailCopy.getCurrentPosition());
        /*
         * RESTARTING SNAIL
         */
        snailCopy.performOperation(Operation.DIFFERENTIATE);
        snailCopy.move();
        snailCopy.restart();
        assertEquals(VALUE_ERROR, snail.getDelta(), snailCopy.getDelta(), ERROR);
        assertEquals(VALUE_ERROR, snail.getHeight(), snailCopy.getHeight(), ERROR);
        assertEquals(VALUE_ERROR, snail.getWidth(), snailCopy.getWidth(), ERROR);
        assertEquals(VALUE_ERROR, snail.getScaleX(), snailCopy.getScaleX(), ERROR);
        assertEquals(VALUE_ERROR, snail.getScaleY(), snailCopy.getScaleY(), ERROR);
        assertEquals(VALUE_ERROR, "x²", snailCopy.getTrajectoryText());
        assertEquals(VALUE_ERROR, new Pair<>(0.0, 0.0 + traj.getTranslationY()), snailCopy.getCurrentPosition());
    }

    @Test
    public void operationTest() {
        /*
         * PERFORMING OPPOSITION ON X/Y Snail -> f(x) = x²
         */
        InitialTrajectory traj = InitialTrajectory.POSITIVE_SQUARED_X;
        Snail snail = defaultSnail(traj);
        assertEquals(VALUE_ERROR, INITIAL_POSITIVE_DEGREE, snail.getAngleDeg(), ERROR);
        assertEquals(VALUE_ERROR, new Pair<>(1.0, 1.0 + traj.getTranslationY()), snail.moveAndGetPosition());
        assertEquals(VALUE_ERROR, Math.atan(3), snail.getAngleRad(), ERROR);
        assertEquals(VALUE_ERROR, new Pair<>(2.0, 4.0 + traj.getTranslationY()), snail.moveAndGetPosition());
        // CHECKSTYLE: MagicNumber OFF
        assertEquals(VALUE_ERROR, Math.atan(5), snail.getAngleRad(), ERROR);
        // CHECKSTYLE: MagicNumber ON
        /*
         * OPPOSE ON Y, x = 2 -> f(x) = -x², v = (2, 4)
         */
        snail.performOperation(Operation.NEGATIVE_Y);
        assertEquals(FUNCTION_ERROR, "-x²", snail.getTrajectoryText());
        assertEquals(VALUE_ERROR, -INITIAL_POSITIVE_DEGREE, snail.getAngleDeg(), ERROR);
        assertEquals(VALUE_ERROR, new Pair<>(2.0, 4.0 + traj.getTranslationY()), snail.getCurrentPosition());
        assertEquals(VALUE_ERROR, new Pair<>(3.0, 3.0 + traj.getTranslationY()), snail.moveAndGetPosition());
        /*
         * OPPOSE ON X, x = 3 -> f(x) = -x², v = (3, 3)
         */
        snail.performOperation(Operation.NEGATIVE_X);
        assertEquals(FUNCTION_ERROR, "-x²", snail.getTrajectoryText());
        assertEquals(VALUE_ERROR, -INITIAL_POSITIVE_DEGREE, snail.getAngleDeg(), ERROR);
        assertEquals(VALUE_ERROR, new Pair<>(3.0, 3.0 + traj.getTranslationY()), snail.getCurrentPosition());
        assertEquals(VALUE_ERROR, new Pair<>(4.0, 2.0 + traj.getTranslationY()), snail.moveAndGetPosition());
        /*
         * PERFORMING dilation on X/Y -> f(x) = x² - 2x + 1
         */
        traj = InitialTrajectory.HIGH_CONCAVITY_PARABOLA;
        snail = defaultSnail(traj);
        assertEquals(VALUE_ERROR, new Pair<>(1.0, 0.0 + traj.getTranslationY()), snail.moveAndGetPosition());
        /*
         * DILATE ON Y, x = 1 -> f(x) = 3x² - 6x + 3, v = (1, -3)
         */
        snail.performOperation(Operation.THREE_Y);
        assertEquals(FUNCTION_ERROR, "3x² - 6x + c", snail.getTrajectoryText());
        assertEquals(VALUE_ERROR, new Pair<>(1.0, 0.0 + traj.getTranslationY()), snail.getCurrentPosition());
        // CHECKSTYLE: MagicNumber OFF
        assertEquals(VALUE_ERROR, new Pair<>(2.0, -3.0 + traj.getTranslationY()), snail.moveAndGetPosition());
        // CHECKSTYLE: MagicNumber ON
        /*
         * DILATE ON X, x = 2 -> f(x) = 3/4x² - 3x + 3/2, v = (2, -1.5)
         */
        snail.performOperation(Operation.HALF_X);
        assertEquals(FUNCTION_ERROR, "3/4x² - 3x + c", snail.getTrajectoryText());
        // CHECKSTYLE: MagicNumber OFF
        assertEquals(VALUE_ERROR, new Pair<>(2.0, -3.0 + traj.getTranslationY()), snail.getCurrentPosition());
        // CHECKSTYLE: MagicNumber ON
        snail.move();
        // CHECKSTYLE: MagicNumber OFF
        assertEquals(VALUE_ERROR, new Pair<>(4.0, -6.0 + traj.getTranslationY()), snail.moveAndGetPosition());
        // CHECKSTYLE: MagicNumber ON
        /*
         * PERFORMING power on X/Y -> f(x) = x
         */
        traj = InitialTrajectory.POSITIVE_LINEAR_X;
        snail = defaultSnail(traj);
        assertEquals(VALUE_ERROR, new Pair<>(1.0, 1.0 + traj.getTranslationY()), snail.moveAndGetPosition());
        /*
         * POWER ON Y, x = 1 -> f(x) = x², v = (1, 0)
         */
        snail.performOperation(Operation.SQUARED_Y);
        assertEquals(FUNCTION_ERROR, "x²", snail.getTrajectoryText());
        assertEquals(VALUE_ERROR, new Pair<>(1.0, 1.0 + traj.getTranslationY()), snail.getCurrentPosition());
        assertEquals(VALUE_ERROR, new Pair<>(2.0, 2.0 + traj.getTranslationY()), snail.moveAndGetPosition());
        /*
         * POWER ON X, x = 1 -> f(x) = x4 = (2, 4)
         */
        snail.performOperation(Operation.SQUARED_X);
        assertEquals(FUNCTION_ERROR, "x4", snail.getTrajectoryText());
        assertEquals(VALUE_ERROR, new Pair<>(2.0, 2.0 + traj.getTranslationY()), snail.getCurrentPosition());
        assertEquals(VALUE_ERROR, new Pair<>(3.0, 3.0 + traj.getTranslationY()), snail.moveAndGetPosition());
        /*
         * PERFORMING differentiation and integration -> f(x) = -3x² + 6x - 3, v = (0, 2)
         */
        traj = InitialTrajectory.LOW_CONCAVITY_PARABOLA;
        snail = defaultSnail(traj);
        snail.performOperation(Operation.THREE_Y);
        assertEquals(VALUE_ERROR, new Pair<>(1.0, 2.0 + traj.getTranslationY()), snail.moveAndGetPosition());
        /*
         * INTEGRATE, x = 1 -> f(x) = -x³ + 3x² - 3x, v = (1, 2)
         */
        snail.performOperation(Operation.INTEGRATE);
        assertEquals(FUNCTION_ERROR, "-x³ + 3x² - 3x", snail.getTrajectoryText());
        assertEquals(VALUE_ERROR, new Pair<>(1.0, 2.0 + traj.getTranslationY()), snail.getCurrentPosition());
        assertEquals(VALUE_ERROR, new Pair<>(2.0, 1.0 + traj.getTranslationY()), snail.moveAndGetPosition());
        /*
         * DIFFERENTIATE, x = 2 -> f(x) = -3x² + 6x - 3, v = (2, 4)
         */
        snail.performOperation(Operation.DIFFERENTIATE);
        assertEquals(FUNCTION_ERROR, "-3x² + 6x - c", snail.getTrajectoryText());
        assertEquals(VALUE_ERROR, new Pair<>(2.0, 1.0 + traj.getTranslationY()), snail.getCurrentPosition());
        assertEquals(VALUE_ERROR, new Pair<>(3.0, 4.0 + traj.getTranslationY()), snail.moveAndGetPosition());
        /*
         * DIFFERENTIATE, x = 3 -> f(x) = -6x + 6, v = (3, -2)
         */
        snail.performOperation(Operation.DIFFERENTIATE);
        assertEquals(FUNCTION_ERROR, "-6x + c", snail.getTrajectoryText());
        assertEquals(VALUE_ERROR, new Pair<>(3.0, 4.0 + traj.getTranslationY()), snail.getCurrentPosition());
        // CHECKSTYLE: MagicNumber OFF
        assertEquals(VALUE_ERROR, new Pair<>(4.0, -2.0 + traj.getTranslationY()), snail.moveAndGetPosition());
        // CHECKSTYLE: MagicNumber ON
        /*
         * DIFFERENTIATE, x = 4 -> f(x) = 6, v = (4, 8)
         */
        snail.performOperation(Operation.DIFFERENTIATE);
        assertEquals(FUNCTION_ERROR, "c", snail.getTrajectoryText());
        // CHECKSTYLE: MagicNumber OFF
        assertEquals(VALUE_ERROR, new Pair<>(4.0, -2.0 + traj.getTranslationY()), snail.getCurrentPosition());
        assertEquals(VALUE_ERROR, new Pair<>(5.0, -2.0 + traj.getTranslationY()), snail.moveAndGetPosition());
        // CHECKSTYLE: MagicNumber ON
        /*
         * INTEGRATE, x = 5 -> f(x) = -6x, v = (4, -2)
         */
        snail.performOperation(Operation.INTEGRATE);
        assertEquals(FUNCTION_ERROR, "-6x", snail.getTrajectoryText());
        // CHECKSTYLE: MagicNumber OFF
        assertEquals(VALUE_ERROR, new Pair<>(5.0, -2.0 + traj.getTranslationY()), snail.getCurrentPosition());
        assertEquals(VALUE_ERROR, new Pair<>(6.0, -8.0 + traj.getTranslationY()), snail.moveAndGetPosition());
        // CHECKSTYLE: MagicNumber ON
    }

    @Test
    public void observerTest() {
        final InitialTrajectory traj = InitialTrajectory.POSITIVE_LINEAR_X;
        final Snail snail = defaultSnail(traj);
        final List<SnailNotificationType> notificationsList = new LinkedList<>();
        final SnailObserver observer = n -> notificationsList.add(n);
        /*
         * EXCEPTION
         */
        try {
            snail.detachObserver(observer);
            fail(EXCEPTION_ERROR);
        } catch (IllegalStateException e) {
            assertEquals(EXCEPTION_ERROR, "Controller Not Yet Attached", e.getMessage());
        }
        /*
         * ATTACH OBSERVER: NOTIFICATIONS
         */
        snail.attachObserver(observer);
        snail.move();
        assertEquals(OBSERVER_ERROR, Collections.emptyList(), notificationsList);
        snail.performOperation(Operation.INTEGRATE);
        assertEquals(OBSERVER_ERROR, Arrays.asList(SnailNotificationType.OPERATION_PERFORMED), notificationsList);
        snail.restart();
        assertEquals(OBSERVER_ERROR, 
                     Arrays.asList(SnailNotificationType.OPERATION_PERFORMED, SnailNotificationType.RESTART),
                     notificationsList);
        notificationsList.clear();
        /*
         * DETACH OBSERVER: NO NOTIFICATIONS
         */
        snail.detachObserver(observer);
        try {
            snail.detachObserver(observer);
            fail(EXCEPTION_ERROR);
        } catch (IllegalStateException e) {
            assertEquals(EXCEPTION_ERROR, "Controller Not Yet Attached", e.getMessage());
        }
        snail.move();
        assertEquals(OBSERVER_ERROR, Collections.emptyList(), notificationsList);
        snail.performOperation(Operation.INTEGRATE);
        assertEquals(OBSERVER_ERROR, Collections.emptyList(), notificationsList);
        snail.restart();
        assertEquals(OBSERVER_ERROR, Collections.emptyList(), notificationsList);
        /*
         * ATTACH OBSERVER: NOTIFICATIONS
         */
        snail.attachObserver(observer);
        IntStream.range(0, 10).forEach(i -> {
            snail.performOperation(Operation.NEGATIVE_Y);
            snail.restart();
        });
        assertEquals(OBSERVER_ERROR, 10, notificationsList.stream()
                                                          .filter(SnailNotificationType.OPERATION_PERFORMED::equals)
                                                          .count());
        assertEquals(OBSERVER_ERROR, 10, notificationsList.stream()
                                                          .filter(SnailNotificationType.RESTART::equals)
                                                          .count());
        /*
         * DETACH ALL OBSERVERS: NO NOTIFICATIONS
         */
        snail.clearObserversList();
        notificationsList.clear();
        try {
            snail.detachObserver(observer);
            fail(EXCEPTION_ERROR);
        } catch (IllegalStateException e) {
            assertEquals(EXCEPTION_ERROR, "Controller Not Yet Attached", e.getMessage());
        }
        snail.move();
        assertEquals(OBSERVER_ERROR, Collections.emptyList(), notificationsList);
        snail.performOperation(Operation.INTEGRATE);
        assertEquals(OBSERVER_ERROR, Collections.emptyList(), notificationsList);
        snail.restart();
        assertEquals(OBSERVER_ERROR, Collections.emptyList(), notificationsList);
    }
}
