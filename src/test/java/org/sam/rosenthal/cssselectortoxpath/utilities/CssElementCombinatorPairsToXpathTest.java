package org.sam.rosenthal.cssselectortoxpath.utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.sam.rosenthal.cssselectortoxpath.model.CssCombinatorType;
import org.sam.rosenthal.cssselectortoxpath.model.CssElementCombinatorPair;
import org.sam.rosenthal.cssselectortoxpath.utilities.basetestcases.BaseCssSelectorToXpathTestCase;

public class CssElementCombinatorPairsToXpathTest 
{
	private static CssElementCombinatorPairsToXpath elementCombinatorPair =new CssElementCombinatorPairsToXpath();

	@SafeVarargs
	private final static <T> List<T> asList(T... expectedOutput) {
		return Arrays.asList(expectedOutput);
	}
	
	@Test
	public void elementCombinatorPairListConversionTest() throws CssSelectorToXPathConverterException
	{
		testCssElementCombinatorPairsToXpath("//A", new CssElementCombinatorPair(null, "A"));
		testCssElementCombinatorPairsToXpath("//A//B",new CssElementCombinatorPair(null, "A"), new CssElementCombinatorPair(CssCombinatorType.SPACE, "B"));
		testCssElementCombinatorPairsToXpath("//A/B",new CssElementCombinatorPair(null, "A"), new CssElementCombinatorPair(CssCombinatorType.GREATER_THAN, "B"));
		testCssElementCombinatorPairsToXpath("//A/following-sibling::*[1]/self::B",new CssElementCombinatorPair(null, "A"), new CssElementCombinatorPair(CssCombinatorType.PLUS, "B"));
		testCssElementCombinatorPairsToXpath("//A/following-sibling::B",new CssElementCombinatorPair(null, "A"), new CssElementCombinatorPair(CssCombinatorType.TILDA, "B"));
		testCssElementCombinatorPairsToXpath("//A//B/C",new CssElementCombinatorPair(null, "A"), new CssElementCombinatorPair(CssCombinatorType.SPACE, "B"),new CssElementCombinatorPair(CssCombinatorType.GREATER_THAN, "C"));
		testCssElementCombinatorPairsToXpath("//A//B/C/following-sibling::*[1]/self::D",new CssElementCombinatorPair(null, "A"), new CssElementCombinatorPair(CssCombinatorType.SPACE, "B"),new CssElementCombinatorPair(CssCombinatorType.GREATER_THAN, "C"),
				new CssElementCombinatorPair(CssCombinatorType.PLUS, "D"));
		testCssElementCombinatorPairsToXpath("//A//B/C/following-sibling::*[1]/self::D/following-sibling::E",new CssElementCombinatorPair(null, "A"), new CssElementCombinatorPair(CssCombinatorType.SPACE, "B"),new CssElementCombinatorPair(CssCombinatorType.GREATER_THAN, "C"),
				new CssElementCombinatorPair(CssCombinatorType.PLUS, "D"),new CssElementCombinatorPair(CssCombinatorType.TILDA, "E"));
		
		testCssElementCombinatorPairsToXpath("//A[@yy=\"-\"]",new CssElementCombinatorPair(null,"A[yy=\"-\"]"));
		testCssElementCombinatorPairsToXpath("//*[@A=\"B\"][@C=\"D\"]",new CssElementCombinatorPair(null,"[A=\"B\"][C=\"D\"]"));
		testCssElementCombinatorPairsToXpath("//A1[@BB=\"-\"][@CCC=\"123\"][@D=\"-\"]",new CssElementCombinatorPair(null,"A1[BB=\"-\"][CCC=\"123\"][D=\"-\"]"));	
		
		testCssElementCombinatorPairsToXpath("//A[starts-with(@B,\"C\")]",new CssElementCombinatorPair(null,"A[B^='C']"));
		testCssElementCombinatorPairsToXpath("//A[contains(@B,\"C\")]",new CssElementCombinatorPair(null,"A[B*='C']"));
		testCssElementCombinatorPairsToXpath("//A[substring(@B,string-length(@B)-string-length(\"C\")+1)=\"C\"]",new CssElementCombinatorPair(null,"A[B$='C']"));

	}
	
