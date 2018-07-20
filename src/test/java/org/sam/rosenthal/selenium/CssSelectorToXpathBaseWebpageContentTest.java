package org.sam.rosenthal.selenium;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.sam.rosenthal.cssselectortoxpath.utilities.BaseCssSelectorToXpathTestCase;
import org.sam.rosenthal.cssselectortoxpath.utilities.CssSelectorToXPathConverterException;
import org.sam.rosenthal.cssselectortoxpath.utilities.NiceCssSelectorStringForOutputException;

public class CssSelectorToXpathBaseWebpageContentTest extends AbstractCssSelectorToXpathTest{
	
	@Override
	protected void goToWebpage(String cssToXpathUrl, String expectedTitle) {
		super.goToWebpage(cssToXpathUrl+"/css-selector-to-xpath-reference-cases", "CSS Selector Reference Cases Test Page");
	}

	@Test
	public void testBasicExceptiobCasesWithSelenium() throws CssSelectorToXPathConverterException, NiceCssSelectorStringForOutputException
	{
		setupTest(BrowserType.CHROME);
		//driver.get("http://localhost/css-selector-to-xpath-reference-cases");
		List<BaseCssSelectorToXpathTestCase> baseCases=BaseCssSelectorToXpathTestCase.getBaseCssSelectorToXpathTestCases(false);
		int i=0;
		for(BaseCssSelectorToXpathTestCase cssSelectorToXpathCase: baseCases)
		{
			++i;
			String name = cssSelectorToXpathCase.getName();
			System.out.println("Starting Test Case "+i+" "+name);

			String cssSelectorWithDivId="div#"+name+" "+cssSelectorToXpathCase.getCssSelector();
			System.out.println("cssSelector="+cssSelectorWithDivId);
			assertEquals(name,driver.findElement(getBy(cssSelectorWithDivId)).getText());
			String xpathWithDivId="//div[@id=\""+name+"\"]"+ cssSelectorToXpathCase.getExpectedXpath();
			System.out.println("xpath="+xpathWithDivId);

			assertEquals(name,driver.findElement(By.xpath(xpathWithDivId)).getText());
			assertEquals(xpathWithDivId, converter.convertCssSelectorStringToXpathString(cssSelectorWithDivId));
			
		}
	}
}
