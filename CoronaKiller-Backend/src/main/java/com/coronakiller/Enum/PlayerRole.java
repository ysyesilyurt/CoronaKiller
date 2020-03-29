package com.coronakiller.Enum;

public enum PlayerRole {
	USER("USER"),
	ADMIN("ADMIN");

	private String displayName;

	PlayerRole(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return this.displayName;
	}
}
