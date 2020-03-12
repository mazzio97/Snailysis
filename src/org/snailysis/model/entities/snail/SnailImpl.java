package org.snailysis.model.entities.snail;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.snailysis.model.utilities.Pair;
import org.snailysis.model.entities.function.Function2D;
import org.snailysis.model.entities.function.Functions;
import org.snailysis.model.entities.snail.SnailObserver.SnailNotificationType;
import org.snailysis.model.utilities.exceptions.ExceptionUtilities;

/**
 * Basic implementation of the Snail interface.
 * For a correct compact text representation, polynomials should not exceed third grade.
 *
 * When performing an operation on the Snail's interface, it is guaranteed that the y-coordinate
 * will be the same both for old and new trajectory. In order to do this, the new g(x) trajectory
 * will implicitly be transalted into a new local g'(x) function by a translation vector v = (x0, f(x0) - g(x0)).
 *
 * The resulting y is so that, given T(Tx, Ty) the translation vector and S(Sx, Sy) the scale values of the axis:
 *      (y - Ty) / Sy = f((x - Tx) / Sx) -> y = Sy * f((x - Tx) / Sx) + Ty
 */
public final class SnailImpl implements Snail {

    private static final String NOT_POSITIVE_VALUE_TEXT = "Not Positive Value";
    private static final String NOT_YET_ATTACHED = "Controller Not Yet Attached";

    private static final double DEFAULT_X = 0;
    private static final Pair<Double, Double> DEFAULT_TRANSLATION_VECTOR = new Pair<>(0.0, 0.0);

    private final List<SnailObserver> observers;
    private final Function2D initialTrajectory;
    private final Pair<Double, Double> initialTranslationVector;
    private final double width;
    private final double height;

    private Function2D currentTrajectory;
    private Pair<Double, Double> currentTranslationVector;
    private double xPosition;
    private double xScale;
    private double yScale;
    private double delta;

    /**
     * Private constructor to create a copy of the snail.
     *
     * @param observers
     *          a list of snail's observers
     * @param initialTrajectory
     *          the initial trajectory of the snail (used when restarting)
     * @param initialTranslationVector
     *          the initial translation vector of the snail (used when restarting)
     * @param currentTrajectory
     *          the current trajectory of the snail
     * @param currentTranslationVector
     *          the current translation vector of the snail
     * @param height
     *          the height of the snail
     * @param width
     *          the width of the snail
     * @param xPosition
     *          the x position of the snail
     * @param xScale
     *          the x-scale of the snail
     * @param yScale
     *          the y-scale of the snail
     * @param delta
     *          the delta movement of the snail
     */
    private SnailImpl(final List<SnailObserver> observers,
                      final Function2D initialTrajectory, final Pair<Double, Double> initialTranslationVector,
                      final Function2D currentTrajectory, final Pair<Double, Double> currentTranslationVector,
                      final double xPosition, final double width, final double height,
                      final double xScale, final double yScale, final double delta) {
        this.observers = new LinkedList<>(observers);
        this.initialTrajectory = initialTrajectory.getCopy();
        this.initialTranslationVector = initialTranslationVector.getCopy();
        this.currentTrajectory = currentTrajectory.getCopy();
        this.currentTranslationVector = currentTranslationVector.getCopy();
        this.xPosition = xPosition;
        this.width = width;
        this.height = height;
        this.xScale = xScale;
        this.yScale = yScale;
        this.delta = delta;
    }

    /**
     * Package protected constructor to create a snail with given dimension and trajectory.
     *
     * @param initialTrajectory
     *          the initial trajectory of the snail
     * @param yTranslation
     *          the y starting position of the snail
     * @param width
     *          the width of the snail
     * @param height
     *          the height of the snail
     * @param xScale
     *          the x-scale of the snail
     * @param yScale
     *          the y-scale of the snail
     * @param delta
     *          the delta movement of the snail
     */
    SnailImpl(final Function2D initialTrajectory, final double yTranslation, final double width,
            final double height, final double xScale, final double yScale, final double delta) {
        this(new LinkedList<>(), initialTrajectory, addPair(DEFAULT_TRANSLATION_VECTOR, 0, yTranslation),
                initialTrajectory, addPair(DEFAULT_TRANSLATION_VECTOR, 0, yTranslation),
                DEFAULT_X, width, height, xScale, yScale, delta);
    }

    @Override
    public Snail getCopy() {
        return new SnailImpl(new LinkedList<>(), initialTrajectory, initialTranslationVector,
                             currentTrajectory, currentTranslationVector, xPosition,
                             width, height, xScale, yScale, delta);
    }

