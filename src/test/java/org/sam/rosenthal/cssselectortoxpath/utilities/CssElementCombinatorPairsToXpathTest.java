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
		
		testCssElementCombinatorPairsToXpath("//A[starts-with(@B,\"C\")]",new CssElementCombinatorPair(null,"A[B^='C']"));
		testCssElementCombinatorPairsToXpath("//A[contains(@B,\"C\")]",new CssElementCombinatorPair(null,"A[B*='C']"));
		testCssElementCombinatorPairsToXpath("//A[substring(@B,string-length(@B)-string-length(\"C\")+1)=\"C\"]",new CssElementCombinatorPair(null,"A[B$='C']"));

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
	public void convertBasicCssStringToXpathStringTest() throws CssSelectorStringSplitterException
	{

		testConvertCssStringToXpathString("A","//A");
		testConvertCssStringToXpathString("*","//*");
		testConvertCssStringToXpathString("A>B","//A/B");
		testConvertCssStringToXpathString("A,B","(//A)|(//B)");		
		testConvertCssStringToXpathString("A B","//A//B");
		testConvertCssStringToXpathString("A+B","//A/following-sibling::*[1]/self::B");
		testConvertCssStringToXpathString("A~B","//A/following-sibling::B");
		
		testConvertCssStringToXpathString("A#B","//A[@id=\"B\"]");
		testConvertCssStringToXpathString("A[B=\"C\"]","//A[@B=\"C\"]");
		testConvertCssStringToXpathString("A[B^=\"C\"]","//A[starts-with(@B,\"C\")]");
		testConvertCssStringToXpathString("A[B*=\"C\"]","//A[contains(@B,\"C\")]");	
		testConvertCssStringToXpathString("A[B$=\"C\"]","//A[substring(@B,string-length(@B)-string-length(\"C\")+1)=\"C\"]");	
		testConvertCssStringToXpathString("A.B","//A[contains(concat(\" \",normalize-space(@class),\" \"),\" B \")]");	
		testConvertCssStringToXpathString("A[B~=\"C\"]","//A[contains(concat(\" \",normalize-space(@B),\" \"),\" C \")]");	
		//p[starts-with(@me,concat("you",'-'))]
		testConvertCssStringToXpathString("A[B|=\"C\"]","//A[starts-with(@B,concat(\"C\",\"-\")) or @B=\"C\"]");	
		testConvertCssStringToXpathString("[rel|=\"alternate\"]","//*[starts-with(@rel,concat(\"alternate\",\"-\")) or @rel=\"alternate\"]");
		testConvertCssStringToXpathString("A[B]","//A[@B]");	

	}
	
	
	@Test
	public void convertComplexCssStringToXpathStringTest() throws CssSelectorStringSplitterException
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
		testConvertCssStringToXpathString("TO DO","xxxTest CI");	

	}

	public void testConvertCssStringToXpathString(String cssSelector, String expectedOutput) throws CssSelectorStringSplitterException  {
		String xpath=elementCombinatorPair.convertCssSelectorStringToXpathString(cssSelector);
		//System.out.println(xpath);
		assertEquals("CssString="+cssSelector,expectedOutput,xpath);
	}
}
