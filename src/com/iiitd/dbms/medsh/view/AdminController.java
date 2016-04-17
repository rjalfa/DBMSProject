package com.iiitd.dbms.medsh.view;

import java.net.URL;
import java.util.ResourceBundle;

import com.iiitd.dbms.medsh.Main;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class AdminController implements Initializable{
	
	private Main mainApp;
	private Stage primaryStage;
	
	//FXML Controls
	//New User Tab
	@FXML private TextField nName;
	@FXML private DatePicker nDOB;
	@FXML private ToggleGroup nGenderSelector;
	@FXML private DatePicker nDOJ;
	@FXML private TextField nContact;
	@FXML private ChoiceBox<String> nType;
	@FXML private TextField nPayroll;
	@FXML private TextField nUsername;
	@FXML private TextField nPassword;
	@FXML private Button nCreateUser;
	@FXML private Button nResetForm;
	
	
	public void setMainApp(Main mainApp)
	{
		this.mainApp = mainApp;
	}
	

	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	
	//Numeric Text Fields Constraint Event
	private void makeNumeric(TextField text)
	{
		text.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>()
		{
			public void handle( KeyEvent t )
			{
				char ar[] = t.getCharacter().toCharArray();
				char ch = ar[t.getCharacter().toCharArray().length - 1];
				if (!(ch >= '0' && ch <= '9')) t.consume();
			}
		});
	}
	
	//Numeric Text Fields (Floating Point Values) Constraint Event
	private void makeNumericF(TextField text)
	{
		text.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>()
		{
			public void handle( KeyEvent t )
			{
				char ar[] = t.getCharacter().toCharArray();
				char ch = ar[t.getCharacter().toCharArray().length - 1];
				if (!(ch >= '0' && ch <= '9' || ch == '.')) t.consume();
			}
		});
	}
	
}
