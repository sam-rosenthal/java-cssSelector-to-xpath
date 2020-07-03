package org.sam.rosenthal.cssselectortoxpath.model;

public class CssAttributePsuedoClass extends CssAttribute
{
	private CssPsuedoClassType psuedoClassType;
	private String element;
	private String parenthesisExpression;
	
	public CssAttributePsuedoClass(CssPsuedoClassType psuedoClassTypeIn, String elementIn, String parenthesisExpressionIn)
	{
		super(null,null,(CssAttributeValueType) null);
		psuedoClassType = psuedoClassTypeIn;
		element = elementIn;
		parenthesisExpression = parenthesisExpressionIn;
	}
	
	public String getXPath()
	{
		return psuedoClassType.getXpath(element, parenthesisExpression);

	}
	
	public CssPsuedoClassType getCssPsuedoClassType()
	{
		return psuedoClassType;
	}
	
	@Override
	public String toString()
	{
		String val = "Psuedo Class = "+ psuedoClassType;

		return val;
	}
	
	 @Override
	 public boolean equals(Object o) 
	 {
		 if(o == null)
		 {
			 return false;
		 }
		 if(!(o instanceof CssAttributePsuedoClass))
		 {
			 return false;
		 }
		 CssAttributePsuedoClass obj = (CssAttributePsuedoClass) o;
		 if(this.parenthesisExpression == null)
		 {
			 if( obj.parenthesisExpression != null)
			 {
				 return false;
			 }
		 }
		 else if(!this.parenthesisExpression.equals(obj.parenthesisExpression))
		 {
			 return false;
		 }
	    return this.psuedoClassType.equals(obj.psuedoClassType);
	}
	
}
