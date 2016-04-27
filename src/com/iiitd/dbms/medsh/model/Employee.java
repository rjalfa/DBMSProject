package com.iiitd.dbms.medsh.model;

import java.util.Date;

import com.iiitd.dbms.medsh.record.EmployeeRecord;

public class Employee {
	private long uid;
	private String name;
	private Date dateOfBirth;
	private String gender;
	private String contact;
	private Date dateOfJoining;
	private String type;
	private double payroll;
	private String userName;
	private String password;
	private Boolean isAdmin;
	
	public class EscalateAccess
	{
		public String getPassword() {return password;}
		private EscalateAccess() {};
	}
	
	public void importAccessKey(EmployeeRecord E)
	{
		E.setKey(new EscalateAccess());
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public boolean comparePassword(String password) {
		return this.password.equals(password);
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public Date getDateOfJoining() {
		return dateOfJoining;
	}
	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
	public double getPayroll() {
		return payroll;
	}
	public void setPayroll(double payroll) {
		this.payroll = payroll;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
}
