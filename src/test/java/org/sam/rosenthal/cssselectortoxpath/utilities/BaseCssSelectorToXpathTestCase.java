package org.sam.rosenthal.cssselectortoxpath.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseCssSelectorToXpathTestCase {
	private static final String ERROR_INVALID_ATTRIBUTE_VALUE = "Invalid attribute value";
	private static final String ERROR_INVALID_ELEMENT_AND_OR_ATTRIBUTES = "Invalid element and/or attributes";
	private static final String ERROR_QUOTATIONS_MISMATCHED = "Quotations mismatched";
	private static final String ERROR_INVALID_CSS_SELECTOR_TRAILING_COMMA = "Invalid CSS Selector, trailing ','";
	private static final String ERROR_INVALID_ID_CSS_SELECTOR = "Invalid id CSS Selector";
	private static final String ERROR_INVALID_CLASS_CSS_SELECTOR = "Invalid class CSS Selector";
	private static final String ERROR_EMPTY_CSS_SELECTOR1 = "Empty CSS Selector1";
	private static final String ERROR_CSS_SELECTOR_STRING_IS_NULL = "CSS Selector String is null";
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
		addBaseCaseToXPath(baseCases,"typeSelector", "A","//A");
		if(includeOrCase)
		{
			addBaseCaseToXPath(baseCases,"or", "A,B","(//A)|(//B)");
		}

		addBaseCaseToXPath(baseCases,"universalSelector", "*","//*");
		addBaseCaseToXPath(baseCases,"idSelector", "#B","//*[@id=\"B\"]");
		addBaseCaseToXPath(baseCases,"classSelector", ".B",	"//*[contains(concat(\" \",normalize-space(@class),\" \"),\" B \")]");
		
		addBaseCaseToXPath(baseCases,"idSelector2", "A#B","//A[@id=\"B\"]");
		addBaseCaseToXPath(baseCases,"classSelector2", "A.B","//A[contains(concat(\" \",normalize-space(@class),\" \"),\" B \")]");

		//Atribute(simple) selectors
		addBaseCaseToXPath(baseCases,"attribute", "A[B]","//A[@B]");
		addBaseCaseToXPath(baseCases,"attributeValueWithoutQuotes", "A[B=C]","//A[@B=\"C\"]");

		addBaseCaseToXPath(baseCases,"carrotEqualAttribute", "A[B^=\"C\"]","//A[starts-with(@B,\"C\")]");
		addBaseCaseToXPath(baseCases,"starEqualAttribute", "A[B*=\"C\"]","//A[contains(@B,\"C\")]");
		addBaseCaseToXPath(baseCases,"moneySignEqualAttribute", "A[B$=\"C\"]","//A[substring(@B,string-length(@B)-string-length(\"C\")+1)=\"C\"]");
		addBaseCaseToXPath(baseCases,"tildaEqualAttribute", "A[B~=\"C\"]","//A[contains(concat(\" \",normalize-space(@B),\" \"),\" C \")]");
		addBaseCaseToXPath(baseCases,"pipeEqualAttribute","A[B|=\"C\"]","//A[starts-with(@B,concat(\"C\",\"-\")) or @B=\"C\"]");
		//moving this test so it it does not immediately follow similar test without quotes because in selenium test we did not want to put a sleep to prevent stale element from occuring
		addBaseCaseToXPath(baseCases,"equalAttribute", "A[B='C']","//A[@B=\"C\"]");

		//Combinators
		addBaseCaseToXPath(baseCases,"descendantCombinator","A B","//A//B");
		addBaseCaseToXPath(baseCases,"childCombinator","A>B","//A/B");
		addBaseCaseToXPath(baseCases,"adjacentSiblingCombinator","A+B","//A/following-sibling::*[1]/self::B");
		addBaseCaseToXPath(baseCases,"generalSiblingCombinator","A~B","//A/following-sibling::B");
		return baseCases;
	}
	private static void addBaseCaseToXPath(List<BaseCssSelectorToXpathTestCase> baseCases,String name,String cssSelector, String expectedXpath) {
		baseCases.add(new BaseCssSelectorToXpathTestCase(name,cssSelector,expectedXpath));
	}
	
	public static Map<String,String> getBaseCssSelectorToXpathExceptionTestCases()
	{
		HashMap<String,String> baseCases=new HashMap<>();
		baseCases.put(null,ERROR_CSS_SELECTOR_STRING_IS_NULL);
		
		baseCases.put("",ERROR_EMPTY_CSS_SELECTOR1);
		baseCases.put(" ",ERROR_EMPTY_CSS_SELECTOR1);
		baseCases.put("A,,B",ERROR_EMPTY_CSS_SELECTOR1);

		
		baseCases.put("A..B",ERROR_INVALID_CLASS_CSS_SELECTOR);
		
		baseCases.put("A##B",ERROR_INVALID_ID_CSS_SELECTOR);
		baseCases.put("A#[B]",ERROR_INVALID_ID_CSS_SELECTOR);
		
		baseCases.put("A,",ERROR_INVALID_CSS_SELECTOR_TRAILING_COMMA);
		baseCases.put("A[B='C\"]",ERROR_QUOTATIONS_MISMATCHED);
		
		baseCases.put("A@!",ERROR_INVALID_ELEMENT_AND_OR_ATTRIBUTES);
		baseCases.put("A[B='']",ERROR_INVALID_ELEMENT_AND_OR_ATTRIBUTES);
		
		baseCases.put("A[B=]",ERROR_INVALID_ATTRIBUTE_VALUE);
		baseCases.put("A[B'C']",ERROR_INVALID_ATTRIBUTE_VALUE);

		String[] pseudoClasses=getPseudoClasses();
		for(String pseudoClass:pseudoClasses)
		{
			baseCases.put(pseudoClass,CssSelectorToXPathConverterUnsupportedPseudoClassException.getPseudoClassUnsupportedError(pseudoClass));

		}
		return baseCases;
	}
	
	public static String[] getPseudoClasses()
	{
		//listing every pseudo class in: https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Selectors
		//so that in the future there will be some we can't handle
		String[] pseudoClasses= {
				":active",
				":active",
				":any-link",
				":checked",
				":default",
				":defined",
				":dir(A)",
				":disabled",
				":empty",
				":enabled",
				":first",
				":first-child",
				":first-of-type",
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
				":last-child",
				":last-of-type",
				":left",
				":link",
				":not(A)",
				":nth-child(A)",
				":nth-last-child(A)",
				":nth-last-of-type(A)",
				":nth-of-type(A)",
				":only-child",
				":only-of-type",
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