    @Override
    public Snail restart() {
        this.currentTrajectory = initialTrajectory.getCopy();
        this.currentTranslationVector = initialTranslationVector.getCopy();
        this.xPosition = DEFAULT_X;
        observers.forEach(o -> o.notifyObserver(SnailNotificationType.RESTART));
        return this;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public double getScaleX() {
        return xScale;
    }

    @Override
    public void setScaleX(final double xScale) {
        ExceptionUtilities.checkPositive(xScale, NOT_POSITIVE_VALUE_TEXT);
        this.xScale = xScale;
    }

    @Override
    public double getScaleY() {
        return yScale;
    }

    @Override
    public void setScaleY(final double yScale) {
        ExceptionUtilities.checkPositive(yScale, NOT_POSITIVE_VALUE_TEXT);
        this.yScale = yScale;
    }

    @Override
    public double getDelta() {
        return delta;
    }

    @Override
    public void setDelta(final double delta) {
        ExceptionUtilities.checkPositive(delta, NOT_POSITIVE_VALUE_TEXT);
        this.delta = delta;
    }

    @Override
    public double getCurrentX() {
        return xPosition + currentTranslationVector.getFirst();
    }

    @Override
    public double getCurrentY() {
        return this.getPositionAt(getCurrentX());
    }

    @Override
    public Pair<Double, Double> getCurrentPosition() {
        return new Pair<>(getCurrentX(), getCurrentY());
    }

    @Override
    public double getPositionAt(final double x) {
        return evaluateFunction(currentTrajectory, x, currentTranslationVector.getFirst(),
                                currentTranslationVector.getSecond(), xScale, yScale);
    }

    @Override
    public double getAngleRad() {
        // angle = arctan(f(x + delta) - f(x) / delta)
        return Math.atan2(getPositionAt(getCurrentX() + delta) - getCurrentY(), delta);
    }

    @Override
    public double getAngleDeg() {
        return getAngleRad() * 180 / Math.PI;
    }

    @Override
    public void move() {
        xPosition += delta;
    }

    @Override
    public Pair<Double, Double> moveAndGetPosition() {
        move();
        return getCurrentPosition();
    }

    @Override
    public void performOperation(final Operation operation) {
        // the integral of 0 is supposed to become x
        final Function2D newTrajectory = operation.perform(Optional.of(operation)
                .filter(o -> o.equals(Operation.INTEGRATE) && currentTrajectory.equals(Functions.constant(0)))
                .map(o -> Functions.constant(1))
                .orElse(currentTrajectory));
        currentTranslationVector = new Pair<>(getCurrentX(), getCurrentY()
                                                - evaluateFunction(newTrajectory, DEFAULT_X, 0, 0, xScale, yScale));
        currentTrajectory = newTrajectory;
        xPosition = DEFAULT_X;
        observers.forEach(o -> o.notifyObserver(SnailNotificationType.OPERATION_PERFORMED));
    }

    @Override
    public String getTrajectoryText() {
        final String trajectory = currentTrajectory.toString();
        return Optional.of(trajectory.split(" [+-] "))
                       .map(v -> v[v.length - 1])
                       .filter(t -> !t.contains("x"))
                       .filter(t -> !t.equals("0"))
                       .map(t -> trajectory.substring(0, trajectory.lastIndexOf(t)).concat("c"))
                       .orElse(trajectory);
    }

    @Override
    public String getTrajectoryCompactText() {
        /*
         * gets the max grade of the function then:
         * - if it is zero grade returns o(1)
         * - otherwise returns the term plus o(grade)
         */
        return Optional.of(currentTrajectory.toString().split(" [+-] ")[0])
                       .filter(s -> s.contains("x"))
                       .map(s -> s + " + o(" + s.replaceAll("[-/0-9]", "") + ")")
                       .orElse("o(1)");
    }

    @Override
    public void attachObserver(final SnailObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detachObserver(final SnailObserver observer) {
        Optional.of(observers.remove(observer))
                .filter(Boolean::booleanValue)
                .orElseThrow(() -> new IllegalStateException(NOT_YET_ATTACHED));
    }

    @Override
    public void clearObserversList() {
        observers.clear();
    }

    private double evaluateFunction(final Function2D trajectory, final double x, final double xTranslation,
            final double yTranslation, final double xScale, final double yScale) {
        return yScale * trajectory.evaluateAt((x - xTranslation) / xScale) + yTranslation;
    }

    // static method as it is used inside a constructor call
    private static Pair<Double, Double> addPair(final Pair<Double, Double> p, final double x, final double y) {
        return new Pair<>(p.getFirst() + x, p.getSecond() + y);
    }
}
