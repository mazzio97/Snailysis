package org.snailysis.model.entities.function;

import org.snailysis.model.utilities.exceptions.ExceptionUtilities;

/**
 * Abstract class implementing Function2D.
 */
public abstract class AbstractFunction2D implements Function2D {

    /**
     * Not positive value's exception message.
     */
    protected static final String NOT_POSITIVE_TEXT = "Not Positive Values are not accepted";
    /**
     * Zero value's exception message.
     */
    protected static final String ZERO_TEXT = "Zero Values are not accepted";

    @Override
    public final Function2D identity() {
        return this.getCopy();
    }

    @Override
    public final Function2D translateOnY(final double constant) {
        return this.add(new Constant(constant));
    }

    @Override
    public final Function2D translateOnX(final double constant) {
        return this.compose(new BasicPolynomial2D(1, 1).add(new Constant(constant)));
    }

    @Override
    public final Function2D opposeOnY() {
        return this.dilateOnY(-1);
    }

    @Override
    public final Function2D opposeOnX() {
        return this.dilateOnX(-1);
    }

    @Override
    public final Function2D dilateOnY(final double coefficient) {
        ExceptionUtilities.checkNotZero(coefficient, ZERO_TEXT);
        return this.multiply(new Constant(coefficient));
    }

    @Override
    public final Function2D dilateOnX(final double coefficient) {
        ExceptionUtilities.checkNotZero(coefficient, ZERO_TEXT);
        return this.compose(new BasicPolynomial2D(coefficient, 1));
    }

    @Override
    public final Function2D powerOnY(final int power) {
        ExceptionUtilities.checkPositive(power, NOT_POSITIVE_TEXT);
        return new BasicPolynomial2D(1, power).compose(this);
    }

    @Override
    public final Function2D powerOnX(final int power) {
        ExceptionUtilities.checkPositive(power, NOT_POSITIVE_TEXT);
        return this.compose(new BasicPolynomial2D(1, power));
    }

    @Override
    public final Function2D getCopy() {
        return this.add(new Constant(0));
    }
}
