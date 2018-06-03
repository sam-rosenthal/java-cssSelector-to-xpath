package org.sam.rosenthal.cssselectortoxpath.model;

public class CssRelationship {
	private CssType type;
	private String selector;
	public CssRelationship(CssType typeIn, String selectorIn)
	{
		this.type=typeIn;
		this.selector=selectorIn;
	}
	public CssType getType() {
		return type;
	}
	public void setType(CssType type) {
		this.type = type;
	}
	public String getSelector() {
		return selector;
	}
	public void setSelector(String selector) {
		this.selector = selector;
	}
	@Override
	public String toString()
	{
		return "(Type="+this.getType()+"; Selector="+this.getSelector()+")";
	}
	@Override
	public boolean equals(Object cssRelationship)
	{
		if(cssRelationship==null)
		{
			return false;
		}
		return this.toString().equals(cssRelationship.toString());
	}
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}
