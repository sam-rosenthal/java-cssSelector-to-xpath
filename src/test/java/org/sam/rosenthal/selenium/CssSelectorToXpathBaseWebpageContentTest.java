package org.sam.rosenthal.selenium;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.sam.rosenthal.cssselectortoxpath.utilities.CssSelectorToXPathConverterException;
import org.sam.rosenthal.cssselectortoxpath.utilities.NiceCssSelectorStringForOutputException;
import org.sam.rosenthal.cssselectortoxpath.utilities.basetestcases.BaseCssSelectorToXpathTestCase;

public class CssSelectorToXpathBaseWebpageContentTest extends AbstractCssSelectorToXpathTest{
	
	@Override
	protected void goToWebpage(String cssToXpathUrl, String expectedTitle) {
		super.goToWebpage(cssToXpathUrl+"/css-selector-to-xpath-reference-cases", "CSS Selector Reference Cases Test Page");
	}

	@Test
	public void testBasicExceptiobCasesWithSeleniumChrome()  throws CssSelectorToXPathConverterException, NiceCssSelectorStringForOutputException
	{
		testBasicExceptiobCasesWithSelenium(BrowserType.CHROME);
	}
	@Test
	public void testBasicExceptiobCasesWithSeleniumFirefox()  throws CssSelectorToXPathConverterException, NiceCssSelectorStringForOutputException
	{
		testBasicExceptiobCasesWithSelenium(BrowserType.FIREFOX);
	}
	@Test
	public void testBasicExceptiobCasesWithSeleniumEdge()  throws CssSelectorToXPathConverterException, NiceCssSelectorStringForOutputException
	{
		testBasicExceptiobCasesWithSelenium(BrowserType.EDGE);
	}
	protected void testBasicExceptiobCasesWithSelenium(BrowserType browserType) throws CssSelectorToXPathConverterException, NiceCssSelectorStringForOutputException
	{
		setupTest(browserType);
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
			assertEquals(name,driver.findElement(getBy(cssSelectorWithDivId)).getText().trim());
			String xpathWithDivId="//div[@id=\""+name+"\"]"+ cssSelectorToXpathCase.getExpectedXpath();
			System.out.println("xpath="+xpathWithDivId);

			assertEquals(name,driver.findElement(By.xpath(xpathWithDivId)).getText().trim());
			assertEquals(xpathWithDivId, converter.convertCssSelectorStringToXpathString(cssSelectorWithDivId));
			
		}
	}
}
