package org.sam.rosenthal.selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sam.rosenthal.cssselectortoxpath.utilities.CssElementCombinatorPairsToXpath;
import org.sam.rosenthal.cssselectortoxpath.utilities.CssSelectorToXPathConverterException;
import org.sam.rosenthal.cssselectortoxpath.utilities.NiceCssSelectorStringForOutputException;

import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class AbstractCssSelectorToXpathTest {

	protected WebDriver driver;
	
	protected static final Properties PROPERTIES=getProperties();
	
	protected WebDriverWait wait;

	protected By forkMeBy;

	protected By errorMessageBy;
	
	protected CssElementCombinatorPairsToXpath converter= new CssElementCombinatorPairsToXpath();

	private String cssToXpathUrl;

	public static Properties getProperties() {
		WebDriverManager.chromedriver().setup();
		WebDriverManager.firefoxdriver().setup();
//		WebDriverManager.edgedriver().setup();

		Properties prop=new Properties();
		
		try {
			prop.load(AbstractCssSelectorToXpathTest.class.getClassLoader().getResourceAsStream("org/sam/rosenthal/selenium/cssSelectorToXpathSelenium.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return prop;
	}

	protected void setupTest(BrowserType browserType) {
		switch(browserType)
		{
		case CHROME:
			driver = new ChromeDriver();
			break;
		case EDGE:
//			EdgeOptions options = new EdgeOptions();
//			driver= new EdgeDriver();
//			break;
			throw new RuntimeException("TODO fix issue with running latest Edge webdriver");
		case FIREFOX:
			driver= new FirefoxDriver();
			break;
		default:
			throw new IllegalArgumentException(""+browserType);
		}
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);				
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		forkMeBy = getBy("span#forkongithub a");
	    errorMessageBy = getBy("form b#errorMessage");

	    cssToXpathUrl = PROPERTIES.getProperty("selenium.CSS_TO_XPATH_URL");
		goToWebpage(cssToXpathUrl,"CSS Selector to XPath");
	}

	protected void goToWebpage(String cssToXpathUrl, String expectedTitle) {
		driver.get(cssToXpathUrl);
		assertEquals(expectedTitle,driver.getTitle());
	}

	@After
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

	protected void test(BrowserType browserType) throws CssSelectorToXPathConverterException, NiceCssSelectorStringForOutputException {
		setupTest(browserType);
		testText();
		testConverterOutput();
		testErrorOutput();
		testWebLinks(browserType);
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
		assertEquals("CSS Selector to XPath Converter",driver.findElement(getBy("div.container form h1")).getText());
		assertEquals("Fork me on GitHub",driver.findElement(forkMeBy).getText());
		assertEquals("Developed by Sam Rosenthal",driver.findElement(getBy("div#footer legend#bySamRosenthal")).getText());
		assertEquals("Implementation notes",driver.findElement(getBy("div#footer legend#ImplementationNotes")).getText());
		assertEquals("Helpful links",driver.findElement(getBy("div#footer legend#HelpfulLinks")).getText());
		assertEquals("Testing notes",driver.findElement(getBy("div#footer legend#TestingNotes")).getText());
	}

	protected By getBy(String cssSelector) {
		return By.cssSelector(cssSelector);
	}

	private void testWebLinks(BrowserType browserType) {
		//didnt do the css selector "body a[href]" due to wicket ajax debug
		List<WebElement> webLinks=driver.findElements(getBy("div a"));
		Map<String,String> urlToPageTitleMap = getUrlToPageTitleMap();
		validateLinksHelper(webLinks, urlToPageTitleMap, browserType);
		System.out.println(urlToPageTitleMap);
		assertTrue(urlToPageTitleMap.isEmpty());
	}

	private void validateLinksHelper(List<WebElement> webLinks, Map<String, String> urlToPageTitleMap, BrowserType browserType) {
		assertEquals(webLinks.size(),urlToPageTitleMap.size());
		for(WebElement element: webLinks)
		{
			String linkUrl = element.getAttribute("href");
			System.out.println(linkUrl);
		    String title = urlToPageTitleMap.get(linkUrl);
		    System.out.println("title="+title);
		    //special handling for duplicate links
		    String dupLinkUrl="1:"+linkUrl;
		    if(urlToPageTitleMap.remove(dupLinkUrl)==null)
		    {
		    	//not a dup and remove
		    	urlToPageTitleMap.remove(linkUrl);
		    }
		    if(linkUrl.startsWith("mailto"))
		    {
		    	System.out.println("linkUrl = "+linkUrl+", title ="+title);
		    	continue;
		    }
		    assertNotNull(title);
		    
		    //this is a workaround for firefox with a link surrounding an image not being clickable
		    //https://sqa.stackexchange.com/questions/32697/webdriver-firefox-element-could-not-be-scrolled-into-view
		    if(element.getAttribute("href").equals("https://github.com/sam-rosenthal/java-cssSelector-to-xpath"))
		    {
		    	element=driver.findElement(forkMeBy);
		    }
		    try {
		    	element.click();
		    }
		    catch(Exception e)
		    {
		    	if(element.getAttribute("href").equals("https://github.com/sam-rosenthal/java-cssSelector-to-xpath"))
		    	{
			    	System.out.println("Handling clicking selenium isssue with url: "+element.getAttribute("href"));
					((JavascriptExecutor) driver).executeScript("window.open(arguments[0])", linkUrl);
		    	}
		    	else
		    	{
		    		e.printStackTrace();
		    	}
		    }

			String winHandleBefore = driver.getWindowHandle();
			assertTrue(wait.until((new ExpectedCondition<Boolean>() {
		        public Boolean apply(WebDriver d) {
		        	return 2==driver.getWindowHandles().size();
		        }
		    })));
					
			boolean found=false;
			for(String winHandle : driver.getWindowHandles())
			{
				if(!winHandleBefore.equals(winHandle)) {
			       driver.switchTo().window(winHandle);
			       found=true;
			       break;
				}
			}
			assertTrue(found);
			assertTrue(wait.until((new ExpectedCondition<Boolean>() {
		        public Boolean apply(WebDriver d) {
		        	return title.equals(driver.getTitle());
		        }
		    })));

			driver.close();
			driver.switchTo().window(winHandleBefore);
		}
		assertTrue(urlToPageTitleMap.isEmpty());
	}


	protected void testConverterOutput(String cssSelector) throws CssSelectorToXPathConverterException 
	{
		submitCssSelector(cssSelector);
		String expectedXpath= converter.convertCssSelectorStringToXpathString(cssSelector);
		assertTrue(expectedXpath.length()>0);
		assertNotEquals(expectedXpath, cssSelector);
	    assertTrue(wait.until(getWaitforExpectedText(expectedXpath,getBy("div#xpathOutputGridText"))));
	    assertTrue(wait.until(getWaitforExpectedText(cssSelector,getBy("div#cssInputGridText"))));
	}
	
	private ExpectedCondition<Boolean> getWaitforExpectedText(String expectedText,By by) {
    	return  new ExpectedCondition<Boolean>() {
	        public Boolean apply(WebDriver d) {
	        	return edgeWorkaroundExpectedTextTest(expectedText,by);
	        }
	    };
	}
	
	private  boolean edgeWorkaroundExpectedTextTest(String expectedText,By by) {
		//I don't know why we have to wrap this with a try/catch.  The wai.until method has one
    	try {
		   String text = driver.findElement(by).getText();
		   System.out.println("expectedText="+expectedText);
		   System.out.println("actualText="+text);
		   return expectedText.equals(text);
    	} catch (RuntimeException e) {
			return false;
		}		
	}

	protected void submitCssSelector(String cssSelector) {
		System.out.println(cssSelector);
		driver.findElement(getBy("div.container form input[type='text']")).sendKeys(cssSelector);
		System.out.println("AAAA");
		By submitButtonBy = getBy("div.container form button#submitButton");
		System.out.println("BBBB");
		wait.until(ExpectedConditions.elementToBeClickable(submitButtonBy));
		System.out.println("CCCC");

		driver.findElement(submitButtonBy).click();
	}
	
	protected void testErrorMessage(String cssSelector) throws NiceCssSelectorStringForOutputException 
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
			converter.niceConvertCssSelectorToXpathForOutput(adjustedCssSelector);
		}
		catch (NiceCssSelectorStringForOutputException e)
		{
			err=e.getMessage();
		}
		assertTrue(wait.until(getWaitforExpectedText(err,errorMessageBy)));
		//System.out.println(driver.findElement(errorMessageBy).getText());

	    String cssInputRowString = driver.findElement(getBy("div#cssInputGridText")).getText();
		System.out.println("cssInputRowString="+cssInputRowString);
      	if(adjustedCssSelector!=null)
      	{
      		assertTrue(adjustedCssSelector.equals(cssInputRowString));
      	}
      	else
      	{
      		assertTrue(cssInputRowString.isEmpty());
      	}
//		assertTrue(driver.findElement(getBy("div.content form i[class='fa fa-times-circle']")).isDisplayed());
	}
	
	private Map<String,String> getUrlToPageTitleMap() {
		HashMap<String,String> urlToPageTitleMap = new HashMap<>();
		urlToPageTitleMap.put("https://github.com/sam-rosenthal/java-cssSelector-to-xpath","GitHub - sam-rosenthal/java-cssSelector-to-xpath");
		urlToPageTitleMap.put("1:https://github.com/sam-rosenthal/java-cssSelector-to-xpath/blob/samdev/README.md","java-cssSelector-to-xpath/README.md at samdev · sam-rosenthal/java-cssSelector-to-xpath · GitHub");
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
		urlToPageTitleMap.put("https://github.com/sam-rosenthal/java-cssSelector-to-xpath/blob/samdev/README.md","java-cssSelector-to-xpath/README.md at samdev · sam-rosenthal/java-cssSelector-to-xpath · GitHub");
		urlToPageTitleMap.put("https://github.com/sam-rosenthal/java-cssSelector-to-xpath/tree/samdev/src/test/java/org/sam/rosenthal/cssselectortoxpath/utilities","java-cssSelector-to-xpath/src/test/java/org/sam/rosenthal/cssselectortoxpath/utilities at samdev · sam-rosenthal/java-cssSelector-to-xpath · GitHub");
		urlToPageTitleMap.put("https://github.com/sam-rosenthal/java-cssSelector-to-xpath/tree/samdev/src/test/java/org/sam/rosenthal/selenium","java-cssSelector-to-xpath/src/test/java/org/sam/rosenthal/selenium at samdev · sam-rosenthal/java-cssSelector-to-xpath · GitHub");
		urlToPageTitleMap.put(cssToXpathUrl+"/TestPage","CSS Selector to XPath");
		urlToPageTitleMap.put("mailto:ser259@cornell.edu",null); //this link is to email, doesn't correspond to a webpage

		return urlToPageTitleMap;
	}
	
}