package org.snailysis.scenes.gameplay;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Generic view for all end game modal windows.
 */
public class EndLevelView {
    @FXML
    private Label endLvlLabel;
    @FXML
    private ImageView endLvlImg;
    @FXML
    private Button menuBtn;
    @FXML
    private Button playAgainBtn;

    private PlayLevelController ctrl;
    /**
     * Setter for controller.
     * @param c
     *      controller
     */
    public void setController(final PlayLevelController c) {
        this.ctrl = c;
    }
    /**
     * Initialize the view of modal windows.
     */
    public void initView() {
        menuBtn.setOnMouseClicked(e -> {
            final Stage stage = (Stage) menuBtn.getScene().getWindow();
            stage.close();
            ctrl.backToMenu();
        });
        playAgainBtn.setOnMouseClicked(e -> {
            final Stage stage = (Stage) playAgainBtn.getScene().getWindow();
            stage.close();
            ctrl.restartLevel();
        });
    }
    /**
     * Getter for the model window Label.
     * @return
     *      end level label
     */
    public Label getEndLvlLabel() {
        return endLvlLabel;
    }
    /**
     * Getter for the model window ImageView.
     * @return
     *      end level image
     */
    public ImageView getEndLvlImg() {
        return endLvlImg;
    }
}
