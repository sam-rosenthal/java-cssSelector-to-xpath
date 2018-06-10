package org.sam.rosenthal.cssselectortoxpath.utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.sam.rosenthal.cssselectortoxpath.model.CssElementCombinatorPair;
import org.sam.rosenthal.cssselectortoxpath.model.CssCombinatorType;
import org.sam.rosenthal.cssselectortoxpath.model.CssElementAttribute;

public class CssSelectorStringSplitterTest {
	
	private CssSelectorStringSplitter splitter=new CssSelectorStringSplitter();
	private CssElementAttributeParser attribute=new CssElementAttributeParser();

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
	public void splitSelectorsIntoElementCombinatorPairsExceptionTester()
	{
		testSplitSelectorsIntoElementCombinatorPairs(" ");
		testSplitSelectorsIntoElementCombinatorPairs("X ");
		testSplitSelectorsIntoElementCombinatorPairs(" y");
	
		testSplitSelectorsIntoElementCombinatorPairs("+");
		testSplitSelectorsIntoElementCombinatorPairs("X+");
		testSplitSelectorsIntoElementCombinatorPairs("+y");
		
		testSplitSelectorsIntoElementCombinatorPairs(">");
		testSplitSelectorsIntoElementCombinatorPairs("X>");
		testSplitSelectorsIntoElementCombinatorPairs(">y");

		testSplitSelectorsIntoElementCombinatorPairs("~");
		testSplitSelectorsIntoElementCombinatorPairs("X~");
		testSplitSelectorsIntoElementCombinatorPairs("~y");
		
		testSplitSelectorsIntoElementCombinatorPairs("X+Y ");
		testSplitSelectorsIntoElementCombinatorPairs(">X~Y");
		testSplitSelectorsIntoElementCombinatorPairs("+~");
	}
	
	private void testSplitSelectorsIntoElementCombinatorPairs(String processedSelector) 
	{
		try {
			List<CssElementCombinatorPair> elementCombinatorPair=splitter.splitSelectorsIntoElementCombinatorPairs(processedSelector);
			fail("CssSelectorStringSplitterException not thrown for: "+processedSelector+" ElementCombinatorPair="+elementCombinatorPair);
		} 
		catch (CssSelectorStringSplitterException e) {
			//success
		}
	}
	
