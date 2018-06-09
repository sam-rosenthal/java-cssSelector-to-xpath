package org.sam.rosenthal.cssselectortoxpath.utilities;

import java.util.Iterator;
import java.util.List;

import org.sam.rosenthal.cssselectortoxpath.model.CssElementCombinatorPair;

public class CssElementCombinatorPairsToXpath 
{
	private CssSelectorStringSplitter cssSelectorString=new CssSelectorStringSplitter();
	
	
	public String cssElementCombinatorPairListConversion(List <CssElementCombinatorPair> elementCombinatorPairs) throws CssSelectorStringSplitterException
	{
		StringBuilder xpathBuilder=new StringBuilder();
		Iterator<CssElementCombinatorPair> cssElementCombinatorPairIterator=elementCombinatorPairs.iterator();
		CssElementCombinatorPair elementCombinatorPair = cssElementCombinatorPairIterator.next();
		xpathBuilder.append("//").append(elementCombinatorPair.getElement());
		while(cssElementCombinatorPairIterator.hasNext())
		{
			elementCombinatorPair = cssElementCombinatorPairIterator.next();
			xpathBuilder.append(elementCombinatorPair.getCombinatorType().getXpath()).append(elementCombinatorPair.getElement());
		}
		return xpathBuilder.toString();
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
	
	public String convertCssStringToXpathString(String selectorString) throws CssSelectorStringSplitterException
	{
		List<List<CssElementCombinatorPair>> cssElementCombinatorPairListList=cssSelectorString.listSplitSelectorsIntoElementCombinatorPairs(selectorString);
		String xpath=cssElementCombinatorPairListListConversion(cssElementCombinatorPairListList);
		return xpath;
	}
}
		
		
	
