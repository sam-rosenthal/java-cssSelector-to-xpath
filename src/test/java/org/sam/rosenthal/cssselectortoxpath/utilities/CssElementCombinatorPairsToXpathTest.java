package org.sam.rosenthal.cssselectortoxpath.utilities;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.sam.rosenthal.cssselectortoxpath.model.CssElementCombinatorPair;
import org.sam.rosenthal.cssselectortoxpath.model.CssCombinatorType;

public class CssElementCombinatorPairsToXpathTest 
{
	private static CssElementCombinatorPairsToXpath elementCombinatorPair =new CssElementCombinatorPairsToXpath();

	@SafeVarargs
	private final static <T> List<T> asList(T... expectedOutput) {
		return Arrays.asList(expectedOutput);
	}
	
	@Test
	public void elementCombinatorPairListConversionTest() throws CssSelectorStringSplitterException
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


	}
	
	public void testCssElementCombinatorPairsToXpath(String expectedOutput, CssElementCombinatorPair... elementCombinatorPairsInput) throws CssSelectorStringSplitterException  {
		String xpath=elementCombinatorPair.cssElementCombinatorPairListConversion(asList(elementCombinatorPairsInput));
		//System.out.println(xpath);
		assertEquals("CssElementCombinatorPairList="+asList(elementCombinatorPairsInput),expectedOutput,xpath);
	}

	
	@Test
	public void elementCombinatorPairListListConversionTest() throws CssSelectorStringSplitterException
	{
		testCssElementCombinatorPairListListToXpath(asList(asList(new CssElementCombinatorPair(null, "A"))),"//A");
		testCssElementCombinatorPairListListToXpath(asList(asList(new CssElementCombinatorPair(null, "A")), asList(new CssElementCombinatorPair(null, "B")), asList(new CssElementCombinatorPair(null, "C"))),"(//A)|(//B)|(//C)");
		testCssElementCombinatorPairListListToXpath(asList(asList(new CssElementCombinatorPair(null, "A"), new CssElementCombinatorPair(CssCombinatorType.SPACE, "B")),asList(new CssElementCombinatorPair(null, "A"), new CssElementCombinatorPair(CssCombinatorType.PLUS, "B"))),"(//A//B)|(//A/following-sibling::*[1]/self::B)");
		testCssElementCombinatorPairListListToXpath(asList(asList(new CssElementCombinatorPair(null, "A"), new CssElementCombinatorPair(CssCombinatorType.SPACE, "B")),asList(new CssElementCombinatorPair(null, "A"), new CssElementCombinatorPair(CssCombinatorType.PLUS, "B"))),"(//A//B)|(//A/following-sibling::*[1]/self::B)");

	}
	
	public void testCssElementCombinatorPairListListToXpath(List<List<CssElementCombinatorPair>> elementCombinatorPairListInput, String expectedOutput) throws CssSelectorStringSplitterException  {
		String xpath=elementCombinatorPair.cssElementCombinatorPairListListConversion(elementCombinatorPairListInput);
		//System.out.println(xpath);
		assertEquals("CssElementCombinatorPairListList="+elementCombinatorPairListInput,expectedOutput,xpath);
	}
	
	@Test
	public void convertCssStringToXpathStringTest() throws CssSelectorStringSplitterException
	{
		testconvertCssStringToXpathString("A","//A");
		testconvertCssStringToXpathString("A,B,C","(//A)|(//B)|(//C)");
		testconvertCssStringToXpathString("A B,A+B","(//A//B)|(//A/following-sibling::*[1]/self::B)");
		testconvertCssStringToXpathString("A B,A+B","(//A//B)|(//A/following-sibling::*[1]/self::B)");

	}
	
	public void testconvertCssStringToXpathString(String cssSelector, String expectedOutput) throws CssSelectorStringSplitterException  {
		String xpath=elementCombinatorPair.convertCssStringToXpathString(cssSelector);
		//System.out.println(xpath);
		assertEquals("CssString="+cssSelector,expectedOutput,xpath);
	}

	

}
