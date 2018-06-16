package org.sam.rosenthal.cssselectortoxpath.utilities;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.sam.rosenthal.cssselectortoxpath.model.CssElementCombinatorPair;
import org.sam.rosenthal.cssselectortoxpath.model.CssAttribute;
import org.sam.rosenthal.cssselectortoxpath.model.CssAttributeValueType;
import org.sam.rosenthal.cssselectortoxpath.model.CssCombinatorType;
import org.sam.rosenthal.cssselectortoxpath.model.CssElementAttributes;

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
		
		testCssElementCombinatorPairsToXpath("//A[@yy=\"-\"]",new CssElementCombinatorPair(null,"A[yy=\"-\"]"));
		testCssElementCombinatorPairsToXpath("//*[@A=\"B\"][@C=\"D\"]",new CssElementCombinatorPair(null,"[A=\"B\"][C=\"D\"]"));
		testCssElementCombinatorPairsToXpath("//A1[@BB=\"-\"][@CCC=\"123\"][@D=\"-\"]",new CssElementCombinatorPair(null,"A1[BB=\"-\"][CCC=\"123\"][D=\"-\"]"));		
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

		testConvertCssStringToXpathString("A","//A");
		testConvertCssStringToXpathString("A>B","//A/B");
		testConvertCssStringToXpathString("A,B,C","(//A)|(//B)|(//C)");
		
		testConvertCssStringToXpathString("A B,A>B","(//A//B)|(//A/B)");
		testConvertCssStringToXpathString("A B,A+B","(//A//B)|(//A/following-sibling::*[1]/self::B)");
		
		testConvertCssStringToXpathString("A[yy=\"-\"]","//A[@yy=\"-\"]");
		testConvertCssStringToXpathString("[A=\"B\"][C=\"D\"]","//*[@A=\"B\"][@C=\"D\"]");
		testConvertCssStringToXpathString("A1[BB=\"-\"][CCC=\"123\"][D=\"-\"]","//A1[@BB=\"-\"][@CCC=\"123\"][@D=\"-\"]");
		
		testConvertCssStringToXpathString("A[B='C']>E[F='G']","//A[@B=\"C\"]/E[@F=\"G\"]");
		testConvertCssStringToXpathString("A[B='C']>E[F='G'],H","(//A[@B=\"C\"]/E[@F=\"G\"])|(//H)");
		testConvertCssStringToXpathString("A#B","//A[@id=\"B\"]");
		testConvertCssStringToXpathString("A#B>E","//A[@id=\"B\"]/E");

	}
	
	public void testConvertCssStringToXpathString(String cssSelector, String expectedOutput) throws CssSelectorStringSplitterException  {
		String xpath=elementCombinatorPair.convertCssStringToXpathString(cssSelector);
		//System.out.println(xpath);
		assertEquals("CssString="+cssSelector,expectedOutput,xpath);
	}
	

	

}
