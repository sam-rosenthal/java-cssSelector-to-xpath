package org.sam.rosenthal.selenium;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;
import org.sam.rosenthal.cssselectortoxpath.utilities.BaseCssSelectorToXpathTestCase;
import org.sam.rosenthal.cssselectortoxpath.utilities.CssSelectorToXPathConverterException;
import org.sam.rosenthal.cssselectortoxpath.utilities.NiceCssSelectorStringForOutputException;

public class CssSelectorToXpathExceptionTest extends AbstractCssSelectorToXpathTest{
		
	@Test
	public void testBasicExceptiobCasesWithSelenium() throws CssSelectorToXPathConverterException, NiceCssSelectorStringForOutputException
	{
		setupTest(BrowserType.CHROME);
		Map<String,String> baseCases=BaseCssSelectorToXpathTestCase.getBaseCssSelectorToXpathExceptionTestCases();

		for(Map.Entry<String,String> baseExceptionCase:baseCases.entrySet())
		{
			String cssSelector=baseExceptionCase.getKey();
			if(cssSelector==null)
			{
				System.out.println("Selenium sendkey() will not allow user to enter null, skipping null case");
				continue;
			}
			else if(cssSelector.trim().isEmpty())
			{
				System.out.println("Wicket GUI trims input and website does not process no data, skipping case");
				continue;
			}
			String expectedErrorMessage="Error: "+new NiceCssSelectorStringForOutputException(baseExceptionCase.getValue(), null).getMessage();
			System.out.println(expectedErrorMessage);
			testErrorMessage(cssSelector);
			
			assertEquals(expectedErrorMessage,driver.findElement(errorMessageBy).getText());
		}
	
	}
}
