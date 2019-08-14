package org.sam.rosenthal.cssselectortoxpath.utilities;

public class CssSelectorToXPathConverterInvalidFirstLastChild extends CssSelectorToXPathConverterException {

	private static final long serialVersionUID = 1L;
	public static final String FIRST_LAST_CHILD_UNSUPPORTED_ERROR_FORMAT="Unable to convert a CSS Selector with \"*\" or \"\" before psuedo class :first-child/:last-child to a XPath";
	public CssSelectorToXPathConverterInvalidFirstLastChild() {
		super(FIRST_LAST_CHILD_UNSUPPORTED_ERROR_FORMAT);
	}

}
