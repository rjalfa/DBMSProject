package com.iiitd.dbms.medsh.view;

import java.net.URL;
import java.util.ResourceBundle;

import com.iiitd.dbms.medsh.model.Employee;
import com.iiitd.dbms.medsh.util.GlobalVars;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
	@FXML protected Button logOuButton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
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
