package org.snailysis.scenes.gameplay;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
/**
 * View of the play level game phase extends AbstractGameSceneView.
 */
public final class PlayLevelView extends AbstractGameSceneView {

    private final Label lvlName = new Label();
    @FXML
    private Label function;
    @FXML
    private GridPane buttonsGrid;
    @FXML
    private Canvas snailCanvas;
    @FXML
    private Canvas wallsCanvas;

    private PlayLevelController ctrl;
    /**
     * Setter for the view controller.
     * @param c
     *      controller
     */
    public void setController(final PlayLevelController c) {
        this.ctrl = c;
        getButtonsGrid().getChildren().clear();
        wallsCanvas.getGraphicsContext2D().clearRect(0, 0, wallsCanvas.getWidth(), wallsCanvas.getHeight()); 
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        setLevelLabel();
        ctrl.getView().setCanvas(this.getSnailCanvas());
        ctrl.setUpObservers();
        getController().startLoop();
    }

    @Override
    public PlayLevelController getController() {
        return this.ctrl;
    }

    @Override
    public Label getFunctionLabel() {
        return this.function;
    }

    @Override
    public GridPane getButtonsGrid() {
        return this.buttonsGrid;
    }

    @Override
    public Canvas getWallsCanvas() {
        return this.wallsCanvas;
    }

    @Override
    public Canvas getSnailCanvas() {
        return this.snailCanvas;
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        getMutualPane().getChildren().add(lvlName);
    }

    private void setLevelLabel() {
        lvlName.setText("Level " + ctrl.getCurrentLvlName());
        lvlName.setTextFill(function.getTextFill());
        lvlName.setFont(function.getFont());
        lvlName.setPrefWidth(getMutualPane().getPrefWidth());
        lvlName.setPrefHeight(getMutualPane().getPrefHeight());
        lvlName.setAlignment(function.getAlignment());
        lvlName.setPadding(function.getPadding());
    }
}
