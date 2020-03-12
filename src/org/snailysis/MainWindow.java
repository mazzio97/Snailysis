package org.snailysis;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import org.snailysis.audio.Music;
import org.snailysis.scenes.Controller;
import org.snailysis.scenes.ControllerImpl;
import org.snailysis.scenes.FXMLScene;
import org.snailysis.scenes.menu.MenuBaseController;

/**
 * Class responsible of loading the main window of the game.
 */
public final class MainWindow extends Application {

    private static Stage primaryStage;

    /**
     * Launch application.
     * 
     * @param args
     *          console arguments on game launch.
     */
    public static void main(final String[] args) {
        launch();
    }

    @Override
    public void start(final Stage primaryStage) {
        setPrimaryStage(primaryStage);
        final Controller controller = new ControllerImpl();
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("org/snailysis/images/snail.png"));
        primaryStage.setTitle("Snailysis");
        final MenuBaseController mbc = FXMLScene.MENU.getLoader().getController();
        mbc.setController(controller);
        primaryStage.setScene(FXMLScene.MENU.getScene());
        FXMLScene.GAME.getScene().getStylesheets().add(getClass().getResource("/org/snailysis/styles/game-buttons.css").toExternalForm());
        FXMLScene.LEVEL_BUILDER.getScene().getStylesheets().add(getClass().getResource("/org/snailysis/styles/game-buttons.css").toExternalForm());
        // ViewDimension.resizeStage(primaryStage);
        primaryStage.show();
        try {
            Music.setOnMusic();
        } catch (Exception e) {
            System.out.println("Could not load music");
        }
    }

    /**
     * Static setter of the primary stage.
     * @param stage
     *          the stage to set as primary
     */
    public static void setPrimaryStage(final Stage stage) {
        MainWindow.primaryStage = stage;
    }

    /**
     * Getter for primaryStage.
     * @return
     *      the primaryStage
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
