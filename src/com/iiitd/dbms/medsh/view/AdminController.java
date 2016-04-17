package com.iiitd.dbms.medsh.view;

import static com.iiitd.dbms.medsh.util.GlobalVars.EmployeeTypes;

import java.net.URL;
import java.util.ResourceBundle;

import com.iiitd.dbms.medsh.Main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class AdminController extends InterfaceController{
	
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
	@FXML private CheckBox nAdmin;
	@FXML private Button nCreateUser;
	@FXML private Button nResetForm;
	
	@FXML private void resetNForm()
	{
		nName.setText("");
		nDOB.setValue(null);
		nDOJ.setValue(null);
		makeNumeric(nContact);
		nContact.setText("");
		nType.setItems(EmployeeTypes);
		nPayroll.setText("");
		nUsername.setText("");
		nPassword.setText("");
		nAdmin.setSelected(false);
	}
	
	public void setMainApp(Main mainApp)
	{
		this.mainApp = mainApp;
	}
	

	public void initialize(URL location, ResourceBundle resources) {
		resetNForm();
	}
}
