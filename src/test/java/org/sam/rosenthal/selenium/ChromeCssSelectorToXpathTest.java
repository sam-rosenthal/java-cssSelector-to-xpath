package org.sam.rosenthal.selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sam.rosenthal.cssselectortoxpath.utilities.CssElementCombinatorPairsToXpath;
import org.sam.rosenthal.cssselectortoxpath.utilities.CssSelectorToXPathConverterException;
import org.sam.rosenthal.cssselectortoxpath.utilities.NiceCssSelectorStringForOutputException;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ChromeCssSelectorToXpathTest {

	private WebDriver driver;
	
	private static Properties properties;

	@BeforeClass
	public static void setupClass() {
		WebDriverManager.chromedriver().setup();
		properties=new Properties();
		
		try {
			properties.load(ChromeCssSelectorToXpathTest.class.getClassLoader().getResourceAsStream("org/sam/rosenthal/selenium/cssSelectorToXpathSelenium.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Before
	public void setupTest() {
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);				

	}

	@After
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void test() throws CssSelectorToXPathConverterException, NiceCssSelectorStringForOutputException {
		driver.get(properties.getProperty("selenium.CSS_TO_XPATH_URL"));
		testText();

		testWebLinks();

		testConverterOutput();
		
		testErrorOutput();
	}

	private void testErrorOutput() throws NiceCssSelectorStringForOutputException {
		testErrorMessage("[a=\"b']");
		testErrorMessage("a,b,c,");
		testErrorMessage("[]");
		testErrorMessage(" ");
		testErrorMessage("   ");
	}

	private void testConverterOutput() throws CssSelectorToXPathConverterException {
		testConverterOutput("div.content h1");
		testConverterOutput("body>a img");
		testConverterOutput("div#footer fieldset.assumptions legend");
		testConverterOutput("div#footer fieldset.links legend");
	}

	private void testText() {
		assertEquals("CSS Selector to XPath",driver.getTitle());
		assertEquals("CSS Selector to XPath Converter",driver.findElement(By.cssSelector("div.content h1")).getText());
		assertEquals("Fork me on GitHub",driver.findElement(By.cssSelector("div#forkme a img")).getAttribute("alt"));
		assertEquals("Implementation Notes",driver.findElement(By.cssSelector("div#footer fieldset.assumptions legend")).getText());
		assertEquals("Helpful Links/More Info",driver.findElement(By.cssSelector("div#footer fieldset.links legend")).getText());
	}

	private void testWebLinks() {
		//didnt do the css selector "body a[href]" due to wicket ajax debug
		List<WebElement> webLinks=driver.findElements(By.cssSelector("div#footer a, div.content a, div#forkme a"));
		Map<String,String> urlToPageTitleMap = getUrlToPageTitleMap();
		assertEquals(urlToPageTitleMap.keySet().size(),webLinks.size());
		for(WebElement element: webLinks)
		{
			String linkUrl = element.getAttribute("href");
			System.out.println(linkUrl);
		    String title = urlToPageTitleMap.get(linkUrl);
		    //special handling for duplicate links
		    String dupLinkUrl="1:"+linkUrl;
		    if(urlToPageTitleMap.remove(dupLinkUrl)==null)
		    {
		    	//not a dup and remove
		    	urlToPageTitleMap.remove(linkUrl);
		    }
		    
		    assertNotNull(title);
		    
		    element.click();
			
			String winHandleBefore = driver.getWindowHandle();
			Set<String> windowHandles = driver.getWindowHandles();
			assertEquals(2,windowHandles.size());
			boolean found=false;
			for(String winHandle : windowHandles)
			{
				if(!winHandleBefore.equals(winHandle)) {
			       driver.switchTo().window(winHandle);
			       found=true;
			       break;
				}
			}
			assertTrue(found);
			assertEquals(title,driver.getTitle());
			int index=linkUrl.indexOf("#");
			if(index>-1)
			{
				//Some links contain an id that directs the link to go directly to a specific text on the the page corresponding to
				//the element withh that id.
				String cssSelector=linkUrl.substring(index);
				assertNotNull(driver.findElement(By.cssSelector(cssSelector)));
			}
			driver.close();
			driver.switchTo().window(winHandleBefore);
		}
		assertTrue(urlToPageTitleMap.isEmpty());
	}

	private void testConverterOutput(String cssSelector) throws CssSelectorToXPathConverterException 
	{
		submitCssSelector(cssSelector);
		String expectedXpath= new CssElementCombinatorPairsToXpath().convertCssSelectorStringToXpathString(cssSelector);
		assertTrue(expectedXpath.length()>0);
		assertNotEquals(expectedXpath, cssSelector);
		
	    WebDriverWait wait = new WebDriverWait(driver, 10);
	    assertTrue(wait.until((new ExpectedCondition<Boolean>() {
	        public Boolean apply(WebDriver d) {
	            return expectedXpath.equals(driver.findElement(By.cssSelector("table#inputOutputTable tr#xpathOutputRow>td#xpathOutputString>span")).getText());
	        }
	    })));
	    assertTrue(cssSelector.equals(driver.findElement(By.cssSelector("table#inputOutputTable tr#cssInputRow>td#cssInputString>span")).getText()));
	}

	private void submitCssSelector(String cssSelector) {
		driver.findElement(By.cssSelector("div.content form input[type='text']")).sendKeys(cssSelector);
		driver.findElement(By.cssSelector("div.content form input[type='submit']")).click();
	}
	
	public void testErrorMessage(String cssSelector) throws NiceCssSelectorStringForOutputException 
	{
		submitCssSelector(cssSelector);
		String err = null;
		String adjustedCssSelector=cssSelector.trim();
		if(adjustedCssSelector.isEmpty())
		{
			//wicket converts empty strings to null
			adjustedCssSelector=null;
		}
		try
		{
			new CssElementCombinatorPairsToXpath().niceConvertCssSelectorToXpathForOutput(adjustedCssSelector);
		}
		catch (NiceCssSelectorStringForOutputException e)
		{
			err=e.getMessage();
		}
	    final String error=err;
		WebDriverWait wait = new WebDriverWait(driver, 10);
	    assertTrue(wait.until((new ExpectedCondition<Boolean>() {
	        public Boolean apply(WebDriver d) {
	        	return error.equals(driver.findElement(By.cssSelector("div.content form div.error")).getText());
	        }
	    })));
	    System.out.println(driver.findElement(By.cssSelector("table#inputOutputTable tr#cssInputRow>td#cssInputString>span")).getText()+"aaa");
      	if(adjustedCssSelector!=null)
      	{
      		assertTrue(adjustedCssSelector.equals(driver.findElement(By.cssSelector("table#inputOutputTable tr#cssInputRow>td#cssInputString>span")).getText()));
      	}
      	else
      	{
      		assertTrue(driver.findElement(By.cssSelector("table#inputOutputTable tr#cssInputRow>td#cssInputString>span")).getText().isEmpty());
      	}
		assertTrue(driver.findElement(By.cssSelector("div.content form i[class='fa fa-times-circle']")).isDisplayed());
	}
	
	private Map<String,String> getUrlToPageTitleMap() {
		HashMap<String,String> urlToPageTitleMap = new HashMap<>();
		urlToPageTitleMap.put("https://github.com/sam-rosenthal/java-cssSelector-to-xpath","GitHub - sam-rosenthal/java-cssSelector-to-xpath");
		urlToPageTitleMap.put("https://github.com/sam-rosenthal/java-cssSelector-to-xpath/blob/samdev/README.md","java-cssSelector-to-xpath/README.md at samdev · sam-rosenthal/java-cssSelector-to-xpath · GitHub");
		urlToPageTitleMap.put("https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Selectors#Simple_selectors","CSS selectors - CSS: Cascading Style Sheets | MDN");
		urlToPageTitleMap.put("https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Selectors#Combinators","CSS selectors - CSS: Cascading Style Sheets | MDN");
		urlToPageTitleMap.put("https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Selectors#Pseudo-classes","CSS selectors - CSS: Cascading Style Sheets | MDN");
		
		urlToPageTitleMap.put("https://yizeng.me/2014/03/23/evaluate-and-validate-xpath-css-selectors-in-chrome-developer-tools/","Evaluate and validate XPath/CSS selectors in Chrome Developer Tools | Yi Zeng’s Blog");
		urlToPageTitleMap.put("1:https://yizeng.me/2014/03/23/evaluate-and-validate-xpath-css-selectors-in-chrome-developer-tools/","Evaluate and validate XPath/CSS selectors in Chrome Developer Tools | Yi Zeng’s Blog");

		urlToPageTitleMap.put("https://github.com/sam-rosenthal","sam-rosenthal (Sam Rosenthal) · GitHub");
		urlToPageTitleMap.put("https://sam-rosenthal.github.io/","Sam Rosenthal");
		urlToPageTitleMap.put("https://en.wikipedia.org/wiki/Cascading_Style_Sheets","Cascading Style Sheets - Wikipedia");
		urlToPageTitleMap.put("https://en.wikipedia.org/wiki/XPath","XPath - Wikipedia");
		urlToPageTitleMap.put("https://en.wikibooks.org/wiki/XPath/CSS_Equivalents","XPath/CSS Equivalents - Wikibooks, open books for an open world");
		urlToPageTitleMap.put("https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Selectors","CSS selectors - CSS: Cascading Style Sheets | MDN");
		urlToPageTitleMap.put("https://developer.mozilla.org/en-US/docs/Web/XPath","XPath | MDN");
		urlToPageTitleMap.put("https://www.w3schools.com/cssref/trysel.asp","Try CSS Selector");
		urlToPageTitleMap.put("https://css-tricks.com/almanac/","CSS Almanac | CSS-Tricks");
		return urlToPageTitleMap;
	}
}