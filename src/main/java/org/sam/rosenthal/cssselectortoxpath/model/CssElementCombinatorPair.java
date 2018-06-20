package org.sam.rosenthal.cssselectortoxpath.model;

import org.sam.rosenthal.cssselectortoxpath.utilities.CssElementAttributeParser;
import org.sam.rosenthal.cssselectortoxpath.utilities.CssSelectorStringSplitterException;

public class CssElementCombinatorPair {
	private CssCombinatorType combinatorType;
	private CssElementAttributes cssElementAttributes;
	
	public CssElementCombinatorPair(CssCombinatorType combinatorTypeIn, String cssElementAttributesStringIn) throws CssSelectorStringSplitterException
	{
		this.combinatorType=combinatorTypeIn;
		this.cssElementAttributes=new CssElementAttributeParser().createElementAttribute(cssElementAttributesStringIn);
	}
	public CssCombinatorType getCombinatorType() {
		return combinatorType;
	}
	public CssElementAttributes getCssElementAttributes() {
		return cssElementAttributes;
	}
	
	@Override
	public String toString()
	{
		return "(Combinator="+this.getCombinatorType()+", "+this.cssElementAttributes+")";
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
