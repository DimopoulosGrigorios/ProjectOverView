package com.grigoris.popup;

import com.grigoris.database.Task;
import com.grigoris.windows.ViewEditwindow;
import com.vaadin.data.Container;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ConfirmationwindowTask extends Window
{
	//---------------------------------------------------------------//
	private static final long serialVersionUID = 1L;
	protected ViewEditwindow viewEditWindow;
	//---------------------------------------------------------------//

	//---------------------------------------------------------------//
	public ConfirmationwindowTask(Container container, Table table, Object itemrow,
			Task ts,ViewEditwindow viewEditWindow) 
	{
		super("Delete"); 
		this.viewEditWindow=viewEditWindow;
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
 
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event)
            {
            	viewEditWindow.taskManager.get("toDelete").add(ts);
            	viewEditWindow.taskManager.get("toInsert").remove(ts);
            	viewEditWindow.taskManager.get("toEdit").remove(ts);
				container.removeItem(itemrow);
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
}
