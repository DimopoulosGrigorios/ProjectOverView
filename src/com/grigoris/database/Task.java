package com.grigoris.database;

import java.sql.Date;
import java.sql.Timestamp;


public class Task 
{
	//---------------------------------------------------------------//
	private int Task_ID;
	private int Project_ID;
	private String Task_DESC;
	private String Task_NOTES;
	private Date Task_DEADLINE;
	private Date Task_STARDATE;
	private Date Task_ENDDATE;
	private Boolean Task_ACTIVE;
	private String Task_Type;
	private String Task_USERNOTES;
	private int ROWVERSION;
	private Timestamp Task_INSERTEDAT;
	private Timestamp Task_MODIFIEDAT;
	private String Task_INSERTEDBY;
	private String Task_MODIFIEDBY;
	//---------------------------------------------------------------//

	//---------------------------------------------------------------//
	public Task(int task_ID, int project_ID, String task_DESC,String task_NOTES, Date task_DEADLINE, Date task_STARDATE,Date task_ENDDATE, boolean task_ACTIVE, String task_Type,
	String task_USERNOTES, int rOWVERSION, Timestamp timestamp,Timestamp timestamp2, String task_INSERTEDBY,String task_MODIFIEDBY) 
	{
		super();
		Task_ID = task_ID;
		Project_ID = project_ID;
		Task_DESC = task_DESC;
		Task_NOTES = task_NOTES;
		Task_DEADLINE = task_DEADLINE;
		Task_STARDATE = task_STARDATE;
		Task_ENDDATE = task_ENDDATE;
		Task_ACTIVE = task_ACTIVE;
		Task_Type = task_Type;
		Task_USERNOTES = task_USERNOTES;
		ROWVERSION = rOWVERSION;
		Task_INSERTEDAT = timestamp;
		Task_MODIFIEDAT = timestamp2;
		Task_INSERTEDBY = task_INSERTEDBY;
		Task_MODIFIEDBY = task_MODIFIEDBY;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public int getTask_ID() 
	{
		return Task_ID;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setTask_ID(int task_ID) 
	{
		Task_ID = task_ID;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public int getProject_ID() 
	{
		return Project_ID;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setProject_ID(int project_ID) 
	{
		Project_ID = project_ID;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public String getTask_DESC() 
	{
		return Task_DESC;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setTask_DESC(String task_DESC) 
	{
		Task_DESC = task_DESC;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public String getTask_NOTES() 
	{
		return Task_NOTES;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setTask_NOTES(String task_NOTES) 
	{
		Task_NOTES = task_NOTES;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public Date getTask_DEADLINE() 
	{
		return Task_DEADLINE;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setTask_DEADLINE(Date task_DEADLINE) 
	{
		Task_DEADLINE = task_DEADLINE;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public Date getTask_STARDATE() 
	{
		return Task_STARDATE;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setTask_STARDATE(Date task_STARDATE) 
	{
		Task_STARDATE = task_STARDATE;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public Date getTask_ENDDATE() 
	{
		return Task_ENDDATE;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setTask_ENDDATE(Date task_ENDDATE) 
	{
		Task_ENDDATE = task_ENDDATE;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public boolean isTask_ACTIVE() 
	{
		return Task_ACTIVE;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setTask_ACTIVE(boolean task_ACTIVE) 
	{
		Task_ACTIVE = task_ACTIVE;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public String getTask_Type() 
	{
		return Task_Type;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setTask_Type(String task_Type) 
	{
		Task_Type = task_Type;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public String getTask_USERNOTES() 
	{
		return Task_USERNOTES;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setTask_USERNOTES(String task_USERNOTES) 
	{
		Task_USERNOTES = task_USERNOTES;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public int getROWVERSION() 
	{
		return ROWVERSION;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setROWVERSION(int rOWVERSION) 
	{
		ROWVERSION = rOWVERSION;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public Timestamp getTask_INSERTEDAT() 
	{
		return Task_INSERTEDAT;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setTask_INSERTEDAT(Timestamp task_INSERTEDAT) 
	{
		Task_INSERTEDAT = task_INSERTEDAT;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public Timestamp getTask_MODIFIEDAT() 
	{
		return Task_MODIFIEDAT;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setTask_MODIFIEDAT(Timestamp task_MODIFIEDAT) 
	{
		Task_MODIFIEDAT = task_MODIFIEDAT;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public String getTask_INSERTEDBY() 
	{
		return Task_INSERTEDBY;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setTask_INSERTEDBY(String task_INSERTEDBY) 
	{
		Task_INSERTEDBY = task_INSERTEDBY;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public String getTask_MODIFIEDBY() 
	{
		return Task_MODIFIEDBY;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setTask_MODIFIEDBY(String task_MODIFIEDBY)
	{
		Task_MODIFIEDBY = task_MODIFIEDBY;
	}
	//---------------------------------------------------------------//
}
