package com.iiitd.dbms.medsh.view;

import static com.iiitd.dbms.medsh.util.GlobalVars.EmployeeTypes;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import com.iiitd.dbms.medsh.model.Employee;
import com.iiitd.dbms.medsh.record.EmployeeRecord;
import com.iiitd.dbms.medsh.util.EmptySetException;
import com.iiitd.dbms.medsh.util.GlobalVars;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

public class AdminController extends InterfaceController{
	
	//Current User Details
	@FXML protected Employee currentUser;
	@FXML protected Text cname;
	@FXML protected Text cuid;
	@FXML protected Text ctype;
	@FXML protected Text cauth;
	@FXML protected Button adminButton;
	@FXML protected Button doctorButton;
	@FXML protected Button nurseButton;
	@FXML protected Button staffButton;
		
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
	
	//Listing Users Tab
	private ObservableList<Employee> userData = null;
	@FXML private TableView<Employee> userTable;
	@FXML private TableColumn<Employee,String> uidColumn;
	@FXML private TableColumn<Employee,String> userColumn;
	@FXML private TableColumn<Employee,String> dateColumn;
	
	private EmployeeRecord empData = GlobalVars.empRecord;
	private Employee updateEmployee = null;
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
		updateEmployee =  null;
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
				Employee e = new Employee();
				if(checkLong(uUID.getText().trim())) e = empData.find(Long.parseLong(uUID.getText().trim()),e);
				else e = empData.find(uUID.getText().trim(),e);
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
				updateEmployee = e;
			}
			else 
			{
				setInvalid(uUID);
				updateEmployee = null;
			}
		}
		catch(Exception e)
		{
			setInvalid(uUID);
			System.out.println("Empty Set. User not found in database");
		}
	}
	
	@FXML private void updateOldUser()
	{
		System.out.println("[INFO] Updating user");
		if(validateOldUser())
		{
			System.out.println("[INFO] Prechecks Passed");
			try
			{
				empData.update(updateEmployee);
			}
			catch(EmptySetException e)
			{
				System.out.println("Empty Set!!");
			}
		}
	}
	
	@FXML private void deleteOldUser()
	{
		if(validateOldUser())
		{
			try {
				empData.delete(updateEmployee.getUid());
			} catch (EmptySetException e) {
				System.out.println("User Not Found in Database");
			}
			updateEmployee = null;
		}
	}
	
	private boolean validateOldUser()
	{
		boolean flag = true;
		if(uName.getText().trim().length() == 0) {flag=false;setInvalid(uName);}
		else setValid(uName);
		if(uContact.getText().trim().length() == 0) {flag=false;setInvalid(uContact);}
		else setValid(uContact);
		if(uDOB.getValue() == null || (uDOB.getValue() != null && !uDOB.getValue().isBefore(LocalDate.now().minusYears(20)))) {flag=false;setInvalid(uDOB);}
		else setValid(uDOB);
		if(uGenderSelector.getSelectedToggle() == null) {flag=false;for(Toggle i:uGenderSelector.getToggles()) setInvalid((RadioButton)i);}
		else for(Toggle i:uGenderSelector.getToggles()) setValid((RadioButton)i);
		if(uDOJ.getValue() == null || (uDOJ.getValue() != null && !uDOJ.getValue().isAfter(LocalDate.now().minusMonths(6)))) {flag=false;setInvalid(uDOJ);}
		else setValid(uDOJ);
		if(uPayroll.getText().trim().length() == 0 || !checkDouble(uPayroll.getText()) || Double.parseDouble(uPayroll.getText()) < 1000) {flag=false;setInvalid(uPayroll);}
		else setValid(uPayroll);
		if(uType.getValue() == null) {flag=false;setInvalid(uType);}
		else setValid(uType);
		if(uUsername.getText().trim().length() == 0) {flag=false;setInvalid(uUsername);}
		else setValid(uUsername);
		if(uPassword.getText().length() == 0 || (uPassword.getText().length() > 0 && !updateEmployee.comparePassword(uPassword.getText()))) {flag=false;setInvalid(uPassword);}
		else setValid(uPassword);
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
	
	public void setCurrentUser()
	{
		System.out.println("[Interface Control] Current User Object: "+GlobalVars.current_user);
		Employee currentUser = GlobalVars.current_user;
		cname.setText(currentUser.getName());
		cuid.setText("UID: "+currentUser.getUid());
		ctype.setText("Type: "+currentUser.getType());
		switch(currentUser.getType())
		{
			case "Doctor":doctorButton.setDisable(false);break;
			case "Nurse":doctorButton.setDisable(false);break;
			case "Staff":doctorButton.setDisable(false);break;
		}
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		resetNForm();
		resetUForm();
		ArrayList<Employee> temp = new ArrayList<>();
		empData.all(temp);
		userData = FXCollections.observableArrayList(temp);
		uidColumn.setCellValueFactory(cellData -> createProperty(""+cellData.getValue().getUid()));
		userColumn.setCellValueFactory(cellData -> createProperty(""+cellData.getValue().getUserName()));
		dateColumn.setCellValueFactory(cellData -> createProperty(dateFormat(cellData.getValue().getDateOfJoining())));
		userTable.setItems(userData);
	}
}
