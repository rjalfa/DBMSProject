package com.iiitd.dbms.medsh.record;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

import com.iiitd.dbms.medsh.model.Patient;
import com.iiitd.dbms.medsh.model.Task;
import com.iiitd.dbms.medsh.util.EmptySetException;
import com.iiitd.dbms.medsh.util.GlobalVars;

import javafx.collections.ObservableList;

public class TaskRecord {
	private JdbcRowSet rowSet = null;
	private Statement updates = null;
	
	public TaskRecord()
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
	
	public Task create(Task t)
	{
		try
		{
			rowSet.setCommand("SELECT * FROM Schedule where doctor_id="+t.getDoctorID());
			rowSet.execute();
			if(!rowSet.next())
				{
					updates.executeUpdate("INSERT INTO Schedule VALUES (NULL,'"+t.getDoctorID()+"')" );
					rowSet.execute();
				}
			rowSet.execute();
			rowSet.next();
			long temp = rowSet.getLong("s_id");
			
			//rowSet.setCommand("SELECT * FROM Patient");
			//rowSet.execute();
			//if(!rowSet.next())
			//{
				updates.executeUpdate("INSERT INTO Patient VALUES (NULL,'"+(new java.sql.Date(t.getPatient().getDob().getTime()))+"','"+t.getPatient().getName()+"')" );
			//}
			rowSet.setCommand("SELECT * FROM Patient where name='"+t.getPatient().getName()+"'");
			rowSet.execute();
			rowSet.next();
			long temp2 = rowSet.getLong("pid");
			
			rowSet.setCommand("SELECT * FROM Task");
			rowSet.execute();
			rowSet.moveToInsertRow();
			rowSet.updateLong("pid", temp2);
			rowSet.updateDate("date_time", new java.sql.Date(t.getDatetime().getTime()));
			rowSet.updateString("task_type", t.getTask_type());
			rowSet.updateLong("s_id", temp);
			
			rowSet.insertRow();
			t.setTask_id(rowSet.getInt("task_id"));
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return t;
	}
	
	public void getTasksForDoctor(ObservableList<Task> t,long uid)
	{
		try {
			rowSet.setCommand("SELECT * FROM Task NATURAL JOIN Patient NATURAL JOIN Schedule NATURAL JOIN Doctor WHERE uid="+uid);
			while(rowSet.next())
			{
				Task tr = new Task();
				populateData(tr);
				t.add(tr);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Task update(Task t) throws EmptySetException
	{
		try
		{
			rowSet.setCommand("SELECT * FROM Task WHERE task_id="+t.getTask_id());
			rowSet.execute();
			if(!rowSet.next()) throw new EmptySetException();
			else {rowSet.beforeFirst();rowSet.next();}
			rowSet.updateString("task_type", t.getTask_type());
			rowSet.updateDate("date_time", new java.sql.Date(t.getDatetime().getTime()));
			rowSet.updateInt("pid", t.getPatient().getPid());
			rowSet.updateInt("task_id", t.getTask_id());
			System.out.println("[RECORD:INFO] SET UPDATED TASK");
			rowSet.updateRow();
			System.out.println("[RECORD:INFO] COMMIT UPDATED TASK");
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
		return t;
	}
	
	public ArrayList<Task> all(ArrayList<Task> arr)
	{
		try
		{
			rowSet.setCommand("SELECT * FROM Task NATURAL JOIN Patient NATURAL JOIN Schedule NATURAL JOIN Doctor");
			rowSet.execute();
			while(rowSet.next())
			{
				Task t = new Task();
				populateData(t);
				arr.add(t);
			}
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return arr;
	}

	public void delete(long task_id) throws EmptySetException
	{
		try
		{
			rowSet.setCommand("SELECT * FROM Task WHERE task_id="+task_id);
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
	
	public Task first(Task t) throws EmptySetException
	{
		try
		{
			rowSet.setCommand("SELECT * FROM Task NATURAL JOIN Patient NATURAL JOIN Schedule NATURAL JOIN Doctor");
			rowSet.execute();
			if(!rowSet.next()) throw new EmptySetException();
			else {rowSet.beforeFirst();rowSet.next();}
			rowSet.first();
			populateData(t);
		}
		catch(EmptySetException ex)
		{
			throw ex;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return t;
	}
	
	public Task last(Task t) throws EmptySetException
	{
		try
		{
			rowSet.setCommand("SELECT * FROM Task NATURAL JOIN Patient NATURAL JOIN Schedule NATURAL JOIN Doctor");
			rowSet.execute();
			if(!rowSet.next()) throw new EmptySetException();
			else {rowSet.beforeFirst();rowSet.next();}
			rowSet.last();
			populateData(t);
		}
		catch(EmptySetException ex)
		{
			throw ex;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return t;
	}
	
	public Task find(long task_id,Task t) throws EmptySetException
	{
		try
		{
			rowSet.setCommand("SELECT * FROM Task NATURAL JOIN Patient NATURAL JOIN Schedule NATURAL JOIN Doctor WHERE task_id="+task_id);
			rowSet.execute();
			if(!rowSet.next()) throw new EmptySetException();
			else {rowSet.beforeFirst();rowSet.next();}
			rowSet.first();
			populateData(t);
		}
		catch(EmptySetException ex)
		{
			throw ex;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return t;
	}
	
	
	private Task populateData(Task t)
	{
		try
		{	
			Patient p = new Patient();
			p.setPid(rowSet.getInt("pid"));
			p.setName(rowSet.getString("name"));
			p.setDob(rowSet.getDate("dob"));
			t.setTask_id(rowSet.getInt("task_id"));
			t.setTask_type(rowSet.getString("task_type"));
			t.setDatetime(rowSet.getDate("date_time"));
			t.setDoctorID(rowSet.getInt("uid"));
			t.setPatient(p);
			rowSet.setCommand("SELECT * FROM Employee NATURAL JOIN LoginDetails where uid="+t.getDoctorID());
			rowSet.execute();
			t.setDoctorName(rowSet.getString("name"));
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return t;
	}
}
