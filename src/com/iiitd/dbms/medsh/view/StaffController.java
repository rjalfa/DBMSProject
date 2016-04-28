package com.iiitd.dbms.medsh.view;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import com.iiitd.dbms.medsh.model.Employee;
import com.iiitd.dbms.medsh.model.Patient;
import com.iiitd.dbms.medsh.model.Room;
import com.iiitd.dbms.medsh.model.Task;
import com.iiitd.dbms.medsh.util.GlobalVars;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class StaffController extends InterfaceController {

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
	@FXML protected Button accountsButton;
	@FXML protected Button logOutButton;
	
	//Create Task
	@FXML private TextField ctaskType;
	@FXML private DatePicker ctaskDate;
	@FXML private TextField ctaskTimeH;
	@FXML private TextField ctaskTimeM;
	@FXML private TextField cPatientName;
	@FXML private DatePicker cPatientDOB;
	@FXML private TextField cDoctorID;
	
	//Rooms
	@FXML private TextField roomTaskID;
	@FXML private TextField roomRID;
	@FXML private ObservableList<Room> roomData = FXCollections.observableArrayList();
	@FXML private TableView<Room> roomTable;
	@FXML private TableColumn<Room,String> roomIdColumn;
	@FXML private TableColumn<Room,String> roomNoColumn;
	@FXML private TableColumn<Room,String> roomFloorColumn;
	@FXML private TableColumn<Room,String> roomBuildingColumn;
	@FXML private TableColumn<Room,String> roomTypeColumn;
	@FXML private TableColumn<Room,String> roomTaskColumn;
	
	@FXML private void loadRooms()
	{
		//fetch rooms in roomData
		roomTable.setVisible(true);
		roomIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getRoomID()));
		roomNoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getRoom_no()));
		roomFloorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getFloor()));
		roomBuildingColumn.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getBuilding()));
		roomTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getRoom_type()));
		roomTaskColumn.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getTask_id()));		
		roomTable.setItems(roomData);
	}
	
	@FXML private void assignRoomToTask()
	{
		if(validateRoomAssignment())
		{
			//assign room to task
		}
	}
 	
	@FXML private void createNewTask()
	{
		if(validateNewTask())
		{
			Task task = new Task();
			Patient p = new Patient();
			p.setName(cPatientName.getText());
			p.setDob((Date.from(cPatientDOB.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())));
			task.setTask_type(ctaskType.getText());
			task.setDoctorID(Long.parseLong(cDoctorID.getText()));
			task.setPatient(p);
			task.setDatetime(Date.from(ctaskDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant().plusSeconds(Integer.parseInt(ctaskTimeH.getText())*3600+Integer.parseInt(ctaskTimeM.getText())*60)));
			//commit task to db
		}
	}
	
	//Nurse Task
	@FXML private TextField nTaskID;
	@FXML private TextField nNurseID;
	
	@FXML private void createNurseTask()
	{
		if(validateNurseTask())
		{
			//.. Commit Nurse Task
			loadRooms();
		}
	}
	
	private boolean validateRoomAssignment()
	{
		boolean flag = true;
		if(roomTaskID.getText().trim().length() == 0) {flag=false;setInvalid(roomTaskID);}
		else setValid(roomTaskID);
		if(roomRID.getText().trim().length() == 0) {flag=false;setInvalid(roomRID);}
		else setValid(roomRID);
		return flag;
	}
	
	private boolean validateNurseTask()
	{
		boolean flag = true;
		if(nTaskID.getText().trim().length() == 0) {flag=false;setInvalid(nTaskID);}
		else setValid(nTaskID);
		if(nNurseID.getText().trim().length() == 0) {flag=false;setInvalid(nNurseID);}
		else setValid(nNurseID);
		return flag;
	}
	
	private boolean validateNewTask()
	{
		boolean flag = true;
		if(ctaskType.getText().trim().length() == 0) {flag=false;setInvalid(ctaskType);}
		else setValid(ctaskType);
		if(ctaskDate.getValue() == null || (ctaskDate.getValue() != null && !ctaskDate.getValue().isAfter(LocalDate.now().minusDays(1)))) {flag=false;setInvalid(ctaskDate);}
		else setValid(ctaskDate);
		if(cPatientDOB.getValue() == null || (cPatientDOB.getValue() != null && !cPatientDOB.getValue().isBefore(LocalDate.now().plusDays(1)))) {flag=false;setInvalid(cPatientDOB);}
		else setValid(cPatientDOB);
		if(ctaskTimeH.getText().trim().length() == 0 || !checkDouble(ctaskTimeH.getText()) || Double.parseDouble(ctaskTimeH.getText()) > 23) {flag=false;setInvalid(ctaskTimeH);}
		else setValid(ctaskTimeH);
		if(ctaskTimeM.getText().trim().length() == 0 || !checkDouble(ctaskTimeM.getText()) || Double.parseDouble(ctaskTimeM.getText()) > 59) {flag=false;setInvalid(ctaskTimeM);}
		else setValid(ctaskTimeM);
		if(cDoctorID.getText().trim().length() == 0) {flag=false;setInvalid(cDoctorID);}
		else setValid(cDoctorID);
		if(cPatientName.getText().trim().length() == 0) {flag=false;setInvalid(cPatientName);}
		else setValid(cPatientName);
		return flag;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ctaskType.setText("");
		setValid(ctaskType);
		ctaskDate.setValue(null);
		setValid(ctaskDate);
		cPatientDOB.setValue(null);
		setValid(cPatientDOB);
		makeNumeric(ctaskTimeH);
		makeNumeric(ctaskTimeM);
		ctaskTimeH.setText("");
		ctaskTimeM.setText("");
		setValid(ctaskTimeH);
		setValid(ctaskTimeM);
		cPatientName.setText("");
		setValid(cPatientName);
		makeNumeric(cDoctorID);
		cDoctorID.setText("");
		setValid(cDoctorID);
		
		makeNumeric(nNurseID);
		nNurseID.setText("");
		setValid(nNurseID);
		makeNumeric(nTaskID);
		nTaskID.setText("");
		setValid(nTaskID);
		
		makeNumeric(roomTaskID);
		roomTaskID.setText("");
		setValid(roomTaskID);
		makeNumeric(roomRID);
		roomRID.setText("");
		setValid(roomRID);
	}
	
	@Override
	public void setCurrentUser() {
		System.out.println("[Interface Control] Current User Object: "+GlobalVars.current_user);
		Employee currentUser = GlobalVars.current_user;
		cname.setText(currentUser.getName());
		cuid.setText("UID: "+currentUser.getUid());
		ctype.setText("Type: "+currentUser.getType());
		if(currentUser.getIsAdmin()) 
		{
			cauth.setText("Authority: Admin");
			adminButton.setDisable(false);
		}
		else
		{
			cauth.setText("Authority: Limited");
			adminButton.setDisable(true);
		}
	}
}
