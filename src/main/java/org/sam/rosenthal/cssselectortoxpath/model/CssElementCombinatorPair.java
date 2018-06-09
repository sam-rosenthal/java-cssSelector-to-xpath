package org.sam.rosenthal.cssselectortoxpath.model;

public class CssElementCombinatorPair {
	private CssCombinatorType combinatorType;
	private String element;
	public CssElementCombinatorPair(CssCombinatorType combinatorTypeIn, String elementIn)
	{
		this.combinatorType=combinatorTypeIn;
		this.element=elementIn;
	}
	public CssCombinatorType getCombinatorType() {
		return combinatorType;
	}
	public String getElement() {
		return element;
	}
	@Override
	public String toString()
	{
		return "(Combinator="+this.getCombinatorType()+"; Element="+this.getElement()+")";
	}
	@Override
	public boolean equals(Object cssElementCombinatorPair)
	{
		if(cssElementCombinatorPair==null)
		{
			return false;
		}
		return this.toString().equals(cssElementCombinatorPair.toString());
	}
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}
