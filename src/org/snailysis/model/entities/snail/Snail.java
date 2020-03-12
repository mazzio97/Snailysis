package org.snailysis.model.entities.snail;

import org.snailysis.model.utilities.Pair;

/**
 * Interface that models the character of the game.
 */
public interface Snail {

    /**
     * Interface that models a builder for the Snail interface.
     */
    interface Builder {

        /**
         * Sets the initial trajectory of the snail.
         * 
         * @param initialTrajectory
         *      the initial trajectory
         * @return
         *      the builder itself
         * @throws
         *      IllegalStateException if already set
         */
        Builder setInitialTrajectory(InitialTrajectory initialTrajectory);

        /**
         * Sets the width of the snail.
         * 
         * @param width
         *      the value of the width
         * @return
         *      the builder itself
         * @throws
         *      IllegalStateException if already set
         *      IllegalArgumentException if width is not positive
         */
        Builder setWidth(double width);

        /**
         * Sets the height of the snail.
         * 
         * @param height
         *      the value of the height
         * @return
         *      the builder itself
         * @throws
         *      IllegalStateException if already set
         *      IllegalArgumentException if height is not positive
         */
        Builder setHeight(double height);

        /**
         * Sets the x-scale of the snail.
         * 
         * @param xScale
         *      the value of the scale
         * @return
         *      the builder itself
         * @throws
         *      IllegalStateException if already set
         *      IllegalArgumentException if scale is not positive
         */
        Builder setScaleX(double xScale);

        /**
         * Sets the y-scale of the snail.
         * 
         * @param yScale
         *      the value of the scale
         * @return
         *      the builder itself
         * @throws
         *      IllegalStateException if already set
         *      IllegalArgumentException if scale is not positive
         */
        Builder setScaleY(double yScale);

        /**
         * Sets the delta of the snail.
         * 
         * @param delta
         *      the value of the delta
         * @return
         *      the builder itself
         * @throws
         *      IllegalStateException if already set
         *      IllegalArgumentException if delta is not positive
         */
        Builder setDelta(double delta);

        /**
         * Builds the snail.
         * 
         * @return
         *      the built snail
         * @throws
         *      IllegalStateException if not all compulsory fields were set
         *      IllegalStateException if build has already been called
         */
        Snail build();
    }

    /**
     * Gets a copy of the snail.
     * 
     * @return
     *          the copy of the snail
     */
    Snail getCopy();

    /**
     * Restarts the snail from zero position with its initial trajectory.
     * 
     * @return
     *          the snail itself
     */
    Snail restart();

    /**
     * Gets the width of the snail.
     * 
     * @return
     *          the width of the snail
     */
    double getWidth();

    /**
     * Gets the height of the snail.
     * 
     * @return
     *          the height of the snail
     */
    double getHeight();

    /**
     * Sets the scale between the written x value and the effective (scaled) x value: y = f(x / xScale).
     * 
     * @param xScale
     *          the scale's value
     * @throws
     *          IllegalArgumentException if scale is not positive
     */
    void setScaleX(double xScale);

    /**
     * Gets the scale between the written x value and the effective (scaled) x value.
     * 
     * @return
     *          the value of the scale
     */
    double getScaleX();

    /**
     * Sets the scale between f(x) and snail's y-coordinate: y = f(x) / yScale.
     * 
     * @param scale
     *          the scale's value
     * @throws
     *          IllegalArgumentException if scale is not positive
     */
    void setScaleY(double scale);

    /**
     * Gets the scale between f(x) and snail's y-coordinate.
     * 
     * @return
     *          the value of the scale
     */
    double getScaleY();

    /**
     * Gets the delta of the snail movement.
     * 
     * @return
     *          the value of the delta
     */
    double getDelta();

    /**
     * Sets the delta of the snail movement.
     * 
     * @param delta
     *          the value of the delta
     * @throws
     *          IllegalArgumentException if delta is not positive
     */
    void setDelta(double delta);

    /**
     * Gets the snail's current x-coordinate.
     * 
     * @return
     *          the current x-coordinate
     */
    double getCurrentX();

    /**
     * Gets the snail's current y-coordinate.
     * 
     * @return
     *          the current y-coordinate
     */
    double getCurrentY();

    /**
     * Gets the snail's current position without moving it.
     * 
     * @return
     *          the position
     */
    Pair<Double, Double> getCurrentPosition();

    /**
     * Gets the snail's position at the given x-coordinate without moving it.
     * 
     * @param x
     *          the value of the coordinate
     * @return
     *          the y-coordinate of the position
     */
    double getPositionAt(double x);

    /**
     * Gets the snail's trajectory angle relative to the x axis.
     * 
     * @return
     *          the value of the angle in radians
     */
    double getAngleRad();

    /**
     * Gets the snail's trajectory angle relative to the x axis.
     * 
     * @return
     *          the value of the angle in degrees
     */
    double getAngleDeg();

    /**
     * Moves the snail forwards.
     */
    void move();

    /**
     * Moves the snail then return its position.
     * 
     * @return
     *          the position after being moved
     */
    Pair<Double, Double> moveAndGetPosition();

    /**
     * Performs an operation over the snail's trajectory.
     * It is guaranteed that the y-coordinate will be the same for the point in which is performed.
     * 
     * @param operation
     *          the operation to be performed
     */
    void performOperation(Operation operation);

    /**
     * Gets the snail's trajectory as text.
     * 
     * @return
     *          a string representing the snail's trajectory
     */
    String getTrajectoryText();

    /**
     * Gets a compact text version of the snail's trajectory.
     * 
     * @return
     *          a string representing the snail's trajectory
     */
    String getTrajectoryCompactText();

    /**
     * Attaches an observer to the snail.
     * 
     * @param observer
     *          the observer
     */
    void attachObserver(SnailObserver observer);

    /**
     * Detaches an observer from the snail.
     * 
     * @param observer
     *          the observer
     * @throws
     *          IllegalStateException if the observer is not attached yet
     */
    void detachObserver(SnailObserver observer);

    /**
     * Detatches all the snail observers.
     */
    void clearObserversList();
}
