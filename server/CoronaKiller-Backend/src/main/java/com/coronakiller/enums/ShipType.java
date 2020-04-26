package com.coronakiller.enums;

public enum ShipType {
	ROOKIE("ROOKIE"),
	NORMAL("NORMAL"),
	VETERAN("VETERAN"),
	POWERFUL_GUNS("POWERFUL_GUNS"),
	BIG_GUNS("BIG_GUNS");

	private final String displayName;

	ShipType(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return this.displayName;
	}
}
