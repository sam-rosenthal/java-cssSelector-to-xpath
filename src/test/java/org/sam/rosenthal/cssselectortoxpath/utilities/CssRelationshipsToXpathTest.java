package org.sam.rosenthal.cssselectortoxpath.utilities;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.sam.rosenthal.cssselectortoxpath.model.CssRelationship;
import org.sam.rosenthal.cssselectortoxpath.model.CssType;

public class CssRelationshipsToXpathTest 
{
	private static CssRelationshipsToXpath relationships =new CssRelationshipsToXpath();

	@SafeVarargs
	private final static <T> List<T> asList(T... expectedOutput) {
		return Arrays.asList(expectedOutput);
	}
	
	@Test
	public void relationshipListConversionTest() throws CssSelectorStringSplitterException
	{
		testCssRelationshipsToXpath("//A", new CssRelationship(null, "A"));
		testCssRelationshipsToXpath("//A//B",new CssRelationship(null, "A"), new CssRelationship(CssType.SPACE, "B"));
		testCssRelationshipsToXpath("//A/B",new CssRelationship(null, "A"), new CssRelationship(CssType.GREATER_THAN, "B"));
		testCssRelationshipsToXpath("//A/following-sibling::*[1]/self::B",new CssRelationship(null, "A"), new CssRelationship(CssType.PLUS, "B"));
		testCssRelationshipsToXpath("//A/following-sibling::B",new CssRelationship(null, "A"), new CssRelationship(CssType.TILDA, "B"));
		testCssRelationshipsToXpath("//A//B/C",new CssRelationship(null, "A"), new CssRelationship(CssType.SPACE, "B"),new CssRelationship(CssType.GREATER_THAN, "C"));
		testCssRelationshipsToXpath("//A//B/C/following-sibling::*[1]/self::D",new CssRelationship(null, "A"), new CssRelationship(CssType.SPACE, "B"),new CssRelationship(CssType.GREATER_THAN, "C"),
				new CssRelationship(CssType.PLUS, "D"));
		testCssRelationshipsToXpath("//A//B/C/following-sibling::*[1]/self::D/following-sibling::E",new CssRelationship(null, "A"), new CssRelationship(CssType.SPACE, "B"),new CssRelationship(CssType.GREATER_THAN, "C"),
				new CssRelationship(CssType.PLUS, "D"),new CssRelationship(CssType.TILDA, "E"));


	}
	
	public void testCssRelationshipsToXpath(String expectedOutput, CssRelationship... relationshipsInput) throws CssSelectorStringSplitterException  {
		String xpath=relationships.cssRelationshipsListConversion(asList(relationshipsInput));
		//System.out.println(xpath);
		assertEquals("CssRelationshipList="+asList(relationshipsInput),expectedOutput,xpath);
	}

	
	@Test
	public void relationshipListListConversionTest() throws CssSelectorStringSplitterException
	{
		testCssRelationshipsListListToXpath(asList(asList(new CssRelationship(null, "A"))),"//A");
		testCssRelationshipsListListToXpath(asList(asList(new CssRelationship(null, "A")), asList(new CssRelationship(null, "B")), asList(new CssRelationship(null, "C"))),"(//A)|(//B)|(//C)");
		testCssRelationshipsListListToXpath(asList(asList(new CssRelationship(null, "A"), new CssRelationship(CssType.SPACE, "B")),asList(new CssRelationship(null, "A"), new CssRelationship(CssType.PLUS, "B"))),"(//A//B)|(//A/following-sibling::*[1]/self::B)");
		testCssRelationshipsListListToXpath(asList(asList(new CssRelationship(null, "A"), new CssRelationship(CssType.SPACE, "B")),asList(new CssRelationship(null, "A"), new CssRelationship(CssType.PLUS, "B"))),"(//A//B)|(//A/following-sibling::*[1]/self::B)");

	}
	
	public void testCssRelationshipsListListToXpath(List<List<CssRelationship>> relationshipsListInput, String expectedOutput) throws CssSelectorStringSplitterException  {
		String xpath=relationships.cssRelationshipsListListConversion(relationshipsListInput);
		//System.out.println(xpath);
		assertEquals("CssRelationshipListList="+relationshipsListInput,expectedOutput,xpath);
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
		String xpath=relationships.convertCssStringToXpathString(cssSelector);
		//System.out.println(xpath);
		assertEquals("CssString="+cssSelector,expectedOutput,xpath);
	}

	

}
