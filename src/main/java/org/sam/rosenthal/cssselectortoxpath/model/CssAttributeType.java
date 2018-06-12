package org.sam.rosenthal.cssselectortoxpath.model;

public enum CssAttributeType 
{
	EQUAL("="), 
	TILDA_EQUAL("~="),
	PIPE_EQUAL("|="),
	CARROT_EQUAL("^="),
	MONEY_EQUAL("$="),
	STAR_EQUAL("*=");
	
	private String equalString;

	private CssAttributeType(String nameIn)
	{
		this.equalString=nameIn;
	}

	public String getEqualStringName() {
		return equalString;
	}
}