	@Test
	public void splitSelectorsIntoElementCombinatorPairsTester() throws CssSelectorStringSplitterException
	{
		testSplitSelectorsIntoElementCombinatorPairs("X",new CssElementCombinatorPair(null, "X"));
		testSplitSelectorsIntoElementCombinatorPairs("X Y",new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.SPACE, "Y"));
		testSplitSelectorsIntoElementCombinatorPairs("X+Y",new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.PLUS, "Y"));
		testSplitSelectorsIntoElementCombinatorPairs("X>Y",new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.GREATER_THAN, "Y"));
		testSplitSelectorsIntoElementCombinatorPairs("X~Y",new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.TILDA, "Y"));
		
		testSplitSelectorsIntoElementCombinatorPairs("X Y Z",new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.SPACE, "Y"),new CssElementCombinatorPair(CssCombinatorType.SPACE, "Z"));
		testSplitSelectorsIntoElementCombinatorPairs("X+Y+Z+A",new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.PLUS, "Y"),new CssElementCombinatorPair(CssCombinatorType.PLUS, "Z"),new CssElementCombinatorPair(CssCombinatorType.PLUS, "A"));
		testSplitSelectorsIntoElementCombinatorPairs("X>Y>Z",new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.GREATER_THAN, "Y"),new CssElementCombinatorPair(CssCombinatorType.GREATER_THAN, "Z"));
		testSplitSelectorsIntoElementCombinatorPairs("X~Y~Z~A~B",new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.TILDA, "Y"),new CssElementCombinatorPair(CssCombinatorType.TILDA, "Z"),new CssElementCombinatorPair(CssCombinatorType.TILDA, "A"),new CssElementCombinatorPair(CssCombinatorType.TILDA, "B"));
		
		testSplitSelectorsIntoElementCombinatorPairs("X Y+Z",new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.SPACE, "Y"),new CssElementCombinatorPair(CssCombinatorType.PLUS, "Z"));
		testSplitSelectorsIntoElementCombinatorPairs("X Y+Z>A~B",new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.SPACE, "Y"),new CssElementCombinatorPair(CssCombinatorType.PLUS, "Z"),new CssElementCombinatorPair(CssCombinatorType.GREATER_THAN, "A"),new CssElementCombinatorPair(CssCombinatorType.TILDA, "B"));
	}

	public void testSplitSelectorsIntoElementCombinatorPairs(String processedSelector,CssElementCombinatorPair... expectedOutput) throws CssSelectorStringSplitterException {
		List<CssElementCombinatorPair> elementCombinatorPairs=splitter.splitSelectorsIntoElementCombinatorPairs(processedSelector);
		assertEquals("processedString="+processedSelector+"; elementCombinatorPairs="+elementCombinatorPairs.toString(),asList(expectedOutput),elementCombinatorPairs);
	}
	
	
	@Test
	public void listSplitSelectorsIntoElementCombinatorPairsTester() throws CssSelectorStringSplitterException
	{
		testListSplitSelectorsIntoElementCombinatorPairs("X",asList(asList(new CssElementCombinatorPair(null, "X"))));
		testListSplitSelectorsIntoElementCombinatorPairs("X,Y,Z,1",asList(asList(new CssElementCombinatorPair(null, "X")),asList(new CssElementCombinatorPair(null, "Y")),asList(new CssElementCombinatorPair(null, "Z")),asList(new CssElementCombinatorPair(null, "1"))));
		testListSplitSelectorsIntoElementCombinatorPairs("X+Y",asList(asList(new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.PLUS, "Y"))));
		testListSplitSelectorsIntoElementCombinatorPairs("X+Y,A>B",asList(asList(new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.PLUS, "Y")),asList(new CssElementCombinatorPair(null, "A"),new CssElementCombinatorPair(CssCombinatorType.GREATER_THAN, "B"))));
		testListSplitSelectorsIntoElementCombinatorPairs("X Y+Z>A~B,C,D E",asList(asList(new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.SPACE, "Y"),new CssElementCombinatorPair(CssCombinatorType.PLUS, "Z"),new CssElementCombinatorPair(CssCombinatorType.GREATER_THAN, "A"),new CssElementCombinatorPair(CssCombinatorType.TILDA, "B")),
				asList(new CssElementCombinatorPair(null, "C")),asList(new CssElementCombinatorPair(null, "D"),new CssElementCombinatorPair(CssCombinatorType.SPACE, "E"))));
		
	}

	public void testListSplitSelectorsIntoElementCombinatorPairs(String selector,List<List<CssElementCombinatorPair>> expectedOutput) throws CssSelectorStringSplitterException {
		List<List<CssElementCombinatorPair>> elementCombinatorPairs=splitter.listSplitSelectorsIntoElementCombinatorPairs(selector);
		assertEquals("selectorString="+selector+"; elementCombinatorPairs="+elementCombinatorPairs.toString(),expectedOutput,elementCombinatorPairs);
	}
	
	
	@Test
	public void cssElementAttributeParserTester() throws CssSelectorStringSplitterException
	{
		testCssElementAttributeParser("X",new CssElementAttribute("X",new ArrayList<>()));
		testCssElementAttributeParser("*",new CssElementAttribute("*",new ArrayList<>()));

		testCssElementAttributeParser("*[X]",new CssElementAttribute("*",asList("[X]")));
		testCssElementAttributeParser("XX[YY]",new CssElementAttribute("XX",asList("[YY]")));
		testCssElementAttributeParser("XXX[YYY][ZZZ]",new CssElementAttribute("XXX",asList("[YYY]","[ZZZ]")));
		testCssElementAttributeParser("[Z]",new CssElementAttribute(null,asList("[Z]")));
		testCssElementAttributeParser("[Y][Z]",new CssElementAttribute(null,asList("[Y]","[Z]")));

	}

	public void testCssElementAttributeParser(String elementAttributeString,CssElementAttribute expectedOutput ) throws CssSelectorStringSplitterException {
		CssElementAttribute elementAttributeList=attribute.createElementAttribute(elementAttributeString);
		assertEquals("elementstringWithattributes="+elementAttributeString,expectedOutput,elementAttributeList);
	}
	@Test
	public void checkValidElementAttributeTester()
	{
		//testCheckValidElementAttribute("****");
		
		
		testCheckValidElementAttribute("xx[");
		testCheckValidElementAttribute("xx[y]zz");
		testCheckValidElementAttribute("xx[yy][qq");
		testCheckValidElementAttribute("[yy]xx");

		testCheckValidElementAttribute("[yy]xx");
		testCheckValidElementAttribute("[zz]xx[yy]");
		testCheckValidElementAttribute("[]");
		testCheckValidElementAttribute("[");
		testCheckValidElementAttribute("]");
		testCheckValidElementAttribute("x[]");
		testCheckValidElementAttribute("x[[y]");
		testCheckValidElementAttribute("x[y]]");
		testCheckValidElementAttribute("x[[y]]");
	}
	private void testCheckValidElementAttribute(String elementAttributeString) 
	{
		try {
			CssElementAttribute elementAttributeList=attribute.createElementAttribute(elementAttributeString);
			fail("CssSelectorStringSplitterException not thrown for: "+elementAttributeString);
		} catch (CssSelectorStringSplitterException e) {
			//success
		}
	}

}
