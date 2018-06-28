package org.sam.rosenthal.cssselectortoxpath.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sam.rosenthal.cssselectortoxpath.model.CssElementCombinatorPair;
import org.sam.rosenthal.cssselectortoxpath.model.CssAttributeValueType;
import org.sam.rosenthal.cssselectortoxpath.model.CssCombinatorType;

public class CssSelectorStringSplitter 
{
	private static final String COMBINATORS = " ~+>";
	private static final String COMBINATOR_RE = "["+COMBINATORS+"]";

	private static final String X = "([^"+COMBINATORS+"\\[]*(\\[[^\\]]+\\])*)";
	private static final String XY = "^"+X+"($|(\\s*("+COMBINATOR_RE+")\\s*"+"([^"+COMBINATORS+"].*)$))";

	public static final String ERROR_INVALID_CLASS_CSS_SELECTOR = "invalid class css selector";
	public static final String ERROR_INVALID_ID_CSS_SELECTOR = "invalid id css selector";
	public static final String ERROR_SELECTOR_STRING_IS_NULL = "Selector string is null";
	private static final String WHITESPACE_PLACE_HOLDER = "~#_placeHolder_#";

	protected String removeNonCssSelectorWhiteSpaces(String selectorString) throws CssSelectorStringSplitterException
	{
//	This method should perform the following
//	i.	Remove all leading and trailing white spaces
//	ii.	Remove all white spaces except tabs and actual space(“ “)
//	1.	Note, very important
//	a.	‘\t’ corresponds to the tab character in java
//	iii.	Consolidate all consecutive tabs and “ “ into a single “ “ and single tab into a “ “‘
//	1.	The final string should have no tabs only non consecutive spaces
//	b.	Implementation
//	i.	Check for null string and if found throw a CssSelectorStringSplitterException
//	ii.	Use String.trim() to remove leading and trailing spaces
//	iii.	Use String.replaceAll()  to manipulate the string
//	1.	The tricky part is that tab and “ “ are both white spaces
//	2.	Preprocess the string
//	a.	Replace all tabs with  a unique string like “~+_placeHolder_+”
//	b.	Replace all spaces with the same unique string
//	i.	Remember at the end we want only spaces
//	3.	Replace all white spaces with the empty string “”
//	4.	Replace “~+_placeHolder_+” with “ “
		if(selectorString==null)
		{
			throw new CssSelectorStringSplitterException(ERROR_SELECTOR_STRING_IS_NULL);
		}
		else
		{
			selectorString=selectorString.trim();
			selectorString=selectorString.replaceAll("[ \\t]+", WHITESPACE_PLACE_HOLDER);
			selectorString=selectorString.replaceAll("\\s+","");
			selectorString=selectorString.replaceAll("("+WHITESPACE_PLACE_HOLDER+")+", " ");
			invalClassIdPairCheck(selectorString);
			selectorString=selectorString.replaceAll("#"+classIdCombinatorRE(),"[id=\"$1\"]");
			selectorString=selectorString.replaceAll("[.]"+classIdCombinatorRE(),"[class~=\"$1\"]");
//			System.out.println(selectorString);
			return selectorString;
		}
	}
	private String classIdCombinatorRE()
	{
		StringBuilder builder=new StringBuilder("([^.#\\[,");
		for(CssCombinatorType combinatorType:CssCombinatorType.values())
		{
			builder.append(combinatorType.getCombinatorChar());
		}
		builder.append("]+)");
		return builder.toString();
		
	}
	
	
	protected void invalClassIdPairCheck(String selectorString) throws CssSelectorStringSplitterException 
	{
		String nextSelectorIdentifier="[.#\\[]";
		Pattern pattern = Pattern.compile("#"+nextSelectorIdentifier);
		Matcher match = pattern.matcher(selectorString);
		if (match.find())
		{
			throw new CssSelectorStringSplitterException(ERROR_INVALID_ID_CSS_SELECTOR);
		}
		pattern= Pattern.compile("[.]"+nextSelectorIdentifier);
		match = pattern.matcher(selectorString);
		if (match.find())
		{
			throw new CssSelectorStringSplitterException(ERROR_INVALID_CLASS_CSS_SELECTOR);
		}
	}
	
