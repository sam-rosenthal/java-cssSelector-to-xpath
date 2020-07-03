package org.sam.rosenthal.cssselectortoxpath.model;

public class CssPsuedoClassNthToXpath implements CssPsuedoClassToXpath {
	
	private boolean last;
	
	public CssPsuedoClassNthToXpath(boolean lastIn)
	{
		last = lastIn;
	}
	
	@Override
	public String getXpath(String element, String parenthesisExpression) {
		return getNthToXpath(element, parenthesisExpression );
	}
	public String getNthToXpath(String element, String parenthesisExpression)
	{
		if(parenthesisExpression.equals("even"))
		{
			return getNthToXpath(element, "2n");
		}
		else if(parenthesisExpression.equals("odd")){
			return getNthToXpath(element, "2n+1");
		}
		else if(!parenthesisExpression.contains("n"))
		{
			parenthesisExpression = parenthesisExpression.replace("+", "");
			int y = Integer.parseInt(parenthesisExpression);

			return getNthXpathNoN(element, y);
		}
		else
		{
			int nIndex = parenthesisExpression.indexOf('n');
			if(parenthesisExpression.charAt(0)=='-')
			{
				int x = 1;
				if(nIndex!=1)
				{
					x = Integer.parseInt(parenthesisExpression.substring(1,nIndex));
				}
				int y = Integer.parseInt(parenthesisExpression.substring(nIndex+2));
				if(y<=x)
				{
					return getNthXpathNoN(element, y);
				}
				
				int dy = x - (y%x); 
				String newExpression = x+"n+"+dy;
				String s = getNthToXpath(element, newExpression);
				s = s.substring(1, s.length()-1);
				return "[("+s+") and "+getPrecedingSiblingXpathHelper(element,"<",y)+"]";
			}
			else
			{
				int x = 1;
				if(parenthesisExpression.charAt(0)=='+')
				{
					if(nIndex!=1)
					{
						x = Integer.parseInt(parenthesisExpression.substring(1,nIndex));
					}
				}
				else
				{
					if(nIndex!=0)
					{
						x = Integer.parseInt(parenthesisExpression.substring(0,nIndex));
					}
				}
				if(nIndex == parenthesisExpression.length()-1)
				{
					return "["+getPrecedingSiblingModXpathHelper(element,"+",1,x)+"]";
				}
				else
				{
					int y = Integer.parseInt(parenthesisExpression.substring(nIndex+2));
					if(y==0)
					{
						String newExpression = ""+x+"n";
						return getNthToXpath(element, newExpression);
					}
					if(parenthesisExpression.charAt(nIndex+1)=='+')
					{
						if(y<=x)
						{
							if(y==x)
							{
								return getNthToXpath(element, x+"n");
							}
							return "["+getPrecedingSiblingXpathHelper(element,"=",y-1)+" or ("+getPrecedingSiblingModXpathHelper(element,"-",y-1,x)+")]";
						}
						else
						{
							return "["+getPrecedingSiblingXpathHelper(element,"=",y-1)+" or (("+getPrecedingSiblingXpathHelper(element,">",y)+" and "
									+ "("+getPrecedingSiblingModXpathHelper(element,"-",y-1,x)+")))]";
						}
					}
					else
					{
						int z = y;
						if (y>x)
						{
							z = y % x;
						}
						String newExpression = ""+x+"n+"+(x-z);
						return getNthToXpath(element, newExpression);
					}
				}
			}
		}
	}

	private String getNthXpathNoN(String element, int y) {
		if(last)
		{
			return "[count(following-sibling::"+element+")="+(y-1)+"]";
		}
		else
		{
			return getNthXpathNoN(y);
		}
	}

	protected String getNthXpathNoN(int y) {
		return "["+y+"]";
	}
	
	protected String getPrecedingSiblingXpathHelper(String element, String operation, int val)
	{
		String s = last==true? "following" : "preceding";
		return "(count("+s+"-sibling::"+element+")"+operation+val+")";
	}
	protected String getPrecedingSiblingModXpathHelper(String element, String operation, int val1, int val2)
	{
		return "("+getPrecedingSiblingXpathHelper(element, operation, val1)+" mod "+val2+")=0"; 
	}

}
