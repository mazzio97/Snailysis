package org.snailysis.model;

/**
 * Game difficulty enum.
 */
public enum DifficultGame {

    /**
     * Easy Mode.
     */
    EASY(0.5),
    /**
     * Medium Mode.
     */
    MEDIUM(1.0),
    /**
     * Hard Mode.
     */
    HARD(1.5);

    private double difficult;

    DifficultGame(final double value) {
        this.difficult = value;
    }

    /**
     * Gets the factor to be applied to snail's delta.
     *
     * @return
     *          the difficulty factor
     */
    public double getDifficult() {
        return this.difficult;
    }
}
