package org.snailysis.model.entities.snail;

import java.util.function.UnaryOperator;

import org.snailysis.model.entities.function.Function2D;

/**
 * Enums for basic operation on the snail.
 */
public enum Operation {
    /**
     * Restarts the trajectory applying an identity function.
     */
    RESTART(f -> f.identity(), "f(0)"),
    /**
     * Oppose the trajectory on the X axis.
     */
    NEGATIVE_X(f -> f.opposeOnX(), "f(-x)"),
    /**
     * Oppose the trajectory on the Y axis.
     */
    NEGATIVE_Y(f -> f.opposeOnY(), "-f(x)"),
    /**
     * Dilates the trajectory on the X axis by a coefficient of 2.
     */
    TWO_X(f -> f.dilateOnX(2), "f(2x)"),
    /**
     * Dilates the trajectory on the X axis by a coefficient of 3.
     */
    THREE_X(f -> f.dilateOnX(3), "f(3x)"),
//    /**
//     * Dilates the trajectory on the X axis by a coefficient of 4.
//     */
//    FOUR_X(f -> f.dilateOnX(4), "f(4x)"),
    /**
     * Dilates the trajectory on the X axis by a coefficient of 1/2.
     */
    HALF_X(f -> f.dilateOnX(1.0 / 2), "f(x/2)"),
    /**
     * Dilates the trajectory on the X axis by a coefficient of 1/2.
     */
    THIRD_X(f -> f.dilateOnX(1.0 / 3), "f(x/3)"),
//    /**
//     * Dilates the trajectory on the X axis by a coefficient of 1/2.
//     */
//    QUARTER_X(f -> f.dilateOnX(1.0 / 4), "f(x/4)"),
    /**
     * Dilates the trajectory on the Y axis by a coefficient of 2.
     */
    TWO_Y(f -> f.dilateOnY(2), "2f(x)"),
    /**
     * Dilates the trajectory on the Y axis by a coefficient of 3.
     */
    THREE_Y(f -> f.dilateOnY(3), "3f(x)"),
//    /**
//     * Dilates the trajectory on the Y axis by a coefficient of 4.
//     */
//    FOUR_Y(f -> f.dilateOnY(4), "4f(x)"),
    /**
     * Dilates the trajectory on the X axis by a coefficient of 1/2.
     */
    HALF_Y(f -> f.dilateOnY(1.0 / 2), "f(x)/2"),
    /**
     * Dilates the trajectory on the X axis by a coefficient of 1/2.
     */
    THIRD_Y(f -> f.dilateOnY(1.0 / 3), "f(x)/3"),
//    /**
//     * Dilates the trajectory on the X axis by a coefficient of 1/2.
//     */
//    QUARTER_Y(f -> f.dilateOnY(1.0 / 4), "f(x)/4"),
    /**
     * Trajectory with X squared.
     */
    SQUARED_X(f -> f.powerOnX(2), "f(x²)"),
//    /**
//     * Trajecotry with X cubed.
//     */
//    CUBED_X(f -> f.powerOnX(3), "f(x³)"),
    /**
     * Squared trajectory.
     */
    SQUARED_Y(f -> f.powerOnY(2), "f(x)²"),
//    /**
//     * Cubed trajectory.
//     */
//    CUBED_Y(f -> f.powerOnY(3), "f(x)³"),
    /**
     * Differentiates the trajectory.
     */
    DIFFERENTIATE(f -> f.differentiate(), "f'(x)"),
    /**
     * Integrates the trajectory.
     */
    INTEGRATE(f -> f.integrate(), "∫f(x)");

    private final UnaryOperator<Function2D> operation;
    private final String symbol;

    Operation(final UnaryOperator<Function2D> operation, final String symbol) {
        this.operation = operation;
        this.symbol = symbol;
    }

    /**
     * Performs the operation to the trajectory.
     * 
     * @param trajectory
     *              the trajectory to be changed
     * @return
     *              the new trajectory
     */
    public Function2D perform(final Function2D trajectory) {
        return operation.apply(trajectory);
    }

    @Override
    public String toString() {
        return this.symbol;
    }
}
