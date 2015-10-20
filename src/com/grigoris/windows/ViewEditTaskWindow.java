package com.grigoris.windows;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.grigoris.database.Project;
import com.grigoris.database.ProjectsDatabase;
import com.grigoris.database.Task;
import com.grigoris.popup.ErrorWindow;
import com.vaadin.data.Container;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("deprecation")
public class ViewEditTaskWindow extends Window 
{
	//H klash auth ulopoiei thn othoni emfaniseis kai epeksergasias enos task
	//Edw fainontai oi leptomeries kathe task
	//Epishs h klash auth kaleitai kai otan theloume na dhmiourghsoume ena task apo thn arxh
	//(h monh diafora einai oti san na kanoume edit ena adeio task)
	
	//To pedio project antistoixh sto project tou opoiou einai to parathiro
	//To container task container einai to container me ta tasks mesa
	//To table task table antistoixei sto container task container
	//To Mode einai triwn eidwn kai analoga to Mode kathorizetai kai h sumperifora twn sunarthsewn ths klashs
	//Ta tria Mode einai NewTask,Edit,View
	//To editable dhlwnei an exoume dikaiwma na peiraksoume ta stoixeia tou task h oxi
	//To task einai to task tou parathirou
	//To taskManager periexei ta tasks pou einai gia diagrafh,edit h gia insert sthn bash
	//(einai en oligois kapoio eidos prosorinhs apothikeueshs
	
	//---------------------------------------------------------------//
	private static final long serialVersionUID = 1L;
	private Task task;
	private boolean editable;
	private String Mode;
	private TextField tf0;
	private Select sf1;
	private DateField df2;
	private DateField df3;
	private DateField df4;
	private Select sf5;
	private TextArea tf6;
    private TextField tf7;
    private TextField tf8;
    private TextField tf9; 
    private TextField tf10;
    private Container task_container;
    private Table task_table;
	private HashMap<String,ArrayList<Task>> taskManager;
	private ProjectsDatabase db;
	private Project project;
	protected ViewEditwindow viewEditWindow;
    private Label errorMessages;
	//---------------------------------------------------------------//

