package org.snailysis.scenes;

import java.util.function.Consumer;
import java.util.stream.Stream;

import org.snailysis.model.Dimension;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * NOTE: this class is currently not used inside the project. It should be a variable container class with window
 * dimensions and utility static methods for stage and canvas resizing proportioned to the screen resolution.
 * 
 * Utility class with default dimensions and scaling utility methods.
 */
public final class ViewDimension {

    /**
     * Default Stage Width.
     */
    public static final double STAGE_WIDTH = 4096;
    /**
     * Default Stage Heigth.
     */
    public static final double STAGE_HEIGHT = 2304;
    /**
     * Default Game Scene Width.
     */
    public static final double GAME_SCENE_WIDTH = 4096;
    /**
     * Default Game Scene Heigth.
     */
    public static final double GAME_SCENE_HEIGHT = 1984;
    /**
     * System Width Resolution.
     */
    public static final double SCREEN_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
    /**
     * System Height Resolution.
     */
    public static final double SCREEN_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
    /**
     * Main stage proportion regards to the screen.
     */
    public static final double WINDOW_PROPORTION = 1;

    private ViewDimension() { }

    /**
     * Stage and game scene resizing factor regards to default STAGE WIDTH and STAGE HEIGHT.
     * 
     * @return
     *          the resizing factor
     */
    public static double getResizingFactor() {
        return WINDOW_PROPORTION * Math.min(SCREEN_WIDTH / STAGE_WIDTH, SCREEN_HEIGHT / STAGE_HEIGHT);
    }

    /**
     * Game scene x-scaling factor regards to Model Dimension.
     * 
     * @return
     *          the x-scaling factor
     */
    public static double getXScalingFactor() {
        return Dimension.PLANE_WIDTH.get() / getGameSceneWidth();
    }

    /**
     * Game scene x-scaling factor regards to Model Dimension.
     * 
     * @return
     *          the x-scaling factor
     */
    public static double getYScalingFactor() {
        return Dimension.PLANE_HEIGHT.get() / getGameSceneHeight();
    }

    /**
     * Gets the width a main stage should have basing upon system resolution.
     *
     * @return
     *          the stage width
     */
    public static double getStageWidth() {
        return getResizingFactor() * STAGE_WIDTH;
    }

    /**
     * Gets the height a main stage should have basing upon system resolution.
     *
     * @return
     *          the stage height
     */
    public static double getStageHeight() {
        return getResizingFactor() * STAGE_HEIGHT;
    }

    /**
     * Gets the width a game scene should have basing upon system resolution.
     *
     * @return
     *          the game scene width
     */
    public static double getGameSceneWidth() {
        return getResizingFactor() * GAME_SCENE_WIDTH;
    }

    /**
     * Gets the height a game scene should have basing upon system resolution.
     *
     * @return
     *          the game scene height
     */
    public static double getGameSceneHeight() {
        return getResizingFactor() * GAME_SCENE_HEIGHT;
    }

    /**
     * Utility method that resizes the stage to a constant proportion of the screen.
     *
     * @param stage
     *          the stage to be scaled
     * @return
     *          the stage itself
     */
    public static Stage resizeStage(final Stage stage) {
        stage.setWidth(getStageWidth());
        stage.setHeight(getStageHeight());
        stage.setX((SCREEN_WIDTH - getStageWidth()) / 2);
        stage.setY((SCREEN_HEIGHT - getStageHeight()) / 2);
        return stage;
    }

    /**
     * Utility method that resizes the game scene to a constant proportion of the screen.
     *
     * @param canvas
     *          the game scene canvas to be resized
     */
    public static void resizeGameScene(final Canvas... canvas) {
        Stream.of(canvas)
              .peek(c -> c.setWidth(getGameSceneWidth()))
              .forEach(c -> c.setHeight(getGameSceneHeight()));
    }

    /**
     * Utility method that scales the canvas to a constant proportion of the screen.
     *
     * @param canvas
     *          the game scene canvas to be scaled
     * @param drawRoutine
     *          the drawing method
     * @return
     *          the canvas itself
     */
    public static Canvas scaleGameSceneAndDraw(final Canvas canvas, final Consumer<GraphicsContext> drawRoutine) {
        final GraphicsContext context = canvas.getGraphicsContext2D();
        context.save();
        context.scale(getXScalingFactor(), getYScalingFactor());
        drawRoutine.accept(context);
        context.restore();
        return canvas;
    }

    /**
     * Utility method that prepend the correct scaling to a a general affine done in a canvas.
     * 
     * @param canvas
     *          the game scene where the affine is performed
     * @param affine
     *          the affine transformation
     * @return
     *          the affine itself
     */
    public static Affine scaleGameSceneAffine(final Canvas canvas, final Affine affine) {
        affine.prependScale(getXScalingFactor(), getYScalingFactor());
        return affine;
    }
}
