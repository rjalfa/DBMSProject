package com.iiitd.dbms.medsh.util;

import com.iiitd.dbms.medsh.model.Employee;
import com.iiitd.dbms.medsh.record.EmployeeRecord;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GlobalVars {
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost/MedSH";
	public static final String DB_USER = "dbms_user";
	public static final String DB_PASS = "dbms_password";
	public static ObservableList<String> EmployeeTypes = FXCollections.observableArrayList("Doctor","Nurse","Staff","Accounts");
	public static Employee current_user = new Employee();
	public static EmployeeRecord empRecord = new EmployeeRecord();
}