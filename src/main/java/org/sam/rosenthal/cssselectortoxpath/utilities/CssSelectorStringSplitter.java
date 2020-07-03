package org.sam.rosenthal.cssselectortoxpath.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sam.rosenthal.cssselectortoxpath.model.CssCombinatorType;
import org.sam.rosenthal.cssselectortoxpath.model.CssElementCombinatorPair;

public class CssSelectorStringSplitter 
{
	public static final String ERROR_INVALID_SELECTOR = "Invalid Selector";
	public static final String ERROR_NO_CSS_SELECTORS = "No CSS Selectors";
	public static final String ERROR_EMPTY_CSS_SELECTOR = "Empty CSS Selector";
	public static final String ERROR_INVALID_CSS_SELECTOR_TRAILING_COMMA = "Invalid CSS Selector, trailing ','";
	public static final String ERROR_INVALID_CSS_SELECTOR_INCONSISTENT_BRACKETS = "Invalid CSS Selector, inconsistent brackets[]";
	private static final String COMBINATORS = " ~+>";
	private static final String COMBINATOR_RE = "["+COMBINATORS+"]";

	private static final String ELEMENT_AND_ATTRIBUTE = "([^"+COMBINATORS+"\\[]*((\\[[^\\]]+\\])|"+CssElementAttributeParser.PSUEDO_RE+")*)";
	private static final String ELEMENT_AND_ATTRIBUTE_FOLLOWED_BY_COMBINATOR_AND_REST_OF_LINE = "^"+ELEMENT_AND_ATTRIBUTE+"($|(\\s*("+COMBINATOR_RE+")\\s*"+"([^"+COMBINATORS+"].*)$))";

	public static final String ERROR_INVALID_CLASS_CSS_SELECTOR = "Invalid class CSS Selector";
	public static final String ERROR_INVALID_ID_CSS_SELECTOR = "Invalid id CSS Selector";
	private static final String PLACE_HOLDER = "~@_placeHolder_@";
	public static final String NTH_OF_TYPE_PLACEHOLDER = "@_nthTypePlaceHolder_@";


	protected String removeNonCssSelectorWhiteSpaces(String selectorString) throws CssSelectorToXPathConverterException
	{
//	This method should perform the following
//	i.	Remove all leading and trailing white spaces
//	ii.	Remove all white spaces except tabs and actual space(" ")
//	1.	Note, very important
//	a.	'\t' corresponds to the tab character in java
//	iii.	Consolidate all consecutive tabs and " " into a single " " and single tab into a " "
//	1.	The final string should have no tabs only non consecutive spaces
//	b.	Implementation
//	i.	Check for null string and if found throw a CssSelectorStringSplitterException
//	ii.	Use String.trim() to remove leading and trailing spaces
//	iii.	Use String.replaceAll()  to manipulate the string
//	1.	The tricky part is that tab and " " are both white spaces
//	2.	Preprocess the string
//	a.	Replace all tabs with  a unique string like "~+_placeHolder_+"
//	b.	Replace all spaces with the same unique string
//	i.	Remember at the end we want only spaces
//	3.	Replace all white spaces with the empty string ""
//	4.	Replace "~+_placeHolder_+" with " "
		if(selectorString==null)
		{
			throw new CssSelectorToXPathConverterException(ERROR_EMPTY_CSS_SELECTOR);
		}
		else
		{
			selectorString=selectorString.trim();
			selectorString=selectorString.replaceAll("[ \\t]+", PLACE_HOLDER);
			selectorString=selectorString.replaceAll("\\s+","");
			selectorString=selectorString.replaceAll("("+PLACE_HOLDER+")+", " ");
			selectorString=classIdAttributeIssueHandler(selectorString,"#","id=");
			selectorString=classIdAttributeIssueHandler(selectorString,".","class~=");
			selectorString=nthOfTypeHandler(selectorString);
			return selectorString;
		}
	}
	