	public void testCssElementCombinatorPairsToXpath(String expectedOutput, CssElementCombinatorPair... elementCombinatorPairsInput) throws CssSelectorToXPathConverterException  {
		String xpath=elementCombinatorPair.cssElementCombinatorPairListConversion(asList(elementCombinatorPairsInput));
		//System.out.println(xpath);
		assertEquals("CssElementCombinatorPairList="+asList(elementCombinatorPairsInput),expectedOutput,xpath);
	}

	
	@Test
	public void elementCombinatorPairListListConversionTest() throws CssSelectorToXPathConverterException
	{
		testCssElementCombinatorPairListListToXpath(asList(asList(new CssElementCombinatorPair(null, "A"))),"//A");
		testCssElementCombinatorPairListListToXpath(asList(asList(new CssElementCombinatorPair(null, "A")), asList(new CssElementCombinatorPair(null, "B")), asList(new CssElementCombinatorPair(null, "C"))),"(//A)|(//B)|(//C)");
		testCssElementCombinatorPairListListToXpath(asList(asList(new CssElementCombinatorPair(null, "A"), new CssElementCombinatorPair(CssCombinatorType.SPACE, "B")),asList(new CssElementCombinatorPair(null, "A"), new CssElementCombinatorPair(CssCombinatorType.PLUS, "B"))),"(//A//B)|(//A/following-sibling::*[1]/self::B)");
		testCssElementCombinatorPairListListToXpath(asList(asList(new CssElementCombinatorPair(null, "A"), new CssElementCombinatorPair(CssCombinatorType.SPACE, "B")),asList(new CssElementCombinatorPair(null, "A"), new CssElementCombinatorPair(CssCombinatorType.PLUS, "B"))),"(//A//B)|(//A/following-sibling::*[1]/self::B)");
	}
	
	public void testCssElementCombinatorPairListListToXpath(List<List<CssElementCombinatorPair>> elementCombinatorPairListInput, String expectedOutput) throws CssSelectorToXPathConverterException  {
		String xpath=elementCombinatorPair.cssElementCombinatorPairListListConversion(elementCombinatorPairListInput);
		//System.out.println(xpath);
		assertEquals("CssElementCombinatorPairListList="+elementCombinatorPairListInput,expectedOutput,xpath);
	}
	
	@Test
	public void convertBasicCssStringToXpathStringTest() throws CssSelectorToXPathConverterException
	{
		List<BaseCssSelectorToXpathTestCase> baseCases=BaseCssSelectorToXpathTestCase.getBaseCssSelectorToXpathTestCases(true);
		for(BaseCssSelectorToXpathTestCase cssSelectorToXpathCase: baseCases)
		{
			testConvertCssStringToXpathString(cssSelectorToXpathCase.getCssSelector(),cssSelectorToXpathCase.getExpectedXpath());
		}
	}
	
	@Test
	public void testCssToXpathBasicException() {
		Map<String,String> baseCases=BaseCssSelectorToXpathTestCase.getBaseCssSelectorToXpathExceptionTestCases();
		for(Map.Entry<String,String> baseExceptionCase:baseCases.entrySet())
		{
			String cssSelector=baseExceptionCase.getKey();
			String expectedErrorMessage=baseExceptionCase.getValue();
			System.out.println(expectedErrorMessage);
			testCssToXpathBasicException(cssSelector, expectedErrorMessage);
		}
		
	}
	
	private void testCssToXpathBasicException(String cssSelector, String expectedErrorMessage) {
		try {
			String xpath=elementCombinatorPair.convertCssSelectorStringToXpathString(cssSelector);
			fail("CssSelector="+cssSelector+", should have been invalid, but xpath string return value="+xpath);
		} catch (CssSelectorToXPathConverterException e) {
			assertEquals(expectedErrorMessage,e.getMessage());
			//success
		}		
	}
	
