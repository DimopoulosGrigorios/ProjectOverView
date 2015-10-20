package com.grigoris.windows;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.annotation.WebServlet;

import com.grigoris.database.Project;
import com.grigoris.database.ProjectsDatabase;
import com.grigoris.filters.DateFilter;
import com.grigoris.popup.ConfirmationwindowProject;
import com.grigoris.popup.ErrorWindow;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.converter.StringToDateConverter;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.AlignmentInfo.Bits;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.themes.BaseTheme;

@SuppressWarnings("serial")
@Theme("gnt_trainee_project")
public class GntTraineeProjectUI extends UI 
{
	//Auth h klasi apotelei to main UI ths efarmoghs
	//Edw arxikopoietai o pinakas twn project kai episeis gemizete me dedomena apo thn bash
	//---------------------------------------------------------------//
	public ProjectsDatabase db;
	private Container container;
	private Table table;
	private  TextField filter;
	protected GntTraineeProjectUI thisobject=this;
	//---------------------------------------------------------------//
	
	//---------------------------------------------------------------//
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = GntTraineeProjectUI.class)
	public static class Servlet extends VaadinServlet 
	{
	}
	//---------------------------------------------------------------//

	//Synarthsh UI pou kanei initialize to grafiko periballon
	//Auth einai prwth synarthsh pou kaleitai kai pernoume instance ths bashs gia prwth fora
	//---------------------------------------------------------------//
	@SuppressWarnings("deprecation")
	@Override
	protected void init(VaadinRequest request) 
	{
		db=new ProjectsDatabase();
		final VerticalLayout layout = new VerticalLayout();
		
		GridLayout gridsearch=new GridLayout(120,1);
		gridsearch.setSizeFull();
	
		GridLayout gridbutton=new GridLayout(1,2);
		gridbutton.setSizeFull();
		
		VerticalSplitPanel vsplit=new VerticalSplitPanel(); 
		vsplit.setSplitPosition(700, Sizeable.UNITS_PIXELS);
		vsplit.setLocked(true);
	    vsplit.setSizeFull();
	    
		VerticalSplitPanel vsplitmain=new VerticalSplitPanel(); 
		vsplitmain.setSplitPosition(64, Sizeable.UNITS_PIXELS);
		vsplitmain.setLocked(true);
	    vsplitmain.setSizeFull();
	    
	    VerticalLayout tablevertical=new VerticalLayout();
	    tablevertical.setSizeFull();
		
		//---Arxikopohsh/Gemisma Container&Table---//
		container = new IndexedContainer();
		table = new Table("Projects");
		filter = new TextField("Search here.");
		containerFiller(table,container);
		tableFiller(container,table);
		//---Arxikopohsh/Gemisma Container&Table---//
		
		Button NewProjectButton=new Button("New Project");
		newProjectButtonHandler( NewProjectButton);
		gridbutton.addComponent(NewProjectButton, 0, 0);
		gridbutton.setMargin(true);
		
		
		
		setFilter(filter,table);


		gridsearch.addComponent(filter, 119, 0);
		gridsearch.setComponentAlignment(filter,new Alignment(Bits.ALIGNMENT_LEFT |Bits.ALIGNMENT_TOP));
		
		tablevertical.addComponent(table);
		tablevertical.setMargin(true);
		vsplit.addComponent(tablevertical);
		vsplit.addComponent(gridbutton);
		vsplitmain.addComponent(gridsearch);
		vsplitmain.addComponent(vsplit);
		layout.addComponent(vsplitmain);
		setContent(vsplitmain);
	}
	//---------------------------------------------------------------//

	//Sunarthsh pou arxikopoiei to container
	//---------------------------------------------------------------//
	public void containerInitializer(Container containerf)
	{
		containerf.addContainerProperty("Name", String.class, null);
		containerf.addContainerProperty("Customer",  String.class, null);
		containerf.addContainerProperty("Project Type",  String.class, null);
		containerf.addContainerProperty("Start Date",  Date.class, null);
		containerf.addContainerProperty("End Date",  Date.class, null); 
		containerf.addContainerProperty("Next Deadline",  Date.class, null);
		containerf.addContainerProperty("Active",  Image.class, null);
		containerf.addContainerProperty("Budget(mandays)",Float.class, null);
		containerf.addContainerProperty("View",  	Button.class, null);
		containerf.addContainerProperty("Edit",Button.class, null);
		containerf.addContainerProperty("Delete",Button.class, null);
	}
	//---------------------------------------------------------------//	

	//Synarthsh pou gemizei to container apo thn bash kai dhmiourgei plhktra diadrashs ston pinaka
	//---------------------------------------------------------------//	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void containerFiller(Table tablef,Container containerf)
	{
		ArrayList<Project> projects=null;
		try 
		{
			projects=db.selectAllProjects();
		}
		catch (SQLException e1) 
		{
			ErrorWindow wind = new ErrorWindow(e1); 
	        UI.getCurrent().addWindow(wind);
			e1.printStackTrace();
		}
		containerInitializer(containerf);
		removeFilter(tablef);
		if (projects!=null)
		{
			for(Project pr : projects)
			{
				/*String customer="";
				try 
				{
					customer=db.selectCustomerforId(pr.getCustomerID());
				}
				catch (SQLException e) 
				{
					ErrorWindow wind = new ErrorWindow(e); 
			        UI.getCurrent().addWindow(wind);
					e.printStackTrace();
				}*/
				Object[] row=new Object[]{pr.getName(),pr.getCustomeName(),pr.getProjectType(),pr.getStartDate(),pr.getEndDate(),pr.getNextDeadline(),(Boolean)pr.isActive(),(Float)pr.getBudget()};
				Object item_row=containerf.addItem();
				Image active = new Image(null,new ThemeResource("icons/check-24.png"));
				Image notactive = new Image(null, new ThemeResource("icons/basic1-177_checkbox_no-24.png"));
				Item newItem = containerf.getItem(item_row);
				newItem.getItemProperty("Name").setValue(row[0]);
			    newItem.getItemProperty("Customer").setValue(row[1]);
			    newItem.getItemProperty("Project Type").setValue(row[2]);
			    newItem.getItemProperty("Start Date").setValue(row[3]);
			    newItem.getItemProperty("End Date").setValue(row[4]);
			    newItem.getItemProperty("Next Deadline").setValue(row[5]);
			    if (pr.isActive()) newItem.getItemProperty("Active").setValue(active);
			    else newItem.getItemProperty("Active").setValue(notactive);
			    newItem.getItemProperty("Budget(mandays)").setValue(row[7]);
			   
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
					public void buttonClick(ClickEvent event)
					{
						ViewEditwindow wind = new ViewEditwindow("View",pr,db,false,containerf,tablef,thisobject);
						wind.setWidth(1200, Sizeable.UNITS_PIXELS);
						wind.setHeight(900, Sizeable.UNITS_PIXELS);
						 wind.center();
						UI.getCurrent().addWindow(wind);
					}
				});
				EditButton.addListener(new Button.ClickListener()
				{
					@Override
					public void buttonClick(ClickEvent event)
					{
						ViewEditwindow wind = new ViewEditwindow("Edit",pr,db,true,containerf,tablef,thisobject);
						wind.setWidth(1200, Sizeable.UNITS_PIXELS);
						wind.setHeight(900, Sizeable.UNITS_PIXELS);
						wind.center();
						UI.getCurrent().addWindow(wind);
					}
				});
				DeleteButton.addListener(new Button.ClickListener()
				{
					@Override
					public void buttonClick(ClickEvent event)
					{
						ConfirmationwindowProject wind = new ConfirmationwindowProject(containerf,tablef,item_row,pr,thisobject); 
				        UI.getCurrent().addWindow(wind);	
					}
				});
				newItem.getItemProperty("View").setValue(ViewButton);
			    newItem.getItemProperty("Edit").setValue(EditButton);
			    newItem.getItemProperty("Delete").setValue( DeleteButton);
			}
		}
	}
	//---------------------------------------------------------------//

	//Gemizei ton pinaka apo to  container pou tha ths dwthei kai thetei idiothtes gia ton pinaka
	//---------------------------------------------------------------//
	private void tableFiller(Container container ,Table table)
	{
		table.setSizeFull();
		table.setColumnWidth("Name", 240);
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
		table.setConverter("Next Deadline", new StringToDateConverter()
		{
			@Override
			public DateFormat getFormat(Locale locale)
			{
				return new SimpleDateFormat("d/M/y");
			}

		});
	}
	//---------------------------------------------------------------//
	
	//Orismous sumperiforas tou koumpiou neou project
	//---------------------------------------------------------------//
	@SuppressWarnings("deprecation")
	private void newProjectButtonHandler(Button NewProjectButton)
	{
		NewProjectButton.addListener(new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				Project emptyproject=new Project(-1,null,-1,"",null, null,
	        			null,false, (float) -1.0,
	        			"", null,"",null,"", -1, -1,"");
				 ViewEditwindow wind=new ViewEditwindow("New Project",emptyproject,db,true,container,table,thisobject);
				 wind.center();
				wind.setWidth(1200, Sizeable.UNITS_PIXELS);
				wind.setHeight(900, Sizeable.UNITS_PIXELS);
			     UI.getCurrent().addWindow(wind);
			}
		});
	}
	//---------------------------------------------------------------//
	
	//Orismou filtrou epanw ston pinaka
	//---------------------------------------------------------------//
	private void setFilter(TextField filter,Table table)
	{
		filter.setImmediate(true);
		filter.setTextChangeEventMode(TextChangeEventMode.EAGER);
		filter.addTextChangeListener(new TextChangeListener() 
		{
			Filter filter = null;
			public void textChange(TextChangeEvent event) 
			{
				// Assuming that the value type is a String
				String search_text = event.getText();
				Filterable f = (Filterable) table.getContainerDataSource();
				// Remove old filter
				if (filter != null)
					f.removeContainerFilter(filter);
				filter = new Or(new SimpleStringFilter("Name", search_text, true,false),
						 new Or(new SimpleStringFilter("Customer",search_text, true, false)),
						 new Or(new SimpleStringFilter("Project Type", search_text, true, false)),
						 new Or(new DateFilter("Start Date", search_text)),
						 new Or(new DateFilter("End Date", search_text)), 
						 new Or(new DateFilter("Next Deadline", search_text)),
						 new SimpleStringFilter("Budget(mandays)", search_text, true, false));
				f.addContainerFilter(filter);
			}
		});
		
	}
	//---------------------------------------------------------------//

	//---------------------------------------------------------------//
	public  void removeFilter(Table table)
	{
				filter.setValue("");
				Filterable f = (Filterable) table.getContainerDataSource();
				f.removeAllContainerFilters();
	}
	//---------------------------------------------------------------//
}
