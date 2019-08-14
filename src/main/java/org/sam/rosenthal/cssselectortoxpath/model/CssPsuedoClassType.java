package org.sam.rosenthal.cssselectortoxpath.model;

import org.sam.rosenthal.cssselectortoxpath.utilities.CssSelectorToXPathConverterInvalidFirstLastChild;

public enum CssPsuedoClassType {
	EMPTY(":empty","[not(*) and .=\"\"]"),
	ONLY_CHILD(":only-child","[count(preceding-sibling::*)=0 and count(following-sibling::*)=0]"),
	FIRST_OF_TYPE(":first-of-type","[1]"),
	LAST_OF_TYPE(":last-of-type", "[last()]"),
	FIRST_CHILD(":first-child","_FIRST_CHILD_PLACEHOLDER_"),
	LAST_CHILD(":last-child","_LAST_CHILD_PLACEHOLDER_");

	
	private String typeString;
	private String xpath;

	private CssPsuedoClassType(String typeStringIn, String xpathIn)
	{
		this.typeString=typeStringIn;
		this.xpath=xpathIn;
	}
	
	public String getPsuedoString() 
	{
		return typeString;
	}

	public String getXpath(String element)
	{
		if (xpath.equals("_FIRST_CHILD_PLACEHOLDER_"))
		{
			if (element==null || element.equals("*")) {
				return "[1]";
			}
			else {
				return "[1][../"+element+"[1]=../*[1]]";
			}
		}
		else if (xpath.equals("_LAST_CHILD_PLACEHOLDER_"))
		{
			if (element==null || element.equals("*")) {
				return "[last()]";
			}
			else {
				return "[last()][../"+element+"[last()]=../*[last()]]";
			}
		}
		return xpath;
	}
	
	public static CssPsuedoClassType psuedoClassTypeString(String unknownString, String element) throws CssSelectorToXPathConverterInvalidFirstLastChild {
		if(unknownString==null)
		{
			return null;
		}
		switch (unknownString) 
		{
			case ":empty": 
                return EMPTY;
			case ":only-child":
				return ONLY_CHILD; 
			case ":first-of-type":
				if (element==null ||element.equals("*")) {
					throw new CssSelectorToXPathConverterInvalidFirstLastChild();
				}
				else {
					return FIRST_OF_TYPE;
				}
			case ":last-of-type":
				if (element==null ||element.equals("*")) {
					throw new CssSelectorToXPathConverterInvalidFirstLastChild();
				}
				else {
					return LAST_OF_TYPE;
				}
			case ":first-child":
				return FIRST_CHILD;
			case ":last-child":
				return LAST_CHILD;
			default:
        		throw new IllegalArgumentException(unknownString);
		}
	}
}
