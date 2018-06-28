package org.sam.rosenthal.cssselectortoxpath.utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.sam.rosenthal.cssselectortoxpath.model.CssElementCombinatorPair;
import org.sam.rosenthal.cssselectortoxpath.model.CssAttribute;
import org.sam.rosenthal.cssselectortoxpath.model.CssAttributeValueType;
import org.sam.rosenthal.cssselectortoxpath.model.CssCombinatorType;
import org.sam.rosenthal.cssselectortoxpath.model.CssElementAttributes;

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
		
		testSplitSelectorsIntoElementCombinatorPairs("X  Y",new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.SPACE, "Y"));
		testSplitSelectorsIntoElementCombinatorPairs("X   Y",new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.SPACE, "Y"));
		testSplitSelectorsIntoElementCombinatorPairs("X+ Y",new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.PLUS, "Y"));
		testSplitSelectorsIntoElementCombinatorPairs("X >Y",new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.GREATER_THAN, "Y"));
		testSplitSelectorsIntoElementCombinatorPairs("X ~ Y",new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.TILDA, "Y"));

		testSplitSelectorsIntoElementCombinatorPairs("X Y Z",new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.SPACE, "Y"),new CssElementCombinatorPair(CssCombinatorType.SPACE, "Z"));
		testSplitSelectorsIntoElementCombinatorPairs("X+Y+Z+A",new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.PLUS, "Y"),new CssElementCombinatorPair(CssCombinatorType.PLUS, "Z"),new CssElementCombinatorPair(CssCombinatorType.PLUS, "A"));
		testSplitSelectorsIntoElementCombinatorPairs("X>Y>Z",new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.GREATER_THAN, "Y"),new CssElementCombinatorPair(CssCombinatorType.GREATER_THAN, "Z"));
		testSplitSelectorsIntoElementCombinatorPairs("X~Y~Z~A~B",new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.TILDA, "Y"),new CssElementCombinatorPair(CssCombinatorType.TILDA, "Z"),new CssElementCombinatorPair(CssCombinatorType.TILDA, "A"),new CssElementCombinatorPair(CssCombinatorType.TILDA, "B"));
		
		testSplitSelectorsIntoElementCombinatorPairs("X Y+Z",new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.SPACE, "Y"),new CssElementCombinatorPair(CssCombinatorType.PLUS, "Z"));
		testSplitSelectorsIntoElementCombinatorPairs("X Y+Z>A~B",new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.SPACE, "Y"),new CssElementCombinatorPair(CssCombinatorType.PLUS, "Z"),new CssElementCombinatorPair(CssCombinatorType.GREATER_THAN, "A"),new CssElementCombinatorPair(CssCombinatorType.TILDA, "B"));
		
		testSplitSelectorsIntoElementCombinatorPairs("X  Y +Z >A ~ B",new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.SPACE, "Y"),new CssElementCombinatorPair(CssCombinatorType.PLUS, "Z"),new CssElementCombinatorPair(CssCombinatorType.GREATER_THAN, "A"),new CssElementCombinatorPair(CssCombinatorType.TILDA, "B"));

	}

	public void testSplitSelectorsIntoElementCombinatorPairs(String processedSelector,CssElementCombinatorPair... expectedOutput) throws CssSelectorStringSplitterException {
		List<CssElementCombinatorPair> elementCombinatorPairs=splitter.splitSelectorsIntoElementCombinatorPairs(processedSelector);
		assertEquals("processedString="+processedSelector+"; elementCombinatorPairs="+elementCombinatorPairs.toString(),asList(expectedOutput),elementCombinatorPairs);
	}
	
	
	@Test
	public void listSplitSelectorsIntoElementCombinatorPairsTester() throws CssSelectorStringSplitterException
	{
		testListSplitSelectorsIntoElementCombinatorPairs("X",asList(asList(new CssElementCombinatorPair(null, "X"))));
		testListSplitSelectorsIntoElementCombinatorPairs("X,Y,Z,a",asList(asList(new CssElementCombinatorPair(null, "X")),asList(new CssElementCombinatorPair(null, "Y")),asList(new CssElementCombinatorPair(null, "Z")),asList(new CssElementCombinatorPair(null, "a"))));
		testListSplitSelectorsIntoElementCombinatorPairs("X+Y",asList(asList(new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.PLUS, "Y"))));
		testListSplitSelectorsIntoElementCombinatorPairs("X+Y,A>B",asList(asList(new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.PLUS, "Y")),asList(new CssElementCombinatorPair(null, "A"),new CssElementCombinatorPair(CssCombinatorType.GREATER_THAN, "B"))));
		testListSplitSelectorsIntoElementCombinatorPairs("X Y+Z>A~B,C,D E",asList(asList(new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.SPACE, "Y"),new CssElementCombinatorPair(CssCombinatorType.PLUS, "Z"),new CssElementCombinatorPair(CssCombinatorType.GREATER_THAN, "A"),new CssElementCombinatorPair(CssCombinatorType.TILDA, "B")),
				asList(new CssElementCombinatorPair(null, "C")),asList(new CssElementCombinatorPair(null, "D"),new CssElementCombinatorPair(CssCombinatorType.SPACE, "E"))));
		
		testListSplitSelectorsIntoElementCombinatorPairs("X          Y",asList(asList(new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.SPACE, "Y"))));
		testListSplitSelectorsIntoElementCombinatorPairs("X   ~   Y",asList(asList(new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.TILDA, "Y"))));
		testListSplitSelectorsIntoElementCombinatorPairs("X+ Y,A   > B",asList(asList(new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.PLUS, "Y")),asList(new CssElementCombinatorPair(null, "A"),new CssElementCombinatorPair(CssCombinatorType.GREATER_THAN, "B"))));
		testListSplitSelectorsIntoElementCombinatorPairs("X+ Y ,A   > B",asList(asList(new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.PLUS, "Y")),asList(new CssElementCombinatorPair(null, "A"),new CssElementCombinatorPair(CssCombinatorType.GREATER_THAN, "B"))));
		testListSplitSelectorsIntoElementCombinatorPairs("X+ Y, A   > B",asList(asList(new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.PLUS, "Y")),asList(new CssElementCombinatorPair(null, "A"),new CssElementCombinatorPair(CssCombinatorType.GREATER_THAN, "B"))));
		testListSplitSelectorsIntoElementCombinatorPairs("X+ Y , A   > B",asList(asList(new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.PLUS, "Y")),asList(new CssElementCombinatorPair(null, "A"),new CssElementCombinatorPair(CssCombinatorType.GREATER_THAN, "B"))));

		
		testListSplitSelectorsIntoElementCombinatorPairs("X  Y +Z> A  ~  B, C,D E",asList(asList(new CssElementCombinatorPair(null, "X"),new CssElementCombinatorPair(CssCombinatorType.SPACE, "Y"),new CssElementCombinatorPair(CssCombinatorType.PLUS, "Z"),new CssElementCombinatorPair(CssCombinatorType.GREATER_THAN, "A"),new CssElementCombinatorPair(CssCombinatorType.TILDA, "B")),
				asList(new CssElementCombinatorPair(null, "C")),asList(new CssElementCombinatorPair(null, "D"),new CssElementCombinatorPair(CssCombinatorType.SPACE, "E"))));
		
	}

	public void testListSplitSelectorsIntoElementCombinatorPairs(String selector,List<List<CssElementCombinatorPair>> expectedOutput) throws CssSelectorStringSplitterException {
		List<List<CssElementCombinatorPair>> elementCombinatorPairs=splitter.listSplitSelectorsIntoElementCombinatorPairs(selector);
		assertEquals("selectorString="+selector+"; elementCombinatorPairs="+elementCombinatorPairs.toString(),expectedOutput,elementCombinatorPairs);
	}
	
	
	@Test
	public void cssElementAttributeParserTester() throws CssSelectorStringSplitterException
	{
		testCssElementAttributeParser("X",new CssElementAttributes("X",new ArrayList<>()));
		testCssElementAttributeParser("x",new CssElementAttributes("x",new ArrayList<>()));
		testCssElementAttributeParser("*",new CssElementAttributes("*",new ArrayList<>()));
		testCssElementAttributeParser("_",new CssElementAttributes("_",new ArrayList<>()));
		testCssElementAttributeParser("-X",new CssElementAttributes("-X",new ArrayList<>()));
		testCssElementAttributeParser("x1",new CssElementAttributes("x1",new ArrayList<>()));
		testCssElementAttributeParser("-X9",new CssElementAttributes("-X9",new ArrayList<>()));
		testCssElementAttributeParser("-xx",new CssElementAttributes("-xx",new ArrayList<>()));
		testCssElementAttributeParser("XX",new CssElementAttributes("XX",new ArrayList<>()));
		
		testCssElementAttributeParser("X[X]",new CssElementAttributes("X",asList(new CssAttribute("X", null, (CssAttributeValueType)null))));

		testCssElementAttributeParser("X[X=\" \"]",new CssElementAttributes("X",asList(new CssAttribute("X", " ", CssAttributeValueType.EQUAL))));
		testCssElementAttributeParser("X[X=\"AAA\"]",new CssElementAttributes("X",asList(new CssAttribute("X", "AAA", CssAttributeValueType.EQUAL))));
		testCssElementAttributeParser("*[ X~=\"-\"]",new CssElementAttributes("*",asList(new CssAttribute("X", "-", "~="))));
		testCssElementAttributeParser("-X[X |=\"_-b\"]",new CssElementAttributes("-X",asList(new CssAttribute("X", "_-b", CssAttributeValueType.PIPE_EQUAL))));
		testCssElementAttributeParser("XX[X^= \"__00_aa-\"]",new CssElementAttributes("XX",asList(new CssAttribute("X", "__00_aa-", "^="))));
		testCssElementAttributeParser("-X9[X$=\"90\" ]",new CssElementAttributes("-X9",asList(new CssAttribute("X", "90", CssAttributeValueType.DOLLAR_SIGN_EQUAL))));
		testCssElementAttributeParser("-xx[ X *= \"9\" ]",new CssElementAttributes("-xx",asList(new CssAttribute("X", "9", "*="))));

		testCssElementAttributeParser("x9[X=\"9\"][X]",new CssElementAttributes("x9",asList(new CssAttribute("X", "9", CssAttributeValueType.EQUAL),new CssAttribute("X", null, (CssAttributeValueType)null))));

		
		testCssElementAttributeParser("x9[X=\"9\"][x=\"X\"]",new CssElementAttributes("x9",asList(new CssAttribute("X", "9", CssAttributeValueType.EQUAL),new CssAttribute("x", "X", CssAttributeValueType.EQUAL))));
		testCssElementAttributeParser("-x9[ X~=\"9\"][-x9=\"9\"]",new CssElementAttributes("-x9",asList(new CssAttribute("X", "9", "~="),new CssAttribute("-x9", "9", "="))));
		testCssElementAttributeParser("[ X *= \"9\" ]",new CssElementAttributes(null,asList(new CssAttribute("X", "9", CssAttributeValueType.STAR_EQUAL))));
		testCssElementAttributeParser("[X9|=\"9\"][-X ^= \"9\"]",new CssElementAttributes(null,asList(new CssAttribute("X9", "9", "|="),new CssAttribute("-X", "9", "^="))));

	}

	public void testCssElementAttributeParser(String elementAttributeString,CssElementAttributes expectedOutput ) throws CssSelectorStringSplitterException {
		CssElementAttributes elementAttributeList=attribute.createElementAttribute(elementAttributeString);
		assertEquals("elementstringWithattributes="+elementAttributeString,expectedOutput,elementAttributeList);
	}
	@Test
	public void checkValidElementAttributeTester()
	{
		testCheckInValidElementAttribute("**");
		testCheckInValidElementAttribute("*X");
		testCheckInValidElementAttribute("-");
		testCheckInValidElementAttribute("--");
		testCheckInValidElementAttribute("---");
		
		testCheckInValidElementAttribute("#");
		testCheckInValidElementAttribute("~=");
		testCheckInValidElementAttribute("$%^@");

		testCheckInValidElementAttribute("xx[x^=\'9\"]");
		testCheckInValidElementAttribute("xx[x$=\"10\']");
		testCheckInValidElementAttribute("xx[x^=9\"]");
		testCheckInValidElementAttribute("xx[x$=\"10]");

		testCheckInValidElementAttribute("xx[x\"yyy\"]");
		testCheckInValidElementAttribute("xx[x$=]");
		testCheckInValidElementAttribute("xx[\"yyy\"]");
		testCheckInValidElementAttribute("xx[=\"yyy\"]");

		testCheckInValidElementAttribute("xx[x$=$='BBB']");
		testCheckInValidElementAttribute("xx[x~=='BBB']");
		testCheckInValidElementAttribute("xx[aa=\"yyy\"\"AA\"]");

		
		testCheckInValidElementAttribute("xx[");		
		testCheckInValidElementAttribute("xx[");
		testCheckInValidElementAttribute("xx[y]zz");
		testCheckInValidElementAttribute("xx[yy][qq");
		testCheckInValidElementAttribute("[yy]xx");

		testCheckInValidElementAttribute("[yy]xx");
		testCheckInValidElementAttribute("[zz]xx[yy]");
		testCheckInValidElementAttribute("[]");
		testCheckInValidElementAttribute("[");
		testCheckInValidElementAttribute("]");
		testCheckInValidElementAttribute("x[]");
		testCheckInValidElementAttribute("x[[y]");
		testCheckInValidElementAttribute("x[y]]");
		testCheckInValidElementAttribute("x[[y]]");
	}
	private void testCheckInValidElementAttribute(String elementAttributeString) 
	{
		try {
			System.out.println(attribute.createElementAttribute(elementAttributeString));
			fail("CssSelectorStringSplitterException not thrown for: "+elementAttributeString);
		} catch (CssSelectorStringSplitterException e) {
			//success
		}
	}

}
