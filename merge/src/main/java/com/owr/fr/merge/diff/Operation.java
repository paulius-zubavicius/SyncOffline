package com.owr.fr.merge.diff;

public enum Operation {

	Modified('⭯'), Move('⮆'), Add('+'), Delete('-'), Conflict('⭍');

	private Character uiSymbol;

	private Operation(Character uiSymbol) {
		this.uiSymbol = uiSymbol;
	}

	public Character getUISymbol() {
		return uiSymbol;
	}

}
