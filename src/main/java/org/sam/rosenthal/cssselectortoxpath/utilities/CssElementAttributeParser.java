package org.sam.rosenthal.cssselectortoxpath.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sam.rosenthal.cssselectortoxpath.model.CssAttributeType;
import org.sam.rosenthal.cssselectortoxpath.model.CssCombinatorType;
import org.sam.rosenthal.cssselectortoxpath.model.CssElementAttribute;
import org.sam.rosenthal.cssselectortoxpath.model.CssElementCombinatorPair;

public class CssElementAttributeParser 
{
	private static final String ATTRIBUTE_VALUE_REGULAR_EXPRESSION = "([_a-zA-Z0-9-]+)";
	private static final String ATTRIBUTE_TYPE_REGULAR_EXPRESSION = createElementAttributeNameRegularExpression();
	private static final String ELEMENT_ATTRIBUTE_NAME_REGULAR_EXPRESSION="(-?[_a-zA-Z]+[_a-zA-Z0-9-]*)";
	private static final String STARTING_ELEMENT_REGULAR_EXPRESSION = "^("+ELEMENT_ATTRIBUTE_NAME_REGULAR_EXPRESSION+"|([*]))?";
	private static final String ATTRIBUTE_REGULAR_EXPRESSION = "(\\["+"\\s*"+ELEMENT_ATTRIBUTE_NAME_REGULAR_EXPRESSION+"\\s*"+ATTRIBUTE_TYPE_REGULAR_EXPRESSION+"\\s*"+"\""+ATTRIBUTE_VALUE_REGULAR_EXPRESSION+"\""+"\\s*"+"\\])"; 
	
	private static String createElementAttributeNameRegularExpression()
	{
		StringBuilder builder=new StringBuilder();
		for(CssAttributeType type:CssAttributeType.values())
		{
			if(builder.length()==0)
			{
				builder.append("((");
			}
			else
			{
				builder.append(")|(");
			}
			builder.append("\\").append(type.getEqualStringName());
		}
		builder.append("))");
		return builder.toString();
	}
	
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
	public void splitAttribute(String attribute) throws CssSelectorStringSplitterException
	{
		attribute=attribute.substring(1, attribute.length()-1);
		String cutter=null;
		for(CssAttributeType type:CssAttributeType.values())
		{
			int splitIndex=attribute.indexOf(type.getEqualStringName());
			if(splitIndex>-1)
			{
				System.out.println(type.getEqualStringName());
				String[] nameValue=attribute.split(type.getEqualStringName());
				String name=nameValue[0];
				System.out.println("name="+name);
				String value=nameValue[1];
				System.out.println("Value="+value);
				cutter=type.getEqualStringName();
			}
		}
	}
}
	


