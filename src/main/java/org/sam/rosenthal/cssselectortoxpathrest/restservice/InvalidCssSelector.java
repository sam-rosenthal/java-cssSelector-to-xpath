package org.sam.rosenthal.cssselectortoxpathrest.restservice;

import org.sam.rosenthal.cssselectortoxpath.utilities.CssSelectorToXPathConverterException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidCssSelector{

	private String message;
	
	public InvalidCssSelector(CssSelectorToXPathConverterException e)
	{
		message = e.getMessage();
	}
	
	public String getMessage()
	{
		return message;
	}
	


}

