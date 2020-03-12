package org.snailysis.scenes.levels.builder;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.IntStream;

import org.snailysis.model.utilities.Pair;
import org.snailysis.MainWindow;
import org.snailysis.model.collisions.SnailImpact;
import org.snailysis.scenes.FXMLScene;
import org.snailysis.scenes.entities.wall.WallView;
import org.snailysis.scenes.entities.wall.WallViewImpl;
import org.snailysis.scenes.gameplay.AbstractGameSceneView;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 * The FXML Controller of the GameScene.fxml file for the level creation. 
 */
public final class LevelBuilderView extends AbstractGameSceneView {

    @FXML
    private Canvas snailCanvas;
    @FXML
    private Canvas wallsCanvas;

    private static final int BUTTONS_GRID_ROWS = 2;
    private static final int BUTTONS_GRID_COLUMNS = 2;

    private final Button resetSnailBtn = new Button("Reset Snail");
    private final Button resetWallsBtn = new Button("Reset Walls");
    private final Button exitBtn = new Button("Exit");
    private final Button saveBtn = new Button("Save");
    private final List<Button> builderBtns = new ArrayList<>(Arrays.asList(resetSnailBtn, resetWallsBtn, exitBtn, saveBtn));
    private final Set<WallView> walls = new LinkedHashSet<>();

    private LevelBuilderController ctrl;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        /*
         * Create a GridPane occupying the mutualPane position 
         * and containing the buttons to interact with the level builder
         */
        final GridPane builderGrid = new GridPane();
        final RowConstraints row = new RowConstraints(getMutualPane().getPrefHeight() / BUTTONS_GRID_ROWS);
        final ColumnConstraints column = new ColumnConstraints(getMutualPane().getPrefWidth() / BUTTONS_GRID_COLUMNS);
        IntStream.range(0, BUTTONS_GRID_ROWS).forEach(i -> builderGrid.getRowConstraints().add(row));
        IntStream.range(0, BUTTONS_GRID_COLUMNS).forEach(i -> builderGrid.getColumnConstraints().add(column));
        builderBtns.stream()
                   .peek(b -> b.setFocusTraversable(false))
                   .peek(b -> GridPane.setHalignment(b, HPos.CENTER))
                   .map(b -> new Pair<>(builderBtns.indexOf(b), b))
                   .forEach(b -> builderGrid.add(b.getValue(), b.getKey() % BUTTONS_GRID_COLUMNS, b.getKey() / BUTTONS_GRID_COLUMNS));
        getMutualPane().getChildren().add(builderGrid);
    }

    /**
     * Attach the relative controller to this view.
     * 
     * @param ctrl
     *          the controller to attach to this view
     */
    public void setController(final LevelBuilderController ctrl) {
        this.ctrl = ctrl;
        initView();
    }

    @Override
    protected LevelBuilderController getController() {
        return this.ctrl;
    }

    @Override
    protected Canvas getWallsCanvas() {
        return this.wallsCanvas;
    }

    @Override
    protected Canvas getSnailCanvas() {
        return this.snailCanvas;
    }

    @Override
    protected void initView() {
        super.initView();
        ctrl.getView().setCanvas(this.getSnailCanvas());
        /*
         * Set event handlers for level builder buttons and the canvas
         */
        resetSnailBtn.setOnMouseClicked(e -> {
            ctrl.resetSnail();
            registerFinishSolutionObservers();
        });
        resetWallsBtn.setOnMouseClicked(e -> {
            ctrl.removeAllWalls();
            walls.forEach(WallView::clear);
            walls.clear();
        });
        saveBtn.setOnMouseClicked(e -> {
            final TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Save your level");
            dialog.setHeaderText("Enter a name for the level");
            dialog.showAndWait().ifPresent(name -> {
                ctrl.saveLevel(name);
                resetOnExit();
            });
        });
        exitBtn.setOnMouseClicked(e -> resetOnExit());
        getWallsCanvas().setOnMouseClicked(e -> e.consume());
        /*
         * Disable buttons whose actions are not possible in this moment
         */
        resetWallsBtn.setDisable(true);
        saveBtn.setDisable(true);
        resetSnailBtn.setDisable(false);
        /*
         * Start snail movement
         */
        registerFinishSolutionObservers();
        ctrl.startLoop();
    }

    private void registerFinishSolutionObservers() {
        ctrl.registerImpactObserver(SnailImpact.END_LEVEL, () -> resetSnailBtn.setDisable(true));
        ctrl.registerImpactObserver(SnailImpact.END_LEVEL, () -> resetWallsBtn.setDisable(false));
        ctrl.registerImpactObserver(SnailImpact.END_LEVEL, () -> saveBtn.setDisable(false));
        ctrl.registerImpactObserver(SnailImpact.END_LEVEL, () -> ctrl.startPuttingWalls());
        ctrl.registerImpactObserver(SnailImpact.END_LEVEL, () -> getWallsCanvas().setOnMouseClicked(e -> updateWalls(e.getSceneX())));
    }

    private void updateWalls(final double wX) {
        if (ctrl.checkWallAddable(wX)) {
            ctrl.createWall(wX);
            walls.add(ctrl.getWalls()
                          .stream()
                          .filter(w -> w.getGapX() == wX)
                          .map(w -> new WallViewImpl(w, getWallsCanvas()))
                          .peek(WallView::render)
                          .findFirst()
                          .orElse(null));
        } else {
            walls.remove(walls.stream()
                              .filter(o -> o.getWall().gapWidthRange().contains(wX))
                              .peek(WallView::clear)
                              .peek(wv -> ctrl.removeWall(wv.getWall().getGapX()))
                              .findFirst()
                              .orElse(null));
        }
    }

    private void resetOnExit() {
        walls.forEach(w -> w.clear());
        walls.clear();
        getButtonsGrid().getChildren().clear();
        getController().getView().setCanvas(null);
        ctrl.stopLoop();
        MainWindow.getPrimaryStage().setScene(FXMLScene.MENU.getScene());
    }

}
