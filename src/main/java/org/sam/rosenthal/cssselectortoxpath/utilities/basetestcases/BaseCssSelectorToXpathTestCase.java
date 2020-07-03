package org.sam.rosenthal.cssselectortoxpath.utilities.basetestcases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sam.rosenthal.cssselectortoxpath.utilities.CssElementAttributeParser;
import org.sam.rosenthal.cssselectortoxpath.utilities.CssSelectorStringSplitter;
import org.sam.rosenthal.cssselectortoxpath.utilities.CssSelectorToXPathConverterUnsupportedPseudoClassException;

public class BaseCssSelectorToXpathTestCase {
	private String name;
	private String cssSelector;
	private String expectedXpath;

	public BaseCssSelectorToXpathTestCase(String nameIn,String cssSelectorIn, String expectedXpathIn)
	{
		this.name=nameIn;
		this.cssSelector=cssSelectorIn;
		this.expectedXpath=expectedXpathIn;
	}
	public static List<BaseCssSelectorToXpathTestCase> getBaseCssSelectorToXpathTestCases(boolean includeOrCase)
	{
		List<BaseCssSelectorToXpathTestCase> baseCases=new ArrayList<>();
		//Simple selectors
		addBaseCaseToXPath(baseCases,"typeSelector", "div","//div");
		if(includeOrCase)
		{
			addBaseCaseToXPath(baseCases,"orSelector", "A,B","(//A)|(//B)");
		}

		addBaseCaseToXPath(baseCases,"universalSelector", "*","//*");
		addBaseCaseToXPath(baseCases,"idSelector", "span#idSelector","//span[@id=\"idSelector\"]");
		addBaseCaseToXPath(baseCases,"classSelector", "form.classSelector",	"//form[contains(concat(\" \",normalize-space(@class),\" \"),\" classSelector \")]");
		
		//Atribute(simple) selectors
		addBaseCaseToXPath(baseCases,"attribute", "a[href]","//a[@href]");
		addBaseCaseToXPath(baseCases,"attributeValueWithoutQuotes", "li","//li");

		addBaseCaseToXPath(baseCases,"carrotEqualAttribute", "a[data-alt^=\"sam\"]","//a[starts-with(@data-alt,\"sam\")]");
		addBaseCaseToXPath(baseCases,"starEqualAttribute", "a[data-alt*=\"css\"]","//a[contains(@data-alt,\"css\")]");
		addBaseCaseToXPath(baseCases,"moneySignEqualAttribute", "a[href$=\"pdf\"]","//a[substring(@href,string-length(@href)-string-length(\"pdf\")+1)=\"pdf\"]");
		addBaseCaseToXPath(baseCases,"tildaEqualAttribute", "a[data-alt~=\"converter\"]","//a[contains(concat(\" \",normalize-space(@data-alt),\" \"),\" converter \")]");
		addBaseCaseToXPath(baseCases,"pipeEqualAttribute","li[data-years|=\"1900\"]","//li[starts-with(@data-years,concat(\"1900\",\"-\")) or @data-years=\"1900\"]");
		//moving this test so it it does not immediately follow similar test without quotes because in selenium test we did not want to put a sleep to prevent stale element from occuring
		addBaseCaseToXPath(baseCases,"equalAttribute", "a[href=\"https://css-selector-to-xpath.appspot.com\"]","//a[@href=\"https://css-selector-to-xpath.appspot.com\"]");

		//Combinators
		addBaseCaseToXPath(baseCases,"descendantCombinator","div a","//div//a");
		addBaseCaseToXPath(baseCases,"childCombinator","div>span","//div/span");
		addBaseCaseToXPath(baseCases,"adjacentSiblingCombinator","div+span","//div/following-sibling::*[1]/self::span");
		addBaseCaseToXPath(baseCases,"generalSiblingCombinator","div~h1","//div/following-sibling::h1");
		
		//Implemented psuedo classes
		addBaseCaseToXPath(baseCases,"empty","span:empty","//span[not(*) and .=\"\"]");	
		addBaseCaseToXPath(baseCases,"first-child","div:first-child","//div[count(preceding-sibling::*)=0]");	
		addBaseCaseToXPath(baseCases,"last-child","span:last-child","//span[count(following-sibling::*)=0]");	
		addBaseCaseToXPath(baseCases,"only-child","form:only-child","//form[count(preceding-sibling::*)=0][count(following-sibling::*)=0]");	
		addBaseCaseToXPath(baseCases,"first-of-type","p:first-of-type","//p[1]");	
		addBaseCaseToXPath(baseCases,"last-of-type","h1:last-of-type","//h1[count(following-sibling::h1)=0]");	
		addBaseCaseToXPath(baseCases,"only-of-type","span:only-of-type","//span[1][count(following-sibling::span)=0]");	
		addBaseCaseToXPath(baseCases,"nth-child_3n","li:nth-child(3n)","//li[((count(preceding-sibling::*)+1) mod 3)=0]");
		addBaseCaseToXPath(baseCases,"nth-child_even","li:nth-child(even)","//li[((count(preceding-sibling::*)+1) mod 2)=0]");
		addBaseCaseToXPath(baseCases,"nth-last-child_odd","li:nth-last-child(odd)","//li[(count(following-sibling::*)=0) or (((count(following-sibling::*)-0) mod 2)=0)]");
		addBaseCaseToXPath(baseCases,"nth-last-child_3","li:nth-last-child(3)","//li[count(following-sibling::*)=2]");
		addBaseCaseToXPath(baseCases,"nth-of-type_n_2","span:nth-of-type(n+2)","//span[(count(preceding-sibling::span)=1) or (((count(preceding-sibling::span)>2) and (((count(preceding-sibling::span)-1) mod 1)=0)))]");
		addBaseCaseToXPath(baseCases,"nth-of-type_3n_1","span:nth-of-type(3n+1)","//span[(count(preceding-sibling::span)=0) or (((count(preceding-sibling::span)-0) mod 3)=0)]");
		addBaseCaseToXPath(baseCases,"nth-of-type_-5n_1","span:nth-of-type(-5n+1)","//span[1]");
		addBaseCaseToXPath(baseCases,"nth-last-of-type_3n-1","span:nth-last-of-type(3n-1)","//span[(count(following-sibling::span)=1) or (((count(following-sibling::span)-1) mod 3)=0)]");
		addBaseCaseToXPath(baseCases,"nth-last-of-type_-3n_7","span:nth-last-of-type(3n+7)","//span[(count(following-sibling::span)=6) or (((count(following-sibling::span)>7) and (((count(following-sibling::span)-6) mod 3)=0)))]");

		return baseCases;
	}
	private static void addBaseCaseToXPath(List<BaseCssSelectorToXpathTestCase> baseCases,String name,String cssSelector, String expectedXpath) {
		baseCases.add(new BaseCssSelectorToXpathTestCase(name,cssSelector,expectedXpath));
	}
	
