package com.grigoris.popup;

import java.sql.SQLException;

import com.grigoris.database.Project;
import com.grigoris.windows.GntTraineeProjectUI;
import com.vaadin.data.Container;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ConfirmationwindowProject extends Window
{
	//---------------------------------------------------------------//
	private static final long serialVersionUID = 1L;
	private GntTraineeProjectUI gntTraineeProjectUI;
	//---------------------------------------------------------------//

	//---------------------------------------------------------------//
	public ConfirmationwindowProject(Container container, Table table, Object item_row,Project pr,GntTraineeProjectUI gntTraineeProjectUI)
	{
		super("Delete"); 
		this.setGntTraineeProjectUI(gntTraineeProjectUI);
	    center();
        VerticalLayout content = new VerticalLayout();
        HorizontalLayout hcontent = new   HorizontalLayout();
        HorizontalLayout hcontent2 = new HorizontalLayout();
        HorizontalLayout hcontent3 = new HorizontalLayout();
        HorizontalLayout hcontent4 = new HorizontalLayout();
        Image image = new Image(null, new ThemeResource("icons/Ui-11-64.png"));
        hcontent2.addComponent(new Label("Are you sure you want to complete the delete?"));
        hcontent2.addComponent(image);
        content.setMargin(true);
        setContent(content);
        setClosable(false);
        setResizable(false);

        Button ok = new Button("Delete");
        ok.addClickListener(new ClickListener() 
        {
			private static final long serialVersionUID = 1637616214898350897L;

			public void buttonClick(ClickEvent event)
            {
            	try
            	{
					gntTraineeProjectUI.db.deleteProject(pr);
				}
            	catch (SQLException e)
            	{
            		ErrorWindow wind = new ErrorWindow(e); 
			        UI.getCurrent().addWindow(wind);
					e.printStackTrace();
				}
				container.removeItem(item_row);
				table.setContainerDataSource(container);
                close(); 
            }
        });
        Button canchel = new Button("Cancel");
        canchel.addClickListener(new ClickListener() 
        {
         
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) 
			{
                close(); 
            }
        });
        
        hcontent3.addComponent(canchel);
        hcontent3.setMargin(true);
        hcontent4.addComponent(ok);
        hcontent4.setMargin(true);
        hcontent.addComponent(hcontent4);
        hcontent.addComponent(hcontent3);
        content.addComponent(hcontent2);	
        content.addComponent(hcontent);	
	}
	//---------------------------------------------------------------//

	//---------------------------------------------------------------//
	public GntTraineeProjectUI getGntTraineeProjectUI()
	{
		return gntTraineeProjectUI;
	}
	//---------------------------------------------------------------//

	//---------------------------------------------------------------//
	public void setGntTraineeProjectUI(GntTraineeProjectUI gntTraineeProjectUI) 
	{
		this.gntTraineeProjectUI = gntTraineeProjectUI;
	}
	//---------------------------------------------------------------//


}
