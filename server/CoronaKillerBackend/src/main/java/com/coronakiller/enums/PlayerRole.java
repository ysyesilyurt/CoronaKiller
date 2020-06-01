package com.coronakiller.enums;

public enum PlayerRole {
	USER("USER"),
	ADMIN("ADMIN");

	private final String displayName;

	PlayerRole(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return this.displayName;
	}
}
