package org.sam.rosenthal.selenium;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.sam.rosenthal.cssselectortoxpath.utilities.CssSelectorToXPathConverterException;

public class CssSelectorToXpathEquivalenceTest extends AbstractCssSelectorToXpathTest{
	
	@Test
	public void testBasicCasesWithSelenium() throws CssSelectorToXPathConverterException
	{
		setupTest(BrowserType.EDGE);
		List<String> cssSelectors = new ArrayList<String>();
		cssSelectors.add("div[class^='score']:empty");
		cssSelectors.add("div[class^='score']:empty:only-child");
		cssSelectors.add("div[class^='score']:only-child:empty");
		cssSelectors.add("div[class^='score']:empty:first-of-type");
		cssSelectors.add("div[class^='score']:first-of-type:empty");
		cssSelectors.add("div[class^='score']:empty:last-of-type");
		cssSelectors.add("div[class^='score']:last-of-type:empty");
		cssSelectors.add("div[class^='score']:empty:first-child");
		cssSelectors.add("div[class^='score']:first-child:empty");
		cssSelectors.add("div[class^='score']:empty:last-child");
		cssSelectors.add("div[class^='score']:last-child:empty");
		
		cssSelectors.add("div[class^='score']:only-child");
		cssSelectors.add("div[class^='score']:only-child:first-of-type");
		cssSelectors.add("div[class^='score']:first-of-type:only-child");
		cssSelectors.add("div[class^='score']:only-child:last-of-type");
		cssSelectors.add("div[class^='score']:last-of-type:only-child");
		cssSelectors.add("div[class^='score']:only-child:first-child");
		cssSelectors.add("div[class^='score']:first-child:only-child");
		cssSelectors.add("div[class^='score']:only-child:last-child");
		cssSelectors.add("div[class^='score']:last-child:only-child");

		cssSelectors.add("div[class^='score']:first-of-type");
		cssSelectors.add("div[class^='score']:first-of-type:last-of-type");
		cssSelectors.add("div[class^='score']:last-of-type:first-of-type");
		cssSelectors.add("div[class^='score']:first-of-type:first-child");
		cssSelectors.add("div[class^='score']:first-child:first-of-type");
		cssSelectors.add("div[class^='score']:first-of-type:last-child");
		cssSelectors.add("div[class^='score']:last-child:first-of-type");

		cssSelectors.add("div[class^='score']:last-of-type");
		cssSelectors.add("div[class^='score']:last-of-type:first-child");
		cssSelectors.add("div[class^='score']:first-child:last-of-type");
		cssSelectors.add("div[class^='score']:last-of-type:last-child");
		cssSelectors.add("div[class^='score']:last-child:last-of-type");
		
		cssSelectors.add("div[class^='score']:first-child");
		cssSelectors.add("div[class^='score']:first-child:last-child");
		cssSelectors.add("div[class^='score']:last-child:first-child");

		cssSelectors.add("div[class^='score']:last-child");
		
		for(String selector: cssSelectors)
		{
			testEquivalence("https://google.com", selector);
		}

	}
	
}
