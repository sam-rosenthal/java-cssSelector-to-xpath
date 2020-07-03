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
//			System.out.println(expectedErrorMessage);
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
		cases.put("*:only-of-type", CssSelectorToXPathConverterInvalidFirstLastOnlyOfType.FIRST_LAST_ONLY_OF_TYPE_UNSUPPORTED_ERROR_FORMAT);
		cases.put(":only-of-type", CssSelectorToXPathConverterInvalidFirstLastOnlyOfType.FIRST_LAST_ONLY_OF_TYPE_UNSUPPORTED_ERROR_FORMAT);
		cases.put("x.a:only-of-type *:only-of-type", CssSelectorToXPathConverterInvalidFirstLastOnlyOfType.FIRST_LAST_ONLY_OF_TYPE_UNSUPPORTED_ERROR_FORMAT);

		for(Map.Entry<String,String> exceptionCase:cases.entrySet())
		{
			String cssSelector=exceptionCase.getKey();
			String expectedErrorMessage=exceptionCase.getValue();
//			System.out.println(expectedErrorMessage);
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
		testConvertCssStringToXpathString("x:empty>y[id='6']","//x[not(*) and .=\"\"]/y[@id=\"6\"]");
		testConvertCssStringToXpathString("x:empty+y:empty","//x[not(*) and .=\"\"]/following-sibling::*[1]/self::y[not(*) and .=\"\"]");
		
		testConvertCssStringToXpathString(":only-child","//*[count(preceding-sibling::*)=0][count(following-sibling::*)=0]");
		testConvertCssStringToXpathString("XXX:only-child","//XXX[count(preceding-sibling::*)=0][count(following-sibling::*)=0]");
		testConvertCssStringToXpathString("div:only-child+[class^='abc']", "//div[count(preceding-sibling::*)=0][count(following-sibling::*)=0]/following-sibling::*[1]/self::*[starts-with(@class,\"abc\")]");
		testConvertCssStringToXpathString("div:only-child>[id^='samm']", "//div[count(preceding-sibling::*)=0][count(following-sibling::*)=0]/*[starts-with(@id,\"samm\")]");

		testConvertCssStringToXpathString("a:first-of-type","//a[1]");
		testConvertCssStringToXpathString("x:first-of-type y:first-of-type","//x[1]//y[1]");
		testConvertCssStringToXpathString("a:last-of-type","//a[count(following-sibling::a)=0]");
		testConvertCssStringToXpathString("x:last-of-type:first-of-type","//x[1][count(following-sibling::x)=0]");
		testConvertCssStringToXpathString("x:first-of-type:last-of-type","//x[1][count(following-sibling::x)=0]");
		testConvertCssStringToXpathString("x:last-of-type y:last-of-type","//x[count(following-sibling::x)=0]//y[count(following-sibling::y)=0]");	
		testConvertCssStringToXpathString("a:only-of-type","//a[1][count(following-sibling::a)=0]");
		testConvertCssStringToXpathString("x:only-of-type>y:only-of-type","//x[1][count(following-sibling::x)=0]/y[1][count(following-sibling::y)=0]");
		testConvertCssStringToXpathString("div div[class^=\"score\"]:only-of-type","//div//div[starts-with(@class,\"score\")][1][count(following-sibling::div)=0]");

		
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
		testConvertCssStringToXpathString("div:first-of-type:only-of-type","//div[1][count(following-sibling::div)=0]");
		testConvertCssStringToXpathString("div:last-of-type:only-of-type","//div[1][count(following-sibling::div)=0]");
		testConvertCssStringToXpathString("div:only-child:only-of-type","//div[count(preceding-sibling::*)=0][count(following-sibling::*)=0]");
		
		testConvertCssStringToXpathString("div#abc:empty","//div[@id=\"abc\"][not(*) and .=\"\"]"); 	
		testConvertCssStringToXpathString("a.test:first-of-type","//a[1][contains(concat(\" \",normalize-space(@class),\" \"),\" test \")]"); 	
		testConvertCssStringToXpathString("#test:only-child","//*[@id=\"test\"][count(preceding-sibling::*)=0][count(following-sibling::*)=0]"); 	
		testConvertCssStringToXpathString(".sam:only-child","//*[contains(concat(\" \",normalize-space(@class),\" \"),\" sam \")][count(preceding-sibling::*)=0][count(following-sibling::*)=0]"); 
		testConvertCssStringToXpathString(".xxx:empty #yyy:empty","//*[contains(concat(\" \",normalize-space(@class),\" \"),\" xxx \")][not(*) and .=\"\"]//*[@id=\"yyy\"][not(*) and .=\"\"]");
		testConvertCssStringToXpathString("yyy:first-child div.zzz:empty","//yyy[count(preceding-sibling::*)=0]//div[contains(concat(\" \",normalize-space(@class),\" \"),\" zzz \")][not(*) and .=\"\"]");
		testConvertCssStringToXpathString("div:empty div#test:first-child","//div[not(*) and .=\"\"]//div[@id=\"test\"][count(preceding-sibling::*)=0]");
		testConvertCssStringToXpathString("div#xxx:empty yyy:empty div.zzz:empty","//div[@id=\"xxx\"][not(*) and .=\"\"]//yyy[not(*) and .=\"\"]//div[contains(concat(\" \",normalize-space(@class),\" \"),\" zzz \")][not(*) and .=\"\"]");
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
	
	 
	@Test
	public void testConverCssStringToXpathNthOfType() throws CssSelectorToXPathConverterException
	{
		testConvertCssStringToXpathString("a:nth-of-type( odd )","//a[(count(preceding-sibling::a)=0) or (((count(preceding-sibling::a)-0) mod 2)=0)]"); 	
		testConvertCssStringToXpathString("a:nth-of-type( Odd)","//a[(count(preceding-sibling::a)=0) or (((count(preceding-sibling::a)-0) mod 2)=0)]"); 	
		testConvertCssStringToXpathString("a:nth-of-type(OdD )","//a[(count(preceding-sibling::a)=0) or (((count(preceding-sibling::a)-0) mod 2)=0)]"); 	

		testConvertCssStringToXpathString("p:nth-of-type(    even   )","//p[((count(preceding-sibling::p)+1) mod 2)=0]"); 	
		testConvertCssStringToXpathString("p:nth-of-type(EVEN)","//p[((count(preceding-sibling::p)+1) mod 2)=0]"); 	
		
		testConvertCssStringToXpathString("div:nth-of-type(3)","//div[3]"); 	
		testConvertCssStringToXpathString("div:nth-of-type(+12)","//div[12]"); 	

		testConvertCssStringToXpathString("ul:nth-of-type(N)","//ul[((count(preceding-sibling::ul)+1) mod 1)=0]"); 
		testConvertCssStringToXpathString("ul:nth-of-type(n)","//ul[((count(preceding-sibling::ul)+1) mod 1)=0]"); 
		testConvertCssStringToXpathString("li:nth-of-type(3n    )","//li[((count(preceding-sibling::li)+1) mod 3)=0]"); 	
		testConvertCssStringToXpathString("input:nth-of-type(33n    )","//input[((count(preceding-sibling::input)+1) mod 33)=0]"); 	
		testConvertCssStringToXpathString("span:nth-of-type( +111n    )","//span[((count(preceding-sibling::span)+1) mod 111)=0]"); 	

		testConvertCssStringToXpathString("div:nth-of-type(n+1)","//div[((count(preceding-sibling::div)+1) mod 1)=0]"); 
		testConvertCssStringToXpathString("div:nth-of-type(+2n+2)","//div[((count(preceding-sibling::div)+1) mod 2)=0]"); 	
		testConvertCssStringToXpathString("div:nth-of-type(2n+2)","//div[((count(preceding-sibling::div)+1) mod 2)=0]"); 	
		testConvertCssStringToXpathString("span:nth-of-type(+3n+5)","//span[(count(preceding-sibling::span)=4) or (((count(preceding-sibling::span)>5) and (((count(preceding-sibling::span)-4) mod 3)=0)))]"); 	
		testConvertCssStringToXpathString("img:nth-of-type(3n+6)","//img[(count(preceding-sibling::img)=5) or (((count(preceding-sibling::img)>6) and (((count(preceding-sibling::img)-5) mod 3)=0)))]"); 	
		testConvertCssStringToXpathString("div:nth-of-type(5n+3)","//div[(count(preceding-sibling::div)=2) or (((count(preceding-sibling::div)-2) mod 5)=0)]"); 
		testConvertCssStringToXpathString("div:nth-of-type(3n+1)","//div[(count(preceding-sibling::div)=0) or (((count(preceding-sibling::div)-0) mod 3)=0)]"); 
		
		testConvertCssStringToXpathString("div:nth-of-type(n-1)","//div[((count(preceding-sibling::div)+1) mod 1)=0]"); 	
		testConvertCssStringToXpathString("div:nth-of-type(n-2)","//div[((count(preceding-sibling::div)+1) mod 1)=0]"); 	
		testConvertCssStringToXpathString("div:nth-of-type(3n-5)","//div[(count(preceding-sibling::div)=0) or (((count(preceding-sibling::div)-0) mod 3)=0)]"); 	
		testConvertCssStringToXpathString("div:nth-of-type(5n-2)","//div[(count(preceding-sibling::div)=2) or (((count(preceding-sibling::div)-2) mod 5)=0)]"); 	

		testConvertCssStringToXpathString("div:nth-of-type(-n+1)","//div[1]"); 	
		testConvertCssStringToXpathString("div:nth-of-type(-2n+1)","//div[1]"); 	
		testConvertCssStringToXpathString("div:nth-of-type(-5n + 3 )","//div[3]"); 	

		testConvertCssStringToXpathString("div:nth-of-type(-n+2)","//div[(((count(preceding-sibling::div)+1) mod 1)=0) and (count(preceding-sibling::div)<2)]"); 	

		testConvertCssStringToXpathString("div:nth-of-type(-n+012)","//div[(((count(preceding-sibling::div)+1) mod 1)=0) and (count(preceding-sibling::div)<12)]"); 	
		testConvertCssStringToXpathString("form:nth-of-type(+  n+ 22) div","//form[(count(preceding-sibling::form)=21) or (((count(preceding-sibling::form)>22) and (((count(preceding-sibling::form)-21) mod 1)=0)))]//div"); 	
		testConvertCssStringToXpathString("div form:nth-of-type(4)","//div//form[4]"); 	
		testConvertCssStringToXpathString("div:empty form:nth-of-type(4)","//div[not(*) and .=\"\"]//form[4]"); 
		
		testConvertCssStringToXpathString("div:nth-of-type(2n+2) span:nth-of-type(2)","//div[((count(preceding-sibling::div)+1) mod 2)=0]//span[2]"); 	
		testConvertCssStringToXpathString("div:nth-of-type(4n+4) span:nth-of-type(2)","//div[((count(preceding-sibling::div)+1) mod 4)=0]//span[2]"); 	

		testConvertCssStringToXpathString("div:nth-of-type(2) span:nth-of-type(4)","//div[2]//span[4]");
		testConvertCssStringToXpathString("div:nth-of-type(2) span:nth-of-type(2)","//div[2]//span[2]"); 	
		testConvertCssStringToXpathString("div:nth-of-type(3n+2) span:nth-of-type(2)","//div[(count(preceding-sibling::div)=1) or (((count(preceding-sibling::div)-1) mod 3)=0)]//span[2]"); 	
		testConvertCssStringToXpathString("div:nth-of-type(n) span:nth-of-type(-n+4)","//div[((count(preceding-sibling::div)+1) mod 1)=0]//span[(((count(preceding-sibling::span)+1) mod 1)=0) and (count(preceding-sibling::span)<4)]"); 			
	
		testConvertCssStringToXpathString("div:nth-of-type(5):nth-of-type(5)","//div[5]");
		testConvertCssStringToXpathString("div:nth-of-type(n):nth-of-type(n)","//div[((count(preceding-sibling::div)+1) mod 1)=0]");
		testConvertCssStringToXpathString("div:nth-of-type(odd):nth-of-type(odd)","//div[(count(preceding-sibling::div)=0) or (((count(preceding-sibling::div)-0) mod 2)=0)]");
		testConvertCssStringToXpathString("div:nth-of-type(2n+5):nth-of-type(n+22)","//div[(count(preceding-sibling::div)=4) or (((count(preceding-sibling::div)>5) and (((count(preceding-sibling::div)-4) mod 2)=0)))][(count(preceding-sibling::div)=21) or (((count(preceding-sibling::div)>22) and (((count(preceding-sibling::div)-21) mod 1)=0)))]");
		testConvertCssStringToXpathString("div:nth-of-type(2n+5):nth-of-type(2n+5)","//div[(count(preceding-sibling::div)=4) or (((count(preceding-sibling::div)>5) and (((count(preceding-sibling::div)-4) mod 2)=0)))]");
		testConvertCssStringToXpathString("div:nth-of-type(n+5):nth-of-type(odd)","//div[(count(preceding-sibling::div)=4) or (((count(preceding-sibling::div)>5) and (((count(preceding-sibling::div)-4) mod 1)=0)))][(count(preceding-sibling::div)=0) or (((count(preceding-sibling::div)-0) mod 2)=0)]");
	}
	
	@Test
	public void testConverCssStringToXpathNthLastOfType() throws CssSelectorToXPathConverterException
	{
		testConvertCssStringToXpathString("a:nth-last-of-type( odd )","//a[(count(following-sibling::a)=0) or (((count(following-sibling::a)-0) mod 2)=0)]"); 	
		testConvertCssStringToXpathString("a:nth-last-of-type( Odd)","//a[(count(following-sibling::a)=0) or (((count(following-sibling::a)-0) mod 2)=0)]"); 	
		testConvertCssStringToXpathString("a:nth-last-of-type(OdD )","//a[(count(following-sibling::a)=0) or (((count(following-sibling::a)-0) mod 2)=0)]"); 	

		testConvertCssStringToXpathString("p:nth-last-of-type(    even   )","//p[((count(following-sibling::p)+1) mod 2)=0]"); 	
		testConvertCssStringToXpathString("p:nth-last-of-type(EVEN)","//p[((count(following-sibling::p)+1) mod 2)=0]"); 	
		
		testConvertCssStringToXpathString("div:nth-last-of-type(2)","//div[count(following-sibling::div)=1]"); 	
		testConvertCssStringToXpathString("div:nth-last-of-type(+12)","//div[count(following-sibling::div)=11]"); 	

		testConvertCssStringToXpathString("ul:nth-last-of-type(N)","//ul[((count(following-sibling::ul)+1) mod 1)=0]"); 
		testConvertCssStringToXpathString("ul:nth-last-of-type(n)","//ul[((count(following-sibling::ul)+1) mod 1)=0]"); 
		testConvertCssStringToXpathString("li:nth-last-of-type(3n    )","//li[((count(following-sibling::li)+1) mod 3)=0]"); 	 
		testConvertCssStringToXpathString("span:nth-last-of-type( +111n    )","//span[((count(following-sibling::span)+1) mod 111)=0]"); 	
		
		testConvertCssStringToXpathString("div:nth-last-of-type(n+1)","//div[((count(following-sibling::div)+1) mod 1)=0]"); 
		testConvertCssStringToXpathString("div:nth-last-of-type(+2n+2)","//div[((count(following-sibling::div)+1) mod 2)=0]"); 	
		testConvertCssStringToXpathString("div:nth-last-of-type(2n+2)","//div[((count(following-sibling::div)+1) mod 2)=0]"); 	
		testConvertCssStringToXpathString("span:nth-last-of-type(+3n+5)","//span[(count(following-sibling::span)=4) or (((count(following-sibling::span)>5) and (((count(following-sibling::span)-4) mod 3)=0)))]"); 	
		testConvertCssStringToXpathString("img:nth-last-of-type(3n+6)","//img[(count(following-sibling::img)=5) or (((count(following-sibling::img)>6) and (((count(following-sibling::img)-5) mod 3)=0)))]"); 	
		testConvertCssStringToXpathString("div:nth-last-of-type(5n+3)","//div[(count(following-sibling::div)=2) or (((count(following-sibling::div)-2) mod 5)=0)]"); 
		testConvertCssStringToXpathString("div:nth-last-of-type(3n+1)","//div[(count(following-sibling::div)=0) or (((count(following-sibling::div)-0) mod 3)=0)]"); 
		
		testConvertCssStringToXpathString("div:nth-last-of-type(n-1)","//div[((count(following-sibling::div)+1) mod 1)=0]"); 	
		testConvertCssStringToXpathString("div:nth-last-of-type(n-2)","//div[((count(following-sibling::div)+1) mod 1)=0]"); 	
		testConvertCssStringToXpathString("div:nth-last-of-type(3n-5)","//div[(count(following-sibling::div)=0) or (((count(following-sibling::div)-0) mod 3)=0)]"); 	
		testConvertCssStringToXpathString("div:nth-last-of-type(5n-2)","//div[(count(following-sibling::div)=2) or (((count(following-sibling::div)-2) mod 5)=0)]"); 	

		testConvertCssStringToXpathString("div:nth-last-of-type(-n+1)","//div[count(following-sibling::div)=0]"); 	
		testConvertCssStringToXpathString("div:nth-last-of-type(-2n+1)","//div[count(following-sibling::div)=0]"); 	
		testConvertCssStringToXpathString("div:nth-last-of-type(-5n + 3 )","//div[count(following-sibling::div)=2]"); 	

		testConvertCssStringToXpathString("div:nth-last-of-type(-n+2)","//div[(((count(following-sibling::div)+1) mod 1)=0) and (count(following-sibling::div)<2)]"); 	
		testConvertCssStringToXpathString("div:nth-last-of-type(-n+012)","//div[(((count(following-sibling::div)+1) mod 1)=0) and (count(following-sibling::div)<12)]"); 	

		testConvertCssStringToXpathString("form:nth-last-of-type(+  n+ 22) div","//form[(count(following-sibling::form)=21) or (((count(following-sibling::form)>22) and (((count(following-sibling::form)-21) mod 1)=0)))]//div"); 	
		testConvertCssStringToXpathString("div div:nth-last-of-type(4)","//div//div[count(following-sibling::div)=3]"); 	
		testConvertCssStringToXpathString("div:nth-last-of-type(4) div:empty","//div[count(following-sibling::div)=3]//div[not(*) and .=\"\"]"); 
		
		testConvertCssStringToXpathString("div:nth-last-of-type(2n+2) span:nth-last-of-type(2)","//div[((count(following-sibling::div)+1) mod 2)=0]//span[count(following-sibling::span)=1]"); 	
		testConvertCssStringToXpathString("div:nth-last-of-type(4n+4) span:nth-last-of-type(2)","//div[((count(following-sibling::div)+1) mod 4)=0]//span[count(following-sibling::span)=1]"); 	
		testConvertCssStringToXpathString("div:nth-last-of-type(2) span:nth-last-of-type(4)","//div[count(following-sibling::div)=1]//span[count(following-sibling::span)=3]");
		
		testConvertCssStringToXpathString("div:nth-last-of-type(2) span:nth-last-of-type(2)","//div[count(following-sibling::div)=1]//span[count(following-sibling::span)=1]"); 	
		testConvertCssStringToXpathString("div:nth-last-of-type(3n+2) span:nth-last-of-type(2)","//div[(count(following-sibling::div)=1) or (((count(following-sibling::div)-1) mod 3)=0)]//span[count(following-sibling::span)=1]"); 	
		testConvertCssStringToXpathString("div:nth-last-of-type(n) span:nth-last-of-type(-n+4)","//div[((count(following-sibling::div)+1) mod 1)=0]//span[(((count(following-sibling::span)+1) mod 1)=0) and (count(following-sibling::span)<4)]"); 	
		testConvertCssStringToXpathString("div:nth-last-of-type(2n+5)","//div[(count(following-sibling::div)=4) or (((count(following-sibling::div)>5) and (((count(following-sibling::div)-4) mod 2)=0)))]"); 	
	
		testConvertCssStringToXpathString("div:nth-last-of-type(5):nth-last-of-type(5)","//div[count(following-sibling::div)=4]");
		testConvertCssStringToXpathString("div:nth-last-of-type(n):nth-last-of-type(n)","//div[((count(following-sibling::div)+1) mod 1)=0]");
		testConvertCssStringToXpathString("div:nth-last-of-type(odd):nth-last-of-type(odd)","//div[(count(following-sibling::div)=0) or (((count(following-sibling::div)-0) mod 2)=0)]");
		testConvertCssStringToXpathString("div:nth-last-of-type(2n+5):nth-last-of-type(n+22)","//div[(count(following-sibling::div)=4) or (((count(following-sibling::div)>5) and (((count(following-sibling::div)-4) mod 2)=0)))][(count(following-sibling::div)=21) or (((count(following-sibling::div)>22) and (((count(following-sibling::div)-21) mod 1)=0)))]");
		testConvertCssStringToXpathString("div:nth-last-of-type(2n+5):nth-last-of-type(2n+5)","//div[(count(following-sibling::div)=4) or (((count(following-sibling::div)>5) and (((count(following-sibling::div)-4) mod 2)=0)))]");
		testConvertCssStringToXpathString("div:nth-last-of-type(n+5):nth-last-of-type(odd)","//div[(count(following-sibling::div)=4) or (((count(following-sibling::div)>5) and (((count(following-sibling::div)-4) mod 1)=0)))][(count(following-sibling::div)=0) or (((count(following-sibling::div)-0) mod 2)=0)]");
	
		testConvertCssStringToXpathString("div:nth-last-of-type(5):nth-child(5)","//div[count(following-sibling::div)=4][count(preceding-sibling::*)=4]");
		testConvertCssStringToXpathString("div:nth-child(n):nth-of-type(2n+2)","//div[((count(preceding-sibling::*)+1) mod 1)=0][((count(preceding-sibling::div)+1) mod 2)=0]");

	}
	
	
	@Test
	public void testCssToXpathNthOfTypeException() throws CssSelectorToXPathConverterException
	{
		Map<String,String> cases = new HashMap<String, String>();
		cases.put("div:nth-of-type(0)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("0"));
		cases.put("div:nth-of-type(0n)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("0n"));
		cases.put("div:nth-of-type(-n)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("-n"));
		cases.put("div:nth-of-type(-3n-0)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("-3n-0"));
		cases.put("div:nth-of-type(-n-5)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("-n-5"));
		cases.put("div:nth-of-type(n+5+1)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("n+5+1"));
		cases.put("div:nth-of-type(2+n)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("2+n"));
		cases.put("div:nth-of-type(n-2+1)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("n-2+1"));
		
		cases.put("div:nth-last-of-type(0)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("0"));
		cases.put("div:nth-last-of-type(0n)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("0n"));
		cases.put("div:nth-last-of-type(-n)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("-n"));
		cases.put("div:nth-last-of-type(-3n-0)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("-3n-0"));
		cases.put("div:nth-last-of-type(-n-5)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("-n-5"));
		cases.put("div:nth-last-of-type(n+5+1)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("n+5+1"));
		cases.put("div:nth-last-of-type(2+n)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("2+n"));
		cases.put("div:nth-last-of-type(n-2+1)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("n-2+1"));

		cases.put("div:nth-child(0)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("0"));
		cases.put("div:nth-child(0n)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("0n"));
		cases.put("div:nth-child(-n)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("-n"));
		cases.put("div:nth-child(-3n-0)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("-3n-0"));
		cases.put("div:nth-child(-n-5)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("-n-5"));
		cases.put("div:nth-child(n+5+1)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("n+5+1"));
		cases.put("div:nth-child(2+n)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("2+n"));
		cases.put("div:nth-child(n-2+1)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("n-2+1"));
		
		cases.put("div:nth-last-child(0)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("0"));
		cases.put("div:nth-last-child(0n)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("0n"));
		cases.put("div:nth-last-child(-n)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("-n"));
		cases.put("div:nth-last-child(-3n-0)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("-3n-0"));
		cases.put("div:nth-last-child(-n-5)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("-n-5"));
		cases.put("div:nth-last-child(n+5+1)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("n+5+1"));
		cases.put("div:nth-last-child(2+n)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("2+n"));
		cases.put("div:nth-last-child(n-2+1)", CssSelectorToXpathConverterInvalidNthOfType.getInvalidParenthesisExpressionError("n-2+1"));

		for(Map.Entry<String,String> exceptionCase:cases.entrySet())
		{
			String cssSelector=exceptionCase.getKey();
			String expectedErrorMessage=exceptionCase.getValue();
//			System.out.println(expectedErrorMessage);
			testCssToXpathNthOfTypeException(cssSelector, expectedErrorMessage);
		}
	}
	
	@Test
	public void testConverCssStringToXpathNthChild() throws CssSelectorToXPathConverterException
	{
		testConvertCssStringToXpathString("a:nth-child( odd )","//a[(count(preceding-sibling::*)=0) or (((count(preceding-sibling::*)-0) mod 2)=0)]"); 	
		testConvertCssStringToXpathString("a:nth-child( Odd)","//a[(count(preceding-sibling::*)=0) or (((count(preceding-sibling::*)-0) mod 2)=0)]"); 	
		testConvertCssStringToXpathString("a:nth-child(OdD )","//a[(count(preceding-sibling::*)=0) or (((count(preceding-sibling::*)-0) mod 2)=0)]"); 	

		testConvertCssStringToXpathString("p:nth-child(    even   )","//p[((count(preceding-sibling::*)+1) mod 2)=0]"); 	
		testConvertCssStringToXpathString("p:nth-child(EVEN)","//p[((count(preceding-sibling::*)+1) mod 2)=0]"); 	
		
		testConvertCssStringToXpathString("div:nth-child(3)","//div[count(preceding-sibling::*)=2]"); 	
		testConvertCssStringToXpathString("div:nth-child(+12)","//div[count(preceding-sibling::*)=11]"); 	

		testConvertCssStringToXpathString("ul:nth-child(N)","//ul[((count(preceding-sibling::*)+1) mod 1)=0]"); 
		testConvertCssStringToXpathString("ul:nth-child(n)","//ul[((count(preceding-sibling::*)+1) mod 1)=0]"); 
		testConvertCssStringToXpathString("li:nth-child(3n    )","//li[((count(preceding-sibling::*)+1) mod 3)=0]"); 	
		testConvertCssStringToXpathString("input:nth-child(33n    )","//input[((count(preceding-sibling::*)+1) mod 33)=0]"); 	
		testConvertCssStringToXpathString("span:nth-child( +11n    )","//span[((count(preceding-sibling::*)+1) mod 11)=0]"); 	

		testConvertCssStringToXpathString("div:nth-child(n+1)","//div[((count(preceding-sibling::*)+1) mod 1)=0]"); 
		testConvertCssStringToXpathString("div:nth-child(+2n+2)","//div[((count(preceding-sibling::*)+1) mod 2)=0]"); 	
		testConvertCssStringToXpathString("div:nth-child(2n+2)","//div[((count(preceding-sibling::*)+1) mod 2)=0]"); 	
		testConvertCssStringToXpathString("span:nth-child(+3n+5)","//span[(count(preceding-sibling::*)=4) or (((count(preceding-sibling::*)>5) and (((count(preceding-sibling::*)-4) mod 3)=0)))]"); 	
		testConvertCssStringToXpathString("img:nth-child(3n+6)","//img[(count(preceding-sibling::*)=5) or (((count(preceding-sibling::*)>6) and (((count(preceding-sibling::*)-5) mod 3)=0)))]"); 	
		testConvertCssStringToXpathString("div:nth-child(5n+3)","//div[(count(preceding-sibling::*)=2) or (((count(preceding-sibling::*)-2) mod 5)=0)]"); 
		testConvertCssStringToXpathString("div:nth-child(3n+1)","//div[(count(preceding-sibling::*)=0) or (((count(preceding-sibling::*)-0) mod 3)=0)]"); 
		
		testConvertCssStringToXpathString("div:nth-child(n-1)","//div[((count(preceding-sibling::*)+1) mod 1)=0]"); 	
		testConvertCssStringToXpathString("div:nth-child(n-2)","//div[((count(preceding-sibling::*)+1) mod 1)=0]"); 	
		testConvertCssStringToXpathString("div:nth-child(3n-5)","//div[(count(preceding-sibling::*)=0) or (((count(preceding-sibling::*)-0) mod 3)=0)]"); 	
		testConvertCssStringToXpathString("div:nth-child(5n-2)","//div[(count(preceding-sibling::*)=2) or (((count(preceding-sibling::*)-2) mod 5)=0)]"); 	

		testConvertCssStringToXpathString("div:nth-child(-n+1)","//div[count(preceding-sibling::*)=0]"); 	
		testConvertCssStringToXpathString("div:nth-child(-2n+1)","//div[count(preceding-sibling::*)=0]"); 	
		testConvertCssStringToXpathString("div:nth-child(-5n + 3 )","//div[count(preceding-sibling::*)=2]"); 	

		testConvertCssStringToXpathString("div:nth-child(-n+2)","//div[(((count(preceding-sibling::*)+1) mod 1)=0) and (count(preceding-sibling::*)<2)]"); 	

		testConvertCssStringToXpathString("div:nth-child(-n+012)","//div[(((count(preceding-sibling::*)+1) mod 1)=0) and (count(preceding-sibling::*)<12)]"); 	
		testConvertCssStringToXpathString("form:nth-child(+  n+ 22) div","//form[(count(preceding-sibling::*)=21) or (((count(preceding-sibling::*)>22) and (((count(preceding-sibling::*)-21) mod 1)=0)))]//div"); 	
		testConvertCssStringToXpathString("div form:nth-child(4)","//div//form[count(preceding-sibling::*)=3]"); 	
		testConvertCssStringToXpathString("div:empty form:nth-child(4)","//div[not(*) and .=\"\"]//form[count(preceding-sibling::*)=3]"); 
		
		testConvertCssStringToXpathString("div:nth-child(2n+2) span:nth-child(2)","//div[((count(preceding-sibling::*)+1) mod 2)=0]//span[count(preceding-sibling::*)=1]"); 	
		testConvertCssStringToXpathString("div:nth-child(4n+4) span:nth-child(2)","//div[((count(preceding-sibling::*)+1) mod 4)=0]//span[count(preceding-sibling::*)=1]"); 	

		testConvertCssStringToXpathString("div:nth-child(2) span:nth-child(4)","//div[count(preceding-sibling::*)=1]//span[count(preceding-sibling::*)=3]");
		testConvertCssStringToXpathString("div:nth-child(2) span:nth-child(2)","//div[count(preceding-sibling::*)=1]//span[count(preceding-sibling::*)=1]"); 	
		testConvertCssStringToXpathString("div:nth-child(3n+2) span:nth-child(2)","//div[(count(preceding-sibling::*)=1) or (((count(preceding-sibling::*)-1) mod 3)=0)]//span[count(preceding-sibling::*)=1]"); 	
		testConvertCssStringToXpathString("div:nth-child(n) span:nth-child(-n+4)","//div[((count(preceding-sibling::*)+1) mod 1)=0]//span[(((count(preceding-sibling::*)+1) mod 1)=0) and (count(preceding-sibling::*)<4)]"); 			

		testConvertCssStringToXpathString("div:nth-child(5):nth-child(5)","//div[count(preceding-sibling::*)=4]");
		testConvertCssStringToXpathString("div:nth-child(n):nth-child(n)","//div[((count(preceding-sibling::*)+1) mod 1)=0]");
		testConvertCssStringToXpathString("div:nth-child(odd):nth-child(odd)","//div[(count(preceding-sibling::*)=0) or (((count(preceding-sibling::*)-0) mod 2)=0)]");
		testConvertCssStringToXpathString("div:nth-child(2n+5):nth-child(n+22)","//div[(count(preceding-sibling::*)=4) or (((count(preceding-sibling::*)>5) and (((count(preceding-sibling::*)-4) mod 2)=0)))][(count(preceding-sibling::*)=21) or (((count(preceding-sibling::*)>22) and (((count(preceding-sibling::*)-21) mod 1)=0)))]");
		testConvertCssStringToXpathString("div:nth-child(2n+5):nth-child(2n+5)","//div[(count(preceding-sibling::*)=4) or (((count(preceding-sibling::*)>5) and (((count(preceding-sibling::*)-4) mod 2)=0)))]");
		testConvertCssStringToXpathString("div:nth-child(n+5):nth-child(odd)","//div[(count(preceding-sibling::*)=4) or (((count(preceding-sibling::*)>5) and (((count(preceding-sibling::*)-4) mod 1)=0)))][(count(preceding-sibling::*)=0) or (((count(preceding-sibling::*)-0) mod 2)=0)]");
}
	
	@Test
	public void testConverCssStringToXpathNthLastChild() throws CssSelectorToXPathConverterException
	{
		testConvertCssStringToXpathString("a:nth-last-child( odd )","//a[(count(following-sibling::*)=0) or (((count(following-sibling::*)-0) mod 2)=0)]"); 	
		testConvertCssStringToXpathString("a:nth-last-child( Odd)","//a[(count(following-sibling::*)=0) or (((count(following-sibling::*)-0) mod 2)=0)]"); 	
		testConvertCssStringToXpathString("a:nth-last-child(OdD )","//a[(count(following-sibling::*)=0) or (((count(following-sibling::*)-0) mod 2)=0)]"); 	

		testConvertCssStringToXpathString("p:nth-last-child(    even   )","//p[((count(following-sibling::*)+1) mod 2)=0]"); 	
		testConvertCssStringToXpathString("p:nth-last-child(EVEN)","//p[((count(following-sibling::*)+1) mod 2)=0]"); 	
		
		testConvertCssStringToXpathString("div:nth-last-child(3)","//div[count(following-sibling::*)=2]"); 	
		testConvertCssStringToXpathString("div:nth-last-child(+12)","//div[count(following-sibling::*)=11]"); 	

		testConvertCssStringToXpathString("ul:nth-last-child(N)","//ul[((count(following-sibling::*)+1) mod 1)=0]"); 
		testConvertCssStringToXpathString("ul:nth-last-child(n)","//ul[((count(following-sibling::*)+1) mod 1)=0]"); 
		testConvertCssStringToXpathString("li:nth-last-child(3n    )","//li[((count(following-sibling::*)+1) mod 3)=0]"); 	
		testConvertCssStringToXpathString("input:nth-last-child(33n    )","//input[((count(following-sibling::*)+1) mod 33)=0]"); 	
		testConvertCssStringToXpathString("span:nth-last-child( +11n    )","//span[((count(following-sibling::*)+1) mod 11)=0]"); 	

		testConvertCssStringToXpathString("div:nth-last-child(n+1)","//div[((count(following-sibling::*)+1) mod 1)=0]"); 
		testConvertCssStringToXpathString("div:nth-last-child(+2n+2)","//div[((count(following-sibling::*)+1) mod 2)=0]"); 	
		testConvertCssStringToXpathString("div:nth-last-child(2n+2)","//div[((count(following-sibling::*)+1) mod 2)=0]"); 	
		testConvertCssStringToXpathString("span:nth-last-child(+3n+5)","//span[(count(following-sibling::*)=4) or (((count(following-sibling::*)>5) and (((count(following-sibling::*)-4) mod 3)=0)))]"); 	
		testConvertCssStringToXpathString("img:nth-last-child(3n+6)","//img[(count(following-sibling::*)=5) or (((count(following-sibling::*)>6) and (((count(following-sibling::*)-5) mod 3)=0)))]"); 	
		testConvertCssStringToXpathString("div:nth-last-child(5n+3)","//div[(count(following-sibling::*)=2) or (((count(following-sibling::*)-2) mod 5)=0)]"); 
		testConvertCssStringToXpathString("div:nth-last-child(3n+1)","//div[(count(following-sibling::*)=0) or (((count(following-sibling::*)-0) mod 3)=0)]"); 
		
		testConvertCssStringToXpathString("div:nth-last-child(n-1)","//div[((count(following-sibling::*)+1) mod 1)=0]"); 	
		testConvertCssStringToXpathString("div:nth-last-child(n-2)","//div[((count(following-sibling::*)+1) mod 1)=0]"); 	
		testConvertCssStringToXpathString("div:nth-last-child(3n-5)","//div[(count(following-sibling::*)=0) or (((count(following-sibling::*)-0) mod 3)=0)]"); 	
		testConvertCssStringToXpathString("div:nth-last-child(5n-2)","//div[(count(following-sibling::*)=2) or (((count(following-sibling::*)-2) mod 5)=0)]"); 	

		testConvertCssStringToXpathString("div:nth-last-child(-n+1)","//div[count(following-sibling::*)=0]"); 	
		testConvertCssStringToXpathString("div:nth-last-child(-2n+1)","//div[count(following-sibling::*)=0]"); 	
		testConvertCssStringToXpathString("div:nth-last-child(-5n + 3 )","//div[count(following-sibling::*)=2]"); 	

		testConvertCssStringToXpathString("div:nth-last-child(-n+2)","//div[(((count(following-sibling::*)+1) mod 1)=0) and (count(following-sibling::*)<2)]"); 	

		testConvertCssStringToXpathString("div:nth-last-child(-n+012)","//div[(((count(following-sibling::*)+1) mod 1)=0) and (count(following-sibling::*)<12)]"); 	
		testConvertCssStringToXpathString("form:nth-last-child(+  n+ 22) div","//form[(count(following-sibling::*)=21) or (((count(following-sibling::*)>22) and (((count(following-sibling::*)-21) mod 1)=0)))]//div"); 	
		testConvertCssStringToXpathString("div form:nth-last-child(4)","//div//form[count(following-sibling::*)=3]"); 	
		testConvertCssStringToXpathString("div:empty form:nth-last-child(4)","//div[not(*) and .=\"\"]//form[count(following-sibling::*)=3]"); 
		
		testConvertCssStringToXpathString("div:nth-last-child(2n+2) span:nth-last-child(2)","//div[((count(following-sibling::*)+1) mod 2)=0]//span[count(following-sibling::*)=1]"); 	
		testConvertCssStringToXpathString("div:nth-last-child(4n+4) span:nth-last-child(2)","//div[((count(following-sibling::*)+1) mod 4)=0]//span[count(following-sibling::*)=1]"); 	
		testConvertCssStringToXpathString("div:nth-last-child(2) span:nth-last-child(4)","//div[count(following-sibling::*)=1]//span[count(following-sibling::*)=3]");
		testConvertCssStringToXpathString("div:nth-last-child(2) span:nth-last-child(2)","//div[count(following-sibling::*)=1]//span[count(following-sibling::*)=1]"); 	
		testConvertCssStringToXpathString("div:nth-last-child(3n+2) span:nth-last-child(2)","//div[(count(following-sibling::*)=1) or (((count(following-sibling::*)-1) mod 3)=0)]//span[count(following-sibling::*)=1]"); 	
		testConvertCssStringToXpathString("div:nth-last-child(n) span:nth-last-child(-n+4)","//div[((count(following-sibling::*)+1) mod 1)=0]//span[(((count(following-sibling::*)+1) mod 1)=0) and (count(following-sibling::*)<4)]"); 			

		testConvertCssStringToXpathString("div:nth-last-child(5):nth-last-child(5)","//div[count(following-sibling::*)=4]");
		testConvertCssStringToXpathString("div:nth-last-child(n):nth-last-child(n)","//div[((count(following-sibling::*)+1) mod 1)=0]");
		testConvertCssStringToXpathString("div:nth-last-child(odd):nth-last-child(odd)","//div[(count(following-sibling::*)=0) or (((count(following-sibling::*)-0) mod 2)=0)]");
		testConvertCssStringToXpathString("div:nth-last-child(2n+5):nth-last-child(n+22)","//div[(count(following-sibling::*)=4) or (((count(following-sibling::*)>5) and (((count(following-sibling::*)-4) mod 2)=0)))][(count(following-sibling::*)=21) or (((count(following-sibling::*)>22) and (((count(following-sibling::*)-21) mod 1)=0)))]");
		testConvertCssStringToXpathString("div:nth-last-child(2n+5):nth-last-child(2n+5)","//div[(count(following-sibling::*)=4) or (((count(following-sibling::*)>5) and (((count(following-sibling::*)-4) mod 2)=0)))]");
		testConvertCssStringToXpathString("div:nth-last-child(n+5):nth-last-child(odd)","//div[(count(following-sibling::*)=4) or (((count(following-sibling::*)>5) and (((count(following-sibling::*)-4) mod 1)=0)))][(count(following-sibling::*)=0) or (((count(following-sibling::*)-0) mod 2)=0)]");
	
	}


	
	private void testCssToXpathNthOfTypeException(String cssSelector, String expectedErrorMessage) throws CssSelectorToXPathConverterException {
		try {
			String xpath=elementCombinatorPair.convertCssSelectorStringToXpathString(cssSelector);
			fail("CssSelector="+cssSelector+", should have been invalid, but xpath string return value="+xpath);
		} catch (CssSelectorToXpathConverterInvalidNthOfType e) {
			assertEquals(expectedErrorMessage,e.getMessage());
			//success
		} 	
	}
}