	@Test
	public void testCssToXpathFirstLastOfTypeBasicException() {
		Map<String,String> cases = new HashMap<String, String>();
		cases.put("*:first-of-type", CssSelectorToXPathConverterInvalidFirstLastOnlyOfType.FIRST_LAST_ONLY_OF_TYPE_UNSUPPORTED_ERROR_FORMAT);
		cases.put(":first-of-type", CssSelectorToXPathConverterInvalidFirstLastOnlyOfType.FIRST_LAST_ONLY_OF_TYPE_UNSUPPORTED_ERROR_FORMAT);
		cases.put("*:last-of-type", CssSelectorToXPathConverterInvalidFirstLastOnlyOfType.FIRST_LAST_ONLY_OF_TYPE_UNSUPPORTED_ERROR_FORMAT);
		cases.put(":last-of-type", CssSelectorToXPathConverterInvalidFirstLastOnlyOfType.FIRST_LAST_ONLY_OF_TYPE_UNSUPPORTED_ERROR_FORMAT);
		cases.put("x:first-of-type *:first-of-type", CssSelectorToXPathConverterInvalidFirstLastOnlyOfType.FIRST_LAST_ONLY_OF_TYPE_UNSUPPORTED_ERROR_FORMAT);
		cases.put("x:last-of-type y :last-of-type", CssSelectorToXPathConverterInvalidFirstLastOnlyOfType.FIRST_LAST_ONLY_OF_TYPE_UNSUPPORTED_ERROR_FORMAT);

		for(Map.Entry<String,String> exceptionCase:cases.entrySet())
		{
			String cssSelector=exceptionCase.getKey();
			String expectedErrorMessage=exceptionCase.getValue();
			System.out.println(expectedErrorMessage);
			testCssToXpathFirstLastOfTypeBasicException(cssSelector, expectedErrorMessage);
		}
	}
	
	private void testCssToXpathFirstLastOfTypeBasicException(String cssSelector, String expectedErrorMessage) {
		try {
			String xpath=elementCombinatorPair.convertCssSelectorStringToXpathString(cssSelector);
			fail("CssSelector="+cssSelector+", should have been invalid, but xpath string return value="+xpath);
		} catch (CssSelectorToXPathConverterException e) {
			assertEquals(expectedErrorMessage,e.getMessage());
			//success
		}		
	}
	

