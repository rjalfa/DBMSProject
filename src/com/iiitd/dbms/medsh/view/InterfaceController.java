package com.iiitd.dbms.medsh.view;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.iiitd.dbms.medsh.Main;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public abstract class InterfaceController implements Initializable {
	
	protected Main mainApp;
	protected Stage primaryStage;
	
	public void setMainApp(Main mainApp)
	{
		this.mainApp = mainApp;
	}
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
}
