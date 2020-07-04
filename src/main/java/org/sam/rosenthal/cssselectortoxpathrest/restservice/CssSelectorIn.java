package org.sam.rosenthal.cssselectortoxpathrest.restservice;

public class CssSelectorIn {
	private String cssSelector;

	public CssSelectorIn()
	{
		//Empty
	}
	
	public CssSelectorIn(String cssSelectorIn)
	{
		this();
		cssSelector = cssSelectorIn;
	}
	
	public String getCssSelector() {
		return cssSelector;
	}

	public void setCssSelector(String cssSelector) {
		this.cssSelector = cssSelector;
	}
	
	
}
