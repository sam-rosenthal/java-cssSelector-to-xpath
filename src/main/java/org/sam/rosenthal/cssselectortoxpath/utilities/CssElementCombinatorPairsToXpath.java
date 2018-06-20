package org.sam.rosenthal.cssselectortoxpath.utilities;

import java.util.Iterator;
import java.util.List;

import org.sam.rosenthal.cssselectortoxpath.model.CssAttribute;
import org.sam.rosenthal.cssselectortoxpath.model.CssAttributeValueType;
import org.sam.rosenthal.cssselectortoxpath.model.CssElementCombinatorPair;

public class CssElementCombinatorPairsToXpath 
{
	private CssSelectorStringSplitter cssSelectorString=new CssSelectorStringSplitter();
	
	public String cssElementCombinatorPairListConversion(List <CssElementCombinatorPair> elementCombinatorPairs) throws CssSelectorStringSplitterException
	{
		StringBuilder xpathBuilder=new StringBuilder();
		
		xpathBuilder.append("//");
		boolean firstTime=true;
		for(Iterator<CssElementCombinatorPair> cssElementCombinatorPairIterator=elementCombinatorPairs.iterator();
			cssElementCombinatorPairIterator.hasNext();)
		{
			CssElementCombinatorPair elementCombinatorPair = cssElementCombinatorPairIterator.next();
			if(firstTime)
			{
				firstTime=false;
			}
			else
			{
				xpathBuilder.append(elementCombinatorPair.getCombinatorType().getXpath());
			}
			addElementToXpathString(xpathBuilder, elementCombinatorPair);
			convertCssAttributeListToXpath(xpathBuilder,elementCombinatorPair);
		}
		return xpathBuilder.toString();
	}

	private void addElementToXpathString(StringBuilder xpathBuilder, CssElementCombinatorPair elementCombinatorPair) {
		String element=elementCombinatorPair.getCssElementAttributes().getElement();
		if(element!=null)
		{
			xpathBuilder.append(element);
		}
		else
		{
			xpathBuilder.append("*");
		}
	}
	
	private void convertCssAttributeListToXpath(StringBuilder xpathBuilder, CssElementCombinatorPair elementCombinatorPair)
	{
		List<CssAttribute> cssAttributeList = elementCombinatorPair.getCssElementAttributes().getCssAttributeList();
		//starts-with(@href, "abc")
		for(CssAttribute cssAttribute: cssAttributeList)
		{
			String name = cssAttribute.getName();
			String value = cssAttribute.getValue();
			if(cssAttribute.getType()==CssAttributeValueType.EQUAL)
			{
				xpathBuilder.append("[");
				exactMatchXpath(xpathBuilder, name, value);
			}
			else if(cssAttribute.getType()==CssAttributeValueType.CARROT_EQUAL)
			{
				xpathBuilder.append("[starts-with(@");
				xpathBuilder.append(name);
				xpathBuilder.append(",\"");
				xpathBuilder.append(value);
				xpathBuilder.append("\")]");
			}
			else if(cssAttribute.getType()==CssAttributeValueType.DOLLAR_SIGN_EQUAL)
			{
				//TODO: implement this when we implement xpath 2.0
//				xpathBuilder.append("[ends-with(@");
//				xpathBuilder.append(cssAttribute.getName());
//				xpathBuilder.append(",\"");
//				xpathBuilder.append(cssAttribute.getValue());
//				xpathBuilder.append("\")]");
				
				xpathBuilder.append("[substring(@");
				xpathBuilder.append(name);
				xpathBuilder.append(",string-length(@");
				xpathBuilder.append(name);
				xpathBuilder.append(")-string-length(\"");
				xpathBuilder.append(value);
				xpathBuilder.append("\")+1)=\"");
				xpathBuilder.append(value);
				xpathBuilder.append("\"]");
			}
			else if(cssAttribute.getType()==CssAttributeValueType.STAR_EQUAL)
			{
				xpathBuilder.append("[contains(@");
				xpathBuilder.append(name);
				xpathBuilder.append(",\"");
				xpathBuilder.append(value);
				xpathBuilder.append("\")]");
			}
			else if(cssAttribute.getType()==CssAttributeValueType.TILDA_EQUAL)
			{
				xpathBuilder.append("[contains(concat(\" \",normalize-space(@");
				xpathBuilder.append(name);
				xpathBuilder.append("),\" \"),\" ");
				xpathBuilder.append(value);
				xpathBuilder.append(" \")]");
			}
			else if(cssAttribute.getType()==CssAttributeValueType.PIPE_EQUAL)
			{
				xpathBuilder.append("[starts-with(@");
				xpathBuilder.append(name);
				xpathBuilder.append(",concat(\"");
				xpathBuilder.append(value);
				xpathBuilder.append("\",\"-\")) or ");
				exactMatchXpath(xpathBuilder, name, value);
			}
			else if(cssAttribute.getType()==null)
			{
				xpathBuilder.append("[@");
				xpathBuilder.append(name);
				xpathBuilder.append("]");
			}
		}
	}

	private void exactMatchXpath(StringBuilder xpathBuilder, String name, String value) {
		xpathBuilder.append("@");
		xpathBuilder.append(name);
		xpathBuilder.append("=\"");
		xpathBuilder.append(value);
		xpathBuilder.append("\"]");
	}
	
	public String cssElementCombinatorPairListListConversion(List<List<CssElementCombinatorPair>> cssElementCombinatorPairListList) throws CssSelectorStringSplitterException
	{
		StringBuilder xpathBuilder=new StringBuilder();
		boolean moreThanOne=cssElementCombinatorPairListList.size()>1;
		Iterator<List<CssElementCombinatorPair>> cssElementCombinatorPairIterator=cssElementCombinatorPairListList.iterator();
		while(cssElementCombinatorPairIterator.hasNext())
		{
			List<CssElementCombinatorPair> cssElementCombinatorPairList = cssElementCombinatorPairIterator.next();
			if(moreThanOne) {
				xpathBuilder.append("(");
			}
			xpathBuilder.append(cssElementCombinatorPairListConversion(cssElementCombinatorPairList));
			if(moreThanOne) 
			{
				xpathBuilder.append(")");
			}
			if(cssElementCombinatorPairIterator.hasNext())
			{
				xpathBuilder.append("|");
			}
		}
		return xpathBuilder.toString();
	}
	
	public String convertCssSelectorStringToXpathString(String selectorString) throws CssSelectorStringSplitterException
	{
		List<List<CssElementCombinatorPair>> cssElementCombinatorPairListList=cssSelectorString.listSplitSelectorsIntoElementCombinatorPairs(selectorString);
		String xpath=cssElementCombinatorPairListListConversion(cssElementCombinatorPairListList);
		System.out.println("CSS Selector="+selectorString+", Xpath string="+xpath);

		return xpath;
	}	
}
		
		
	
