package org.sam.rosenthal.cssselectortoxpath.utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.sam.rosenthal.cssselectortoxpath.model.CssRelationship;
import org.sam.rosenthal.cssselectortoxpath.model.CssType;

public class CssSelectorStringSplitterTest {
	
	private CssSelectorStringSplitter splitter=new CssSelectorStringSplitter();


	@Test
	public void splitSelectorsErrorTester()
	{
		testSelectorException(",,");
		testSelectorException(" ");//No commas, single space
		testSelectorException("   ");//No commas,multiple spaces
		testSelectorException("	");//No commas,single tab
		testSelectorException("			");//No commas,multiple tabs
		testSelectorException(" 	 ");//No commas,space+tab+space
		testSelectorException("a, ,b");
		testSelectorException("AB,	,CD");
		testSelectorException("  ,ABC,	");//2spaces+letters+tab
		testSelectorException(" ,ABC, ");//space+letters+space
		testSelectorException(", ,");//split() does not return any trailing empty Strings
		testSelectorException("a,");
		testSelectorException(",b");
		testSelectorException(",");

	}
	private void testSelectorException(String selectorInput) {
		try {
			System.out.println(splitter.splitSelectors(selectorInput));			
			fail("CssSelectorStringSplitterException not thrown for: "+selectorInput);
		} catch (CssSelectorStringSplitterException e) {
			//success
		}
	}

	@Test
	public void splitSelectorsTester() throws CssSelectorStringSplitterException
	{
		testSelector("A","A"); //No commas, single letter
		testSelector("ABCD","ABCD"); //No commas, only letters
		testSelector("1","1"); //No commas, single number
		testSelector("123","123"); //No commas, multiple numbers
		testSelector("~`!@$%^&*()_+-=}{|:<>/?[];'","~`!@$%^&*()_+-=}{|:<>/?[];'"); //No commas, only characters
		testSelector("A1B2C3","A1B2C3"); //No commas, letters+numbers
		testSelector("A$B%C^","A$B%C^"); //No commas, letters+characters
		testSelector("1$2%3^4","1$2%3^4"); //No commas, numbers+characters

		testSelector("A,B","A","B"); //Single letters, one comma
		testSelector("A,B,C,D","A","B","C","D"); //Single letters, multiple commas
		testSelector("ABC,DEF,GHI,JKL,MNO,PQR,STU,VWX,YZ","ABC","DEF","GHI","JKL","MNO","PQR","STU","VWX","YZ"); //Multiple letters, single comma
		testSelector("A@1			", "A@1"); //No comma, letter+multiple tabs
		testSelector("   1@A", "1@A"); //No comma, multiple spaces+characharacters+numbers+letters
	}

	public void testSelector(String selectorInput,String... expectedOutput) throws CssSelectorStringSplitterException 
	{
		List<String> selectors=splitter.splitSelectors(selectorInput);
		//System.out.println(selectors);
		assertEquals("selectorInput="+selectorInput,asList(expectedOutput),selectors);
	}
	
	@SafeVarargs
	private final <T> List<T> asList(T... expectedOutput) {
		return Arrays.asList(expectedOutput);
	}
	
	@Test //(expected=CssSelectorStringSplitterException.class)
	public void whitespacesErrorTester() 
	{	
		testWhitespacesException(null,CssSelectorStringSplitter.ERROR_SELECTOR_STRING_IS_NULL);

		testWhitespacesException("#[",CssSelectorStringSplitter.ERROR_INVALID_ID_CSS_SELECTOR);
		testWhitespacesException("#.",CssSelectorStringSplitter.ERROR_INVALID_ID_CSS_SELECTOR);
		testWhitespacesException("##",CssSelectorStringSplitter.ERROR_INVALID_ID_CSS_SELECTOR);

		testWhitespacesException(".[",CssSelectorStringSplitter.ERROR_INVALID_CLASS_CSS_SELECTOR);
		testWhitespacesException(".#",CssSelectorStringSplitter.ERROR_INVALID_CLASS_CSS_SELECTOR);
		testWhitespacesException("..",CssSelectorStringSplitter.ERROR_INVALID_CLASS_CSS_SELECTOR);
//		String whitespaces=splitter.removeNonCssSelectorWhiteSpaces(null);
//		System.out.println("this should never be reached, whitespaces="+whitespaces);
	}
	
