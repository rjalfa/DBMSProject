package com.iiitd.dbms.medsh.view;

import java.net.URL;
import java.util.ResourceBundle;

import com.iiitd.dbms.medsh.model.Employee;
import com.iiitd.dbms.medsh.model.Task;
import com.iiitd.dbms.medsh.util.GlobalVars;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class DoctorController extends InterfaceController {

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
	
	//Tabs
	//Appointments
	@FXML private ObservableList<Task> procData = FXCollections.observableArrayList();
	@FXML private TableView<Task> procTable;
	@FXML private TableColumn<Task,String> taskIDColumn;
	@FXML private TableColumn<Task,String> datetimeColumn;
	@FXML private TableColumn<Task,String> ttypeColumn;
	@FXML private TableColumn<Task,HBox> moreColumn;
	
	//Patients
	@FXML private ObservableList<Task> patientData = FXCollections.observableArrayList();
	@FXML private TextField patientID;
	@FXML private TableView<Task> patientTable;
	@FXML private TableColumn<Task,String> pTaskIDColumn;
	@FXML private TableColumn<Task,String> pDatetimeColumn;
	@FXML private TableColumn<Task,String> pTypeColumn;
	@FXML private TableColumn<Task,String> pDoctorColumn;
	
	@FXML private void updateTasks()
	{
		//get tasks from DB in taskData
		GlobalVars.taskRecord.getTasksForDoctor(procData,GlobalVars.current_user.getLD().getUid());
		procTable.setVisible(true);
		taskIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getTask_id()));
		datetimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getDatetime()));
		ttypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty((cellData.getValue().getTask_type())));
		moreColumn.setCellValueFactory(cellData -> buttonLink(cellData));
		procTable.setItems(procData);
	}
	
	@FXML public void searchPatientRecords()
	{
		//..Fetch Patient Records in patientData
		patientTable.setVisible(true);
		pTaskIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getTask_id()));
		pDatetimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getDatetime()));
		pTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty((cellData.getValue().getTask_type())));
		pDoctorColumn.setCellValueFactory(cellData -> new SimpleStringProperty((cellData.getValue().getDoctorName())));
		patientTable.setItems(patientData);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	@Override
	public void setCurrentUser() {
		System.out.println("[Interface Control] Current User Object: "+GlobalVars.current_user);
		Employee currentUser = GlobalVars.current_user;
		cname.setText(currentUser.getName());
		cuid.setText("UID: "+currentUser.getLD().getUid());
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
