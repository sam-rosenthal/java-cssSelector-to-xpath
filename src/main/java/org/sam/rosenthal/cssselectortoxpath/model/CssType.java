package org.sam.rosenthal.cssselectortoxpath.model;

public enum CssType {
	SPACE(' '), 
	PLUS('+'),
	GREATER_THAN('>'),
	TILDA('~');
	
	private char typeChar;

	private CssType(char typeCharIn)
	{
		this.typeChar=typeCharIn;
	}

	public char getTypeChar() {
		return typeChar;
	}

}
