package com.grigoris.filters;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;

@SuppressWarnings("serial")
public class DateFilter implements Container.Filter
{
	//---------------------------------------------------------------//
	private String Regex;
	private String IDproperty;
	//---------------------------------------------------------------//

	//---------------------------------------------------------------//
	public DateFilter(String propertyId, String regex) 
	{
		this.IDproperty = propertyId;
		this.Regex = regex;
	}
	//---------------------------------------------------------------//

	//---------------------------------------------------------------//
	@Override
	public boolean appliesToProperty(Object propertyId) 
	{
		return (propertyId.equals(this.IDproperty)&&(propertyId != null));
	}
	//---------------------------------------------------------------//

	//---------------------------------------------------------------//
	@SuppressWarnings("rawtypes")
	@Override
	public boolean passesFilter(Object itemID, Item item)
	{
		Property property = item.getItemProperty(IDproperty);
		if ((property.getValue() == null )||( !property.getType().equals(java.sql.Date.class)))
			return false;
		String value;
		value = new SimpleDateFormat("d/M/y").format((Date) property.getValue());
		return value.matches("(.*)" + Regex + "(.*)");
	}
	//---------------------------------------------------------------//

}