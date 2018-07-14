package org.sam.rosenthal.selenium;

import org.junit.Test;
import org.sam.rosenthal.cssselectortoxpath.utilities.CssSelectorToXPathConverterException;
import org.sam.rosenthal.cssselectortoxpath.utilities.NiceCssSelectorStringForOutputException;

public class CssSelectorToXpathTestUsingCssSelectors extends AbstractCssSelectorToXpathTest{
	
	@Test
	public void testUsingCssSelectorsWithChrome() throws CssSelectorToXPathConverterException, NiceCssSelectorStringForOutputException
	{
		test(BrowserType.CHROME);
	}
	@Test
	public void testUsingCssSelectorsWithFirefox() throws CssSelectorToXPathConverterException, NiceCssSelectorStringForOutputException
	{
		test(BrowserType.FIREFOX);
	}
	@Test
	public void testUsingCssSelectorsWithEdge() throws CssSelectorToXPathConverterException, NiceCssSelectorStringForOutputException
	{
		test(BrowserType.EDGE);
	}
}
