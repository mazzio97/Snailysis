package org.snailysis.scenes.menu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.measure.unit.Dimension.Model;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.snailysis.audio.Music;
import org.snailysis.model.DifficultGame;
import org.snailysis.model.ModelImpl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SettingsMenuController implements Initializable{

	@FXML
	private AnchorPane panelBase;
	
	@FXML
	private AnchorPane panelButton;
	
	@FXML
	private Button on;
	
	@FXML
	private Button off;
	
	@FXML
	private Button easy;
	
	@FXML
	private Button medium;
	
	@FXML
	private Button hard;
	
		
	public void setONMusic (ActionEvent event) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		Music.setOnMusic();
		on.setDisable(true);
		off.setDisable(false);
	}
	
	public Button getButton() {
		return on;
	}
	
	public void setOFFMusic (ActionEvent event) {
		Music.setOffMusic();
		off.setDisable(true);
		on.setDisable(false);
	}
	
	public void setEASY (ActionEvent event) {
	        ModelImpl.getInstance().setDifficulty(DifficultGame.EASY);
		easy.setDisable(true);
		medium.setDisable(false);
		hard.setDisable(false);
	}
	
	public void setMEDIUM (ActionEvent event) {
            ModelImpl.getInstance().setDifficulty(DifficultGame.MEDIUM);
		easy.setDisable(false);
		medium.setDisable(true);
		hard.setDisable(false);
	}
	
	public void setHARD (ActionEvent event) {
            ModelImpl.getInstance().setDifficulty(DifficultGame.HARD);
		easy.setDisable(false);
		medium.setDisable(false);
		hard.setDisable(true);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
