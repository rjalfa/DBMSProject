package com.iiitd.dbms.medsh.view;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.iiitd.dbms.medsh.Main;
import com.iiitd.dbms.medsh.model.Task;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public abstract class InterfaceController implements Initializable {
	
	protected Main mainApp;
	protected Stage primaryStage;
	//FXML Controls	
	protected final DateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy"); 
	
	public void setMainApp(Main mainApp)
	{
		this.mainApp = mainApp;
	}
	
	public abstract void setCurrentUser();
	//Numeric Text Fields Constraint Event
	protected void makeNumeric(TextField text)
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
	protected void makeNumericF(TextField text)
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
	
	@FXML private void switchContext(ActionEvent evt)
	{
		Button eSource = (Button)evt.getSource();
		primaryStage = (Stage) eSource.getScene().getWindow();
		switch(eSource.getId())
		{
			case "adminButton":mainApp.loadGUI(primaryStage, "AdminInterface");break;
			case "doctorButton":mainApp.loadGUI(primaryStage, "DoctorInterface");break;
			case "nurseButton":mainApp.loadGUI(primaryStage, "NurseInterface");break;
			case "staffButton":mainApp.loadGUI(primaryStage, "StaffInterface");break;
			case "accountsButton":mainApp.loadGUI(primaryStage, "AccountsInterface");break;
		}
	}
	
	//Superclass Valid/Invalid CSS Apply method
	protected void setValid(Control control) {control.setStyle("-fx-border-color: none;");}
	protected void setInvalid(Control control) {control.setStyle("-fx-border-color: red;");}
	protected boolean checkDouble(String s)
	{
		if(s.matches("[\\x00-\\x20]*[+-]?(((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*"))
		{
			return (Double.parseDouble(s) >= 0);
		}
		return false;
	}
	protected boolean checkLong(String s)
	{
		if(s.matches("^[0-9]*$"))
		{
			return (Long.parseLong(s) >= 0);
		}
		return false;
	}
	
	public static LocalDate fromDate(Date date) {
	    Instant instant = Instant.ofEpochMilli(date.getTime());
	    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
	        .toLocalDate();
	}
	
	public StringProperty createProperty(String s)
	{
		return new SimpleStringProperty(s);
	}
	
	public String dateFormat(Date d)
	{
		return df.format(d);
	}
	
	protected void taskExpandHandler(long tid)
	{
		try {
			mainApp.showTaskExpanded(tid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected SimpleObjectProperty<HBox> buttonLink(CellDataFeatures<Task, HBox> cellData)
	{
		Button btn = new Button("Expanded Task");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			 @Override
			 public void handle(ActionEvent e) {
				 taskExpandHandler(cellData.getValue().getTask_id());
		}});
		HBox t = new HBox();
		t.getChildren().add(btn);
		return new SimpleObjectProperty<HBox>(t);
	}
	
	@FXML protected void destroySession()
	{
		System.out.println("[Interface Control] Destroy session request");
		mainApp.logOutUser();
	}
}
