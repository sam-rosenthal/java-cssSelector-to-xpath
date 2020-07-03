package org.sam.rosenthal.cssselectortoxpath.utilities;

public class CssSelectorToXpathConverterInvalidNthOfType extends CssSelectorToXPathConverterException {
	public static final String NTH_OF_TYPE_UNSUPPORTED_ERROR_FORMAT="Unable to convert. %s is an invalid argument expression for :nth-of-type()";
	private static final long serialVersionUID = 1L;
	public CssSelectorToXpathConverterInvalidNthOfType(String parenthesisExpression) {
		super(getInvalidParenthesisExpressionError(parenthesisExpression));
	}
	public static String getInvalidParenthesisExpressionError(String parenthesisExpression)
	{
		return String.format(NTH_OF_TYPE_UNSUPPORTED_ERROR_FORMAT, parenthesisExpression);
		
	}
}