package org.sam.rosenthal.cssselectortoxpath.model;

public class CssAttributePsuedoClass extends CssAttribute
{
	private CssPsuedoClassType psuedoClassType;

	public CssAttributePsuedoClass(CssPsuedoClassType psuedoClassTypeIn)
	{
		super(null,null,(String) null);
		psuedoClassType = psuedoClassTypeIn;
	}
	public String getXPath()
	{
		return psuedoClassType.getXpath();
	}
	
	@Override
	public String toString()
	{
		return "Psuedo Class = "+ psuedoClassType;
	}
	
}
