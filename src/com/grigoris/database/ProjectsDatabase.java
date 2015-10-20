package com.grigoris.database;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


public class ProjectsDatabase 
{
		

	//---------------------------------------------------------------//
	public ProjectsDatabase() 
	{
	
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public Connection select() 
	{
			return DBhelper.getConnection();
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public ArrayList<Project> selectAllProjects() throws SQLException 
	{
		Connection conn = null;
		PreparedStatement prepStmt = null;
		ArrayList<Project> rtrn=null;
		try {
			conn = select();
			String sql = "SELECT pr.*,cst.CUST_NAME FROM PROJECTS pr,CUSTOMERS cst WHERE cst.CUST_ID=pr.CUST_ID";
			prepStmt = conn.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			rtrn=new ArrayList<Project>();
			while (rs.next())
			{
				Project proj = new Project(rs.getInt("PROJ_ID"), rs.getString("PROJ_NAME"),rs.getInt("CUST_ID"),
						rs.getString("PROJ_TYPE"), rs.getDate("PROJ_FROM"),rs.getDate("PROJ_TO"),
						rs.getDate("PROJ_DEADLINE"),(rs.getString("PROJ_ACTIVE")).equals("Y"),rs.getFloat("PROJ_BUDGET"),
						rs.getString("PROJ_DESCRIPTION"), rs.getTimestamp("INSERTED_AT"), rs.getString("INSERTED_BY"),
						 rs.getTimestamp("MODIFIED_AT"), rs.getString("MODIFIED_BY"),rs.getInt("ROWVERSION"), rs.getInt("PAR_PROJ_ID"),rs.getString("CUST_NAME")); 
				rtrn.add(proj);
			}
			rs.close();
		}
		finally 
		{
			
			if (prepStmt != null)
					conn.close();
			
			if (conn != null)
					conn.close();
		}
		return rtrn;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public String selectCustomerforId(int id) throws SQLException 
	{
		Connection conn = null;
		PreparedStatement prepStmt = null;
		String rtrn="";
		if( id==-1)  return rtrn;
		try {
			conn = select();
			String sql = "SELECT CUST_NAME FROM CUSTOMERS WHERE CUST_ID=?";
			prepStmt = conn.prepareStatement(sql);
			prepStmt.setInt(1, id);
			ResultSet rs = prepStmt.executeQuery();
			if (!rs.next()) 
			{
				return null;
			}
			rtrn = rs.getString("CUST_NAME");
			rs.close();
		}
		
		finally 
		{
			if (prepStmt != null)
					conn.close();
			if (conn != null)
					conn.close();	
		}
		return rtrn;
	}
	//---------------------------------------------------------------//

	//---------------------------------------------------------------//
	public int selectCustomerforName(String Name) throws SQLException 
	{
		Connection conn = null;
		PreparedStatement prepStmt = null;
		int rtrn=-1;
		if(Name.equals(""))  return rtrn;
		try {
			conn = select();
			String sql = "SELECT CUST_ID FROM CUSTOMERS WHERE CUST_NAME=?";
			prepStmt = conn.prepareStatement(sql);
			prepStmt.setString(1, Name);
			ResultSet rs = prepStmt.executeQuery();
			if (!rs.next()) 
			{
				return -1;
			}
			rtrn = rs.getInt("CUST_ID");
			rs.close();
		}
		finally 
		{
			if (prepStmt != null)
				conn.close();
			if (conn != null)
				conn.close();
		}
		return rtrn;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public ArrayList<String> selectAllCustomers() throws SQLException 
	{
		Connection conn = null;
		PreparedStatement prepStmt = null;
		ArrayList<String> rtrn=null;
		try {
			conn = select();
			String sql = "SELECT CUST_NAME FROM CUSTOMERS";
			prepStmt = conn.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			if (!rs.next()) 
			{
				return null;
			}
			rtrn=new ArrayList<String>();
			while (rs.next())
			{
				rtrn.add(rs.getString("CUST_NAME"));
			}
			rs.close();
		}
		finally 
		{
				if (prepStmt != null)
					conn.close();
			
				if (conn != null)
					conn.close();
		}
		return rtrn;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public ArrayList<Task> selectTaskforProjID(int projID) throws SQLException 
	{
		Connection conn = null;
		PreparedStatement prepStmt = null;
		ArrayList<Task> rtrn=null;
		try {
			conn = select();
			String sql = "SELECT * FROM TASKS WHERE PROJ_ID=?";
			prepStmt = conn.prepareStatement(sql);
			prepStmt.setInt(1, projID);
			ResultSet rs = prepStmt.executeQuery();
			rtrn=new ArrayList<Task>();
			while (rs.next())
			{
				Task task = new Task(rs.getInt("TASK_ID"), rs.getInt("PROJ_ID"), rs.getString("TASK_DESCRIPTION"),
						rs.getString("TASK_NOTES"), rs.getDate("TASK_DEADLINE"), rs.getDate("TASK_FROM"),
						rs.getDate("TASK_TO"), rs.getString("TASK_ACTIVE").equals("Y"), rs.getString("TASK_TYPE"),
						rs.getString("TASK_USER_NOTES"), rs.getInt("ROWVERSION"), rs.getTimestamp("INSERTED_AT"),
						 rs.getTimestamp("MODIFIED_AT"), rs.getString("INSERTED_BY"),
						 rs.getString("MODIFIED_BY"));
				rtrn.add(task);
			}
			rs.close();
		}
		
		finally 
		{
				if (prepStmt != null)
					conn.close();
				if (conn != null)
					conn.close();
		}
		return rtrn;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void deleteProject( Project project) throws SQLException
	{
		Connection conn = null;
		PreparedStatement prepStmt = null;
		try
		{
			conn = select();
			String sql = "DELETE FROM PROJECTS WHERE PROJ_ID = "+String.valueOf(project.getProjectID());
			prepStmt = conn.prepareStatement(sql);
			prepStmt.executeQuery();
		}
		finally 
		{
				if (prepStmt != null)
					conn.close();
				if (conn != null)
					conn.close();
		}
		return;
	}
	//---------------------------------------------------------------//

	//---------------------------------------------------------------//
	public void deleteTask( Task task,Connection conn) throws SQLException
	{
		PreparedStatement prepStmt = null;
		String sql = "DELETE FROM TASKS WHERE TASK_ID = ?";
		prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, task.getTask_ID());
		prepStmt.executeQuery();
		return;
	}
	//---------------------------------------------------------------//

	//---------------------------------------------------------------//
	public int insertProject(Project project,Connection conn) throws SQLException
	{
		PreparedStatement prepStmt = null;
		int rtrn=-1;	
		java.util.Date date= new java.util.Date();
		Timestamp currentdate=new Timestamp(date.getTime());
		conn = select();
		String sql = "INSERT INTO PROJECTS(PROJ_ID,PROJ_NAME,INSERTED_AT,INSERTED_BY,MODIFIED_AT,MODIFIED_BY,PROJ_ACTIVE,PROJ_BUDGET,PROJ_DEADLINE,PROJ_DESCRIPTION,PROJ_FROM,PROJ_TYPE,PROJ_TO,CUST_ID,ROWVERSION)"+
		" VALUES(PROJ_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";		
		String cols[] = {"PROJ_ID"};
		prepStmt = conn.prepareStatement(sql,cols);
		prepStmt.setString(1, project.getName());
		prepStmt.setTimestamp(2, currentdate);
		prepStmt.setString(3, project.getInserted_by());
		prepStmt.setDate(4, null);
		prepStmt.setString(5,null);
		if (project.isActive())prepStmt.setString(6, "Y");
		else prepStmt.setString(6, "N");
		if (project.getBudget()!=-1) prepStmt.setFloat(7, project.getBudget());
		else prepStmt.setInt(7, 0);
		if (project.getNextDeadline()==null)prepStmt.setDate(8, null );
		else prepStmt.setDate(8, new java.sql.Date(project.getNextDeadline().getTime()) );
		prepStmt.setString(9, project.getDescription());
		prepStmt.setDate(10, new java.sql.Date(project.getStartDate().getTime()) );
		prepStmt.setString(11, project.getProjectType());
		if(project.getEndDate()==null)prepStmt.setDate(12, null); 
		else prepStmt.setDate(12, new java.sql.Date(project.getEndDate().getTime()));
		prepStmt.setInt(13,project.getCustomerID());
		prepStmt.setInt(14, project.getRowversion());
		prepStmt.executeUpdate();
		ResultSet rs =prepStmt.getGeneratedKeys();
		if (rs.next())
		{
	            rtrn=rs.getInt(1);
	    }
		rs.close();
		return rtrn;
	}
	//---------------------------------------------------------------//

	//---------------------------------------------------------------//
	public int editProject(Project project,Connection conn) throws SQLException
	{
		PreparedStatement prepStmt = null;
		int rtrn=-1;
		java.util.Date date= new java.util.Date();
		Timestamp currentdate=new Timestamp(date.getTime());
		conn = select();
		String sql = "UPDATE PROJECTS SET PROJ_NAME=?,MODIFIED_AT=?,MODIFIED_BY=?,PROJ_ACTIVE=?,PROJ_BUDGET=?,PROJ_DEADLINE=?,PROJ_DESCRIPTION=?,PROJ_FROM=?,PROJ_TYPE=?,PROJ_TO=?,CUST_ID=?,ROWVERSION=ROWVERSION+1"+
		"WHERE PROJ_ID=?";
		
		prepStmt = conn.prepareStatement(sql);
		prepStmt.setString(1, project.getName());
		
		prepStmt.setTimestamp(2, currentdate);
		
		prepStmt.setString(3, project.getModified_by());
		
		if (project.isActive())prepStmt.setString(4, "Y");
		else prepStmt.setString(4, "N");
		
		if (project.getBudget()!=-1) prepStmt.setFloat(5, project.getBudget());
		else prepStmt.setInt(5, 0);
		
		if (project.getNextDeadline()==null)prepStmt.setDate(6, null );
		else prepStmt.setDate(6, new java.sql.Date(project.getNextDeadline().getTime()) );
		
		prepStmt.setString(7, project.getDescription());
		
		prepStmt.setDate(8, new java.sql.Date(project.getStartDate().getTime()) );
		
		prepStmt.setString(9, project.getProjectType());
		
		if(project.getEndDate()==null)prepStmt.setDate(10, null); 
		else prepStmt.setDate(10, new java.sql.Date(project.getEndDate().getTime()));
		
		prepStmt.setInt(11,project.getCustomerID());
		prepStmt.setInt(12, project.getProjectID());
		prepStmt.executeUpdate();
		rtrn=project.getProjectID();
		return rtrn;
	}
	//---------------------------------------------------------------//

	//---------------------------------------------------------------//
	public void addTasktoProject(int projectid,Task task,Connection conn) throws SQLException
	{
		PreparedStatement prepStmt = null;
		java.util.Date date= new java.util.Date();
		Timestamp currentdate=new Timestamp(date.getTime());
		conn = select();
		String sql = "INSERT INTO TASKS(TASK_ID,PROJ_ID,TASK_DESCRIPTION,TASK_NOTES,TASK_DEADLINE,TASK_FROM,TASK_TO,TASK_ACTIVE,TASK_TYPE,TASK_USER_NOTES,ROWVERSION,INSERTED_AT,INSERTED_BY,MODIFIED_AT,MODIFIED_BY)"+
		" VALUES(PROJ_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";			
		prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, projectid);
		prepStmt.setString(2, task.getTask_DESC());
		prepStmt.setString(3, task.getTask_NOTES());
		
		prepStmt.setDate(4,task.getTask_DEADLINE());
		prepStmt.setDate(5, task.getTask_STARDATE());
		prepStmt.setDate(6, task.getTask_ENDDATE());

		if (task.isTask_ACTIVE())prepStmt.setString(7, "Y");
		else prepStmt.setString(7, "N");
		
		prepStmt.setString(8, task.getTask_Type());
		prepStmt.setString(9, task.getTask_USERNOTES());
		prepStmt.setInt(10, 1);

		if (task.getTask_INSERTEDAT()!=null)prepStmt.setTimestamp(11, currentdate);
		else prepStmt.setDate(11, null);
		prepStmt.setString(12, "Grigoris");
		if (task.getTask_MODIFIEDAT()!=null)prepStmt.setTimestamp(13, currentdate);
		else prepStmt.setDate(13, null);
		if (task.getTask_MODIFIEDAT()!=null)prepStmt.setString(14,"Grigoris");
		else prepStmt.setString(14, "Grigoris");
		prepStmt.executeUpdate(); 
		return;
	}
	//---------------------------------------------------------------//

	//---------------------------------------------------------------//
	public void updateTasktoProject(Task task,Connection conn) throws SQLException
	{
		PreparedStatement prepStmt = null;
		java.util.Date date= new java.util.Date();
		Timestamp currentdate=new Timestamp(date.getTime());
		conn = select();
		String sql = "UPDATE TASKS SET TASK_DESCRIPTION=?,TASK_NOTES=?,TASK_DEADLINE=?,TASK_FROM=?,TASK_TO=?,TASK_ACTIVE=?,TASK_TYPE=?,TASK_USER_NOTES=?,ROWVERSION=+ROWVERSION+1,INSERTED_AT=?,INSERTED_BY=?,MODIFIED_AT=?,MODIFIED_BY=?"+
		" WHERE TASK_ID=?";			
		prepStmt = conn.prepareStatement(sql);
		prepStmt.setString(1, task.getTask_DESC());
		prepStmt.setString(2, task.getTask_NOTES());
		
		prepStmt.setDate(3,task.getTask_DEADLINE());
		prepStmt.setDate(4, task.getTask_STARDATE());
		prepStmt.setDate(5, task.getTask_ENDDATE());

		if (task.isTask_ACTIVE())prepStmt.setString(6, "Y");
		else prepStmt.setString(6, "N");
		
		prepStmt.setString(7, task.getTask_Type());
		prepStmt.setString(8, task.getTask_USERNOTES());

		if (task.getTask_INSERTEDAT()!=null)prepStmt.setTimestamp(9, currentdate);
		else prepStmt.setDate(9, null);
		prepStmt.setString(10, "Grigoris");
		if (task.getTask_MODIFIEDAT()!=null)prepStmt.setTimestamp(11, currentdate);
		else prepStmt.setDate(11, null);
		prepStmt.setString(12, "Grigoris");
		prepStmt.setInt(13, task.getTask_ID());
		prepStmt.executeUpdate();
		return;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void projectModifier(String Mode,Project project,HashMap<String,ArrayList<Task>> taskManager) throws SQLException
	{
		
		int proid = -7777;
		Connection conn = null;
		PreparedStatement prepStmt = null;
		try
		{
			conn = select();
			if (Mode.equals("New Project"))
				proid=insertProject(project,conn);
			else
				proid=editProject(project,conn);
			project.setProjectID(proid);
			for(Task ts:taskManager.get("toInsert"))
			{
				addTasktoProject(project.getProjectID(),ts,conn);
			}
			for(Task ts:taskManager.get("toDelete"))
			{
				deleteTask(ts,conn);
			}
			for(Task ts:taskManager.get("toEdit"))
			{
				updateTasktoProject(ts,conn);
			}
		}
		finally 
		{
			if (prepStmt != null)
				conn.close();
			if (conn != null)
				conn.close();
		}
	}
	//---------------------------------------------------------------//

}