	private String nthOfTypeHandler(String selectorString) {
//		Pattern nthOfTypeRe = Pattern.compile("(.*)(:nth-of-type[(][^)]+[)])(.*)");
		Pattern nthOfTypeRe = Pattern.compile(":nth(-last)?-((of-type)|child)[(][^)]+[)]");
		Matcher match = nthOfTypeRe.matcher(selectorString);
		int start = 0;
		while(match.find(start))
		{
			String nthOfType = match.group(0);
			int length = nthOfType.length();
			int i = selectorString.indexOf(nthOfType, start);
			start = i + length;
			nthOfType = nthOfType.toLowerCase();
			nthOfType = nthOfType.replaceAll(" ","");
			nthOfType = nthOfType.replaceAll("\\+",NTH_OF_TYPE_PLACEHOLDER);
 			selectorString = selectorString.substring(0,i)+ nthOfType + selectorString.substring(start);
 			length = nthOfType.length();
			start = i + length;
 			if(start==selectorString.length())
 			{
 				break;
 			}
 			match = nthOfTypeRe.matcher(selectorString);
		}
		return selectorString;

	}
	private String classIdCombinatorRE()
	{
		StringBuilder builder=new StringBuilder("([^.#\\[,:");
		for(CssCombinatorType combinatorType:CssCombinatorType.values())
		{
			builder.append(combinatorType.getCombinatorChar());
		}
		builder.append("]+)");
		return builder.toString();
		
	}
	
	protected void invalidClassIdPairCheck(String selectorString, boolean testId) throws CssSelectorToXPathConverterException 
	{
		String nextSelectorIdentifier="[.#\\[]";
		if(testId)
		{
			Pattern pattern = Pattern.compile("#"+nextSelectorIdentifier);
			Matcher match = pattern.matcher(selectorString);
			if (match.find())
			{
				throw new CssSelectorToXPathConverterException(ERROR_INVALID_ID_CSS_SELECTOR);
			}
		}
		else
		{
			Pattern pattern= Pattern.compile("[.]"+nextSelectorIdentifier);
			Matcher match = pattern.matcher(selectorString);
			if (match.find())
			{
				throw new CssSelectorToXPathConverterException(ERROR_INVALID_CLASS_CSS_SELECTOR);
			}
		}
	}
	private String classIdAttributeIssueHandler(String selectorString, String classOrIdChar, String classOrIdPartialAttributeNameAndRelationship ) throws CssSelectorToXPathConverterException
	{
		if(selectorString.replaceAll("[^\\[]", "").length() != selectorString.replaceAll("[^\\]]", "").length())
		{
			throw new CssSelectorToXPathConverterException(ERROR_INVALID_CSS_SELECTOR_INCONSISTENT_BRACKETS);
		}
		String classOrIdCharacterRE = "["+classOrIdChar+"]";
		String attributeGeneralRE = "([^\\[]*)((\\[[^\\]]*\\])*)";
		Pattern pattern = Pattern.compile(attributeGeneralRE);
		Matcher match = pattern.matcher(selectorString);
		//System.out.println(selectorString);
		boolean found=false;
		StringBuffer stringBuffer = new StringBuffer();
		while(match.find()) {
			stringBuffer.append(match.group(1));
			stringBuffer.append(match.group(2).replaceAll(classOrIdCharacterRE,PLACE_HOLDER));

		    found=true;
		}
		//System.out.println("SB"+stringBuffer);
		selectorString=stringBuffer.toString();

		selectorString=selectorString.replaceAll(classOrIdCharacterRE+classIdCombinatorRE(),"["+classOrIdPartialAttributeNameAndRelationship+"\"$1\"]");
		invalidClassIdPairCheck(selectorString,"#".equals(classOrIdChar));

		if(found)
		{
			selectorString=selectorString.replaceAll(PLACE_HOLDER, classOrIdChar);
		}

		return selectorString;
	}
	
