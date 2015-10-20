package com.grigoris.database;
import java.sql.Timestamp;
import java.util.Date;

public class Project 
{
	//---------------------------------------------------------------//
	private int ProjectID;
	private String Name;
	private int CustomerID;
	private String ProjectType;
	private Date StartDate;
	private Date EndDate;
	private Date NextDeadline;
	private boolean Active;
	private float Budget;
	private String Description;
	private Timestamp Inserted_at;
	private String Inserted_by;
	private Timestamp Modified_at;
	private String Modified_by;
	private int Rowversion;
	private int ParProjID;
	private String customeName;
	//---------------------------------------------------------------//

	//---------------------------------------------------------------//
	public Project(int projectID, String name, int customerID,String projectType, Date startDate, Date endDate,
			Date nextDeadline, boolean active, float budget,
	String description, Timestamp timestamp, String inserted_by,Timestamp timestamp2, String modified_by, int rowversion, int parProjID,String sname) 
	{
		super();
		this.setCustomeName(sname);
		ProjectID = projectID;
		Name = name;
		CustomerID = customerID;
		ProjectType = projectType;
		StartDate = startDate;
		EndDate = endDate;
		NextDeadline = nextDeadline;
		Active = active;
		Budget = budget;
		Description = description;
		Inserted_at = timestamp;
		Inserted_by = inserted_by;
		Modified_at = timestamp2;
		Modified_by = modified_by;
		Rowversion = rowversion;
		ParProjID = parProjID;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public String getName() 
	{
		return Name;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setName(String name) 
	{
		Name = name;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public String getProjectType() 
	{
		return ProjectType;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setProjectType(String projectType) 
	{
		ProjectType = projectType;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public Date getStartDate() 
	{
		return StartDate;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setStartDate(Date startDate) 
	{
		StartDate = startDate;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public Date getEndDate() 
	{
		return EndDate;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setEndDate(Date endDate) 
	{
		EndDate = endDate;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public Date getNextDeadline() 
	{
		return NextDeadline;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setNextDeadline(Date nextDeadline) 
	{
		NextDeadline = nextDeadline;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public boolean isActive() 
	{
		return Active;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setActive(boolean active) 
	{
		Active = active;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public float getBudget() 
	{
		return Budget;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setBudget(float budget) 
	{
		Budget = budget;
	}
	//---------------------------------------------------------------//

	//---------------------------------------------------------------//
	public String getDescription()
	{
		return Description;
	}
	//---------------------------------------------------------------//

	//---------------------------------------------------------------//
	public void setDescription(String description) 
	{
		Description = description;
	}
	//---------------------------------------------------------------//+

	//---------------------------------------------------------------//
	public int getProjectID() 
	{
		return ProjectID;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setProjectID(int projectID) 
	{
		ProjectID = projectID;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public int getCustomerID() 
	{
		return CustomerID;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setCustomerID(int customerID) 
	{
		CustomerID = customerID;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public Timestamp getInserted_at() 
	{
		return Inserted_at;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setInserted_at(Timestamp inserted_at) 
	{
		Inserted_at = inserted_at;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public String getInserted_by() 
	{
		return Inserted_by;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setInserted_by(String inserted_by) 
	{
		Inserted_by = inserted_by;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public Timestamp getModified_at() 
	{
		return Modified_at;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setModified_at(Timestamp modified_at) 
	{
		Modified_at = modified_at;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public String getModified_by() 
	{
		return Modified_by;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setModified_by(String modified_by) 
	{
		Modified_by = modified_by;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public int getRowversion() 
	{
		return Rowversion;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setRowversion(int rowversion) 
	{
		Rowversion = rowversion;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public int getParProjID() 
	{
		return ParProjID;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setParProjID(int parProjID) 
	{
		ParProjID = parProjID;
	}
	//---------------------------------------------------------------//

	//---------------------------------------------------------------//
	public String getCustomeName()
	{
		return customeName;
	}
	//---------------------------------------------------------------//

	//---------------------------------------------------------------//
	public void setCustomeName(String customeName) 
	{
		this.customeName = customeName;
	}
	//---------------------------------------------------------------//

}
