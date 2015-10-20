package com.grigoris.windows;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import com.grigoris.database.Project;
import com.grigoris.database.ProjectsDatabase;
import com.grigoris.database.Task;
import com.grigoris.filters.DateFilter;
import com.grigoris.popup.ConfirmationwindowTask;
import com.grigoris.popup.ErrorWindow;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.converter.StringToDateConverter;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.AlignmentInfo.Bits;
import com.vaadin.ui.Button;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.DateField;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

@SuppressWarnings("deprecation")
public class ViewEditwindow extends Window 
{
	//H klash auth ulopoiei thn othoni emfaniseis kai epeksergasias enos project
	//Edw fainontai oi leptomeries kathe project
	//Opws epishs kai ta task pou diathetei
	//Epishs h klash auth kaleitai kai otan theloume na dhmiourghsoume ena project apo thn arxh
	//(h monh diafora einai oti san na kanoume edit ena adeio project)
	
	//To pedio project antistoixh sto project tou opoiou einai to parathiro
	//To container container einai to container me ta projects mesa
	//To table  antistoixei sto container 
	//To Mode einai triwn eidwn kai analoga to Mode kathorizetai kai h sumperifora twn sunarthsewn ths klashs
	//Ta tria Mode einai NewProject,Edit,View
	//To editable dhlwnei an exoume dikaiwma na peiraksoume ta stoixeia tou project h oxi
	//To taskManager periexei ta tasks pou einai gia diagrafh,edit h gia insert sthn bash
	//(einai en oligois kapoio eidos prosorinhs apothikeueshs
	//To counter einai boithitikh variable gia ta projects pou den exoun id wste na mporoume na doulepsoume me auta
	//kai as mhn exoun kataxwrithei sthn bash
	//Otan kataxwrithei sthn bash pernei kanoniko id omws
	//---------------------------------------------------------------//
	private static final long serialVersionUID = 1L;
	private  Project project;
	private ProjectsDatabase db;
	private boolean editable;
	private String Mode;
	private TextField tf0;
	private Select sf1;
	private Select sf2;
	private DateField df3;
	private DateField df4;
	private DateField df5;
	private Select sf6;
	private TextField tf7;
    private TextArea tf8;
    private TextField tf9;
    private TextField tf10; 
    private TextField tf11; 
    private TextField tf12;
    private Container project_container;
    private Table project_table;
    private Container container;
    private Table table;
    public  HashMap<String,ArrayList<Task>> taskManager;
    public TextField filter;
    private int counter=-1;
    private GntTraineeProjectUI gntTraineeProjectUI;
    protected  ViewEditwindow thisobject=this;
    private Label errorMessages;
	//---------------------------------------------------------------//

