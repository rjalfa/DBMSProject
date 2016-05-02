package com.iiitd.dbms.medsh.model;

import java.util.Date;

public class LogRecord {
	private int id;
	private long did;
	private Date date_in;
	private Date date_out;
	private String appt_type;
	private int payment;
	private boolean isPaid;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate_in() {
		return date_in;
	}
	public void setDate_in(Date date_in) {
		this.date_in = date_in;
	}
	public Date getDate_out() {
		return date_out;
	}
	public void setDate_out(Date date_out) {
		this.date_out = date_out;
	}
	public String getAppt_type() {
		return appt_type;
	}
	public void setAppt_type(String appt_type) {
		this.appt_type = appt_type;
	}
	public int getPayment() {
		return payment;
	}
	public void setPayment(int payment) {
		this.payment = payment;
	}
	public boolean isPaid() {
		return isPaid;
	}
	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	public long getDid() {
		return did;
	}
	public void setDid(long did) {
		this.did = did;
	}
}
