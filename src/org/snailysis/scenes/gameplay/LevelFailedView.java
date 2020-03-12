package org.snailysis.scenes.gameplay;

import java.net.URL;
import java.util.ResourceBundle;

import org.snailysis.scenes.entities.EntitySprite;

import javafx.fxml.Initializable;
/**
 *
 */
public final class LevelFailedView extends EndLevelView implements Initializable {
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        initView();
        getEndLvlImg().setImage(EntitySprite.GAME_OVER.getImage());
        getEndLvlLabel().setText("Game Over");
    }
}
