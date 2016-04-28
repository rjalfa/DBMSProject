package com.iiitd.dbms.medsh.record;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

import com.iiitd.dbms.medsh.model.Employee;
import com.iiitd.dbms.medsh.util.EmptySetException;
import com.iiitd.dbms.medsh.util.GlobalVars;

public class EmployeeRecord {
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
			Class.forName(GlobalVars.JDBC_DRIVER);
			RowSetFactory factory = RowSetProvider.newFactory(); 
			rowSet = factory.createJdbcRowSet();
			rowSet.setUrl(GlobalVars.DB_URL);
			rowSet.setUsername(GlobalVars.DB_USER);
			rowSet.setPassword(GlobalVars.DB_PASS);
			
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
			rowSet.setCommand("SELECT * FROM Employee WHERE uid="+e.getUid());
			rowSet.execute();
			if(!rowSet.next()) throw new EmptySetException();
			else {rowSet.beforeFirst();rowSet.next();}
			rowSet.updateString("name", e.getName());
			rowSet.updateDate("dob", new java.sql.Date(e.getDateOfBirth().getTime()));
			rowSet.updateString("gender", e.getGender());
			rowSet.updateString("type", e.getType());
			rowSet.updateString("contact", e.getContact());
			rowSet.updateDate("doj", new java.sql.Date(e.getDateOfJoining().getTime()));
			rowSet.updateDouble("payroll", e.getPayroll());
			rowSet.updateString("username", e.getUserName());
			rowSet.updateBoolean("isAdmin",e.getIsAdmin());
			e.importAccessKey(this);
			rowSet.updateString("password",accessPassKey.getPassword());
			System.out.println("[RECORD:INFO] SET UPDATED USER");
			rowSet.updateRow();
			System.out.println("[RECORD:INFO] COMMIT UPDATED USER");
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
			rowSet.setCommand("SELECT * FROM Employee");
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
	
	public void delete(long uid) throws EmptySetException
	{
		try
		{
			rowSet.setCommand("SELECT * FROM Employee WHERE uid="+uid);
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
			rowSet.setCommand("SELECT * FROM Employee");
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
			rowSet.setCommand("SELECT * FROM Employee");
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
			rowSet.setCommand("SELECT * FROM Employee WHERE uid="+uid);
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
			rowSet.setCommand("SELECT * FROM Employee WHERE username=\""+username+"\"");
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
			e.setUid(rowSet.getLong("uid"));
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