	public List<String> splitSelectors(String selectorString) throws CssSelectorToXPathConverterException
	{
		selectorString=removeNonCssSelectorWhiteSpaces(selectorString);
//		System.out.println("ADJUSTED="+selectorString);
		//selectorString=removeNonCssSelectorWhiteSpaces(selectorString);
		//split() will not error out if there is a trailing ','
		int index=selectorString.lastIndexOf(',');
		int cssSelectorStringLength = selectorString.length();
		if((cssSelectorStringLength>0)&&(index==(cssSelectorStringLength-1)))
		{
			throw new CssSelectorToXPathConverterException(ERROR_INVALID_CSS_SELECTOR_TRAILING_COMMA);		
		}
		String[] selectorArray=selectorString.split(",");
		List<String> selectorList=new ArrayList<>();
		for(String selector:selectorArray)
		{
			selector=selector.trim();
			if(selector.isEmpty())
			{
				throw new CssSelectorToXPathConverterException(ERROR_EMPTY_CSS_SELECTOR);
			}
			selectorList.add(selector);
		}
		if(selectorList.isEmpty())
		{
			throw new CssSelectorToXPathConverterException(ERROR_NO_CSS_SELECTORS);
		}
		return selectorList;
	}
	protected List<CssElementCombinatorPair> splitSelectorsIntoElementCombinatorPairs(String processedSelector) throws CssSelectorToXPathConverterException
	{
		List<CssElementCombinatorPair> selectorList=new ArrayList<>();
		recursiveSelectorSplit(null,processedSelector,selectorList);
		return selectorList;
	}
	protected void recursiveSelectorSplit(CssCombinatorType previousCombinatorType, String cssSelector,List<CssElementCombinatorPair> selectorList) throws CssSelectorToXPathConverterException
	{ 
//		System.out.println("Original String:"+cssSelector);
		Pattern cssCombinator = Pattern.compile(ELEMENT_AND_ATTRIBUTE_FOLLOWED_BY_COMBINATOR_AND_REST_OF_LINE);
		Matcher match = cssCombinator.matcher(cssSelector);
		//System.out.println(XY);
		if(match.find())
		{
			CssCombinatorType type= CssCombinatorType.combinatorTypeChar(match.group(8));
			//System.out.println("TYPE:"+type);
			if(type!=null)
			{	
				String firstCssSelector=match.group(1);
//				System.out.println("firstCssSelector:"+firstCssSelector);
				firstCssSelector = firstCssSelector.replaceAll(NTH_OF_TYPE_PLACEHOLDER, "+");
				if(firstCssSelector.isEmpty())
				{
					throw new CssSelectorToXPathConverterException(ERROR_EMPTY_CSS_SELECTOR);
				}
				selectorList.add(new CssElementCombinatorPair(previousCombinatorType,firstCssSelector));
				String nextCssSelector=match.group(9); 

				if(nextCssSelector.isEmpty())
				{
					throw new CssSelectorToXPathConverterException(ERROR_EMPTY_CSS_SELECTOR);
				}
				recursiveSelectorSplit(type,nextCssSelector,selectorList);
			}
			else
			{
				if(cssSelector.isEmpty())
				{
					throw new CssSelectorToXPathConverterException(ERROR_EMPTY_CSS_SELECTOR);
				}
				selectorList.add(new CssElementCombinatorPair(previousCombinatorType,cssSelector));
			}
		}
		else
		{
			throw new CssSelectorToXPathConverterException(ERROR_INVALID_SELECTOR);

		}
	}
	
	public List<List<CssElementCombinatorPair>> listSplitSelectorsIntoElementCombinatorPairs(String selectorString) throws CssSelectorToXPathConverterException
	{
		List<List<CssElementCombinatorPair>> listList=new ArrayList<>();
		List<String> selectorList=splitSelectors(selectorString);
		for(String selector:selectorList)
		{
			List<CssElementCombinatorPair> cssElementCombinatorPairList= splitSelectorsIntoElementCombinatorPairs(selector);
			listList.add(cssElementCombinatorPairList);
		}
		

		return listList;
	}
	
}
