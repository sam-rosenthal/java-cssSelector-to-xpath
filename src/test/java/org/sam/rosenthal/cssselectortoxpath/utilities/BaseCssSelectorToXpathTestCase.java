package org.sam.rosenthal.cssselectortoxpath.utilities;

import java.util.ArrayList;
import java.util.List;

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

		addBaseCaseToXPath(baseCases,"equalAttribute", "A[B=C]","//A[@B=\"C\"]");
		addBaseCaseToXPath(baseCases,"carrotEqualAttribute", "A[B^=\"C\"]","//A[starts-with(@B,\"C\")]");
		addBaseCaseToXPath(baseCases,"starEqualAttribute", "A[B*=\"C\"]","//A[contains(@B,\"C\")]");
		addBaseCaseToXPath(baseCases,"moneySignEqualAttribute", "A[B$=\"C\"]","//A[substring(@B,string-length(@B)-string-length(\"C\")+1)=\"C\"]");
		addBaseCaseToXPath(baseCases,"tildaEqualAttribute", "A[B~=\"C\"]","//A[contains(concat(\" \",normalize-space(@B),\" \"),\" C \")]");
		addBaseCaseToXPath(baseCases,"pipeEqualAttribute","A[B|=\"C\"]","//A[starts-with(@B,concat(\"C\",\"-\")) or @B=\"C\"]");

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
