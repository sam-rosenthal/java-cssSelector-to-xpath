package org.sam.rosenthal.cssselectortoxpath.model;

public class CssAttributePsuedoClass extends CssAttribute
{
	private CssPsuedoClassType psuedoClassType;
	private String element;
	
	public CssAttributePsuedoClass(CssPsuedoClassType psuedoClassTypeIn, String elementIn)
	{
		super(null,null,(String) null);
		psuedoClassType = psuedoClassTypeIn;
		element = elementIn;
	}
	
	public String getXPath()
	{
		return psuedoClassType.getXpath(element);
	}
	
	@Override
	public String toString()
	{
		return "Psuedo Class = "+ psuedoClassType;
	}
	
}
