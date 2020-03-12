package org.snailysis.scenes.gameplay;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.snailysis.model.utilities.Pair;
import org.snailysis.model.entities.snail.Operation;
import org.snailysis.scenes.entities.wall.WallViewImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * Abstract class defining the common behaviour of every view of the game.
 */
public abstract class AbstractGameSceneView implements Initializable {

    @FXML
    private Label function;
    @FXML
    private GridPane buttonsGrid;
    @FXML
    private AnchorPane mutualPane;
    private final List<KeyCode> shortcuts = Arrays.asList(KeyCode.UP, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.DOWN);
    private final Map<KeyCode, Pair<Button, Operation>> buttons = new LinkedHashMap<>();
    private boolean operationAllowed = true;

    /**
     * Getter for view controller.
     * @return
     *      controller of the view
     */
    protected abstract GameSceneController getController();

    /**
     * Getter for label contains current function.
     * @return
     *      label where current function is written
     */
    protected Label getFunctionLabel() {
        return this.function;
    }
    /**
     * Getter for grid contains control's buttons.
     * @return
     *      grid pane contains command's buttons
     */
    protected GridPane getButtonsGrid() {
        return this.buttonsGrid;
    }
    /**
     * Getter for canvas contains walls.
     * @return
     *      canvas contains wall
     */
    protected abstract Canvas getWallsCanvas();
    /**
     * Getter for canvas contains snail.
     * @return
     *      canvas contains snail
     */
    protected abstract Canvas getSnailCanvas();

    /**
     * Getter for pane contains node based on view which extends this one.
     * @return
     *      the pane to fill based on the view which extends this one
     */
    protected Pane getMutualPane() {
        return this.mutualPane;
    }

    /**
     * The routine to call when the controller is attached.
     */
    protected void initView() {
        getFunctionLabel().setText("f(x) = " + getController().getSnailCurrentTrajectory());
        getFunctionLabel().setAlignment(Pos.CENTER);
        buttons.clear();
        fillButtonsMap();
        computeButtonsEvents();
        fillButtonsGrid(getController().getOperationsNumber());
        fillWallsCanvas();
    }
    private void fillButtonsMap() {
        final List<Pair<Button, Operation>> values = getController().getOperations().stream().sorted()
                                                                                             .map(o -> new Pair<>(new Button(o.toString()), o))
                                                                                             .peek(p -> p.getKey().setFocusTraversable(false))
                                                                                             .collect(Collectors.toList());
        IntStream.range(0, getController().getOperationsNumber()).forEach(i -> {
            if (getController().getOperationsNumber() == 2) {
                buttons.put(shortcuts.get(i + 1), values.get(i));
            } else {
                buttons.put(shortcuts.get(i), values.get(i));
            }
        });
    }
    private void computeButtonsEvents() {
        buttons.values().forEach(b -> b.getKey().setOnMouseClicked(e -> doOperationByClick(b.getValue())));
        getSnailCanvas().setOnKeyPressed((event) -> doOperationByKey(event.getCode()));
    }
    private void fillButtonsGrid(final int nOperation) {
        buttons.values().forEach(b -> b.getKey().setPrefSize(getButtonsGrid().getPrefWidth() / getButtonsGrid().getRowConstraints().size() / 2, 
                                      getButtonsGrid().getPrefHeight() / (2 * getButtonsGrid().getColumnConstraints().size())));
        switch (nOperation) {
        case 1:
            getButtonsGrid().add(buttons.get(shortcuts.get(0)).getKey(), 1, 0);
            break;
        case 2:
            getButtonsGrid().add(buttons.get(shortcuts.get(1)).getKey(), 0, 1);
            getButtonsGrid().add(buttons.get(shortcuts.get(2)).getKey(), 2, 1);
            break;
        case 3:
            getButtonsGrid().add(buttons.get(shortcuts.get(0)).getKey(), 1, 0);
            getButtonsGrid().add(buttons.get(shortcuts.get(1)).getKey(), 0, 1);
            getButtonsGrid().add(buttons.get(shortcuts.get(2)).getKey(), 2, 1);
            break;
        case 4:
            getButtonsGrid().add(buttons.get(shortcuts.get(0)).getKey(), 1, 0);
            getButtonsGrid().add(buttons.get(shortcuts.get(1)).getKey(), 0, 1);
            getButtonsGrid().add(buttons.get(shortcuts.get(2)).getKey(), 2, 1);
            getButtonsGrid().add(buttons.get(shortcuts.get(3)).getKey(), 1, 1);
            break;
        default:
            break;
        }
    }
    private void fillWallsCanvas() {
        getController().getWalls().stream().map(w -> new WallViewImpl(w, getWallsCanvas())).forEach(WallViewImpl::render);
    }
    private void disableCommands(final boolean disabled) {
        buttons.values().stream()
                        .map(p -> p.getKey())
                        .forEach(b -> b.setDisable(disabled));
    }
    private void doOperationByKey(final KeyCode k) {
        Optional.of(getController())
            .filter(ctrl -> ctrl.isLoopRunning())
            .filter(oA -> operationAllowed)
            .filter(ctrl -> buttons.containsKey(k))
            .ifPresent(ctrl -> {
                ctrl.registerControlsObserver(ctrl.computeControlsResetTime(), () -> operationAllowed = true);
                ctrl.registerControlsObserver(ctrl.computeControlsResetTime(), () -> disableCommands(false));
                operationAllowed = false;
                disableCommands(true);
                ctrl.performOperationOnSnail(buttons.get(k).getValue());
                getFunctionLabel().setText("f(x) = " + ctrl.getSnailCurrentTrajectory());
        });
    }
    private void doOperationByClick(final Operation o) {
        Optional.of(getController())
            .filter(ctrl -> ctrl.isLoopRunning())
            .filter(oA -> operationAllowed)
            .ifPresent(ctrl -> {
                ctrl.registerControlsObserver(ctrl.computeControlsResetTime(), () -> operationAllowed = true);
                ctrl.registerControlsObserver(ctrl.computeControlsResetTime(), () -> disableCommands(false));
                operationAllowed = false;
                disableCommands(true);
                ctrl.performOperationOnSnail(o);
                getFunctionLabel().setText("f(x) = " + ctrl.getSnailCurrentTrajectory());
        });
    }
}