	//---------------------------------------------------------------//
	public ViewEditwindow(String Mode,Project pr,ProjectsDatabase db,boolean Editable,Container project_container,Table project_table,GntTraineeProjectUI gntTraineeProjectUI) 
	{
		super(Mode);
		this.project_container=project_container;
		this.project_table=project_table;
		this.gntTraineeProjectUI=gntTraineeProjectUI;
        this.db=db;
        this. project=pr;
        this.editable=Editable;
        this.Mode=Mode;
        
        this.taskManager=new HashMap<String,ArrayList<Task>>();
        this.addFocusListener(new FocusListener() 
        {
			private static final long serialVersionUID = 1L;
			public void focus(FocusEvent event) 
        	{
        		errorMessages.setVisible(false);
        	}
        });
        ArrayList<Task> toDelete=new ArrayList<Task> ();
        ArrayList<Task> toInsert=new ArrayList<Task> ();
        ArrayList<Task> toEdit=new ArrayList<Task> ();
        taskManager.put("toDelete",toDelete );
        taskManager.put("toEdit", toEdit);
        taskManager.put("toInsert",toInsert);
       
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

		
        HorizontalSplitPanel hsplit = new HorizontalSplitPanel();
		hsplit.setSplitPosition(330, Sizeable.UNITS_PIXELS);
		hsplit.setLocked(true);
		hsplit.setSizeFull();
        
        VerticalSplitPanel vsplit=new VerticalSplitPanel(); 
		vsplit.setSplitPosition(750, Sizeable.UNITS_PIXELS);
		vsplit.setLocked(true);
        vsplit.setSizeFull();
        
        VerticalSplitPanel vsplittask=new VerticalSplitPanel(); 
		vsplittask.setSplitPosition(64, Sizeable.UNITS_PIXELS);
		vsplittask.setLocked(true);
        vsplittask.setSizeFull();
        
        GridLayout grid=new  GridLayout(2,1);
        grid.setSizeFull();
        
        FormLayout fl = new FormLayout();
        formFiller(fl);
        
        VerticalLayout content = new VerticalLayout();
        content.setMargin(true);
        content.setSizeFull();
		
		VerticalSplitPanel vsplittable=new VerticalSplitPanel(); 
		vsplittable.setSplitPosition(690, Sizeable.UNITS_PIXELS);
		vsplittable.setLocked(true);
		vsplittable.setSizeFull();
		
		GridLayout gridsearch=new GridLayout(120,1);
		gridsearch.setSizeFull();
		
		VerticalLayout tablevertical=new VerticalLayout();
	    tablevertical.setSizeFull();
	    
	    GridLayout gridbutton=new GridLayout(1,2);
		gridbutton.setSizeFull();
		Button ok = new Button("Ok");
	    Button cancel = new Button("Cancel");
	    cancelOkHandler(cancel,ok);
	    
	    Button NewTaskButton=new Button("New Task");
		newTaskButtonHandler(NewTaskButton);
     
		container = new IndexedContainer();
		table = new Table("Project Tasks");
		filter = new TextField("Search here.");
		if (tasks!=null)
			 containerFiller(table,container,tasks);
		else
			containerInitializer(container);
		
		tableFiller(container,table);
		
		
		
		setFilter(filter,table);

		errorMessages=new Label();
        errorMessages.setVisible(false);
        errorMessages.setEnabled(false);
        errorMessages.addStyleName("labelstyle");
        errorMessages.setValue("");
        errorMessages.setWidth(500, Sizeable.UNITS_PIXELS);
        errorMessages.setHeight(50, Sizeable.UNITS_PIXELS);
        errorMessages.setReadOnly(true);
        
		gridsearch.addComponent(filter, 119, 0);
		gridsearch.addComponent(errorMessages, 60, 0);
		gridsearch.setComponentAlignment(filter,new Alignment(Bits.ALIGNMENT_LEFT |Bits.ALIGNMENT_TOP));
		grid.addComponent(ok, 0, 0);
        grid.addComponent(cancel, 1, 0);
        
        vsplit.addComponent(fl);
        VerticalLayout vlay=new VerticalLayout();
        vlay.addComponent(grid);
        vlay.setMargin(true);
        vlay.setHeight(80, Sizeable.UNITS_PIXELS);
        vsplit.addComponent(vlay);
        
        hsplit.addComponent(vsplit);
        tablevertical.addComponent(table);
        vsplittable.addComponent(tablevertical);
		gridbutton.addComponent(NewTaskButton, 0, 1);
		HorizontalLayout hlay=new HorizontalLayout();
	    hlay.addComponent(gridbutton);
	    hlay.setMargin(true);
        hlay.setHeight(80, Sizeable.UNITS_PIXELS);
        vsplittable.addComponent(hlay);
        vsplittask.addComponent(gridsearch);
        vsplittask.addComponent(vsplittable);
        hsplit.addComponent(vsplittask);
        content.addComponent(hsplit);
        
        setContent(content);
    }
	//---------------------------------------------------------------//
	
	//Setarisma ths sumperiforas tou button gia neo project
	//---------------------------------------------------------------//
	@SuppressWarnings({ "serial" })
	private void newTaskButtonHandler(Button NewTaskButton)
	{
		if (editable)
		{
			NewTaskButton.addListener(new Button.ClickListener()
			 {
				@Override
				public void buttonClick(com.vaadin.ui.Button.ClickEvent event) 
				{
					errorMessages.setVisible(false);
					Task emptytask=new Task(--counter, project.getProjectID(), null,null, null,null,null,false, null,null, 1, null,null, "","") ;
					ViewEditTaskWindow wind=new ViewEditTaskWindow("New Task",emptytask,true,container,table,taskManager,db,project,thisobject) ;
					setProject();
					wind.center();
					UI.getCurrent().addWindow(wind);
				}
			});
		}
		else
		{
			NewTaskButton.setEnabled(false);
		}
	}
	//---------------------------------------------------------------//

