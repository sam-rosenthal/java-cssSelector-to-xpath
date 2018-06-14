package org.sam.rosenthal.cssselectortoxpath.model;

public class CssAttribute 
{
	private String name;
	private String value;
	private CssAttributeValueType type;
	public CssAttribute(String nameIn,String valueIn, String typeStringIn)
	{
		this(nameIn,valueIn,CssAttributeValueType.valueTypeString(typeStringIn));
		
	}
	public CssAttribute(String nameIn,String valueIn, CssAttributeValueType typeIn)
	{
		this.name=nameIn;
		this.value=valueIn;
		this.type=typeIn;
	}
	
	@Override
	public String toString()
	{
		return "Name="+this.name+"; Value="+this.value+"; Type="+this.type;
	}
	
	@Override
	public boolean equals(Object cssAttribute)
	{
		if(cssAttribute==null)
		{
			return false;
		}
		return this.toString().equals(cssAttribute.toString());
	}
	@Override
	public int hashCode() {
		return toString().hashCode();
	}

}
