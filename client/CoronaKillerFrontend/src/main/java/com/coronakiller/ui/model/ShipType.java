package com.coronakiller.ui.model;

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

	public Integer resolveEnumCode() {
		switch (this) {
			case ROOKIE:
				return 0;
			case NORMAL:
				return 1;
			case VETERAN:
				return 2;
			case POWERFUL_GUNS:
				return 3;
			case BIG_GUNS:
				return 4;
			default:
				return -1;
		}
	}

	@Override
	public String toString() {
		return this.displayName;
	}
}