	private void testWhitespacesException(String whitespacesInput, String expectedErrorMessage) {
		try {
			splitter.removeNonCssSelectorWhiteSpaces(whitespacesInput);
			fail("CssSelectorStringSplitterException not thrown for: "+whitespacesInput);
		} catch (CssSelectorStringSplitterException e) {
			assertEquals(expectedErrorMessage, e.getMessage());
		}
	}
	
	@Test
	public void removeNonCssSelectorWhiteSpacesTester() throws CssSelectorStringSplitterException
	{
		testWhitespaces("		123", "123"); //tabs before 
		testWhitespaces("123		", "123"); //tabs after
		testWhitespaces("   123", "123"); //spaces before
		testWhitespaces("123   ", "123"); //spaces after 
		testWhitespaces("	123	", "123"); 
		testWhitespaces(" 123	", "123"); 
		testWhitespaces("1     2  3", "1 2 3"); 
		testWhitespaces("a#b ", "a[id=\"b\"]"); 
		testWhitespaces("a#b[c]","a[id=\"b\"][c]"); 
		testWhitespaces("a#bbb.ccc", "a[id=\"bbb\"][class~=\"ccc\"]"); 

	}

	public void testWhitespaces(String whitespacesInput, String expectedOutput) throws CssSelectorStringSplitterException 
	{
		String whitespaces=splitter.removeNonCssSelectorWhiteSpaces(whitespacesInput);
		assertEquals("selectorInput="+whitespacesInput,expectedOutput,whitespaces);
	}
	@Test
	public void splitSelectorsIntoRelationshipsExceptionTester()
	{
		testSplitSelectorsIntoRelationships(" ");
		testSplitSelectorsIntoRelationships("X ");
		testSplitSelectorsIntoRelationships(" y");
	
		testSplitSelectorsIntoRelationships("+");
		testSplitSelectorsIntoRelationships("X+");
		testSplitSelectorsIntoRelationships("+y");
		
		testSplitSelectorsIntoRelationships(">");
		testSplitSelectorsIntoRelationships("X>");
		testSplitSelectorsIntoRelationships(">y");

		testSplitSelectorsIntoRelationships("~");
		testSplitSelectorsIntoRelationships("X~");
		testSplitSelectorsIntoRelationships("~y");
		
		testSplitSelectorsIntoRelationships("X+Y ");
		testSplitSelectorsIntoRelationships(">X~Y");
		testSplitSelectorsIntoRelationships("+~");
	}
	
	private void testSplitSelectorsIntoRelationships(String processedSelector) 
	{
		try {
			List<CssRelationship> relationships=splitter.splitSelectorsIntoRelationships(processedSelector);
			fail("CssSelectorStringSplitterException not thrown for: "+processedSelector+" relationships="+relationships);
		} 
		catch (CssSelectorStringSplitterException e) {
			//success
		}
	}
	
