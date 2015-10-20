package com.grigoris.popup;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;


public class ErrorWindow extends Window
{
	//---------------------------------------------------------------//
	private static final long serialVersionUID = 1L;
	//---------------------------------------------------------------//

	//---------------------------------------------------------------//
	public ErrorWindow(Exception e) 
	{
		 super("Error"); 
	     center();
        VerticalLayout content = new VerticalLayout();
        HorizontalLayout hcontent = new   HorizontalLayout();
        HorizontalLayout hcontent2 = new HorizontalLayout();
        HorizontalLayout hcontent3 = new HorizontalLayout();
        HorizontalLayout hcontent4 = new HorizontalLayout();
        Image image = new Image(null, new ThemeResource("icons/Alert_exclamation_exclamation_mark_mark-64.png"));
        hcontent2.addComponent(new Label("Error:"+e.getMessage()));
        hcontent2.addComponent(image);
        content.setMargin(true);
        setContent(content);
        
        setClosable(false);
        setResizable(false);

        Button ok = new Button("Ok");
        ok.addClickListener(new ClickListener() 
        {
			private static final long serialVersionUID = -1586082162995820681L;

			public void buttonClick(ClickEvent event)
            {
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
