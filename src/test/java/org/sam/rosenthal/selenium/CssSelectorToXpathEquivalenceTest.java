package org.sam.rosenthal.selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.sam.rosenthal.cssselectortoxpath.utilities.CssSelectorToXPathConverterException;

public class CssSelectorToXpathEquivalenceTest extends AbstractCssSelectorToXpathTest{
	
	private static final String WEBSITE_URL_TO_TEST_WITH = "https://en.wikipedia.org/wiki/Liverpool_F.C.";
	private static final String NON_PSEUDO_CLASS_ATTRIBUTE = "[class^='mw']";

	@Test
	public void testBasicCasesWithSelenium() throws CssSelectorToXPathConverterException
	{
		testBasicCasesWithSelenium(BrowserType.CHROME, NON_PSEUDO_CLASS_ATTRIBUTE, WEBSITE_URL_TO_TEST_WITH);
		testBasicCasesWithSelenium(BrowserType.FIREFOX, NON_PSEUDO_CLASS_ATTRIBUTE, WEBSITE_URL_TO_TEST_WITH);
	}
	
	
	private void testBasicCasesWithSelenium(BrowserType browserType,String nonPseudoClassAttribute, String websiteUrlToTest) throws CssSelectorToXPathConverterException
	{
		setupTest(browserType);
		List<String> cssSelectors = new ArrayList<String>();
		cssSelectors.add("div"+nonPseudoClassAttribute+":empty");
		cssSelectors.add("div"+nonPseudoClassAttribute+":empty:only-child");
		cssSelectors.add("div"+nonPseudoClassAttribute+":only-child:empty");
		cssSelectors.add("div"+nonPseudoClassAttribute+":empty:first-of-type");
		cssSelectors.add("div"+nonPseudoClassAttribute+":first-of-type:empty");
		cssSelectors.add("div"+nonPseudoClassAttribute+":empty:last-of-type");
		cssSelectors.add("div"+nonPseudoClassAttribute+":last-of-type:empty");
		cssSelectors.add("div"+nonPseudoClassAttribute+":empty:first-child");
		cssSelectors.add("div"+nonPseudoClassAttribute+":first-child:empty");
		cssSelectors.add("div"+nonPseudoClassAttribute+":empty:last-child");
		cssSelectors.add("div"+nonPseudoClassAttribute+":last-child:empty");
		
		cssSelectors.add("div"+nonPseudoClassAttribute+":only-child");
		cssSelectors.add("div"+nonPseudoClassAttribute+":only-child:first-of-type");
		cssSelectors.add("div"+nonPseudoClassAttribute+":first-of-type:only-child");
		cssSelectors.add("div"+nonPseudoClassAttribute+":only-child:last-of-type");
		cssSelectors.add("div"+nonPseudoClassAttribute+":last-of-type:only-child");
		cssSelectors.add("div"+nonPseudoClassAttribute+":only-child:first-child");
		cssSelectors.add("div"+nonPseudoClassAttribute+":first-child:only-child");
		cssSelectors.add("div"+nonPseudoClassAttribute+":only-child:last-child");
		cssSelectors.add("div"+nonPseudoClassAttribute+":last-child:only-child");

		cssSelectors.add("div"+nonPseudoClassAttribute+":first-of-type");
		cssSelectors.add("div"+nonPseudoClassAttribute+":first-of-type:last-of-type");
		cssSelectors.add("div"+nonPseudoClassAttribute+":last-of-type:first-of-type");
		cssSelectors.add("div"+nonPseudoClassAttribute+":first-of-type:first-child");
		cssSelectors.add("div"+nonPseudoClassAttribute+":first-child:first-of-type");
		cssSelectors.add("div"+nonPseudoClassAttribute+":first-of-type:last-child");
		cssSelectors.add("div"+nonPseudoClassAttribute+":last-child:first-of-type");

		cssSelectors.add("div"+nonPseudoClassAttribute+":last-of-type");
		cssSelectors.add("div"+nonPseudoClassAttribute+":last-of-type:first-child");
		cssSelectors.add("div"+nonPseudoClassAttribute+":first-child:last-of-type");
		cssSelectors.add("div"+nonPseudoClassAttribute+":last-of-type:last-child");
		cssSelectors.add("div"+nonPseudoClassAttribute+":last-child:last-of-type");
		
		cssSelectors.add("div"+nonPseudoClassAttribute+":first-child");
		cssSelectors.add("div"+nonPseudoClassAttribute+":first-child:last-child");
		cssSelectors.add("div"+nonPseudoClassAttribute+":last-child:first-child");

		cssSelectors.add("div"+nonPseudoClassAttribute+":last-child");
		
		cssSelectors.add("div"+nonPseudoClassAttribute+":nth-of-type(n)");
		cssSelectors.add("div"+nonPseudoClassAttribute+":nth-of-type(odd)");
		cssSelectors.add("div"+nonPseudoClassAttribute+":nth-of-type(even)");
		cssSelectors.add("div"+nonPseudoClassAttribute+":nth-of-type(2n+3)");
		cssSelectors.add("div"+nonPseudoClassAttribute+":nth-of-type(-2n+3)");

		cssSelectors.add("div"+nonPseudoClassAttribute+":nth-last-of-type(n)");
		cssSelectors.add("div"+nonPseudoClassAttribute+":nth-last-of-type(odd)");
		cssSelectors.add("div"+nonPseudoClassAttribute+":nth-last-of-type(even)");
		cssSelectors.add("div"+nonPseudoClassAttribute+":nth-last-of-type(2n+3)");
		cssSelectors.add("div"+nonPseudoClassAttribute+":nth-last-of-type(-2n+3)");
		
		cssSelectors.add("div"+nonPseudoClassAttribute+":nth-child(n)");
		cssSelectors.add("div"+nonPseudoClassAttribute+":nth-child(odd)");
		cssSelectors.add("div"+nonPseudoClassAttribute+":nth-child(even)");
		cssSelectors.add("div"+nonPseudoClassAttribute+":nth-child(2n+3)");
		cssSelectors.add("div"+nonPseudoClassAttribute+":nth-child(-2n+3)");

		cssSelectors.add("div"+nonPseudoClassAttribute+":nth-last-child(n)");
		cssSelectors.add("div"+nonPseudoClassAttribute+":nth-last-child(odd)");
		cssSelectors.add("div"+nonPseudoClassAttribute+":nth-last-child(even)");
		cssSelectors.add("div"+nonPseudoClassAttribute+":nth-last-child(2n+3)");
		cssSelectors.add("div"+nonPseudoClassAttribute+":nth-last-child(-2n+3)");
		String winHandleBefore = driver.getWindowHandle();
		String newWebsiteWindow = null;
		((JavascriptExecutor) driver).executeScript("window.open(arguments[0])", websiteUrlToTest);
		assertTrue(wait.until((new ExpectedCondition<Boolean>() {
	        public Boolean apply(WebDriver d) {
	        	return 2==driver.getWindowHandles().size();
	        }
	    })));
		for(String winHandle : driver.getWindowHandles())
		{
			if(!winHandleBefore.equals(winHandle)) {
				newWebsiteWindow = winHandle;
				driver.switchTo().window(winHandle);		
		        break;
			}
		}
		driver.get(websiteUrlToTest);
	    driver.switchTo().window(winHandleBefore);

		for(String selector: cssSelectors)
		{
			testEquivalence(winHandleBefore, newWebsiteWindow, selector);
		}
	}
	
	protected void testEquivalence(String winHandleBefore, String newWebsiteWindow, String cssSelector) throws CssSelectorToXPathConverterException {
		testConverterOutput(cssSelector);
		String xpath = converter.convertCssSelectorStringToXpathString(cssSelector);
		System.out.println("XPATH = "+xpath);

	    driver.switchTo().window(newWebsiteWindow);
		List<WebElement> cssResults = driver.findElements(By.cssSelector(cssSelector));
		List<WebElement> xpathResults = driver.findElements(By.xpath(xpath));
		System.out.println("Css Elements Found:" + cssResults.size() + ", XPath elements found:" + xpathResults.size());
		assertEquals(cssResults.size(), xpathResults.size()); 
		for(int i = 0; i<cssResults.size(); i++)
		{
			WebElement cssElement = cssResults.get(i);
			WebElement xpathElement = xpathResults.get(i);
			assertEquals("CSS:"+cssElement.getLocation() +"  XPATH:" + xpathElement.getLocation(),cssElement, xpathElement);
		}
	    driver.switchTo().window(winHandleBefore);
	}

	
}