	public List<String> splitSelectors(String selectorString) throws CssSelectorStringSplitterException
	{
		selectorString=removeNonCssSelectorWhiteSpaces(selectorString);
		System.out.println("ADJUSTED="+selectorString);
		//selectorString=removeNonCssSelectorWhiteSpaces(selectorString);
		//split() will not error out if there is a trailing ','
		int index=selectorString.lastIndexOf(',');
		if(index==(selectorString.length()-1))
		{
			throw new CssSelectorStringSplitterException("Invalid Css Selector, trailing ','");		
		}
		String[] selectorArray=selectorString.split(",");
		List<String> selectorList=new ArrayList<>();
		for(String selector:selectorArray)
		{
			selector=selector.trim();
			if(selector.isEmpty())
			{
				throw new CssSelectorStringSplitterException("Empty CSS Selector");
			}
			
			selectorList.add(selector);
		}
		if(selectorList.isEmpty())
		{
			throw new CssSelectorStringSplitterException("No CSS Selectors");
		}
		return selectorList;
	}
	protected List<CssElementCombinatorPair> splitSelectorsIntoElementCombinatorPairs(String processedSelector) throws CssSelectorStringSplitterException
	{
		List<CssElementCombinatorPair> selectorList=new ArrayList<>();
		recursiveSelectorSplit(null,processedSelector,selectorList);
		return selectorList;
	}
	protected void recursiveSelectorSplit(CssCombinatorType previousCombinatorType, String cssSelector,List<CssElementCombinatorPair> selectorList) throws CssSelectorStringSplitterException
	{ 

		Pattern cssCombinator = Pattern.compile(XY);
		Matcher match = cssCombinator.matcher(cssSelector);
		System.out.println(XY);
		if(match.find())
		{
			CssCombinatorType type= CssCombinatorType.combinatorTypeChar(match.group(5));
			System.out.println("TYPE:"+type);
			if(type!=null)
			{	
				String firstCssSelector=match.group(1);
				System.out.println("firstcss"+firstCssSelector);
				if(firstCssSelector.isEmpty())
				{
					throw new CssSelectorStringSplitterException("Empty Selector");
				}
				selectorList.add(new CssElementCombinatorPair(previousCombinatorType,firstCssSelector));
				String nextCssSelector=match.group(6); 
				System.out.println("nextcss="+nextCssSelector+"; type"+type);

				if(nextCssSelector.isEmpty())
				{
					throw new CssSelectorStringSplitterException("Empty Selector");
				}
				recursiveSelectorSplit(type,nextCssSelector,selectorList);
			}
			else
			{
				if(cssSelector.isEmpty())
				{
					throw new CssSelectorStringSplitterException("Empty Selector");
				}
				selectorList.add(new CssElementCombinatorPair(previousCombinatorType,cssSelector));
			}
		}
		else
		{
			throw new CssSelectorStringSplitterException("Invalid Selector");

		}
	}
	
	public List<List<CssElementCombinatorPair>> listSplitSelectorsIntoElementCombinatorPairs(String selectorString) throws CssSelectorStringSplitterException
	{
		System.out.println("###"+selectorString);

		List<List<CssElementCombinatorPair>> listList=new ArrayList<>();
		List<String> selectorList=splitSelectors(selectorString);
		for(String selector:selectorList)
		{
			List<CssElementCombinatorPair> cssElementCombinatorPairList= splitSelectorsIntoElementCombinatorPairs(selector);
			listList.add(cssElementCombinatorPairList);
		}
		
		System.out.println("$$$$"+listList);
		System.out.println("XXX");

		return listList;
	}
	
}