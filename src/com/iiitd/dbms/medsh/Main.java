package com.iiitd.dbms.medsh;
import com.iiitd.dbms.medsh.model.Employee;
import com.iiitd.dbms.medsh.record.EmployeeRecord;
import com.iiitd.dbms.medsh.util.GlobalVars;
import com.iiitd.dbms.medsh.view.InterfaceController;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application {
	//Login Page FXML Controls
	EmployeeRecord emp = GlobalVars.empRecord;
	@FXML private Button loginButton;
	@FXML private TextField userID;
	@FXML private TextField userPassword;
	private Stage primaryStage;
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			this.primaryStage = primaryStage;
			loader.setLocation(Main.class.getResource("view/LoginPage.fxml"));
			BorderPane root = (BorderPane) loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadGUI(Stage primaryStage, String Interface) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/"+Interface+".fxml"));
			GridPane root = (GridPane) loader.load();
			Scene scene = new Scene(root);
			System.out.println(primaryStage);
			primaryStage.setScene(scene);
			InterfaceController controller = loader.getController();
			controller.setMainApp(this);
			controller.setCurrentUser();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void tryLogin()
	{
		try
		{
			primaryStage = (Stage) userID.getScene().getWindow();
			System.out.println();
			System.out.println("[SYSTEM] Login Attempt");
			String username = userID.getText();
			String password = userPassword.getText();
			emp.find(username,GlobalVars.current_user);
			Employee current_user = GlobalVars.current_user;
			if(current_user.comparePassword(password)) 
			{
				System.out.println("[SYSTEM] Current User Object: " + GlobalVars.current_user);
				if(current_user.getIsAdmin()) loadGUI(primaryStage,"AdminInterface");
				else loadGUI(primaryStage,"view/"+current_user.getType()+"Interface");
			}
			else throw new Exception("User unauthenticated");
		}
		catch(Exception e)
		{
			System.out.println("[SYSTEM] Login Attempt Failure");
			e.printStackTrace();
		}
	}
	
	public Stage getPrimaryStage(){
		return this.primaryStage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
