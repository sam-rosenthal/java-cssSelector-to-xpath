package org.sam.rosenthal.cssselectortoxpath.utilities;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
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
	public void conversionTest() throws CssSelectorStringSplitterException
	{
		testSplitSelectorsIntoRelationships("//A", new CssRelationship(null, "A"));
		testSplitSelectorsIntoRelationships("//A//B",new CssRelationship(null, "A"), new CssRelationship(CssType.SPACE, "B"));
		testSplitSelectorsIntoRelationships("//A/B",new CssRelationship(null, "A"), new CssRelationship(CssType.GREATER_THAN, "B"));
		testSplitSelectorsIntoRelationships("//A/following-sibling::*[1]/self::B",new CssRelationship(null, "A"), new CssRelationship(CssType.PLUS, "B"));
		testSplitSelectorsIntoRelationships("//A/following-sibling::B",new CssRelationship(null, "A"), new CssRelationship(CssType.TILDA, "B"));
		testSplitSelectorsIntoRelationships("//A//B/C",new CssRelationship(null, "A"), new CssRelationship(CssType.SPACE, "B"),new CssRelationship(CssType.GREATER_THAN, "C"));
		testSplitSelectorsIntoRelationships("//A//B/C/following-sibling::*[1]/self::D",new CssRelationship(null, "A"), new CssRelationship(CssType.SPACE, "B"),new CssRelationship(CssType.GREATER_THAN, "C"),
				new CssRelationship(CssType.PLUS, "D"));
		testSplitSelectorsIntoRelationships("//A//B/C/following-sibling::*[1]/self::D/following-sibling::E",new CssRelationship(null, "A"), new CssRelationship(CssType.SPACE, "B"),new CssRelationship(CssType.GREATER_THAN, "C"),
				new CssRelationship(CssType.PLUS, "D"),new CssRelationship(CssType.TILDA, "E"));


	}
	
	public void testSplitSelectorsIntoRelationships(String expectedOutput, CssRelationship... relationshipsInput) throws CssSelectorStringSplitterException  {
		String xpath=relationships.cssRelationshipListsConversion(asList(relationshipsInput));
		System.out.println(xpath);
		assertEquals("CssRelationshipList="+asList(relationshipsInput),expectedOutput,xpath);
	}
	

	

}
