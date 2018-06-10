package org.sam.rosenthal.cssselectortoxpath.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sam.rosenthal.cssselectortoxpath.model.CssElementAttribute;

public class CssElementAttributeParser 
{
	private static final String STARTING_ELEMENT_REGULAR_EXPRESSION = "^([^\\[\\]])*";
	private static final String ATTRIBUTE_REGULAR_EXPRESSION = "(\\[[^\\[\\]]+\\])";

	public void checkValid(String elementWithAttributesString) throws CssSelectorStringSplitterException
	{
		Pattern pattern1 = Pattern.compile(STARTING_ELEMENT_REGULAR_EXPRESSION+ATTRIBUTE_REGULAR_EXPRESSION+"*$");
		Matcher match = pattern1.matcher(elementWithAttributesString);
		if (match.find()==false)
		{
			throw new CssSelectorStringSplitterException("invalid elementInput");
		}
		else
		{
			System.out.println("Valid");
		}
	}
	
	public List<String> createAttributeList(String elementWithAttributesString) 
	{
		Pattern pattern2 = Pattern.compile(STARTING_ELEMENT_REGULAR_EXPRESSION);
		Matcher match = pattern2.matcher(elementWithAttributesString);
		List<String> builder=new ArrayList<String>();
		String element=null;
		if (match.find())
		{
			String possibleElement = match.group();
			if(!possibleElement.isEmpty())
			{
				element=possibleElement;
				System.out.println(possibleElement);
			}
		}
		builder.add(element);

		Pattern pattern3 = Pattern.compile(ATTRIBUTE_REGULAR_EXPRESSION);
		match = pattern3.matcher(elementWithAttributesString);
		while(match.find())
		{
			builder.add(match.group());
			System.out.println(builder);
		}	
		return builder;
	}
	
	public CssElementAttribute stringToCssElementAttribute(String elementWithAttributesString) throws CssSelectorStringSplitterException
	{
		checkValid(elementWithAttributesString);
		return new CssElementAttribute(elementWithAttributesString,createAttributeList(elementWithAttributesString));
		
	}
	

}
