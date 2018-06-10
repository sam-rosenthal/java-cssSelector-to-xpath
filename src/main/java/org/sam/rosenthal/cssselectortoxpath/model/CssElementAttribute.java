package org.sam.rosenthal.cssselectortoxpath.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sam.rosenthal.cssselectortoxpath.utilities.CssSelectorStringSplitterException;
//Take a string and the format of the string is...
//xxx[yyy]...[zzz]
//-->break it up into a list = xxx,[yyy],..,[zzz]
//if xxx DNE==>break into list=*,[yyy],...,[zzz]
//
//create new class
//make into css element attribute
//fills this stuff,
//only has constructor and getters
public class CssElementAttribute
{
	private String elementWithAttributesString;
	private List<String> attributeList;

	public CssElementAttribute(String elementWithAttributesStringIn, List<String> attributeListIn) throws CssSelectorStringSplitterException
	{
		this.elementWithAttributesString=elementWithAttributesStringIn;
		this.attributeList=attributeListIn;
	}
	
	public String getElement() 
	{
		return elementWithAttributesString;
	}

	public List<String> getAttributeList() 
	{
		return attributeList;
	}
	
	@Override
	public String toString()
	{
		return "(ElementWithAttributesString="+this.elementWithAttributesString+"; AttributeList="+this.attributeList;
	}
	
	@Override
	public boolean equals(Object cssElementAttribute)
	{
		if(cssElementAttribute==null)
		{
			return false;
		}
		return this.toString().equals(cssElementAttribute.toString());
	}
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
}