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
	private static final String ATTRIBUTE_VALUE_RE_NO_SPACES = "([_a-zA-Z0-9-]+)";
	private static final String ATTRIBUTE_TYPE_RE = createElementAttributeNameRegularExpression();
	private static final String ELEMENT_ATTRIBUTE_NAME_RE="(-?[_a-zA-Z]+[_a-zA-Z0-9-]*)";
	private static final String STARTING_ELEMENT_RE = "^("+ELEMENT_ATTRIBUTE_NAME_RE+"|([*]))?";
	private static final String PSUEDO_RE = "(:[a-z]+([(][^)]+[)])?)";
	private static final String ATTRIBUTE_RE = "("+PSUEDO_RE+"|(\\["+"\\s*"+ELEMENT_ATTRIBUTE_NAME_RE+"\\s*"+ATTRIBUTE_TYPE_RE+"\\s*"+"(("+QUOTES_RE+ATTRIBUTE_VALUE_RE+QUOTES_RE+")|("+ATTRIBUTE_VALUE_RE_NO_SPACES+"))?"+"\\s*"+"\\]))"; 
	
	
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
	
	public void checkValid(String elementWithAttributesString) throws CssSelectorToXPathConverterException
	{
		int reIndexAttributeValueType=9;
		int reIndexAttributeValue=16;
		int reIndexStartingQuote=reIndexAttributeValue+2;
		int reIndexEndingQuote=reIndexStartingQuote+2;
		
		//System.out.println("checkValid: "+elementWithAttributesString+" ,re="+STARTING_ELEMENT_RE+ATTRIBUTE_RE+"*$");
		Pattern cssElementAtributePattern = Pattern.compile(STARTING_ELEMENT_RE+ATTRIBUTE_RE+"*$");
		Matcher match = cssElementAtributePattern.matcher(elementWithAttributesString);
		if (!match.find())
		{
			throw new CssSelectorToXPathConverterException("invalid element and/or attributes");
		}
		//System.out.println();
		boolean cssAttributeValueTypeExists = match.group(reIndexAttributeValueType)!=null;
		boolean cssAttributeValueExists = match.group(reIndexAttributeValue)!=null;
		//System.out.println("Type="+cssAttributeValueTypeExists+", Value="+cssAttributeValueExists);
		if((cssAttributeValueTypeExists&&!cssAttributeValueExists)||(!cssAttributeValueTypeExists&&cssAttributeValueExists))
		{
				throw new CssSelectorToXPathConverterException("invalid attribute value");
		}

		String startQuote = match.group(reIndexStartingQuote);
		String endQuote = match.group(reIndexEndingQuote);
		boolean startQuoteExists=startQuote!=null;
		//note the only way startQuote could be null is that there no attribute value 
		if(startQuoteExists && !(startQuote.equals(endQuote)))
		{
			throw new CssSelectorToXPathConverterException("invalid quotations");
		}
		//System.out.println("Valid: "+elementWithAttributesString);
	}
	
	public CssElementAttributes createElementAttribute(String elementWithAttributesString) throws CssSelectorToXPathConverterException 
	{
		int rePseudoClass=2;
		int reIndexAttributeName=5;
		int reIndexAttributeType=reIndexAttributeName+1;
		int reIndexAttributeValueWithQuotes=14;
		int reIndexAttributeValueWithinQuotes=reIndexAttributeValueWithQuotes+2;
		int reIndexAttributeValueWithoutQuotes=reIndexAttributeValueWithinQuotes+2;
		
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
		//System.out.println(ATTRIBUTE_RE);
		match = restOfCssElementAtributePattern.matcher(elementWithAttributesString);
		


		while(match.find())
		{
			String psuedoClass=match.group(rePseudoClass);
			if(psuedoClass!=null)
			{
				throw new CssSelectorToXPathConverterException("Unable to convert("+psuedoClass+").  A converter for CSS Seletor Psuedo-Classes has not been implement at this time.  TODO: A future capability.");
			}
			boolean attributeValueHasQuotes = match.group(reIndexAttributeValueWithQuotes)!=null;
			attributeList.add(new CssAttribute(
					match.group(reIndexAttributeName),
					match.group(attributeValueHasQuotes?reIndexAttributeValueWithinQuotes:reIndexAttributeValueWithoutQuotes),
					match.group(reIndexAttributeType)));
		}	
		
		CssElementAttributes cssElementAttribute = new CssElementAttributes(element,attributeList);
		//System.out.println(cssElementAttribute);
		return cssElementAttribute;
	}	

}
	


