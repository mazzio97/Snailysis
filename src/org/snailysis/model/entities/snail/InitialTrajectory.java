package org.snailysis.model.entities.snail;

import static org.snailysis.model.Dimension.PLANE_HEIGHT;

import org.snailysis.model.entities.function.Function2D;
import org.snailysis.model.entities.function.Functions;

/**
 * Enums for snail's initial trajectories.
 */
public enum InitialTrajectory {
    /**
     * Constant function: f(x) = 0.
     */
    CONSTANT(Functions.constant(0), 0, "0"),
    /**
     * Positive linear x: f(x) = x.
     */
    POSITIVE_LINEAR_X(Functions.polynomial(1, 1), -PLANE_HEIGHT.get() / 2, "x"),
    /**
     * Negative linear x: f(x) = -x.
     */
    NEGATIVE_LINEAR_X(Functions.polynomial(-1, 1), PLANE_HEIGHT.get() / 2, "-x"),
    /**
     * Positive linear 2x: f(x) = 2x.
     */
    POSITIVE_DOUBLE_X(Functions.polynomial(2, 1), -PLANE_HEIGHT.get() / 2, "2x"),
    /**
     * Negative linear 2x: f(x) = -2x.
     */
    NEGATIVE_DOUBLED_X(Functions.polynomial(-2, 1), PLANE_HEIGHT.get() / 2, "-2x"),
    /**
     * Positive squared x: f(x) = x^2.
     */
    POSITIVE_SQUARED_X(Functions.polynomial(1, 2), -PLANE_HEIGHT.get() / 2, "x²"),
    /**
     * Negative squared x: f(x) = -x^2.
     */
    NEGATIVE_SQUARED_X(Functions.polynomial(-1, 2), PLANE_HEIGHT.get() / 2, "-x²"),
    /**
     * High concavity squared function: f(x) = x^2 - 2x + 1.
     */
    HIGH_CONCAVITY_PARABOLA(Functions.polynomial(1, 1).translateOnY(-1).powerOnY(2), 0, "(x - 1)²"),
    /**
     * Low concavity squared function: f(x) = -x^2 + 2x - 1.
     */
    LOW_CONCAVITY_PARABOLA(Functions.polynomial(1, 1).translateOnY(-1).powerOnY(2).opposeOnY(), 0, "-(x - 1)²");

    private static final double TRANSLATION_SCALE = 0.8;
    private static final double TRANSLATION_ORIGIN = PLANE_HEIGHT.get() / 2;
    private final Function2D function;
    private final double yTranslation;
    private final String text;

    InitialTrajectory(final Function2D function, final double yTranslation, final String text) {
        this.function = function;
        this.yTranslation = yTranslation;
        this.text = text;
    }

    /**
     * Gets the initial trajectory.
     * 
     * @return
     *          the initial trajectory
     */
    public Function2D getTrajectory() {
        return function;
    }

    /**
     * Gets the y initial translation.
     * 
     * @return
     *          the y initial translation
     */
    public double getTranslationY() {
        return yTranslation * TRANSLATION_SCALE + TRANSLATION_ORIGIN;
    }

    @Override
    public String toString() {
        return "f(x) = " + text;
    }
}
