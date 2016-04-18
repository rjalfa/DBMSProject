package com.iiitd.dbms.medsh.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GlobalVars {
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost/MedSH";
	public static final String DB_USER = "dbms_user";
	public static final String DB_PASS = "dbms_password";
	public static ObservableList<String> EmployeeTypes = FXCollections.observableArrayList("Doctor","Nurse","Staff");
}