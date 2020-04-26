package com.coronakiller.ui.model;

public enum ShipType {
	ROOKIE("ROOKIE"),
	NORMAL("NORMAL"),
	VETERAN("VETERAN"),
	POWERFUL_GUNS("POWERFUL_GUNS"),
	BIG_GUNS("BIG_GUNS");

	private String displayName;

	ShipType(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return this.displayName;
	}
}