	//Setarisma ths sumperiforas tou ok kai tou cancel button
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
					if (((!(tf0.getValue().toString().trim().length() > 0))||(tf0.getValue().toString().equals(""))||(tf0.getValue()==null)||(sf6.getValue()==null)||(sf1.getValue()==null)||(df3.getValue()==null)))
					{
						if ((!(tf0.getValue().toString().trim().length() > 0))||(tf0.getValue().toString().equals(""))||(tf0.getValue()==null))
						{
							errorMessages.setValue("Wrong input on Name field(Name can not be empty!).");
							errorMessages.setVisible(true);
						}
						if((sf1.getValue()==null))
						{
							errorMessages.setValue("I need you to tell me the Customer!");
							errorMessages.setVisible(true);
						}
						if((sf6.getValue()==null))
						{
							errorMessages.setValue("Active field can't be empty!");
							errorMessages.setVisible(true);
						}
						if ((df3.getValue()==null))
						{
							errorMessages.setValue("I need the Start Date from you.");
							errorMessages.setVisible(true);
						}
					}
					else
					{
						if(!isfloat(tf7.getValue().toString())&&(!tf7.getValue().toString().equals(""))||tf7.getValue().toString().length()>5)
						{
							errorMessages.setValue("Budget should be a number with a max of 5 digits");
							errorMessages.setVisible(true);
						}
						else
						{
							if (!(checkdates(df3.getValue(),df4.getValue())&&checkdates(df3.getValue(),df5.getValue())&&checkdates(df5.getValue(),df4.getValue())))
							{
								if(!checkdates(df3.getValue(),df4.getValue()))
								{
									errorMessages.setValue("End date can't be before Start date.");
									errorMessages.setVisible(true);
								}
								if(!checkdates(df3.getValue(),df5.getValue()))
								{
									errorMessages.setValue("Deadline should be between start/end(check Deadline).");
									errorMessages.setVisible(true);
								}
								if(!checkdates(df5.getValue(),df4.getValue()))
								{
									errorMessages.setValue("Deadline should be between start/end(check Deadline).");
									errorMessages.setVisible(true);
								}
							}
							else
							{
								if(( Mode.equals("New Project"))||( Mode.equals("Edit")))
					        	{
									setProject();
					        	}
								//Chekare an oles oi hmeromhnies twn tasks sou einai swstes
								if (checkProjectTaskDates(project))
								{
									try
									{
										db.projectModifier(Mode, project, taskManager);
									}
									catch (SQLException e) 
									{
										ErrorWindow wind = new ErrorWindow(e); 
								        UI.getCurrent().addWindow(wind);	
								        e.printStackTrace();
									}
									project_container.removeAllItems();
									gntTraineeProjectUI.containerFiller(project_table,project_container);
									project_table.setContainerDataSource(project_container);
									close();
									
								}
								else
								{
									errorMessages.setValue("Tasks/project dates mismatch(check task/project dates).");
									errorMessages.setVisible(true);
								}
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
	
	//Arxikopoihse ton container
	//---------------------------------------------------------------//
	public void containerInitializer(Container container)
	{
		container.addContainerProperty("Description", String.class, null);
		container.addContainerProperty("Start Date",  Date.class, null);
		container.addContainerProperty("End Date",  Date.class, null); 
		container.addContainerProperty("Deadline",  Date.class, null);
		container.addContainerProperty("Active",  Image.class, null);
		container.addContainerProperty("Type",String.class, null);
		container.addContainerProperty("View",  	Button.class, null);
		container.addContainerProperty("Edit",Button.class, null);
		container.addContainerProperty("Delete",Button.class, null);
	}
	//---------------------------------------------------------------//
	
	//Sunarthsh pou genizei to container kai prosthetei plhktra diaxeirishs twn tasks
	//---------------------------------------------------------------//
	@SuppressWarnings({ "serial", "unchecked" })
	public void containerFiller(Table table ,Container container,ArrayList<Task> tasks)
	{
		
		containerInitializer(container);
		removeFilter(table);
		for(Task ts : tasks)
		{
				Object[] row=new Object[]{ts.getTask_DESC(),ts.getTask_STARDATE(),ts.getTask_ENDDATE(),ts.getTask_DEADLINE(),ts.isTask_ACTIVE(),ts.getTask_Type()};
				Object itemrow=container.addItem();
				Item newItem = container.getItem(itemrow);
				Image active = new Image(null,new ThemeResource("icons/check-24.png"));
				Image notactive = new Image(null, new ThemeResource("icons/basic1-177_checkbox_no-24.png"));
			    newItem.getItemProperty("Description").setValue(row[0]);
			    newItem.getItemProperty("Start Date").setValue(row[1]);
			    newItem.getItemProperty("End Date").setValue(row[2]);
			    newItem.getItemProperty("Deadline").setValue(row[3]);
			    if (ts.isTask_ACTIVE()) newItem.getItemProperty("Active").setValue(active);
			    else newItem.getItemProperty("Active").setValue(notactive);
			    newItem.getItemProperty("Type").setValue(row[5]);
			
			   
			    
			    
			    Button ViewButton=new Button();
				Button EditButton=new Button ();
				Button DeleteButton=new Button();
				ViewButton.setStyleName(BaseTheme.BUTTON_LINK);
				EditButton.setStyleName(BaseTheme.BUTTON_LINK);
				DeleteButton.setStyleName(BaseTheme.BUTTON_LINK);
				ViewButton.setIcon(new ThemeResource("icons/01_arrow_right-24.png"));
				EditButton.setIcon( new ThemeResource("icons/09_pencil-24.png"));
				DeleteButton.setIcon(new ThemeResource("icons/010_trash-2-24.png"));
				ViewButton.addListener(new Button.ClickListener()
				{
					@Override
					public void buttonClick(com.vaadin.ui.Button.ClickEvent event) 
					{
						ViewEditTaskWindow wind = new ViewEditTaskWindow("View",ts,false,container,table,null,db,project,thisobject); 
						wind.center();
				        UI.getCurrent().addWindow(wind);
						
					}
				});
				EditButton.addListener(new Button.ClickListener()
				{
					@Override
					public void buttonClick(com.vaadin.ui.Button.ClickEvent event)
					{
						if (editable)
						{
							ViewEditTaskWindow wind = new ViewEditTaskWindow("Edit",ts,true,container,table,taskManager,db,project,thisobject); 
							wind.center();
					        UI.getCurrent().addWindow(wind);
						}
						else
						{
							Notification.show("You are in View Mode you dont have privilege to edit or delete");
						}
						
					}
				});
				DeleteButton.addListener(new Button.ClickListener()
				{
				
					@Override
					public void buttonClick(com.vaadin.ui.Button.ClickEvent event) 
					{
						if (editable)
						{		
							ConfirmationwindowTask wind = new ConfirmationwindowTask(container,table,itemrow,ts,thisobject); 
					        UI.getCurrent().addWindow(wind);	
						}
						else
						{	
							Notification.show("You are in View Mode you dont have privilege to edit or delete");	
						}
					}
				});
				if (!editable)EditButton.setEnabled(false);
				if (!editable)DeleteButton.setEnabled(false);
				newItem.getItemProperty("View").setValue(ViewButton);
			    newItem.getItemProperty("Edit").setValue(EditButton);
			    newItem.getItemProperty("Delete").setValue( DeleteButton);
		}
		
	}
	//---------------------------------------------------------------//

	//Gemise twn pinaka apo to container
	//---------------------------------------------------------------//
	@SuppressWarnings("serial")
	private void tableFiller(Container container,Table table)
	{
		table.setWidth("100%");
		table.setHeight("100%");
		
		table.setColumnWidth("Description", 140);
		table.setColumnWidth("View", 60);
		table.setColumnWidth("Edit", 60);
		table.setColumnWidth("Delete", 65);
		table.setPageLength(table.size());
		 
		table.setContainerDataSource(container);
		table.setConverter("Start Date", new StringToDateConverter()
		{

			@Override
			public DateFormat getFormat(Locale locale)
			{
				return new SimpleDateFormat("d/M/y");
			}
			
		});
		table.setConverter("End Date", new StringToDateConverter()
		{

			@Override
			public DateFormat getFormat(Locale locale)
			{
				return new SimpleDateFormat("d/M/y");
			}
			
		});
		table.setConverter("Deadline", new StringToDateConverter()
		{
			
			@Override
			public DateFormat getFormat(Locale locale)
			{
				
				return new SimpleDateFormat("d/M/y");
				
			}
			
		});
	}
	//---------------------------------------------------------------//
	
	//Dhmiourgia ths formas kai kathorismos twn idiothtwn ths
	//---------------------------------------------------------------//
	private void formFiller(FormLayout fl)
	{
        fl.setSizeFull();

		tf0=new TextField("Name");
		tf0.setRequired(true);
		
        sf1 = new Select ("Customer");
        try 
        {
			for(String st :db.selectAllCustomers())
			{
				sf1.addItem(st);
			}
		} 
    	catch (UnsupportedOperationException | SQLException e) 
    	{
    		ErrorWindow wind = new ErrorWindow(e); 
	        UI.getCurrent().addWindow(wind);
    		e.printStackTrace();
    	}
        sf1.setRequired(true);
        
        sf2 = new Select ("Project Type");
        sf2.addItem("In house");
        sf2.addItem("Outsourcing");
        df3=new  DateField("Start Date");
        df3.setDateFormat("d-M-y");
        df3.setRequired(true);
        
        df4=new  DateField("End Date");
        df4.setDateFormat("d-M-y");
        
        df4.setRangeStart(df3.getValue());
        
        df5=new  DateField("Next DeadLine");
        df5.setDateFormat("d-M-y");
        df5.setRangeStart(df3.getValue());
        df5.setRangeEnd(df4.getValue());
        sf6 = new Select ("Active");
        sf6.addItem("yes");
        sf6.addItem("no");
        sf6.setRequired(true);
        
        tf7=new TextField("Budget(mandays)");
        
        tf8=new TextArea("Description");
        
        tf9=new TextField("Inserted By");
        
        tf10=new TextField("Inserted At");
        
        tf11=new TextField("Modified By");
        
        tf12=new TextField("Modified At");
        
        if( project.getName()!=null)tf0.setValue(project.getName());
        else tf0.setValue("");
		if(!editable)tf0.setReadOnly(true);
        fl.addComponent(tf0, 0);
        

        if(project.getCustomerID()!=-1)
			try 
        	{
				sf1.setValue(db.selectCustomerforId(project.getCustomerID()));
			}
        	catch (ReadOnlyException | SQLException e) 
        	{
        		ErrorWindow wind = new ErrorWindow(e); 
		        UI.getCurrent().addWindow(wind);
				e.printStackTrace();
			}
		else sf1.setValue("");
        if(!editable)sf1.setReadOnly(true);
        fl.addComponent(sf1, 1);
        
        
        if(project.getProjectType()!=null)sf2.setValue(project.getProjectType());
        else sf2.setValue("");
        if(!editable)sf2.setReadOnly(true);
        fl.addComponent(sf2, 2);
        
        if(project.getStartDate()!=null) df3.setValue(project.getStartDate());
        if(!editable)df3.setReadOnly(true);
        fl.addComponent(df3, 3);
        
        if(project.getEndDate()!=null) df4.setValue(project.getEndDate());
        if(!editable)df4.setReadOnly(true);
        fl.addComponent(df4, 4);
        
        if(project.getNextDeadline()!=null)df5.setValue(project.getNextDeadline());
        if(!editable)df5.setReadOnly(true);
        fl.addComponent(df5, 5);
        
        if (project.isActive())sf6.setValue("yes");
        else sf6.setValue("no");
        if(!editable)sf6.setReadOnly(true);
        fl.addComponent(sf6, 6);
        
        if(project.getBudget()!=-1.0) tf7.setValue(String.valueOf(project.getBudget()));
        else tf7.setValue("");
        if(!editable)tf7.setReadOnly(true);
        fl.addComponent(tf7, 7);
        
        if(project.getDescription()!=null)tf8.setValue(project.getDescription());
        else tf8.setValue("");
        if(!editable)tf8.setReadOnly(true);
        fl.addComponent(tf8, 8);
        
        if(project.getInserted_by()!=null)tf9.setValue(project.getInserted_by());
        else tf9.setValue("");
        tf9.setEnabled(false);
        fl.addComponent(tf9, 9);
        
        if(project.getInserted_at()!=null)tf10.setValue(project.getInserted_at().toString());
        else tf10.setValue("");
        tf10.setEnabled(false);
        fl.addComponent(tf10, 10);
        
        if(project.getModified_by()!=null)tf11.setValue(project.getModified_by());
        else tf11.setValue("");
        tf11.setEnabled(false);
        fl.addComponent(tf11, 11);
        
        if(project.getModified_at()!=null)tf12.setValue(project.getModified_at().toString());
        else tf12.setValue("");
        tf12.setEnabled(false);
        fl.addComponent(tf12, 12);
        
        
	}
	//---------------------------------------------------------------//

	//---------------------------------------------------------------//
	public Project getProject()
	{
		return project;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setProject(Project project) 
	{
		this.project = project;
	}
	//---------------------------------------------------------------//

	//---------------------------------------------------------------//
	public ProjectsDatabase getDb() 
	{
		return db;
	}
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void setDb(ProjectsDatabase db) 
	{
		this.db = db;
	}
	//---------------------------------------------------------------//
	
	//Dhmiourgia kai efarmogh filtrou ston pinaka twn tasks
	//---------------------------------------------------------------//
	@SuppressWarnings("serial")
	private void setFilter(TextField filter,Table table)
	{
		filter.setImmediate(true);
		filter.setTextChangeEventMode(TextChangeEventMode.EAGER);
		filter.addTextChangeListener(new TextChangeListener() 
		{
			Filter filter = null;
			public void textChange(TextChangeEvent event) 
			{
				String search_text = event.getText();
				Filterable f = (Filterable) table.getContainerDataSource();
				if (filter != null)
					f.removeContainerFilter(filter);
				filter = new Or(new SimpleStringFilter("Description", search_text, true,false),
						 new Or(new DateFilter("Start Date", search_text)),
						 new Or(new DateFilter("End Date", search_text)), 
						 new Or(new DateFilter("Deadline", search_text)),
						 new SimpleStringFilter("Type", search_text, true, false));
				f.addContainerFilter(filter);
			}
		});
	}
	//---------------------------------------------------------------//

	//---------------------------------------------------------------//
	 public boolean isfloat(String str) 
	 {
        try 
        {
            Float.parseFloat(str);
            return true;
        }
        catch (NumberFormatException e) 
        {
            return false;
        }
	}
	//---------------------------------------------------------------//
	
	//Boithitikh sunarthsh pou setarei to project
	//---------------------------------------------------------------//
	private void setProject()
	{
		project.setName(tf0.getValue().toString());
 		project.setDescription(tf8.getValue().toString());
 		project.setEndDate(df4.getValue());
 		if (sf6.getValue().toString().equals("yes"))project.setActive(true);
 		else project.setActive(false);
 		if (!tf7.getValue().toString().equals(""))project.setBudget(Float.parseFloat(tf7.getValue().toString()));
 		else project.setBudget(-1);
 		project.setNextDeadline(df5.getValue());
 		project.setStartDate(df3.getValue());
 		try 
 		{
 				if (sf1.getValue()!=null)
				project.setCustomerID(db.selectCustomerforName( sf1.getValue().toString()));
		}
 		catch (SQLException|java.lang.NullPointerException e) 
 		{
 			ErrorWindow wind = new ErrorWindow(e); 
 	        UI.getCurrent().addWindow(wind);		
 	        e.printStackTrace();
			}
 		project.setInserted_by("Grigoris");
 		project.setModified_by("Grigoris");
 		project.setRowversion(1);
 		if (sf2.getValue()!=null)project.setProjectType(sf2.getValue().toString());
 		else project.setProjectType(null);
	 }
	//---------------------------------------------------------------//

	//Boithitikh synarthsh pou tsekarei tis hmeromhnies
	//---------------------------------------------------------------//
	private boolean checkdates(java.util.Date date,java.util.Date date2)
	{
		if (date==null||date2==null) return true;
		if (!date.after(date2)&&!date.before(date2))return true;
		return date.before(date2);
	}
	//---------------------------------------------------------------//
	
	//Synarthsh pou tsekarei tis hmeromhnies gia ta tasks tou project
	//---------------------------------------------------------------//
	private boolean checkProjectTaskDates(Project pr)
	{
		for(Task ts:taskManager.get("toInsert"))
		{
			if(
				checkdates(ts.getTask_STARDATE(),pr.getEndDate())&&
				checkdates(pr.getStartDate(),ts.getTask_STARDATE())&&
				checkdates(ts.getTask_ENDDATE(),pr.getEndDate())&&
				checkdates(pr.getStartDate(),ts.getTask_ENDDATE()))
					continue;
			else
					return false;
		}
		for(Task ts:taskManager.get("toEdit"))
		{
			if(
				checkdates(ts.getTask_STARDATE(),pr.getEndDate())&&
				checkdates(pr.getStartDate(),ts.getTask_STARDATE())&&
				checkdates(ts.getTask_ENDDATE(),pr.getEndDate())&&
				checkdates(pr.getStartDate(),ts.getTask_ENDDATE()))
					 continue;
			else
					return false;
				
		}
			
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
		for(Task ts:tasks)
		{
			if(
				checkdates(ts.getTask_STARDATE(),pr.getEndDate())&&
				checkdates(pr.getStartDate(),ts.getTask_STARDATE())&&
				checkdates(ts.getTask_ENDDATE(),pr.getEndDate())&&
				checkdates(pr.getStartDate(),ts.getTask_ENDDATE()))
					 continue;
			else
					return false;
					
		}
		return true;
	 }
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	public void removeFilter(Table table)
	{
				filter.setValue("");
				Filterable f = (Filterable) table.getContainerDataSource();
				f.removeAllContainerFilters();
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