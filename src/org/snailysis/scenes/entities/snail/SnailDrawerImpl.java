package org.snailysis.scenes.entities.snail;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.snailysis.model.utilities.Pair;
import org.snailysis.model.Dimension;
import org.snailysis.scenes.entities.EntitySprite;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

/**
 * Basic implementation of the SnailView interface.
 */
public final class SnailDrawerImpl implements SnailDrawer {

    private static final double AXIS_WIDTH = 1;
    private static final double INDICATORS_RADIUS = Dimension.SNAIL_HEIGHT.get() / 10;
    private static final double DROOL_RADIUS = Dimension.SNAIL_HEIGHT.get() / 8;
    private static final List<Double> DASHES = Arrays.asList(20.0, 10.0);
    private static final Color BACKGROUND_COLOR = Color.rgb(152, 178, 29);
    private static final Color AXIS_COLOR = Color.DARKGREEN;
    private static final Color DROOL_COLOR = Color.rgb(120, 100, 255, 0.37);
    private static final Effect DROOL_EFFECT = new Shadow(BlurType.ONE_PASS_BOX, DROOL_COLOR, DROOL_RADIUS);

    private final Image snail = EntitySprite.SNAIL.getImage();
    private final Image origin = EntitySprite.ORIGIN.getImage();
    private final Canvas canvas;

    /**
     * Public constructor for SnailViewImpl class.
     * 
     * @param canvas
     *          the canvas where the snail must be drawn
     */
    public SnailDrawerImpl(final Canvas canvas) {
        this.canvas = getCleanedCanvas(canvas);
    }

    @Override
    public void draw(final Pair<Double, Double> currentPosition, final double angle,
                     final Pair<Double, Double> changingPoint,
                     final List<Pair<Double, Double>> previousPositions,
                     final List<Pair<Double, Double>> nextPositions) {
//        ViewDimension.scaleGameSceneAndDraw(getCleanedCanvas(canvas), c -> {
//            squareCanvas(c.getCanvas(), Color.RED);
//            drawOrigin(c, changingPoint);
//            drawIndicators(c, nextPositions);
//            drawDrool(c, previousPositions);
//            drawSnail(c, currentPosition, angle);
//        });
//        squareCanvas(canvas, Color.BLUE);
        final GraphicsContext c = getCleanedCanvas(canvas).getGraphicsContext2D();
        drawOrigin(c, changingPoint);
        drawIndicators(c, nextPositions);
        drawDrool(c, previousPositions);
        drawSnail(c, currentPosition, angle);
    }

    private Canvas getCleanedCanvas(final Canvas canvas) {
        canvas.getGraphicsContext2D().setFill(BACKGROUND_COLOR);
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        return canvas;
    }

    private void drawOrigin(final GraphicsContext context, final Pair<Double, Double> changingPosition) {
        context.save();
        context.setLineWidth(AXIS_WIDTH);
        context.setLineDashes(DASHES.stream().mapToDouble(Double::doubleValue).toArray());
        context.setStroke(AXIS_COLOR);
        Optional.ofNullable(changingPosition)
                .map(this::overturn)
                .map(Pair::getSecond)
                .ifPresent(y -> context.strokeLine(0, y, canvas.getWidth(), y));
        Optional.ofNullable(changingPosition)
                .filter(p -> !p.getFirst().equals(0.0))
                .map(this::overturn)
                .map(p -> toUpperLeft(p, origin.getHeight(), origin.getWidth()))
                .ifPresent(p -> context.drawImage(origin, p.getFirst(), p.getSecond()));
        context.restore();
    }

    private void drawIndicators(final GraphicsContext context, final List<Pair<Double, Double>> points) {
        context.setFill(Color.BLACK);
        points.stream()
              .map(this::overturn)
              .map(p -> toUpperLeft(p, INDICATORS_RADIUS, INDICATORS_RADIUS))
              .forEach(p -> context.fillOval(p.getFirst(), p.getSecond(), INDICATORS_RADIUS, INDICATORS_RADIUS));
    }

    private void drawDrool(final GraphicsContext context, final List<Pair<Double, Double>> points) {
        /*
         * Saving the graphic context and sets the drool parameters
         */
        context.save();
        context.setEffect(DROOL_EFFECT);
        context.setStroke(DROOL_COLOR);
        context.setLineWidth(DROOL_RADIUS);
        context.beginPath();
        context.moveTo(overturn(points.get(0)).getFirst(), overturn(points.get(0)).getSecond());
        /*
         * Drawing the curve basing upon points
         */
        points.stream()
              .skip(1)
              .map(this::overturn)
              .forEach(p -> {
                  context.lineTo(p.getFirst(), p.getSecond());
                  context.stroke();
                  context.closePath();
                  context.beginPath();
                  context.moveTo(p.getFirst(), p.getSecond());
              });
        context.closePath();
        context.restore();
    }

    private void drawSnail(final GraphicsContext context, final Pair<Double, Double> position, final double angle) {
        context.save();
        Stream.of(position)
              .map(this::overturn)
//              .peek(p -> context.setTransform(ViewDimension.scaleGameSceneAffine(
//                         context.getCanvas(), new Affine(new Rotate(-angle, p.getFirst(), p.getSecond())))))
              .peek(p -> context.setTransform(new Affine(new Rotate(-angle, p.getFirst(), p.getSecond()))))
              .map(p -> new Pair<>(p.getFirst(), p.getSecond() - snail.getHeight()))
              .forEach(p -> context.drawImage(snail, p.getFirst(), p.getSecond()));
        context.restore();
    }

//    private void squareCanvas(final Canvas canvas, final Color color) {
//        final GraphicsContext context = canvas.getGraphicsContext2D();
//        context.setStroke(color);
//        context.setLineWidth(10);
//        context.strokeLine(0, 0, canvas.getWidth(), 0);
//        context.strokeLine(canvas.getWidth(), 0, canvas.getWidth(), canvas.getHeight());
//        context.strokeLine(canvas.getWidth(), canvas.getHeight(), 0, canvas.getHeight());
//        context.strokeLine(0, canvas.getHeight(), 0, 0);
//    }

    private Pair<Double, Double> overturn(final Pair<Double, Double> position) {
        return new Pair<>(position.getFirst(), canvas.getHeight() - position.getSecond());
    }

    private Pair<Double, Double> toUpperLeft(final Pair<Double, Double> position, final double h, final double w) {
        return new Pair<>(position.getFirst() - w / 2, position.getSecond() - h / 2);
    }
}
