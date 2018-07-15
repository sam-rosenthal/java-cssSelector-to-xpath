package org.sam.rosenthal.selenium;

import java.util.List;

import org.junit.Test;
import org.sam.rosenthal.cssselectortoxpath.utilities.BaseCssSelectorToXpathTestCase;
import org.sam.rosenthal.cssselectortoxpath.utilities.CssSelectorToXPathConverterException;

public class CssSelectorToXpathTest extends AbstractCssSelectorToXpathTest{
	
	@Test
	public void testBasicCasesWithSelenium() throws CssSelectorToXPathConverterException
	{
		setupTest(BrowserType.CHROME);
		List<BaseCssSelectorToXpathTestCase> baseCases=BaseCssSelectorToXpathTestCase.getBaseCssSelectorToXpathTestCases(true);
		for(BaseCssSelectorToXpathTestCase cssSelectorToXpathCase: baseCases)
		{
			testConverterOutput(cssSelectorToXpathCase.getCssSelector());
		}
	}
}
