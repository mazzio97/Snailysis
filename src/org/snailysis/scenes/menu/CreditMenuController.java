package org.snailysis.scenes.menu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.*;

public class CreditMenuController implements Initializable{
	
	@FXML
	private Button button1;
	
	@FXML
	private Button button2;
	
	@FXML
	private Button button3;
	
	@FXML
	private Button button4;
	
	@FXML
	private AnchorPane anchorPane1;
	
	@FXML
	private AnchorPane anchorPane2;
	
	@FXML
	private AnchorPane anchorPane3;
	
	@FXML
	private AnchorPane anchorPane4;
	
	@FXML
	private Label labelDesciption1;
	
	@FXML
	private Label labelDesciption2;
	
	@FXML
	private Label labelDesciption3;
	
	@FXML
	private Label labelDesciption4;
	
	String textLine;
	
	
	
	public void pressanchorPane1 (ActionEvent event) throws IOException {
		
		button1.setOpacity(0);
		button1.setDisable(true);
		//-
		//FileReader fr = new FileReader("/org/snailysis/file/Description1.txt");
		//BufferedReader reader = new BufferedReader(fr);
		labelDesciption1.setText("Luca Giuliani (Snail & Function)\n\n"
		        + "“Snails, don’t talk to me about snails!”");
		//System.out.println(reader.read());
		//reader.close();
	}
	
	public void pressanchorPane2 (ActionEvent event) {
		button2.setOpacity(0);
		button2.setDisable(true);
		labelDesciption2.setText("Milo Marchetti (Collisions & Controls)\n\n"
		        + "“I used to be an adventurer like you, then I took a project in the knee”");
	}
	
	public void pressanchorPane3 (ActionEvent event) {
		button3.setOpacity(0);
		button3.setDisable(true);
		labelDesciption3.setText("Diego Mazzieri (Levels)\n\n"
                        + "“The snail is a lie”");
	}
	
	public void pressanchorPane4 (ActionEvent event) {
		button4.setOpacity(0);
		button4.setDisable(true);
		labelDesciption4.setText("Ciao, mi chiamo Paolo Pasianot. Questo e` il mio primo progetto creato in Java8 per l'universita` di Bologna. Nel progetto principalmente ho ideato e realizzato il menu.");
	}
	
	public void reputbutton4(ActionEvent event) {
		button4.setOpacity(1);
		button4.setDisable(false);
	}
	
	public void reputbutton1 (ActionEvent event) {
		button1.setOpacity(1);
		button1.setDisable(false);
	}
	
	public void reputbutton2 (ActionEvent event) {
		button2.setOpacity(1);
		button2.setDisable(false);
	}
	
	public void reputbutton3 (ActionEvent event) {
		button3.setOpacity(1);
		button3.setDisable(false);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	
}
