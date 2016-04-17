package com.iiitd.dbms.medsh;
import com.iiitd.dbms.medsh.view.InterfaceController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application {
	
	private Stage primaryStage;
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/LoginPage.fxml"));
			BorderPane root = (BorderPane) loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			loadGUI(primaryStage,"AdminInterface");
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
			primaryStage.setScene(scene);
			InterfaceController controller = loader.getController();
			controller.setMainApp(this);
			
			primaryStage.show();
		} catch(Exception e) {
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
