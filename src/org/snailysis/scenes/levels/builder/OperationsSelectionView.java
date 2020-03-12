package org.snailysis.scenes.levels.builder;

import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.snailysis.model.utilities.Pair;
import org.snailysis.MainWindow;
import org.snailysis.model.entities.snail.InitialTrajectory;
import org.snailysis.model.entities.snail.Operation;
import org.snailysis.scenes.FXMLScene;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * FXML Controller of the OperationsSelection.fxml file.
 */
public final class OperationsSelectionView implements Initializable {

    private static final String BACKGROUND_DEFAULT_COLOR = "-fx-background-color: #339933";
    private static final String BACKGROUND_SELECTED_COLOR = "-fx-background-color: #82C168";
    private static final double BUTTON_HEIGHT = 60;
    private static final double BUTTON_WIDTH = 80;

    @FXML
    private ComboBox<InitialTrajectory> initTrajComboBox;
    @FXML
    private Button confirmButton;
    @FXML
    private Button resetButton;
    @FXML
    private TilePane operationsPane;

    private Map<Operation, ToggleButton> opButtons;
    private OperationsSelectionController ctrl;

    /**
     * 
     * @param ctrl
     *          the controller to set for this view
     */
    public void setController(final OperationsSelectionController ctrl) {
        this.ctrl = ctrl;
        this.initTrajComboBox.getSelectionModel().select(OperationsSelectionController.DEFAULT_INIT_TRAJECTORY);
        this.initTrajComboBox.setOnAction(e -> ctrl.modifyInitialTrajectory(initTrajComboBox.getSelectionModel().getSelectedItem()));
        confirmButton.setDisable(opButtons.entrySet().stream()
                                                     .filter(e -> e.getValue().isSelected())
                                                     .map(e -> e.getKey())
                                                     .peek(ctrl::selectOperation)
                                                     .count() == 0);
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        /*
         * Initial trajectory combo box
         */
        this.initTrajComboBox.getItems().addAll(Arrays.asList(InitialTrajectory.values()));
        /*
         * Operations buttons
         */
        this.opButtons = Arrays.asList(Operation.values())
                               .stream()
                               .sorted()
                               .map(o -> new Pair<>(o, new ToggleButton(o.toString())))
                               .peek(p -> p.getSecond().setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT))
                               .peek(b -> b.getSecond().setTextFill(Color.WHITE))
                               .peek(p -> p.getSecond().setFont(new Font(16)))
                               .peek(p -> p.getSecond().setStyle(BACKGROUND_DEFAULT_COLOR))
                               .peek(p -> p.getSecond().setOnMouseClicked(e -> handleOperationSelection(p.getFirst(), p.getSecond())))
                               .collect(Collectors.toMap(p -> p.getFirst(), p -> p.getSecond()));
        operationsPane.getChildren().addAll(opButtons.values());
        /*
         * Confirm button
         */
        confirmButton.setOnMouseClicked(e -> {
            final FXMLLoader loader = FXMLScene.LEVEL_BUILDER.getLoader();
            final LevelBuilderView lvlBldView = loader.getController();
            MainWindow.getPrimaryStage().setScene(FXMLScene.LEVEL_BUILDER.getScene());
            lvlBldView.setController(ctrl.startLevelCreation());
            ctrl.removeAllOperations();
            resetHandler();
        });
        /*
         * Reset button
         */
        resetButton.setOnMouseClicked(e -> resetHandler());
    }

    private void resetHandler() {
        ctrl.removeAllOperations();
        opButtons.values().stream()
                          .peek(tb -> tb.setDisable(false))
                          .peek(tb -> tb.setSelected(false))
                          .forEach(tb -> tb.setStyle(BACKGROUND_DEFAULT_COLOR));
        confirmButton.setDisable(true);
    }

    private void handleOperationSelection(final Operation op, final ToggleButton tb) {
        if (tb.isSelected()) {
            ctrl.selectOperation(op);
            tb.setStyle(BACKGROUND_SELECTED_COLOR);
        } else {
            ctrl.removeOperation(op);
            tb.setStyle(BACKGROUND_DEFAULT_COLOR);
        }
        opButtons.entrySet().stream()
                            .filter(b -> !b.getValue().isSelected())
                            .forEach(b -> b.getValue().setDisable(!ctrl.checkIfAddable(b.getKey())));
        confirmButton.setDisable(!opButtons.values().stream().anyMatch(b -> b.isSelected()));
    }

}
