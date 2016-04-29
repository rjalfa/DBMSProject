package com.iiitd.dbms.medsh.record;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

import com.iiitd.dbms.medsh.model.Employee;
import com.iiitd.dbms.medsh.model.Task;
import com.iiitd.dbms.medsh.util.EmptySetException;
import com.iiitd.dbms.medsh.util.GlobalVars;

public class TaskRecord {
	private JdbcRowSet rowSet = null;
	
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
			rowSet.setCommand("SELECT * FROM Task");
			rowSet.execute();
			rowSet.moveToInsertRow();
			rowSet.updateInt("task_id", t.getTask_id());
			rowSet.updateInt("pid", t.getPatient().getPid());
			rowSet.updateDate("date_time", new java.sql.Date(t.getDatetime().getTime()));
			rowSet.updateString("task_type", t.getTask_type());
			rowSet.insertRow();
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return t;
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
			rowSet.setCommand("SELECT * FROM Task");
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
			rowSet.setCommand("SELECT * FROM Task");
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
			rowSet.setCommand("SELECT * FROM Task");
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
			rowSet.setCommand("SELECT * FROM Task WHERE task_id="+task_id);
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
			t.setTask_id(rowSet.getInt("task_id"));
			t.setTask_type(rowSet.getString("task_type"));
			t.setDatetime(rowSet.getDate("date_time"));
			//Set Patient object from pid??
			//t.setPatient();
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		return t;
	}
}
