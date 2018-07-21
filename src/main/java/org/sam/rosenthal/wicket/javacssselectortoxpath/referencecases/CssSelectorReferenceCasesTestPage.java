package org.sam.rosenthal.wicket.javacssselectortoxpath.referencecases;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.sam.rosenthal.cssselectortoxpath.utilities.basetestcases.BaseCssSelectorToXpathTestCase;

public class CssSelectorReferenceCasesTestPage extends WebPage{

	private static final long serialVersionUID = 1L;
	
	public CssSelectorReferenceCasesTestPage()
	{
		List<BaseCssSelectorToXpathTestCase> baseCases=BaseCssSelectorToXpathTestCase.getBaseCssSelectorToXpathTestCases(false);
		for(BaseCssSelectorToXpathTestCase cssSelectorToXpathCase: baseCases)
		{
			String name = cssSelectorToXpathCase.getName();
			String cssSelector = cssSelectorToXpathCase.getCssSelector();
			String xpath = cssSelectorToXpathCase.getExpectedXpath();

			add(new Label(name,"Test Case Name="+name+", CSS Selector="+cssSelector+", XPath="+xpath));
		}
	}
}
