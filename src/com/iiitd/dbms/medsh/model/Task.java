package com.iiitd.dbms.medsh.model;

import java.util.Date;

public class Task {
	private Date datetime;
	private String task_type;
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	public String getTask_type() {
		return task_type;
	}
	public void setTask_type(String task_type) {
		this.task_type = task_type;
	}
}