	@Test
	public void splitSelectorsIntoRelationshipsTester() throws CssSelectorStringSplitterException
	{
		testSplitSelectorsIntoRelationships("X",new CssRelationship(null, "X"));
		testSplitSelectorsIntoRelationships("X Y",new CssRelationship(null, "X"),new CssRelationship(CssType.SPACE, "Y"));
		testSplitSelectorsIntoRelationships("X+Y",new CssRelationship(null, "X"),new CssRelationship(CssType.PLUS, "Y"));
		testSplitSelectorsIntoRelationships("X>Y",new CssRelationship(null, "X"),new CssRelationship(CssType.GREATER_THAN, "Y"));
		testSplitSelectorsIntoRelationships("X~Y",new CssRelationship(null, "X"),new CssRelationship(CssType.TILDA, "Y"));
		
		testSplitSelectorsIntoRelationships("X Y Z",new CssRelationship(null, "X"),new CssRelationship(CssType.SPACE, "Y"),new CssRelationship(CssType.SPACE, "Z"));
		testSplitSelectorsIntoRelationships("X+Y+Z+A",new CssRelationship(null, "X"),new CssRelationship(CssType.PLUS, "Y"),new CssRelationship(CssType.PLUS, "Z"),new CssRelationship(CssType.PLUS, "A"));
		testSplitSelectorsIntoRelationships("X>Y>Z",new CssRelationship(null, "X"),new CssRelationship(CssType.GREATER_THAN, "Y"),new CssRelationship(CssType.GREATER_THAN, "Z"));
		testSplitSelectorsIntoRelationships("X~Y~Z~A~B",new CssRelationship(null, "X"),new CssRelationship(CssType.TILDA, "Y"),new CssRelationship(CssType.TILDA, "Z"),new CssRelationship(CssType.TILDA, "A"),new CssRelationship(CssType.TILDA, "B"));
		
		testSplitSelectorsIntoRelationships("X Y+Z",new CssRelationship(null, "X"),new CssRelationship(CssType.SPACE, "Y"),new CssRelationship(CssType.PLUS, "Z"));
		testSplitSelectorsIntoRelationships("X Y+Z>A~B",new CssRelationship(null, "X"),new CssRelationship(CssType.SPACE, "Y"),new CssRelationship(CssType.PLUS, "Z"),new CssRelationship(CssType.GREATER_THAN, "A"),new CssRelationship(CssType.TILDA, "B"));
	}

	public void testSplitSelectorsIntoRelationships(String processedSelector,CssRelationship... expectedOutput) throws CssSelectorStringSplitterException {
		List<CssRelationship> relationships=splitter.splitSelectorsIntoRelationships(processedSelector);
		assertEquals("processedString="+processedSelector+"; relationships="+relationships.toString(),asList(expectedOutput),relationships);
	}
	
	
	@Test
	public void listSplitSelectorsIntoRelationshipsTester() throws CssSelectorStringSplitterException
	{
		testListSplitSelectorsIntoRelationships("X",asList(asList(new CssRelationship(null, "X"))));
		testListSplitSelectorsIntoRelationships("X,Y,Z,1",asList(asList(new CssRelationship(null, "X")),asList(new CssRelationship(null, "Y")),asList(new CssRelationship(null, "Z")),asList(new CssRelationship(null, "1"))));
		testListSplitSelectorsIntoRelationships("X+Y",asList(asList(new CssRelationship(null, "X"),new CssRelationship(CssType.PLUS, "Y"))));
		testListSplitSelectorsIntoRelationships("X+Y,A>B",asList(asList(new CssRelationship(null, "X"),new CssRelationship(CssType.PLUS, "Y")),asList(new CssRelationship(null, "A"),new CssRelationship(CssType.GREATER_THAN, "B"))));
		testListSplitSelectorsIntoRelationships("X Y+Z>A~B,C,D E",asList(asList(new CssRelationship(null, "X"),new CssRelationship(CssType.SPACE, "Y"),new CssRelationship(CssType.PLUS, "Z"),new CssRelationship(CssType.GREATER_THAN, "A"),new CssRelationship(CssType.TILDA, "B")),
				asList(new CssRelationship(null, "C")),asList(new CssRelationship(null, "D"),new CssRelationship(CssType.SPACE, "E"))));
		
	}

	public void testListSplitSelectorsIntoRelationships(String selector,List<List<CssRelationship>> expectedOutput) throws CssSelectorStringSplitterException {
		List<List<CssRelationship>> relationships=splitter.listSplitSelectorsIntoRelationships(selector);
		assertEquals("selectorString="+selector+"; relationships="+relationships.toString(),expectedOutput,relationships);
	}

}
