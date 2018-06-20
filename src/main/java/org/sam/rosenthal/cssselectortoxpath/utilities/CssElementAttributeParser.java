package org.sam.rosenthal.cssselectortoxpath.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sam.rosenthal.cssselectortoxpath.model.CssAttribute;
import org.sam.rosenthal.cssselectortoxpath.model.CssAttributeValueType;
import org.sam.rosenthal.cssselectortoxpath.model.CssElementAttributes;


public class CssElementAttributeParser 
{
	private static final String QUOTES_RE = "([\"\'])";
	private static final String ATTRIBUTE_VALUE_RE = "([_a-zA-Z0-9- ]+)";
	private static final String ATTRIBUTE_TYPE_RE = createElementAttributeNameRegularExpression();
	private static final String ELEMENT_ATTRIBUTE_NAME_RE="(-?[_a-zA-Z]+[_a-zA-Z0-9-]*)";
	private static final String STARTING_ELEMENT_RE = "^("+ELEMENT_ATTRIBUTE_NAME_RE+"|([*]))?";
	private static final String ATTRIBUTE_RE = "(\\["+"\\s*"+ELEMENT_ATTRIBUTE_NAME_RE+"\\s*"+ATTRIBUTE_TYPE_RE+"\\s*"+"("+QUOTES_RE+ATTRIBUTE_VALUE_RE+QUOTES_RE+")?"+"\\s*"+"\\])"; 
	
	
	private static String createElementAttributeNameRegularExpression()
	{
		StringBuilder builder=new StringBuilder();
		for(CssAttributeValueType type:CssAttributeValueType.values())
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
		builder.append("))?");
		//System.out.println("elementAttributeRE="+builder);
		return builder.toString();
	}
	
	public void checkValid(String elementWithAttributesString) throws CssSelectorStringSplitterException
	{
		//System.out.println("checkValid: "+elementWithAttributesString+" ,re="+STARTING_ELEMENT_RE+ATTRIBUTE_RE+"*$");
		Pattern cssElementAtributePattern = Pattern.compile(STARTING_ELEMENT_RE+ATTRIBUTE_RE+"*$");
		Matcher match = cssElementAtributePattern.matcher(elementWithAttributesString);
		if (!match.find())
		{
			throw new CssSelectorStringSplitterException("invalid element and/or attributes");
		}
		//System.out.println();
		boolean cssAttributeValueTypeExists = match.group(6)!=null;
		boolean cssAttributeValueExists = match.group(13)!=null;
		//System.out.println("Type="+cssAttributeValueTypeExists+", Value="+cssAttributeValueExists);
		if((cssAttributeValueTypeExists&&!cssAttributeValueExists)||(!cssAttributeValueTypeExists&&cssAttributeValueExists))
		{
				throw new CssSelectorStringSplitterException("invalid attribute value");
		}

		String startQuote = match.group(14);
		String endQuote = match.group(16);
		boolean startQuoteExists=startQuote!=null;
		//note the only way startQuote could be null is that there no attribute value 
		if(startQuoteExists && !(startQuote.equals(endQuote)))
		{
			throw new CssSelectorStringSplitterException("invalid quotations");
		}
		//System.out.println("Valid: "+elementWithAttributesString);
	}
	
	public CssElementAttributes createElementAttribute(String elementWithAttributesString) throws CssSelectorStringSplitterException 
	{
		checkValid(elementWithAttributesString);
		Pattern startingCssElementAtributePattern = Pattern.compile(STARTING_ELEMENT_RE);
		Matcher match = startingCssElementAtributePattern.matcher(elementWithAttributesString);
		List<CssAttribute> attributeList=new ArrayList<CssAttribute>();
		String element=null;
		if (match.find())
		{
			String possibleElement = match.group();
			if(!possibleElement.isEmpty())
			{
				element=possibleElement;
				//System.out.println(possibleElement);
			}
		}
		Pattern restOfCssElementAtributePattern = Pattern.compile(ATTRIBUTE_RE);
		match = restOfCssElementAtributePattern.matcher(elementWithAttributesString);

		while(match.find())
		{
			attributeList.add(new CssAttribute(match.group(2),match.group(12),match.group(3)));
			//System.out.println(attributeList);
		}	
		CssElementAttributes cssElementAttribute = new CssElementAttributes(element,attributeList);
		//System.out.println(cssElementAttribute);
		return cssElementAttribute;
	}	

}
	


