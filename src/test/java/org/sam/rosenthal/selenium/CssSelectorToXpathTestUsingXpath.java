package org.sam.rosenthal.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.sam.rosenthal.cssselectortoxpath.utilities.CssSelectorToXPathConverterException;
import org.sam.rosenthal.cssselectortoxpath.utilities.NiceCssSelectorStringForOutputException;

public class CssSelectorToXpathTestUsingXpath extends AbstractCssSelectorToXpathTest{

	@Override
	protected By getBy(String cssSelector){
		String xpath = null;
		try {
			xpath = converter.convertCssSelectorStringToXpathString(cssSelector);
		} 
		catch (CssSelectorToXPathConverterException e) {
			throw new RuntimeException(e);
		}
		System.out.println("original cssSelector="+cssSelector+", using xpath="+xpath);
		return By.xpath(xpath);
	}
	
	@Test
	public void testUsingXpathWithChrome() throws CssSelectorToXPathConverterException, NiceCssSelectorStringForOutputException
	{
		test(BrowserType.CHROME);
	}
	@Test
	public void testUsingXpathWithFirefox() throws CssSelectorToXPathConverterException, NiceCssSelectorStringForOutputException
	{
		test(BrowserType.FIREFOX);
	}
//	@Test
//	public void testUsingXpathWithEdge() throws CssSelectorToXPathConverterException, NiceCssSelectorStringForOutputException
//	{
//		test(BrowserType.EDGE);
//	}
	

}
