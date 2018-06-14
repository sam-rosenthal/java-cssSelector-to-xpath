package org.sam.rosenthal.cssselectortoxpath.model;

public enum CssAttributeValueType 
{
	EQUAL("="), 
	TILDA_EQUAL("~="),
	PIPE_EQUAL("|="),
	CARROT_EQUAL("^="),
	MONEY_EQUAL("$="),
	STAR_EQUAL("*=");

	private String equalString;

	private CssAttributeValueType(String nameIn)
	{
		this.equalString=nameIn;
	}

	public String getEqualStringName() {
		return equalString;
	}

	public static CssAttributeValueType valueTypeString(String unknownString) {
		CssAttributeValueType valueType;
		switch (unknownString) 
		{
			case "=": valueType = EQUAL;
        		break;
        	case "~=":  valueType = TILDA_EQUAL;
                 break;
        	case "|=":  valueType = PIPE_EQUAL;
            	break;
        	case "$=":  valueType = MONEY_EQUAL;
            	break;
        	case "^=":  valueType = CARROT_EQUAL;
        		break;
        	case "*=":  valueType = STAR_EQUAL;
        		break;
        	default: valueType = null;
        		break;
		}
		return valueType;
	}
}
