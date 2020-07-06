package org.sam.rosenthal.cssselectortoxpathrest.httpclient;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpMethod;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sam.rosenthal.cssselectortoxpath.utilities.CssElementCombinatorPairsToXpath;
import org.sam.rosenthal.cssselectortoxpath.utilities.CssSelectorToXPathConverterException;
import org.sam.rosenthal.cssselectortoxpath.utilities.basetestcases.BaseCssSelectorToXpathTestCase;
import org.sam.rosenthal.cssselectortoxpathrest.restservice.CssSelectorIn;
import org.sam.rosenthal.cssselectortoxpathrest.restservice.InvalidCssSelector;
import org.sam.rosenthal.cssselectortoxpathrest.restservice.XpathOut;

import com.google.appengine.repackaged.com.google.gson.Gson;

public class RestCssSelectorToXpathTest {
	private static final int REST_PORT = 8080;
	private static final String BASE_REST_URL = "http://localhost:"+REST_PORT+"/cssSelectorToXpath/";
	private static Process restAppProcess;
	private Gson gson = new Gson();
	private	HttpClient httpClient;

	
	@BeforeClass
	public static void startRestApplicationProcess() throws IOException
	{
		System.out.println("In @BeforeClass startRestApplicationProcess");
		String classPath = System.getProperty("java.class.path");
		String javaExe = System.getProperty("java.home")+"/bin/java";
		System.out.println("java exe="+javaExe);
		ProcessBuilder builder = new ProcessBuilder(new String[]{javaExe,"-cp",classPath,"org.sam.rosenthal.cssselectortoxpathrest.CssSelectorToXpathRestApplication"});
		builder.redirectOutput(Redirect.INHERIT);
		builder.redirectError(Redirect.INHERIT);
		System.out.println("Before start process startRestApplicationProcess");
		restAppProcess = builder.start();
		//Since linux doesn't wait when services aren't up, adding sleep to ensure tests work on windows and linux
		try {
			Thread.sleep(10000L);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		System.out.println("out startRestApplicationProcess");
	}
	
	@AfterClass
	public static void endRestApplicationProcess()
	{
		if(restAppProcess!=null)
		{
			restAppProcess.destroy();
			restAppProcess=null;
		}
	}
	
	@Test
	public void testBasicTestCases() throws CssSelectorToXPathConverterException, IOException, InterruptedException {
		List<BaseCssSelectorToXpathTestCase> baseCases=BaseCssSelectorToXpathTestCase.getBaseCssSelectorToXpathTestCases(true);
		test(true, baseCases, BaseCssSelectorToXpathTestCase::getCssSelector, BaseCssSelectorToXpathTestCase::getExpectedXpath, 200, this::getXpathFromXpathOutJsonString );
	}
	
	@Test
	public void testExceptionTestCases() throws CssSelectorToXPathConverterException, IOException, InterruptedException {
		Map<String,String> baseExceptions=BaseCssSelectorToXpathTestCase.getBaseCssSelectorToXpathExceptionTestCases();
		test(false, baseExceptions.entrySet(), Entry<String, String>::getKey, Entry<String, String>::getValue, 400, this::getXpathFromInvalidCssSelectorJsonString);
	}
	
	@Test
	public void testVersion() throws CssSelectorToXPathConverterException, IOException, InterruptedException {
		ContentResponse response = getResponse("version");
		assertEquals(200,  response.getStatus());
		assertEquals(new CssElementCombinatorPairsToXpath().getVersionNumber(), response.getContentAsString());
	}
	
	public <T> void test(boolean isBasic, Collection<T> testCaseCollection, 
			Function<T, String> cssSelectorFunction,
			Function<T, String> xpathFunction,
			int expectedStatusCode,
			Function<String, String> jsonToXpath) throws CssSelectorToXPathConverterException, IOException, InterruptedException
	{
		for(T testCase : testCaseCollection)
		{
			String cssSelector = cssSelectorFunction.apply(testCase); 
			String expectedXpath = xpathFunction.apply(testCase); 
			assertJsonAndStatus(cssSelector, expectedXpath, expectedStatusCode, jsonToXpath);
		}
	}
	
	protected ContentResponse postResponse(String cssSelector) {
		return sendRequest(()->{
			String cssJson = gson.toJson(new CssSelectorIn(cssSelector));
			Request request = httpClient.newRequest(BASE_REST_URL+"convert");
			request.content(new StringContentProvider(cssJson), "application/json");
			request.method(HttpMethod.POST);
			return request;
		});
	}
	
	protected ContentResponse getResponse(String endpoint) {
		return sendRequest(()->httpClient.newRequest(BASE_REST_URL+endpoint));
	}
	
	protected ContentResponse sendRequest(Supplier<Request> requestSupplier) {
		httpClient = new HttpClient();
		try {
			httpClient.start();
			return requestSupplier.get().send();
		} 
		catch (Exception e) 
		{
			throw new RuntimeException(e);
		}
		finally
		{
			try 
			{
				httpClient.stop();
			} 
			catch (Exception e) 
			{
				throw new RuntimeException(e);
			}			
		}
	}
	 
	protected void assertJsonAndStatus(String cssSelector, String expectedXpath, int expectedStatusCode, Function<String, String> jsonToXpath ) {
		ContentResponse response = postResponse(cssSelector);
		String jsonXml = response.getContentAsString();
		int actualStatusCode = response.getStatus();
		String actualXpath = jsonToXpath.apply(jsonXml);
		System.out.println(cssSelector+" "+ actualXpath);
		assertEquals(expectedStatusCode, actualStatusCode);
		assertEquals("CSS Selector="+cssSelector, expectedXpath, actualXpath);		
	}
	
	protected String getXpathFromXpathOutJsonString(String jsonXml)
	{
		XpathOut actualXpath = gson.fromJson(jsonXml, XpathOut.class);
		return actualXpath.getXpath();
	}
	
	protected String getXpathFromInvalidCssSelectorJsonString(String jsonXml)
	{
		InvalidCssSelector invalidCssSelector = gson.fromJson(jsonXml, InvalidCssSelector.class);
		return invalidCssSelector.getMessage();
	}
}
