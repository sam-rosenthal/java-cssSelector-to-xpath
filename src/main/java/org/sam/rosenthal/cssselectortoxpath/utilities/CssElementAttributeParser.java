package org.sam.rosenthal.cssselectortoxpath.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sam.rosenthal.cssselectortoxpath.model.CssElementAttribute;

public class CssElementAttributeParser 
{
	private static final String STARTING_ELEMENT_REGULAR_EXPRESSION = "^((-?[_a-zA-Z]+[_a-zA-Z0-9-]*)|([*]))?";
	private static final String ATTRIBUTE_REGULAR_EXPRESSION = "(\\[[^\\[\\]]+\\])";

	public void checkValid(String elementWithAttributesString) throws CssSelectorStringSplitterException
	{
		Pattern cssElementAtributePattern = Pattern.compile(STARTING_ELEMENT_REGULAR_EXPRESSION+ATTRIBUTE_REGULAR_EXPRESSION+"*$");
		Matcher match = cssElementAtributePattern.matcher(elementWithAttributesString);
		if (!match.find())
		{
			throw new CssSelectorStringSplitterException("invalid elementWithAttributesStringInput");
		}
		else
		{
			System.out.println("Valid");
		}
	}
	
	public CssElementAttribute createElementAttribute(String elementWithAttributesString) throws CssSelectorStringSplitterException 
	{
		checkValid(elementWithAttributesString);
		Pattern startingCssElementAtributePattern = Pattern.compile(STARTING_ELEMENT_REGULAR_EXPRESSION);
		Matcher match = startingCssElementAtributePattern.matcher(elementWithAttributesString);
		List<String> attributeList=new ArrayList<String>();
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
		Pattern restOfCssElementAtributePattern = Pattern.compile(ATTRIBUTE_REGULAR_EXPRESSION);
		match = restOfCssElementAtributePattern.matcher(elementWithAttributesString);
		while(match.find())
		{
			attributeList.add(match.group());
			System.out.println(attributeList);
		}	
		CssElementAttribute cssElementAttribute = new CssElementAttribute(element,attributeList);
		System.out.println(cssElementAttribute);
		return cssElementAttribute;
	}		
}
	


