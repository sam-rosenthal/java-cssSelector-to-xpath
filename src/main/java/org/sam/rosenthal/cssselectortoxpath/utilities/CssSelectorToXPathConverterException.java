package org.sam.rosenthal.cssselectortoxpath.utilities;

public class CssSelectorToXPathConverterException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String message;
	public CssSelectorToXPathConverterException() {
		super();
	}
	
	public CssSelectorToXPathConverterException(String errorMessageIn) {
		super(errorMessageIn);
		message = errorMessageIn;
	}
	
	public CssSelectorToXPathConverterException(String errorMessageIn, Throwable cause) {
		super(errorMessageIn, cause);
		message = errorMessageIn;
	}
	@Override
	public String getMessage()
	{
		System.out.println(message);
		if(message!=null)
		{
			return message;
		}
		else
		{
			return super.getMessage();
		}
	}
}