	//---------------------------------------------------------------//
	public ViewEditTaskWindow(String Mode,Task tsk,boolean Editable,Container task_container,Table task_table,HashMap<String,ArrayList<Task>> taskManager,ProjectsDatabase db,Project project,ViewEditwindow viewEditWindow) 
	{
		super(Mode);
		this.viewEditWindow=viewEditWindow;
		this.task_container=task_container;
		this.task_table=task_table;

        this.task=tsk;
        this.editable=Editable;
        this.Mode=Mode;
        this.taskManager=taskManager;
        this.project=project;
        this.db=db;
        this.addFocusListener(new FocusListener() 
        {
			private static final long serialVersionUID = 1L;
			public void focus(FocusEvent event) 
        	{
        		errorMessages.setVisible(false);
        	}
        });
        
        VerticalLayout maincontent=new VerticalLayout();
        maincontent.setSizeUndefined();
        maincontent.setMargin(true);
     
        
        HorizontalLayout hsplitbutton=new HorizontalLayout();
        hsplitbutton.setSizeFull();
        hsplitbutton.setSpacing(true);
        
        FormLayout fl = new FormLayout();
        formFiller(fl);
        
       

		Button ok = new Button("Ok");
	    Button cancel = new Button("Cancel");
	    cancelOkHandler(cancel,ok);
	    
	    errorMessages=new Label();
        errorMessages.setVisible(false);
        errorMessages.setEnabled(false);
        errorMessages.addStyleName("labelstyle");
        errorMessages.setValue("");
        errorMessages.setReadOnly(true);
	
	    hsplitbutton.addComponent(ok);
	    hsplitbutton.addComponent(cancel);
	    maincontent.addComponent(errorMessages);
	    maincontent.addComponent(fl);
	    maincontent.addComponent(hsplitbutton);
        setContent(maincontent);
    }
	//---------------------------------------------------------------//
	
	
	//---------------------------------------------------------------//
	@SuppressWarnings("serial")
	private void cancelOkHandler(Button cancel,Button ok)
	{
		ok.addClickListener(new ClickListener() 
        {
			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) 
			{
				errorMessages.setVisible(false);
				if (editable)
				{
					if (((!(tf0.getValue().toString().trim().length() > 0))||(tf0.getValue().toString().equals(""))||(tf0.getValue()==null)||(sf5.getValue()==null)||(df2.getValue()==null)))
					{
						if ((!(tf0.getValue().toString().trim().length() > 0))||(tf0.getValue().toString().equals(""))||(tf0.getValue()==null))
						{
							errorMessages.setValue("Wrong input on Name field(Name can not be empty!).");
							errorMessages.setVisible(true);
						}
						if((sf5.getValue()==null))
						{
							errorMessages.setValue("Task state can not be empty.Check the Active field!");
							errorMessages.setVisible(true);
						}
						if ((df2.getValue()==null))
						{
							errorMessages.setValue("You should tell me the \n Start Date of the project Buddy.");
							errorMessages.setVisible(true);
						}
					}
					else
					{
						if (!checkdates(df2.getValue(),df3.getValue()))
						{
							errorMessages.setValue("End Date can't be before Start Date.");
							errorMessages.setVisible(true);
						}
						else
						{
							if (!(
									(checkdates(project.getStartDate(),df2.getValue()))&&
									(checkdates(df3.getValue(),project.getEndDate()))&&
							(checkdates(df2.getValue(),project.getEndDate()))&&
							(checkdates(df2.getValue(),df4.getValue()))&&
							(checkdates(df4.getValue(),df3.getValue()))))
							{
								if(!(checkdates(project.getStartDate(),df2.getValue())))
								{
									errorMessages.setValue("Project can not start after Task(check Start Date)");
									errorMessages.setVisible(true);
								}
								if(!(checkdates(df3.getValue(),project.getEndDate())))
								{
									errorMessages.setValue("Project can not end before Task(check End Date).");
									errorMessages.setVisible(true);
								}
								if(!(checkdates(df2.getValue(),project.getEndDate())))
								{
									errorMessages.setValue("Task can't start after project's end(chekc Start Date).");
									errorMessages.setVisible(true);
								}
								if(!(checkdates(df2.getValue(),df4.getValue())))
								{
									errorMessages.setValue("Deadline should be between start/end(check Deadline).");
									errorMessages.setVisible(true);
								}
								if(!(checkdates(df4.getValue(),df3.getValue())))
								{
									errorMessages.setValue("Deadline should be between start/end(check Deadline).");
									errorMessages.setVisible(true);
								}
							}
							else
							{
								if( Mode.equals("New Task")||Mode.equals("Edit"))
					        	{
									setTask();
					        	}
								
								task_container.removeAllItems();
								ArrayList<Task> tasks=null;
								try 
								{
									tasks=db.selectTaskforProjID(project.getProjectID());
								}
								catch (SQLException e) 
								{
									ErrorWindow wind = new ErrorWindow(e); 
							        UI.getCurrent().addWindow(wind);
									e.printStackTrace();
								}
								boolean puttoedit=true;
								ArrayList<Task> tasks2=new ArrayList<Task>(tasks);
								for(Task task:tasks)
									for(Task del:taskManager.get("toDelete"))
										if (task.getTask_ID()==del.getTask_ID())
											tasks2.remove(task);
								tasks.removeAll(tasks);
								tasks.addAll(tasks2);
								if (Mode.equals("Edit"))
								{
									
									//An to task pou epeksergzesai einai sthn bash 
									//Mhn emfaniseis sto table to palio task alla to kainourgio
									for(int i=0;i<tasks.size();i++)
									{
										if (tasks.get(i).getTask_ID()==task.getTask_ID())
										{
											tasks.remove(i);
											tasks.add(task);
										}
									}
									//An to task pou epeksergzesai einai sthn lista Insert
									//Mhn emfaniseis sto table to palio task alla to kainourgio
									for(int i=0;i<taskManager.get("toInsert").size();i++)
									{
										if (taskManager.get("toInsert").get(i).getTask_ID()==task.getTask_ID())
										{
											taskManager.get("toInsert").remove(i);taskManager.get("toInsert").add(task);
											puttoedit=false;
										}
									}
									//An to task pou epeksergzesai einai sthn lista Edit
									//Mhn emfaniseis sto table to palio task alla to kainourgio
									for(int i=0;i<taskManager.get("toEdit").size();i++)
									{
										if (taskManager.get("toEdit").get(i).getTask_ID()==task.getTask_ID())
										{
											taskManager.get("toEdit").remove(i);
										}							
										
									}
									
								}
								
								if (Mode.equals("New Task"))taskManager.get("toInsert").add(task);
								if ((Mode.equals("Edit"))&&puttoedit)taskManager.get("toEdit").add(task);
								
								//Mhn emfaniseis diplotipa sto table
								for(int i=0;i<tasks.size();i++)
								{
									for(int j=0;j<taskManager.get("toInsert").size();j++)
										if (taskManager.get("toInsert").get(j).getTask_ID()==tasks.get(i).getTask_ID())
											tasks.remove(i);
									for(int j=0;j<taskManager.get("toEdit").size();j++)
										if (taskManager.get("toEdit").get(j).getTask_ID()==tasks.get(i).getTask_ID())
											tasks.remove(i);
									
								}
								
								tasks.addAll(taskManager.get("toInsert"));
								tasks.addAll(taskManager.get("toEdit"));
								viewEditWindow.containerFiller(task_table,task_container,tasks);
								task_table.setContainerDataSource(task_container);
								close();
							}
						}
					}
				}
				else
				{
					close();
				}
			}
			
        });
		