	@Test
	public void convertComplexCssStringToXpathStringTest() throws CssSelectorToXPathConverterException
	{
		testConvertCssStringToXpathString("A,B,C","(//A)|(//B)|(//C)");
	
		testConvertCssStringToXpathString("A B,A>B","(//A//B)|(//A/B)");
		testConvertCssStringToXpathString("A B, A>B","(//A//B)|(//A/B)");
		testConvertCssStringToXpathString("A B ,A>B","(//A//B)|(//A/B)");
		testConvertCssStringToXpathString("A B , A>B","(//A//B)|(//A/B)");
		testConvertCssStringToXpathString("A  B , A >B","(//A//B)|(//A/B)");
		testConvertCssStringToXpathString("A B , A   >B","(//A//B)|(//A/B)");
		testConvertCssStringToXpathString("A    B , A   > B","(//A//B)|(//A/B)");

		testConvertCssStringToXpathString("A  B, A >B","(//A//B)|(//A/B)");

		testConvertCssStringToXpathString("A ~B, A+ B","(//A/following-sibling::B)|(//A/following-sibling::*[1]/self::B)");

		testConvertCssStringToXpathString("A~B,A+B","(//A/following-sibling::B)|(//A/following-sibling::*[1]/self::B)");

		testConvertCssStringToXpathString("A[B^=  C  ]","//A[starts-with(@B,\"C\")]");
		testConvertCssStringToXpathString("A[B*= C]","//A[contains(@B,\"C\")]");	
		testConvertCssStringToXpathString("A[B$=C]","//A[substring(@B,string-length(@B)-string-length(\"C\")+1)=\"C\"]");	
		
		testConvertCssStringToXpathString("A[B=  \"C\" ]","//A[@B=\"C\"]");
		testConvertCssStringToXpathString("A[B=  \"C \" ]","//A[@B=\"C \"]");
		testConvertCssStringToXpathString("A[B=  \"x   C\" ]","//A[@B=\"x C\"]");
		testConvertCssStringToXpathString("A[B=  \"x\tC\" ]","//A[@B=\"x C\"]");


		testConvertCssStringToXpathString("#B","//*[@id=\"B\"]");
		testConvertCssStringToXpathString("[B=\"C\"]","//*[@B=\"C\"]");
		testConvertCssStringToXpathString("[B^=\"C\"]","//*[starts-with(@B,\"C\")]");
		testConvertCssStringToXpathString("[B*=\"C\"]","//*[contains(@B,\"C\")]");
		
		testConvertCssStringToXpathString("A[yy=\"-\"]","//A[@yy=\"-\"]");
		testConvertCssStringToXpathString("[A=\"B\"][C=\"D\"]","//*[@A=\"B\"][@C=\"D\"]");
		testConvertCssStringToXpathString("A1[BB=\"-\"][CCC=\"123\"][D=\"-\"]","//A1[@BB=\"-\"][@CCC=\"123\"][@D=\"-\"]");
		
		testConvertCssStringToXpathString("A[B=\"C\"]>E[F=\"G\"]","//A[@B=\"C\"]/E[@F=\"G\"]");
		testConvertCssStringToXpathString("A[B=\"C\"]>E[F=\"G\"],H","(//A[@B=\"C\"]/E[@F=\"G\"])|(//H)");
		
		testConvertCssStringToXpathString("A#B>E","//A[@id=\"B\"]/E");

		testConvertCssStringToXpathString("A[B*=\"C\"]","//A[contains(@B,\"C\")]");
		testConvertCssStringToXpathString("[A*=\"B\"][C=\"D\"]","//*[contains(@A,\"B\")][@C=\"D\"]");
		
		testConvertCssStringToXpathString("A[B^=\"C\"]","//A[starts-with(@B,\"C\")]");
		testConvertCssStringToXpathString("[A*=\"B\"][C=\"D\"]~E[F^=\"G\"]","//*[contains(@A,\"B\")][@C=\"D\"]/following-sibling::E[starts-with(@F,\"G\")]");
		testConvertCssStringToXpathString("A[B*=\" \"]","//A[contains(@B,\" \")]");	
		
		testConvertCssStringToXpathString("A>B,[B$=\"C\"]","(//A/B)|(//*[substring(@B,string-length(@B)-string-length(\"C\")+1)=\"C\"])");	
		testConvertCssStringToXpathString("A.B+C","//A[contains(concat(\" \",normalize-space(@class),\" \"),\" B \")]/following-sibling::*[1]/self::C");	
		testConvertCssStringToXpathString("A[B~=\"C\"][D$=\"E\"],F","(//A[contains(concat(\" \",normalize-space(@B),\" \"),\" C \")][substring(@D,string-length(@D)-string-length(\"E\")+1)=\"E\"])|(//F)");	
		testConvertCssStringToXpathString("A[B|=\"C\"],[D~=\"E\"]+F","(//A[starts-with(@B,concat(\"C\",\"-\")) or @B=\"C\"])|(//*[contains(concat(\" \",normalize-space(@D),\" \"),\" E \")]/following-sibling::*[1]/self::F)");
		
		testConvertCssStringToXpathString("[B][C]","//*[@B][@C]");	
		
		testConvertCssStringToXpathString("a#b[c=#][d='a#b']","//a[@id=\"b\"][@c=\"#\"][@d=\"a#b\"]");	
		testConvertCssStringToXpathString("a[b=#]>c[d='a#b']","//a[@b=\"#\"]/c[@d=\"a#b\"]");
		testConvertCssStringToXpathString("a[b=##],c[d='a##b']","(//a[@b=\"##\"])|(//c[@d=\"a##b\"])");
		testConvertCssStringToXpathString("a~b#c[d=#e],h#j[k='l#']>m[n='o#p']","(//a/following-sibling::b[@id=\"c\"][@d=\"#e\"])|(//h[@id=\"j\"][@k=\"l#\"]/m[@n=\"o#p\"])");	
		
		testConvertCssStringToXpathString("a[c$=.d.]","//a[substring(@c,string-length(@c)-string-length(\".d.\")+1)=\".d.\"]");	
		testConvertCssStringToXpathString("a.b[c=.] d[e='f.']","//a[contains(concat(\" \",normalize-space(@class),\" \"),\" b \")][@c=\".\"]//d[@e=\"f.\"]");
		testConvertCssStringToXpathString("a.bc[d*=.e], h.j[k='l']~m[n='o.p']","(//a[contains(concat(\" \",normalize-space(@class),\" \"),\" bc \")][contains(@d,\".e\")])|(//h[contains(concat(\" \",normalize-space(@class),\" \"),\" j \")][@k=\"l\"]/following-sibling::m[@n=\"o.p\"])");	
		
		testConvertCssStringToXpathString("a#b.c[d$='e.#f'], g[h='i...j'], k[l='m###n']","(//a[@id=\"b\"][contains(concat(\" \",normalize-space(@class),\" \"),\" c \")][substring(@d,string-length(@d)-string-length(\"e.#f\")+1)=\"e.#f\"])|(//g[@h=\"i...j\"])|(//k[@l=\"m###n\"])");	

		testConvertCssStringToXpathString("[B][C]","//*[@B][@C]");	
		
		testConvertCssStringToXpathString(":empty","//*[not(*) and .=\"\"]");
		testConvertCssStringToXpathString("x:empty","//x[not(*) and .=\"\"]");
		testConvertCssStringToXpathString("*:empty","//*[not(*) and .=\"\"]");
		testConvertCssStringToXpathString("a:empty b:empty","//a[not(*) and .=\"\"]//b[not(*) and .=\"\"]");
		testConvertCssStringToXpathString("div:empty","//div[not(*) and .=\"\"]");
		testConvertCssStringToXpathString("div:empty:empty","//div[not(*) and .=\"\"]");
	 	testConvertCssStringToXpathString(":empty[id=\"6\"]","//*[not(*) and .=\"\"][@id=\"6\"]");
		testConvertCssStringToXpathString("x y:empty[id=\"6\"]","//x//y[not(*) and .=\"\"][@id=\"6\"]");
		testConvertCssStringToXpathString("x:empty>y[id=\"6\"]","//x[not(*) and .=\"\"]/y[@id=\"6\"]");
		testConvertCssStringToXpathString("x:empty+y:empty","//x[not(*) and .=\"\"]/following-sibling::*[1]/self::y[not(*) and .=\"\"]");
		
		testConvertCssStringToXpathString(":only-child","//*[count(preceding-sibling::*)=0][count(following-sibling::*)=0]");
		testConvertCssStringToXpathString("XXX:only-child","//XXX[count(preceding-sibling::*)=0][count(following-sibling::*)=0]");
		testConvertCssStringToXpathString("div:only-child+[class^='abc']", "//div[count(preceding-sibling::*)=0][count(following-sibling::*)=0]/following-sibling::*[1]/self::*[starts-with(@class,\"abc\")]");
		testConvertCssStringToXpathString("div:only-child>[id^='samm']", "//div[count(preceding-sibling::*)=0][count(following-sibling::*)=0]/*[starts-with(@id,\"samm\")]");

		testConvertCssStringToXpathString("a:first-of-type","//a[count(preceding-sibling::a)=0]");
		testConvertCssStringToXpathString("x:first-of-type y:first-of-type","//x[count(preceding-sibling::x)=0]//y[count(preceding-sibling::y)=0]");
		testConvertCssStringToXpathString("a:last-of-type","//a[count(following-sibling::a)=0]");
		testConvertCssStringToXpathString("x:last-of-type:first-of-type","//x[count(following-sibling::x)=0][count(preceding-sibling::x)=0]");
		testConvertCssStringToXpathString("x:first-of-type:last-of-type","//x[count(preceding-sibling::x)=0][count(following-sibling::x)=0]");
		testConvertCssStringToXpathString("x:last-of-type y:last-of-type","//x[count(following-sibling::x)=0]//y[count(following-sibling::y)=0]");	
		
		testConvertCssStringToXpathString(":first-child","//*[count(preceding-sibling::*)=0]");
		testConvertCssStringToXpathString("*:first-child","//*[count(preceding-sibling::*)=0]");
		testConvertCssStringToXpathString(":last-child","//*[count(following-sibling::*)=0]");
		testConvertCssStringToXpathString("*:last-child","//*[count(following-sibling::*)=0]");
		testConvertCssStringToXpathString("x:first-child","//x[count(preceding-sibling::*)=0]");
		testConvertCssStringToXpathString("x:first-child y:first-child","//x[count(preceding-sibling::*)=0]//y[count(preceding-sibling::*)=0]");
		testConvertCssStringToXpathString("x:first-child :first-child","//x[count(preceding-sibling::*)=0]//*[count(preceding-sibling::*)=0]");
		testConvertCssStringToXpathString("x:first-child y:last-child","//x[count(preceding-sibling::*)=0]//y[count(following-sibling::*)=0]");
		testConvertCssStringToXpathString("x:last-child y:first-child","//x[count(following-sibling::*)=0]//y[count(preceding-sibling::*)=0]");

		//Test for trimming
		testConvertCssStringToXpathString("div.x.x","//div[contains(concat(\" \",normalize-space(@class),\" \"),\" x \")]");
		testConvertCssStringToXpathString("div#x#x","//div[@id=\"x\"]");
		testConvertCssStringToXpathString("div#x.y#x.z","//div[@id=\"x\"][contains(concat(\" \",normalize-space(@class),\" \"),\" y \")][contains(concat(\" \",normalize-space(@class),\" \"),\" z \")]");
		testConvertCssStringToXpathString("div>span[class='sam'][class='sam']","//div/span[@class=\"sam\"]");
		testConvertCssStringToXpathString(":empty:empty+:first-child:empty:first-child","//*[not(*) and .=\"\"]/following-sibling::*[1]/self::*[count(preceding-sibling::*)=0][not(*) and .=\"\"]");

		
		//Tests for trimming/simplifying of psuedo classes
		testConvertCssStringToXpathString("div:first-child:first-of-type","//div[count(preceding-sibling::*)=0]");
		testConvertCssStringToXpathString("div:only-child:first-of-type","//div[count(preceding-sibling::*)=0][count(following-sibling::*)=0]");
		testConvertCssStringToXpathString("div:first-child:first-of-type","//div[count(preceding-sibling::*)=0]");
		testConvertCssStringToXpathString("div:last-child:last-of-type","//div[count(following-sibling::*)=0]");
		testConvertCssStringToXpathString("div:only-child:last-of-type","//div[count(preceding-sibling::*)=0][count(following-sibling::*)=0]");
		testConvertCssStringToXpathString("div:last-child:last-of-type","//div[count(following-sibling::*)=0]");		
		testConvertCssStringToXpathString("div:first-of-type:only-of-type","//div[count(preceding-sibling::div)=0][count(following-sibling::div)=0]");
		testConvertCssStringToXpathString("div:last-of-type:only-of-type","//div[count(preceding-sibling::div)=0][count(following-sibling::div)=0]");
		testConvertCssStringToXpathString("div:only-child:only-of-type","//div[count(preceding-sibling::*)=0][count(following-sibling::*)=0]");

	}

