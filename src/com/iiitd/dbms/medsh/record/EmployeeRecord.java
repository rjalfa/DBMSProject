package com.iiitd.dbms.medsh.record;
import java.sql.SQLException;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

import com.iiitd.dbms.medsh.model.Employee;

public class EmployeeRecord {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/<<database_name>>";
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
			rowSet.updateLong("", e.getUid());
			rowSet.updateString("", e.getName());
			rowSet.updateDate("", new java.sql.Date(e.getDateOfBirth().getTime()));
			rowSet.updateString("", e.getGender());
			rowSet.updateString("", e.getContact());
			rowSet.updateDate("", new java.sql.Date(e.getDateOfJoining().getTime()));
			rowSet.updateDouble("", e.getPayroll());
			rowSet.updateString("", e.getUserName());
			e.importAccessKey(this);
			rowSet.updateString("",accessPassKey.getPassword());
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
			rowSet.updateLong("", e.getUid());
			rowSet.updateString("", e.getName());
			rowSet.updateDate("", new java.sql.Date(e.getDateOfBirth().getTime()));
			rowSet.updateString("", e.getGender());
			rowSet.updateString("", e.getContact());
			rowSet.updateDate("", new java.sql.Date(e.getDateOfJoining().getTime()));
			rowSet.updateDouble("", e.getPayroll());
			rowSet.updateString("", e.getUserName());
			e.importAccessKey(this);
			rowSet.updateString("",accessPassKey.getPassword());
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
}
