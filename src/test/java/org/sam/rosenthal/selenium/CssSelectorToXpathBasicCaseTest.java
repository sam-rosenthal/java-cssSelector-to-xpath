package org.sam.rosenthal.selenium;

import java.util.List;

import org.junit.Test;
import org.sam.rosenthal.cssselectortoxpath.utilities.BaseCssSelectorToXpathTestCase;
import org.sam.rosenthal.cssselectortoxpath.utilities.CssSelectorToXPathConverterException;

public class CssSelectorToXpathBasicCaseTest extends AbstractCssSelectorToXpathTest{
	
	@Test
	public void testBasicCasesWithSelenium() throws CssSelectorToXPathConverterException
	{
		setupTest(BrowserType.CHROME);
		List<BaseCssSelectorToXpathTestCase> baseCases=BaseCssSelectorToXpathTestCase.getBaseCssSelectorToXpathTestCases(true);
		for(BaseCssSelectorToXpathTestCase cssSelectorToXpathCase: baseCases)
		{
			//System.out.println(cssSelectorToXpathCase.getCssSelector());
			testConverterOutput(cssSelectorToXpathCase.getCssSelector());
		}
	}
	
	
}