		cancel.addClickListener(new ClickListener() 
        {
			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) 
			{
				errorMessages.setVisible(false);
				close();
				
			}
        });
	}
	//---------------------------------------------------------------//
	
	//Arxikopoihsh kai setarisma idiotitwn ths formas
	//---------------------------------------------------------------//
	private void formFiller(FormLayout fl)
	{
        fl.setSizeFull();

		tf0=new TextField("Description");
		tf0.setRequired(true);
        
        sf1 = new Select ("Type");
        sf1.addItem("Flexible");
        sf1.addItem("UnFlexible");
        df2=new  DateField("Start Date");
        df2.setDateFormat("d-M-y");
        df2.setRequired(true);
        
        df2.setRangeEnd(project.getEndDate());
        df2.setRangeStart(project.getStartDate());
        
        df3=new  DateField("End Date");
        df3.setDateFormat("d-M-y");
        
       
        
        df4=new  DateField("DeadLine");
        df4.setDateFormat("d-M-y");
        
        df4.setRangeStart(df2.getValue());
        df4.setRangeEnd(df3.getValue());
        
        sf5 = new Select ("Active");
        sf5.addItem("yes");
        sf5.addItem("no");
        sf5.setRequired(true);
        
        tf6=new TextArea("User Notes");

        tf7=new TextField("Inserted By");
        
        tf8=new TextField("Inserted At");
        
        tf9=new TextField("Modified By");
        
        tf10=new TextField("Modified At");
        
        if( task.getTask_DESC()!=null)tf0.setValue(task.getTask_DESC());
        else tf0.setValue("");
		if(!editable)tf0.setReadOnly(true);
        fl.addComponent(tf0, 0);
        

        if(task.getTask_Type()!=null) sf1.setValue(task.getTask_Type());
        else sf1.setValue("");
        if(!editable)sf1.setReadOnly(true);
        fl.addComponent(sf1, 1);
        
        if(task.getTask_STARDATE()!=null) df2.setValue(task.getTask_STARDATE());
        if(!editable)df2.setReadOnly(true);
        fl.addComponent(df2, 2);
        
        if(task.getTask_ENDDATE()!=null) df3.setValue(task.getTask_ENDDATE());
        if(!editable)df3.setReadOnly(true);
        fl.addComponent(df3, 3);
        
        if(task.getTask_DEADLINE()!=null)df4.setValue(task.getTask_DEADLINE());
        if(!editable)df4.setReadOnly(true);
        fl.addComponent(df4, 4);
        
        if (task.isTask_ACTIVE())sf5.setValue("yes");
        else sf5.setValue("no");
        if(!editable)sf5.setReadOnly(true);
        fl.addComponent(sf5, 5);
        
        if(task.getTask_USERNOTES()!=null) tf6.setValue(task.getTask_USERNOTES());
        else tf6.setValue("");
        if(!editable)tf6.setReadOnly(true);
        fl.addComponent(tf6, 6);
        
        if(task.getTask_INSERTEDBY()!=null)tf7.setValue(task.getTask_INSERTEDBY());
        else tf7.setValue("");
        tf7.setEnabled(false);
        fl.addComponent(tf7, 7);
        
        if(task.getTask_INSERTEDAT()!=null)tf8.setValue(task.getTask_INSERTEDAT().toString());
        else tf8.setValue("");
        tf8.setEnabled(false);
        fl.addComponent(tf8, 8);
        
        if(task.getTask_MODIFIEDBY()!=null)tf9.setValue(task.getTask_MODIFIEDBY());
        else tf9.setValue("");
        tf9.setEnabled(false);
        fl.addComponent(tf9,9);
        
        if(task.getTask_MODIFIEDAT()!=null)tf10.setValue(task.getTask_MODIFIEDAT().toString());
        else tf10.setValue("");
        tf10.setEnabled(false);
        fl.addComponent(tf10, 10);
      
	}
	//---------------------------------------------------------------//
	
	//Boithitikh synarthsh gia na kaneis set to task me ta pedia ths formas
	//---------------------------------------------------------------//
	private void setTask()
	{
		Date currentdate=new Date(Calendar.getInstance().getTimeInMillis());
		task.setTask_DESC(tf0.getValue().toString());
		if (sf1.getValue()!=null)task.setTask_Type(sf1.getValue().toString());
		else task.setTask_Type(null);
		if (df2.getValue()!=null)task.setTask_STARDATE(new java.sql.Date(df2.getValue().getTime()));
		else task.setTask_STARDATE(null);
		if (df3.getValue()!=null) task.setTask_ENDDATE(new java.sql.Date(df3.getValue().getTime()));
		else task.setTask_ENDDATE(null);
		if (df4.getValue()!=null)task.setTask_DEADLINE(new java.sql.Date(df4.getValue().getTime()));
		else task.setTask_DEADLINE(null);
		task.setTask_ACTIVE(sf5.getValue().toString().equals("yes"));
		task.setTask_USERNOTES(tf6.getValue().toString());
		if( Mode.equals("New Task")) task.setTask_INSERTEDBY("Grigoris");
		if (Mode.equals("Edit") )task.setTask_MODIFIEDBY("Grigoris");
		if( Mode.equals("New Task"))task.setROWVERSION(1);
		if (Mode.equals("Edit") ) task.setTask_MODIFIEDAT(new Timestamp(currentdate.getTime()));
		if( Mode.equals("New Task"))task.setTask_INSERTEDAT(new Timestamp(currentdate.getTime()));
	}
	//---------------------------------------------------------------//

	//Boithitikh sunarthsh hmeromhnias pou einai anektikh se null times
	//Idia leitourgkothta me to before
	//---------------------------------------------------------------//
	private boolean checkdates(java.util.Date date,java.util.Date date2)
	{
		if (date==null||date2==null) return true;
		if (!date.after(date2)&&!date.before(date2))return true;
		return date.before(date2);
	}
	//---------------------------------------------------------------//

	//---------------------------------------------------------------//
	public Label getErrorMessages() 
	{
		return errorMessages;
	}
	//---------------------------------------------------------------//

	//---------------------------------------------------------------//
	public void setErrorMessages(Label errorMessages) 
	{
		this.errorMessages = errorMessages;
	}
	//---------------------------------------------------------------//

}
	

