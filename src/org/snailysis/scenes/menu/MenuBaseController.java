package org.snailysis.scenes.menu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import org.snailysis.scenes.Controller;
import org.snailysis.scenes.FXMLScene;
import org.snailysis.scenes.levels.builder.OperationsSelectionControllerImpl;
import org.snailysis.scenes.levels.builder.OperationsSelectionView;
import org.snailysis.scenes.levels.manager.LevelManagerControllerImpl;
import org.snailysis.scenes.levels.manager.LevelManagerView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;


public class MenuBaseController implements Initializable  {
	/**
	 * class that controls the menu.
	 */
	//Variable
	private static String cb = "-fx-background-color:#006600"; //Color Base
	private static String ch = "-fx-background-color:#339933"; //Color HighLighter
	
	//FXML Object
	@FXML
	private AnchorPane panelBase;
	
	@FXML
	private AnchorPane panelButton;
	
	@FXML
	private SplitPane split;
	
	@FXML
	private Button buttonBase;
	
	@FXML
	private Button buttonPlay;
	
	@FXML
	private Button buttonSettings;
	
	@FXML
	private Button buttonBuilder;
	
	@FXML
	private Button buttonCredit;
	
	@FXML
	private Button buttonExit;
	
	private Controller controller;
	
	/**
     * Sets the controller.
     * 
     * @param ctrl
     *          controller
     */
	public void setController(final Controller ctrl) {
	    this.controller = ctrl;
	}
	/**
     * Controll play button
     * 
     * @param event
     *          controll the event of button
     */
	public void pressPlay (ActionEvent event) throws IOException  {
		//Reset Color
		buttonBase.setStyle(cb);
		buttonPlay.setStyle(ch);
		buttonSettings.setStyle(cb);
		buttonBuilder.setStyle(cb);
		buttonCredit.setStyle(cb);
		buttonExit.setStyle(cb);
		//System.out.println("Ho premuto il tasto play");
		//Change AnchorPane
//		Parent menuPlay = FXMLLoader.load(getClass().getResource("/org/snailysis/scenes/menu/PlayMenu.fxml"));
		panelBase.getChildren().clear();
                final FXMLLoader loader = FXMLScene.LEVEL_SELECTION.getLoader();
                final LevelManagerView fxmlCtrl = loader.getController();
                fxmlCtrl.setController(new LevelManagerControllerImpl(controller));
                panelBase.getChildren().add(FXMLScene.LEVEL_SELECTION.getScene().getRoot());
	}
	/**
     * Controll sNailysis button
     * 
     * @param event
     *          controll the event of button
     */
	public void presssNailysis (ActionEvent event) throws IOException {
		//Reset Color
		buttonBase.setStyle(ch);
		buttonPlay.setStyle(cb);
		buttonSettings.setStyle(cb);
		buttonBuilder.setStyle(cb);
		buttonCredit.setStyle(cb);
		buttonExit.setStyle(cb);
		
		//System.out.println("Ho premuto il tasto sNailysis");
		panelBase.getChildren().clear();
		panelBase.getChildren().add(FXMLScene.SNAILYSISHOME.getScene().getRoot());
		//panelBase.maxWidthProperty().bind(split.widthProperty().multiply(0.23));
	}

	public void pressSettings (ActionEvent event) throws IOException {
		//Reset Color
		buttonBase.setStyle(cb);
		buttonPlay.setStyle(cb);
		buttonSettings.setStyle(ch);
		buttonBuilder.setStyle(cb);
		buttonCredit.setStyle(cb);
		buttonExit.setStyle(cb);
		//System.out.println("Ho premuto il tasto Settings");
		//Change AnchorPane
		panelBase.getChildren().clear();
		panelBase.getChildren().add(FXMLScene.SETTINGS.getScene().getRoot());
		
	}

	public void pressBuilder (ActionEvent event) throws IOException {
		//Reset Color
		buttonBase.setStyle(cb);
		buttonPlay.setStyle(cb);
		buttonSettings.setStyle(cb);
		buttonBuilder.setStyle(ch);
		buttonCredit.setStyle(cb);
		buttonExit.setStyle(cb);
		//System.out.println("Ho premuto il tasto Builder");
		
		//Change AnchorPane
//		Parent menuBuilder = FXMLScene.OPERATIONS_SELECTION.getLoader().load(); //FXMLLoader.load(getClass().getResource("/org/snailysis/scenes/menu/BuilderMenu.fxml"));
		panelBase.getChildren().clear();
		final FXMLLoader loader = FXMLScene.OPERATIONS_SELECTION.getLoader();
                final OperationsSelectionView fxmlCtrl = loader.getController();
                fxmlCtrl.setController(new OperationsSelectionControllerImpl(controller));
                panelBase.getChildren().add(FXMLScene.OPERATIONS_SELECTION.getScene().getRoot());
                
               // panelBase.maxWidthProperty().bind(split.widthProperty().multiply(0.23));

	}
	
	public void pressCredit (ActionEvent event) throws IOException {
		//Reset Color
		buttonBase.setStyle(cb);
		buttonPlay.setStyle(cb);
		buttonSettings.setStyle(cb);
		buttonBuilder.setStyle(cb);
		buttonCredit.setStyle(ch);
		buttonExit.setStyle(cb);
		//System.out.println("Ho premuto il tasto Credit");
		
		//Change AnchorPane
		panelBase.getChildren().clear();
		panelBase.getChildren().add(FXMLScene.CREDIT.getScene().getRoot());
		
	}
	
	public void pressExit (ActionEvent event) {
		//Reset Color
		buttonBase.setStyle(cb);
		buttonPlay.setStyle(cb);
		buttonSettings.setStyle(cb);
		buttonBuilder.setStyle(cb);
		buttonCredit.setStyle(cb);
		buttonExit.setStyle(ch);
		//System.out.println("Ho premuto il tasto Exit");
		
		//Change AnchorPane
		System.exit(0);
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) { }

}
