package com.iiitd.dbms.medsh.view;

import static com.iiitd.dbms.medsh.util.GlobalVars.EmployeeTypes;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import com.iiitd.dbms.medsh.model.Employee;
import com.iiitd.dbms.medsh.record.EmployeeRecord;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
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
	
	private EmployeeRecord empData = new EmployeeRecord();
	
	@FXML private void resetNForm()
	{
		nName.setText("");
		nDOB.setValue(null);
		nDOJ.setValue(null);
		makeNumeric(nContact);
		nContact.setText("");
		nType.setItems(EmployeeTypes);
		makeNumericF(nPayroll);
		nPayroll.setText("");
		nUsername.setText("");
		nPassword.setText("");
		nAdmin.setSelected(false);
	}
	
	@FXML private void createNewUser()
	{
		if(validateNewUser())
		{
			Employee e = new Employee();
			e.setName(nName.getText().trim());
			e.setDateOfBirth(Date.from(nDOB.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
			e.setGender(((RadioButton)(nGenderSelector.getSelectedToggle())).getText());
			e.setContact(nContact.getText().trim());
			e.setDateOfJoining(Date.from(nDOJ.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
			e.setPayroll(Double.parseDouble(nPayroll.getText()));
			e.setUserName(nUsername.getText().trim());
			e.setPassword(nPassword.getText());
			empData.create(e);
		}
	}
	
	private boolean validateNewUser()
	{
		boolean flag = true;
		if(nName.getText().trim().length() == 0) {flag=false;setInvalid(nName);}
		else setValid(nName);
		if(nContact.getText().trim().length() == 0) {flag=false;setInvalid(nContact);}
		else setValid(nContact);
		if(nDOB.getValue() == null || (nDOB.getValue() != null && !nDOB.getValue().isBefore(LocalDate.now().minusYears(20)))) {flag=false;setInvalid(nDOB);}
		else setValid(nDOB);
		if(nGenderSelector.getSelectedToggle() == null) {flag=false;for(Toggle i:nGenderSelector.getToggles()) setInvalid((RadioButton)i);}
		else for(Toggle i:nGenderSelector.getToggles()) setValid((RadioButton)i);
		if(nDOJ.getValue() == null || (nDOJ.getValue() != null && !nDOJ.getValue().isAfter(LocalDate.now().minusMonths(6)))) {flag=false;setInvalid(nDOJ);}
		else setValid(nDOJ);
		if(nPayroll.getText().trim().length() == 0 || !checkDouble(nPayroll.getText()) || Double.parseDouble(nPayroll.getText()) < 1000) {flag=false;setInvalid(nPayroll);}
		else setValid(nPayroll);
		if(nType.getValue() == null) {flag=false;setInvalid(nType);}
		else setValid(nType);
		if(nUsername.getText().trim().length() == 0) {flag=false;setInvalid(nUsername);}
		else setValid(nUsername);
		if(nPassword.getText().length() == 0) {flag=false;setInvalid(nPassword);}
		else setValid(nPassword);
		return flag;
	}

	public void initialize(URL location, ResourceBundle resources) {
		resetNForm();
	}
}
