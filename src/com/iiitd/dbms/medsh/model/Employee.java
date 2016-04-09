package com.iiitd.dbms.medsh.model;

import java.util.Date;

public class Employee {
	private long uid;
	private String name;
	private Date dateOfBirth;
	private String gender;
	private String contact;
	private Date dateOfJoining;
	private double payroll;
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
	
}
