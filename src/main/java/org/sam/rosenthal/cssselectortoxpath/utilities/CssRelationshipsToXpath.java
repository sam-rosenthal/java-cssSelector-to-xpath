package org.sam.rosenthal.cssselectortoxpath.utilities;

import java.util.Iterator;
import java.util.List;

import org.sam.rosenthal.cssselectortoxpath.model.CssRelationship;

public class CssRelationshipsToXpath 
{
	private CssSelectorStringSplitter cssSelectorString=new CssSelectorStringSplitter();
	
	
	public String cssRelationshipsListConversion(List <CssRelationship> cssRelationships) throws CssSelectorStringSplitterException
	{
		StringBuilder xpathBuilder=new StringBuilder();
		Iterator<CssRelationship> cssRelationshipIterator=cssRelationships.iterator();
		CssRelationship cssRelationship = cssRelationshipIterator.next();
		xpathBuilder.append("//").append(cssRelationship.getSelector());
		while(cssRelationshipIterator.hasNext())
		{
			cssRelationship = cssRelationshipIterator.next();
			xpathBuilder.append(cssRelationship.getType().getXpath()).append(cssRelationship.getSelector());
		}
		return xpathBuilder.toString();
	}
	
	
	public String cssRelationshipsListListConversion(List<List<CssRelationship>> cssRelationshipsListList) throws CssSelectorStringSplitterException
	{
		StringBuilder xpathBuilder=new StringBuilder();
		boolean moreThanOne=cssRelationshipsListList.size()>1;
		Iterator<List<CssRelationship>> cssRelationshipIterator=cssRelationshipsListList.iterator();
		while(cssRelationshipIterator.hasNext())
		{
			List<CssRelationship> cssRelationshipList = cssRelationshipIterator.next();
			if(moreThanOne) {
				xpathBuilder.append("(");
			}
			xpathBuilder.append(cssRelationshipsListConversion(cssRelationshipList));
			if(moreThanOne) 
			{
				xpathBuilder.append(")");
			}
			if(cssRelationshipIterator.hasNext())
			{
				xpathBuilder.append("|");
			}
		}
		return xpathBuilder.toString();
	}
	
	public String convertCssStringToXpathString(String selectorString) throws CssSelectorStringSplitterException
	{
		List<List<CssRelationship>> cssRelationshipsListList=cssSelectorString.listSplitSelectorsIntoRelationships(selectorString);
		String xpath=cssRelationshipsListListConversion(cssRelationshipsListList);
		return xpath;
	}
	
	
	

		
}
		
		
	