	protected void testConvertCssStringToXpathString(String cssSelector, String expectedOutput) throws CssSelectorToXPathConverterException  {
		String xpath=elementCombinatorPair.convertCssSelectorStringToXpathString(cssSelector);
		//System.out.println(xpath);
		assertEquals("CssString="+cssSelector,expectedOutput,xpath);
	}
	
	@Test
	public void mainGoTest() throws NiceCssSelectorStringForOutputException
	{
		String versionNumber = elementCombinatorPair.getVersionNumber();
		testMainGo(new String[]{"-version"},versionNumber);
		testMainGo(new String[]{"-v"},versionNumber);
		
		String usage = elementCombinatorPair.getUsageString();
		testMainGo(new String[]{"-help"}, usage);
		testMainGo(new String[]{"-h"}, usage);
		testMainGo(new String[]{"test"},"//test");
	}
	
	@Test
	public void mainGoBExceptionTest()
	{
		testMainGoException(new String[]{"A","test"},true);
		testMainGoException(new String[]{"This", "is ","a ","test"},true);
		testMainGoException(new String[]{"[]"},false);
		testMainGoException(new String[]{},true);
		testMainGoException(null,true);
	}
	private void testMainGo(String[] args, String expected) throws NiceCssSelectorStringForOutputException
	{
		String version=elementCombinatorPair.mainGo(args);
		assertTrue(args[0].toString(),version.contains(expected));
	}
		
	private void testMainGoException(String[] args, boolean isRuntimeException) {
		try {
			System.out.println(elementCombinatorPair.mainGo(args));
			fail(""+args.length+((args.length>0)?args[0]:"no args"));
		} catch (NiceCssSelectorStringForOutputException e) {
			assertFalse(isRuntimeException);
		} catch(RuntimeException e)
		{
			assertTrue(isRuntimeException);
		}
	}
}
