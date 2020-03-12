package org.snailysis.scenes.gameplay;

import java.util.Set;

import org.snailysis.MainWindow;
import org.snailysis.model.collisions.SnailImpact;
import org.snailysis.model.entities.snail.Operation;
import org.snailysis.model.entities.wall.Wall;
import org.snailysis.model.levels.Level;
import org.snailysis.model.levels.manager.LevelManager;
import org.snailysis.scenes.Controller;
import org.snailysis.scenes.FXMLScene;
import org.snailysis.scenes.View;
import org.snailysis.scenes.levels.manager.LevelManagerController;
import org.snailysis.scenes.menu.MenuBaseController;
import org.snailysis.scenes.utilities.save.SaveCustomBuild;
import org.snailysis.scenes.utilities.save.SaveCustomBuildimpl;

import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * Implementations of PlayLevelController interface.
 */
public final class PlayLevelControllerImpl extends AbstractGameSceneController implements PlayLevelController {

    /**
     * Constructor of PlayLevelControllerImpl.
     * @param ctrl
     *          main controller
     */
    public PlayLevelControllerImpl(final Controller ctrl) {
        super(ctrl);
        getMainController().setGameLoop(new GameLoopImpl(ctrl));
        setSnail();
    }
    @Override
    public View getView() {
        return getMainController().getView();
    }
    @Override
    public Set<Operation> getOperations() {
        return getMainController().getModel().getCurrentLevel().getAvailableOperations();
    }
    @Override
    public Set<Wall> getWalls() {
        return getMainController().getModel().getCurrentLevel().getObstacles();
    }
    private void setSnail() {
        getMainController().getModel().setSnail(getMainController().getModel().getCurrentLevel().getInitTrajectory());
    }
    @Override
    public void setUpObservers() {
            registerImpactObserver(SnailImpact.END_LEVEL, () -> showEndLevelWindow(SnailImpact.END_LEVEL));
            registerImpactObserver(SnailImpact.PLAYAREA, () -> showEndLevelWindow(SnailImpact.PLAYAREA));
            registerImpactObserver(SnailImpact.WALL, () -> showEndLevelWindow(SnailImpact.WALL));
    }
    @Override
    public void backToMenu() {
        unlockNextLevel();
        getMainController().getView().setCanvas(null);
        getMainController().getModel().endLevel();
        MainWindow.getPrimaryStage().setScene(FXMLScene.MENU.getScene());
        try {
            ((MenuBaseController) FXMLScene.MENU.getLoader().getController()).pressPlay(null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void restartLevel() {
        unlockNextLevel();
        getMainController().getModel().getSnail().restart();
        getMainController().setGameLoop(new GameLoopImpl(getMainController()));
        getMainController().getGameLoop().start();
        setUpObservers();
    }

    private void showEndLevelWindow(final SnailImpact sI) {
        final Stage modal = new Stage();
        modal.setScene(sI.equals(SnailImpact.END_LEVEL) ? FXMLScene.LEVEL_COMPLETE.getScene()
                                                        : FXMLScene.LEVEL_FAILED.getScene());
        final EndLevelView elv = (sI.equals(SnailImpact.END_LEVEL) ? FXMLScene.LEVEL_COMPLETE.getLoader().getController()
                                                                   : FXMLScene.LEVEL_FAILED.getLoader().getController());
        elv.setController(this);
        modal.initModality(Modality.WINDOW_MODAL);
        modal.setOnCloseRequest(e -> backToMenu());
        modal.initOwner(MainWindow.getPrimaryStage().getScene().getWindow());
        modal.setResizable(false);
        modal.show();
    }

    private void unlockNextLevel() {
        if (getMainController().getModel().isLevelComplete()) {
            getMainController().getModel().unlockNextDefaultLevel();
            final SaveCustomBuild<LevelManager> saveProgress = new SaveCustomBuildimpl<>();
            saveProgress.save(getMainController().getModel().getDefaultManager(), LevelManagerController.DEFAULT_SAVE_FILE);
        }
    }
    @Override
    public String getCurrentLvlName() {
        final Level currLvl = getMainController().getModel().getCurrentLevel();
        return getMainController().getModel()
                                  .getDefaultManager()
                                  .getLevelName(currLvl)
                                  .orElse(getMainController().getModel()
                                                             .getCustomManager()
                                                             .getLevelName(currLvl)
                                                             .orElse(null));
    }
}
