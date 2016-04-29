package com.iiitd.dbms.medsh.record;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

import com.iiitd.dbms.medsh.model.Employee;
import com.iiitd.dbms.medsh.model.LoginDetails;
import com.iiitd.dbms.medsh.util.EmptySetException;
import com.iiitd.dbms.medsh.util.GlobalVars;

public class EmployeeRecord {
	private Employee.EscalateAccess accessPassKey;
	private JdbcRowSet rowSet = null;
	private Statement updates = null;
	public void setKey(Employee.EscalateAccess key)
	{
		this.accessPassKey = key;
	}
	
	public EmployeeRecord()
	{
		try
		{
			Class.forName(GlobalVars.JDBC_DRIVER);
			RowSetFactory factory = RowSetProvider.newFactory(); 
			rowSet = factory.createJdbcRowSet();
			rowSet.setUrl(GlobalVars.DB_URL);
			rowSet.setUsername(GlobalVars.DB_USER);
			rowSet.setPassword(GlobalVars.DB_PASS);
			Connection connection = DriverManager.getConnection(GlobalVars.DB_URL, GlobalVars.DB_USER, GlobalVars.DB_PASS);
	        updates = connection.createStatement();
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
			rowSet.setCommand("SELECT * FROM Employee");
			rowSet.execute();
			rowSet.moveToInsertRow();
			rowSet.updateString("name", e.getName());
			rowSet.updateDate("dob", new java.sql.Date(e.getDateOfBirth().getTime()));
			rowSet.updateString("gender", e.getGender());
			rowSet.updateString("type", e.getType());
			rowSet.updateString("contact", e.getContact());
			rowSet.updateDate("doj", new java.sql.Date(e.getDateOfJoining().getTime()));
			rowSet.updateDouble("payroll", e.getPayroll());
			rowSet.updateString("username", e.getUserName());
			rowSet.updateBoolean("isAdmin", e.getIsAdmin());
			e.importAccessKey(this);
			rowSet.updateString("password",accessPassKey.getPassword());
			rowSet.insertRow();
			rowSet.setCommand("SELECT * FROM LoginDetails");
			rowSet.execute();
			rowSet.moveToInsertRow();
			rowSet.updateString("username", e.getUserName());
			rowSet.insertRow();
			rowSet.setCommand("SELECT * FROM LoginDetails where username='"+e.getUserName()+"'");
			rowSet.execute();
			rowSet.next();
			LoginDetails LD = new LoginDetails();
			LD.setUid(rowSet.getLong("uid"));
			LD.setUserName(rowSet.getString("username"));
			e.setLD(LD);
			System.out.println("[SYSTEM] Committed user "+e.getLD().getUid());
			switch(e.getType())
			{
				case "Doctor":updates.executeUpdate("INSERT INTO Doctor VALUES ("+e.getLD().getUid()+",1)");break;
				case "Nurse":updates.executeUpdate("INSERT INTO Nurse VALUES ("+e.getLD().getUid()+")");break;
				case "Staff":updates.executeUpdate("INSERT INTO Staff VALUES ("+e.getLD().getUid()+",'Normal Staff')");break;
				case "Accounts":updates.executeUpdate("INSERT INTO Staff VALUES ("+e.getLD().getUid()+",'Accountant Staff')");break;
			}
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return e;
	}
	
	public Employee update(Employee e) throws EmptySetException
	{
		try
		{
			rowSet.setCommand("SELECT * FROM Employee WHERE username=(SELECT username FROM Employee NATURAL JOIN LoginDetails where LoginDetails.uid="+e.getLD().getUid()+")");
			rowSet.execute();
			if(!rowSet.next()) throw new EmptySetException();
			else {rowSet.beforeFirst();rowSet.next();}
			System.out.println("[QUERY] UPDATE Employee SET name='"+e.getName()+"',dob='"+e.getDateOfBirth()+"',gender='"+e.getGender()+"',type='"+e.getType()+"',contact='"+e.getContact()+"',doj='"+e.getDateOfJoining()+"',payroll='"+e.getPayroll()+"',isAdmin="+GlobalVars.boolToString(e.getIsAdmin())+" WHERE username='"+e.getUserName()+"'");
			updates.executeUpdate("UPDATE Employee SET name='"+e.getName()+"',dob='"+e.getDateOfBirth()+"',gender='"+e.getGender()+"',type='"+e.getType()+"',contact='"+e.getContact()+"',doj='"+e.getDateOfJoining()+"',payroll='"+e.getPayroll()+"',isAdmin="+GlobalVars.boolToString(e.getIsAdmin())+" WHERE username='"+e.getUserName()+"'");
			
		}
		catch(EmptySetException ex)
		{
			throw ex;
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
	
	public ArrayList<Employee> all(ArrayList<Employee> arr)
	{
		try
		{
			rowSet.setCommand("SELECT * FROM Employee NATURAL JOIN LoginDetails");
			rowSet.execute();
			while(rowSet.next())
			{
				Employee e = new Employee();
				populateData(e);
				arr.add(e);
			}
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return arr;
	}
	
	public void delete(String username) throws EmptySetException
	{
		try
		{
			rowSet.setCommand("SELECT * FROM Employee WHERE username=\""+username+"\"");
			rowSet.execute();
			if(!rowSet.next()) if(!rowSet.next()) throw new EmptySetException();
			else {rowSet.beforeFirst();rowSet.next();}
			rowSet.deleteRow();
		}
		catch(EmptySetException ex)
		{
			throw ex;
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
	
	public Employee first(Employee e) throws EmptySetException
	{
		try
		{
			rowSet.setCommand("SELECT * FROM Employee NATURAL JOIN LoginDetails");
			rowSet.execute();
			if(!rowSet.next()) throw new EmptySetException();
			else {rowSet.beforeFirst();rowSet.next();}
			rowSet.first();
			populateData(e);
		}
		catch(EmptySetException ex)
		{
			throw ex;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return e;
	}
	
	public Employee last(Employee e) throws EmptySetException
	{
		try
		{
			rowSet.setCommand("SELECT * FROM Employee NATURAL JOIN LoginDetails");
			rowSet.execute();
			if(!rowSet.next()) throw new EmptySetException();
			else {rowSet.beforeFirst();rowSet.next();}
			rowSet.last();
			populateData(e);
		}
		catch(EmptySetException ex)
		{
			throw ex;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return e;
	}
	
	public Employee find(long uid,Employee e) throws EmptySetException
	{
		try
		{
			rowSet.setCommand("SELECT * FROM Employee NATURAL JOIN LoginDetails WHERE uid=(SELECT uid FROM Employee NATURAL JOIN LoginDetails WHERE LoginDetails.uid="+uid+")");
			rowSet.execute();
			if(!rowSet.next()) throw new EmptySetException();
			else {rowSet.beforeFirst();rowSet.next();}
			rowSet.first();
			populateData(e);
		}
		catch(EmptySetException ex)
		{
			throw ex;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return e;
	}
	
	public Employee find(String username, Employee e) throws EmptySetException
	{
		try
		{
			rowSet.setCommand("SELECT * FROM Employee NATURAL JOIN LoginDetails WHERE username=\""+username+"\"");
			rowSet.execute();
			if(!rowSet.next()) throw new EmptySetException();
			else {rowSet.beforeFirst();rowSet.next();}
			rowSet.first();
			populateData(e);
		}
		catch(EmptySetException ex)
		{
			throw ex;
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
			LoginDetails ld = new LoginDetails();
			ld.setUid(rowSet.getLong("uid"));
			ld.setUserName(rowSet.getString("username"));
			e.setLD(ld);
			e.setName(rowSet.getString("name"));
			e.setDateOfBirth(rowSet.getDate("dob"));
			e.setGender(rowSet.getString("gender"));
			e.setContact(rowSet.getString("contact"));
			e.setDateOfJoining(rowSet.getDate("doj"));
			e.setType(rowSet.getString("type"));
			e.setIsAdmin(rowSet.getBoolean("isAdmin"));
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
