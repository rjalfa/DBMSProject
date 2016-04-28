package com.iiitd.dbms.medsh.view;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import com.iiitd.dbms.medsh.model.Task;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class TaskDialogController implements Initializable{

	@FXML private Text taskID;
	@FXML private Text taskTimestamp;
	@FXML private Text taskType;
	@FXML private Text taskDocID;
	@FXML private Text taskDocName;
	@FXML private Text taskPatName;
	@FXML private Text taskPatAge;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	private String calculateAge(Date d)
	{
		long ageInMillis = new Date().getTime() - d.getTime();
		return ""+ageInMillis/31556952000L;
	}
	
	public void displayTask(Task t)
	{
		taskID.setText(""+t.getTask_id());
		taskTimestamp.setText(""+t.getDatetime());
		taskType.setText(""+t.getTask_type());
		taskDocID.setText(""+t.getDoctorID());
		taskDocName.setText(""+t.getDoctorName());
		taskPatName.setText(""+t.getPatient().getName());
		taskPatAge.setText(""+calculateAge(t.getPatient().getDob()));
	}
}
