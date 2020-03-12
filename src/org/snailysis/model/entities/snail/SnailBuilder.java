package org.snailysis.model.entities.snail;

import java.util.Optional;

import org.snailysis.model.Dimension;
import org.snailysis.model.entities.snail.Snail.Builder;
import org.snailysis.model.utilities.exceptions.ExceptionUtilities;

import static org.snailysis.model.utilities.exceptions.ExceptionUtilities.checkPositive;
import static org.snailysis.model.utilities.exceptions.ExceptionUtilities.checkNotNull;

/**
 * Basic implementation of a Snail Builder.
 * Default values are taken from "Dimension" enum, and the default initial trajectory is "x".
 */
public final class SnailBuilder implements Builder {

    private static final String NOT_POSITIVE_VALUE_TEXT = "Not Positive Values Are Not Acceptable";
    private static final String NULL_TEXT = "Null Values Are Not Acceptable";
    private static final String ALREADY_SET_TEXT = "This Field Has Already Been Set";
    private static final String ALREADY_BUILT_TEXT = "This Snail Has Already Been Built";

    private static final InitialTrajectory DEFAULT_TRAJECTORY = InitialTrajectory.POSITIVE_LINEAR_X;
    private static final double DEFAULT_WIDTH = Dimension.SNAIL_WIDTH.get();
    private static final double DEFAULT_HEIGHT = Dimension.SNAIL_HEIGHT.get();
    private static final double DEFAULT_XSCALE = Dimension.PLANE_WIDTH.get() / Dimension.PLANE_WIDTH_SCALE.get();
    private static final double DEFAULT_YSCALE = Dimension.PLANE_HEIGHT.get() / Dimension.PLANE_HEIGHT_SCALE.get();
    private static final double DEFAULT_DELTA = Dimension.SNAIL_DELTA.get();

    private Optional<InitialTrajectory> initialTrajectory = Optional.empty();
    private Optional<Double> width = Optional.empty();
    private Optional<Double> height = Optional.empty();
    private Optional<Double> xScale = Optional.empty();
    private Optional<Double> yScale = Optional.empty();
    private Optional<Double> delta = Optional.empty();
    private boolean built;

    @Override
    public Builder setInitialTrajectory(final InitialTrajectory initialTrajectory) {
        checkNotBuilt();
        checkNotSet(this.initialTrajectory);
        checkNotNull(initialTrajectory, NULL_TEXT);
        this.initialTrajectory = Optional.of(initialTrajectory);
        return this;
    }

    @Override
    public Builder setWidth(final double width) {
        checkNotBuilt();
        checkNotSet(this.width);
        checkPositive(width, NOT_POSITIVE_VALUE_TEXT);
        this.width = Optional.of(width);
        return this;
    }

    @Override
    public Builder setHeight(final double height) {
        checkNotBuilt();
        checkNotSet(this.height);
        checkPositive(height, NOT_POSITIVE_VALUE_TEXT);
        this.height = Optional.of(height);
        return this;
    }

    @Override
    public Builder setScaleX(final double xScale) {
        checkNotBuilt();
        checkNotSet(this.xScale);
        checkPositive(xScale, NOT_POSITIVE_VALUE_TEXT);
        this.xScale = Optional.of(xScale);
        return this;
    }

    @Override
    public Builder setScaleY(final double yScale) {
        checkNotBuilt();
        checkNotSet(this.yScale);
        checkPositive(yScale, NOT_POSITIVE_VALUE_TEXT);
        this.yScale = Optional.of(yScale);
        return this;
    }

    @Override
    public Builder setDelta(final double delta) {
        checkNotBuilt();
        checkNotSet(this.delta);
        checkPositive(delta, NOT_POSITIVE_VALUE_TEXT);
        this.delta = Optional.of(delta);
        return this;
    }

    @Override
    public Snail build() {
        checkNotBuilt();
        built = true;
        return new SnailImpl(initialTrajectory.orElse(DEFAULT_TRAJECTORY).getTrajectory(),
                             initialTrajectory.orElse(DEFAULT_TRAJECTORY).getTranslationY(),
                             width.orElse(DEFAULT_WIDTH), height.orElse(DEFAULT_HEIGHT), 
                             xScale.orElse(DEFAULT_XSCALE), yScale.orElse(DEFAULT_YSCALE),
                             delta.orElse(DEFAULT_DELTA));
    }

    private void checkNotSet(final Optional<?> optional) {
        ExceptionUtilities.throwExceptionIf(optional.isPresent(), new IllegalStateException(ALREADY_SET_TEXT));
    }

    private void checkNotBuilt() {
        ExceptionUtilities.throwExceptionIf(built, new IllegalStateException(ALREADY_BUILT_TEXT));
    }
}
