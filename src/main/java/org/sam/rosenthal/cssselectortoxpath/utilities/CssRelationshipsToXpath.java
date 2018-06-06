package org.sam.rosenthal.cssselectortoxpath.utilities;

import java.util.List;

import org.sam.rosenthal.cssselectortoxpath.model.CssRelationship;
import org.sam.rosenthal.cssselectortoxpath.model.CssType;

public class CssRelationshipsToXpath 
{

	
	public String cssRelationshipListsConversion(List <CssRelationship> cssRelationships) throws CssSelectorStringSplitterException
	{
		String xpath="";
		for(CssRelationship relationship:cssRelationships)
		{
			xpath+= conversion(relationship);
		}
		return xpath;
	}

	private String conversion(CssRelationship relationship) throws CssSelectorStringSplitterException 
	{
		if(relationship.getType()==null)
		{
			return "//"+relationship.getSelector();
		}
		else if(relationship.getType()==CssType.SPACE)
		{
			return "//"+relationship.getSelector();
		}
		else if(relationship.getType()==CssType.GREATER_THAN)
		{
			return "/"+relationship.getSelector();
		}
		else if(relationship.getType()==CssType.PLUS)
		{
			return "/following-sibling::*[1]/self::"+relationship.getSelector();
		}
		else if(relationship.getType()==CssType.TILDA)
		{
			return "/following-sibling::"+relationship.getSelector();
		}
		else
		{
			throw new CssSelectorStringSplitterException();
		}

	}
		
}
		
		
	