	public static Map<String,String> getBaseCssSelectorToXpathExceptionTestCases()
	{
		HashMap<String,String> baseCases=new HashMap<>();
		baseCases.put(null,CssSelectorStringSplitter.ERROR_EMPTY_CSS_SELECTOR);
		
		baseCases.put("",CssSelectorStringSplitter.ERROR_EMPTY_CSS_SELECTOR);
		baseCases.put(" ",CssSelectorStringSplitter.ERROR_EMPTY_CSS_SELECTOR);
		baseCases.put("A,,B",CssSelectorStringSplitter.ERROR_EMPTY_CSS_SELECTOR);

		
		baseCases.put("A..B",CssSelectorStringSplitter.ERROR_INVALID_CLASS_CSS_SELECTOR);
		
		baseCases.put("A##B",CssSelectorStringSplitter.ERROR_INVALID_ID_CSS_SELECTOR);
		baseCases.put("A#[B]",CssSelectorStringSplitter.ERROR_INVALID_ID_CSS_SELECTOR);
		
		baseCases.put("A,",CssSelectorStringSplitter.ERROR_INVALID_CSS_SELECTOR_TRAILING_COMMA);
		baseCases.put("A[B='C\"]",CssElementAttributeParser.ERROR_QUOTATIONS_MISMATCHED);
		
		baseCases.put("A@!",CssElementAttributeParser.ERROR_INVALID_ELEMENT_AND_OR_ATTRIBUTES);
		baseCases.put("A[B='']",CssElementAttributeParser.ERROR_INVALID_ELEMENT_AND_OR_ATTRIBUTES);
		
		baseCases.put("A[B=]",CssElementAttributeParser.ERROR_INVALID_ATTRIBUTE_VALUE);
		baseCases.put("A[B'C']",CssElementAttributeParser.ERROR_INVALID_ATTRIBUTE_VALUE);
		baseCases.put("A[b b]",CssElementAttributeParser.ERROR_INVALID_ATTRIBUTE_VALUE);
		
		baseCases.put("A[]",CssSelectorStringSplitter.ERROR_INVALID_SELECTOR);

		baseCases.put("A]b",CssSelectorStringSplitter.ERROR_INVALID_CSS_SELECTOR_INCONSISTENT_BRACKETS);
		baseCases.put("A[b",CssSelectorStringSplitter.ERROR_INVALID_CSS_SELECTOR_INCONSISTENT_BRACKETS);
		baseCases.put("Ab[",CssSelectorStringSplitter.ERROR_INVALID_CSS_SELECTOR_INCONSISTENT_BRACKETS);
		baseCases.put("Ab]",CssSelectorStringSplitter.ERROR_INVALID_CSS_SELECTOR_INCONSISTENT_BRACKETS);
		baseCases.put("Ab][c",CssSelectorStringSplitter.ERROR_INVALID_CSS_SELECTOR_INCONSISTENT_BRACKETS);

		
		String[] pseudoClasses=getUnimplementedPseudoClasses();
		for(String pseudoClass:pseudoClasses)
		{
			baseCases.put(pseudoClass,CssSelectorToXPathConverterUnsupportedPseudoClassException.getPseudoClassUnsupportedError(pseudoClass));

		}
		return baseCases;
	}
	
