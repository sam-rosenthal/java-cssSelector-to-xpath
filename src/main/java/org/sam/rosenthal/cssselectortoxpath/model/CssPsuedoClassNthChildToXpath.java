package org.sam.rosenthal.cssselectortoxpath.model;

public class CssPsuedoClassNthChildToXpath extends CssPsuedoClassNthToXpath{

	
	public CssPsuedoClassNthChildToXpath(boolean lastIn) {
		super(lastIn);
	}
	
	@Override
	public String getNthToXpath(String element, String parenthesisExpression)
	{
		return super.getNthToXpath("*", parenthesisExpression);
	}
	
	@Override
	protected String getNthXpathNoN(int y) {
		return "[count(preceding-sibling::*)="+(y-1)+"]";
	}

}
