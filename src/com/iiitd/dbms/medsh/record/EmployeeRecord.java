package com.iiitd.dbms.medsh.record;
import java.sql.SQLException;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

import com.iiitd.dbms.medsh.model.Employee;

public class EmployeeRecord {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/DBMS_test";
	static final String DB_USER = "root";
	static final String DB_PASS = "rounaq";
	private Employee.EscalateAccess accessPassKey;
	private JdbcRowSet rowSet = null;
	
	public void setKey(Employee.EscalateAccess key)
	{
		this.accessPassKey = key;
	}
	
	public EmployeeRecord()
	{
		try
		{
			Class.forName(JDBC_DRIVER);
			RowSetFactory factory = RowSetProvider.newFactory(); 
			rowSet = factory.createJdbcRowSet();
			rowSet.setUrl(DB_URL);
			rowSet.setUsername(DB_USER);
			rowSet.setPassword(DB_PASS);
			rowSet.setCommand("SELECT * FROM Employee");
			rowSet.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public Employee create(Employee e)
	{
		try
		{
			rowSet.moveToInsertRow();
			rowSet.updateLong("uid", e.getUid());
			rowSet.updateString("name", e.getName());
			rowSet.updateDate("dob", new java.sql.Date(e.getDateOfBirth().getTime()));
			rowSet.updateString("gender", e.getGender());
			rowSet.updateString("contact", e.getContact());
			rowSet.updateDate("doj", new java.sql.Date(e.getDateOfJoining().getTime()));
			rowSet.updateDouble("payroll", e.getPayroll());
			rowSet.updateString("username", e.getUserName());
			e.importAccessKey(this);
			rowSet.updateString("password",accessPassKey.getPassword());
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return e;
	}
	
	public Employee update(Employee e)
	{
		try
		{
			rowSet.updateLong("uid", e.getUid());
			rowSet.updateString("name", e.getName());
			rowSet.updateDate("dob", new java.sql.Date(e.getDateOfBirth().getTime()));
			rowSet.updateString("gender", e.getGender());
			rowSet.updateString("contact", e.getContact());
			rowSet.updateDate("doj", new java.sql.Date(e.getDateOfJoining().getTime()));
			rowSet.updateDouble("payroll", e.getPayroll());
			rowSet.updateString("username", e.getUserName());
			e.importAccessKey(this);
			rowSet.updateString("password",accessPassKey.getPassword());
			rowSet.updateRow();
			rowSet.moveToCurrentRow();
		}
		catch(SQLException ex)
		{
			try
			{
				rowSet.rollback();
			}
			catch(SQLException ec) 
			{
				ec.printStackTrace();
			}
			ex.printStackTrace();
		}
		return e;
	}
	
	public void delete()
	{
		try
		{
			rowSet.moveToCurrentRow();
			rowSet.deleteRow();
		}
		catch(SQLException ex)
		{
			try
			{
				rowSet.rollback();
			}
			catch(SQLException ec) 
			{
				ec.printStackTrace();
			}
			ex.printStackTrace();
		}
	}
	
	public Employee first()
	{
		Employee e = new Employee();
		try
		{
			rowSet.first();
			populateData(e);
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return e;
	}
	
	public Employee last()
	{
		Employee e = new Employee();
		try
		{
			rowSet.last();
			populateData(e);
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return e;
	}
	
	public Employee find(long uid)
	{
		Employee e = new Employee();
		try
		{
			rowSet.last();
			populateData(e);
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return e;
	}
	
	private Employee populateData(Employee e)
	{
		try
		{
			e.setUid(rowSet.getLong("uid"));
			e.setName(rowSet.getString("name"));
			e.setDateOfBirth(rowSet.getDate("dob"));
			e.setGender(rowSet.getString("gender"));
			e.setContact(rowSet.getString("contact"));
			e.setDateOfJoining(rowSet.getDate("doj"));
			e.setPayroll(rowSet.getDouble("payroll"));
			e.setUserName(rowSet.getString("username"));
			e.setPassword(rowSet.getString("password"));
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return e;
	}
	
	
}
