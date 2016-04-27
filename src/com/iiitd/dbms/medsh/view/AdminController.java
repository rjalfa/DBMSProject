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
	
	//Search, Modify and Delete
	@FXML private TextField uUID;
	@FXML private TextField uName;
	@FXML private DatePicker uDOB;
	@FXML private ToggleGroup uGenderSelector;
	@FXML private DatePicker uDOJ;
	@FXML private TextField uContact;
	@FXML private ChoiceBox<String> uType;
	@FXML private TextField uPayroll;
	@FXML private TextField uUsername;
	@FXML private TextField uPassword;
	@FXML private CheckBox uAdmin;
	@FXML private Button uUpdateUser;
	@FXML private Button uResetForm;
	@FXML private Button uDeleteUser;
	@FXML private RadioButton uGenderMale;
	@FXML private RadioButton uGenderFemale;
	
	private EmployeeRecord empData = new EmployeeRecord();
	
	@FXML private void resetNForm()
	{
		nName.setText("");
		setValid(nName);
		nDOB.setValue(null);
		setValid(nDOB);
		nDOJ.setValue(null);
		setValid(nDOJ);
		makeNumeric(nContact);
		nContact.setText("");
		setValid(nContact);
		nType.setItems(EmployeeTypes);
		setValid(nType);
		makeNumericF(nPayroll);
		setValid(nPayroll);
		nPayroll.setText("");
		nUsername.setText("");
		setValid(nUsername);
		nPassword.setText("");
		setValid(nPassword);
		nAdmin.setSelected(false);
	}
	
	@FXML private void resetUForm()
	{
		uName.setText("");
		setValid(uName);
		uDOB.setValue(null);
		setValid(uDOB);
		uDOJ.setValue(null);
		setValid(uDOJ);
		makeNumeric(uContact);
		uGenderMale.setSelected(false);
		uGenderFemale.setSelected(false);
		uContact.setText("");
		setValid(uContact);
		uType.setItems(EmployeeTypes);
		setValid(uType);
		makeNumericF(uPayroll);
		setValid(uPayroll);
		uPayroll.setText("");
		uUsername.setText("");
		setValid(uUsername);
		uPassword.setText("");
		setValid(uPassword);
		uAdmin.setSelected(false);
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
			e.setType(nType.getValue());
			e.setDateOfJoining(Date.from(nDOJ.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
			e.setPayroll(Double.parseDouble(nPayroll.getText()));
			e.setUserName(nUsername.getText().trim());
			e.setPassword(nPassword.getText());
			empData.create(e);
		}
	}
	
	@FXML private void searchOldUser()
	{
		try
		{
			if(uUID.getText().trim().length()>0)
			{
				setValid(uUID);
				Employee e = null;
				if(checkLong(uUID.getText().trim())) e = empData.find(Long.parseLong(uUID.getText().trim()));
				else e = empData.find(uUID.getText().trim());
				uName.setText(e.getName());
				uDOB.setValue(fromDate(e.getDateOfBirth()));
				if(e.getGender().equals("Male"))
				{
					uGenderMale.setSelected(true);
					uGenderFemale.setSelected(false);
				}
				else
				{
					uGenderMale.setSelected(false);
					uGenderFemale.setSelected(true);
				}
				uType.setValue(e.getType());
				uContact.setText(e.getContact());
				uDOJ.setValue(fromDate(e.getDateOfJoining()));
				uPayroll.setText(e.getPayroll()+"");
				uUsername.setText(e.getUserName());
			}
			else setInvalid(uUID);
		}
		catch(Exception e)
		{
			setInvalid(uUID);
			e.printStackTrace();
		}
	}
	
	private boolean validateOldUser()
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
		resetUForm();
	}
}
