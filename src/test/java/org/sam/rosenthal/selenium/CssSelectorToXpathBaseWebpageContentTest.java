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
		super.goToWebpage(cssToXpathUrl+"/TestPage", "CSS Selector to XPath");
	}

	@Test
	public void testBasicExceptionCasesWithSeleniumChrome() throws CssSelectorToXPathConverterException, NiceCssSelectorStringForOutputException
	{
		testBasicExceptionCasesWithSelenium(BrowserType.CHROME);
	}
	
	@Test
	public void testBasicExceptionCasesWithSeleniumFirefox() throws CssSelectorToXPathConverterException, NiceCssSelectorStringForOutputException
	{
		testBasicExceptionCasesWithSelenium(BrowserType.FIREFOX);
	}
	
//	@Test
//	public void testBasicExceptionCasesWithSeleniumEdge() throws CssSelectorToXPathConverterException, NiceCssSelectorStringForOutputException
//	{
//		testBasicExceptionCasesWithSelenium(BrowserType.EDGE);
//	}
	protected void testBasicExceptionCasesWithSelenium(BrowserType browserType) throws CssSelectorToXPathConverterException, NiceCssSelectorStringForOutputException
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

			String cssSelectorWithDivId="div[id=\""+name+"\"] "+cssSelectorToXpathCase.getCssSelector();
			System.out.println("cssSelector="+cssSelectorWithDivId);
			String xpathWithDivId="//div[@id=\""+name+"\"]"+ cssSelectorToXpathCase.getExpectedXpath();
			System.out.println("xpath="+xpathWithDivId);
			String expectedName=name.equals("empty")?"":name;
			assertEquals(expectedName,driver.findElement(getBy(cssSelectorWithDivId)).getText().trim());
			assertEquals(expectedName,driver.findElement(By.xpath(xpathWithDivId)).getText().trim());
			assertEquals(xpathWithDivId, converter.convertCssSelectorStringToXpathString(cssSelectorWithDivId));
			
		}
	}
}
