package org.sam.rosenthal.cssselectortoxpath.model;

public enum CssPsuedoClassType {
	EMPTY(":empty","[not(*) and .=\"\"]");
	
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
	
	public String getXpath()
	{
		return xpath;
	}
	public static CssPsuedoClassType psuedoClassTypeString(String unknownString) {
		if(unknownString==null)
		{
			return null;
		}

		switch (unknownString) 
		{
			case ":empty": 
                 return EMPTY;
        	default:
        		throw new IllegalArgumentException(unknownString);
		}
	}

}
