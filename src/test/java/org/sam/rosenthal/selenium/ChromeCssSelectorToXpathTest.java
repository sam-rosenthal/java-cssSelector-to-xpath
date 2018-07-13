package org.sam.rosenthal.selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sam.rosenthal.cssselectortoxpath.utilities.CssElementCombinatorPairsToXpath;
import org.sam.rosenthal.cssselectortoxpath.utilities.CssSelectorToXPathConverterException;

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
	public void test() throws CssSelectorToXPathConverterException {
		driver.get(properties.getProperty("selenium.CSS_TO_XPATH_URL"));
		assertEquals("CSS Selector to XPath",driver.getTitle());
		assertEquals("CSS Selector to XPath Converter",driver.findElement(By.cssSelector("div.content h1")).getText());
		assertEquals("Fork me on GitHub",driver.findElement(By.cssSelector("body>a img")).getAttribute("alt"));
		assertEquals("Implementation Notes",driver.findElement(By.cssSelector("div#footer fieldset.assumptions legend")).getText());
		assertEquals("Helpful Links/More Info",driver.findElement(By.cssSelector("div#footer fieldset.links legend")).getText());
		
		String cssSelector1="div.content h1";
		driver.findElement(By.cssSelector("div.content form input[type='text']")).sendKeys(cssSelector1);
		driver.findElement(By.cssSelector("div.content form input[type='submit']")).click();
		String expectedXpath= new CssElementCombinatorPairsToXpath().convertCssSelectorStringToXpathString(cssSelector1);
		assertTrue(expectedXpath.length()>0);
		assertNotEquals(expectedXpath, cssSelector1);
		
	    WebDriverWait wait = new WebDriverWait(driver, 10);
	 
	    assertTrue(wait.until((new ExpectedCondition<Boolean>() {
	        public Boolean apply(WebDriver d) {
	            return expectedXpath.equals(driver.findElement(By.cssSelector("table#inputOutputTable tr#xpathOutputRow>td#xpathOutputString>span")).getText());
	        }
	    })));
	}
}