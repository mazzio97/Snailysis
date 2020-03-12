package org.snailysis.scenes.levels.manager;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.snailysis.MainWindow;
import org.snailysis.model.levels.Level;
import org.snailysis.model.utilities.Pair;
import org.snailysis.scenes.FXMLScene;
import org.snailysis.scenes.gameplay.PlayLevelView;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * FXML Controller of the LevelSelection.fxml file.
 */
public class LevelManagerView implements Initializable {

    private static final String BACKGROUND_COLOR = "-fx-background-color: #006600";
    private static final double BUTTON_WIDTH = 90;
    private static final double BUTTON_HEIGHT = 90;
    private static final double FONT_SIZE = 18;

    @FXML
    private TilePane defaultLvlSelection;

    @FXML
    private TilePane customLvlSelection;

    private LevelManagerController ctrl;

    @Override
    public final void initialize(final URL location, final ResourceBundle resources) {
    }

    /**
     * 
     * @param ctrl
     *          the controller to set for this view
     */
    public void setController(final LevelManagerController ctrl) {
        this.ctrl = ctrl;
        defaultLvlSelection.getChildren().clear();
        defaultLvlSelection.getChildren().addAll(getLevelsButtons(ctrl.getDefaultLevels()));
        customLvlSelection.getChildren().clear();
        customLvlSelection.getChildren().addAll(getLevelsButtons(ctrl.getCustomLevels()));
    }

    private List<Button> getLevelsButtons(final Collection<Level> levels) {
        return levels.stream()
                     .map(l -> new Pair<>(l, new Button(ctrl.getLevelName(l))))
                     .peek(p -> p.getSecond().setStyle(BACKGROUND_COLOR))
                     .peek(p -> p.getSecond().setOnMouseClicked(e -> {
                         final FXMLLoader loader = FXMLScene.GAME.getLoader();
                         final PlayLevelView fxmlCtrl = loader.getController();
                         fxmlCtrl.setController(ctrl.selectLevelToPlay(p.getFirst()));
                         MainWindow.getPrimaryStage().setScene(FXMLScene.GAME.getScene());
                     }))
                     .peek(p -> p.getSecond().setDisable(!ctrl.isUnlocked(p.getFirst())))
                     .map(p -> p.getSecond())
                     .peek(b -> b.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT))
                     .peek(b -> b.setTextFill(Color.WHITE))
                     .peek(b -> b.setFont(new Font(FONT_SIZE)))
                     .peek(b -> b.setWrapText(true))
                     .collect(Collectors.toList());
    }

}