	public static String[] getUnimplementedPseudoClasses()
	{
		//listing every pseudo class in: https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Selectors
		//so that in the future there will be some we can't handle
		String[] pseudoClasses= {
				":active",
				":any-link",
				":checked",
				":default",
				":defined",
				":dir(A)",
				":disabled",
//implemented	":empty",
				":enabled",
				":first",
//implemented	":first-child",
//implemented	":first-of-type",
				":fullscreen",
				":focus",
				":focus-visible",
				":focus-within",
				":host",
				":host(A)",
				":host-context(A)",
				":hover",
				":indeterminate",
				":in-range",
				":invalid",
				":lang(A)",
//implemented	":last-child",
//implemented	":last-of-type",
				":left",
				":link",
				":not(A)",
//implemented	":nth-child(A)",
//implemented	":nth-last-child(A)",
//implemented	":nth-last-of-type(A)",
//implemented	":nth-of-type(A)",
//implemented	":only-child",
//implemented	":only-of-type",
				":optional",
				":out-of-range",
				":placeholder-shown",
				":read-only",
				":read-write",
				":required",
				":right",
				":root",
				":scope",
				":target",
				":valid",
				":visited"};
		
		return pseudoClasses;
	}

	public String getName() {
		return name;
	}
	public String getCssSelector() {
		return cssSelector;
	}
	public String getExpectedXpath() {
		return expectedXpath;
	}
}
