package com.coronakiller.ui.constants;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class UiConstants {
	public static final String BACKEND_BASE_URL = "http://localhost:8080/api";
	public static final String WINDOW_TITLE = "Corona Killer";
	public static final int WINDOW_WIDTH = 600;
	public static final int WINDOW_HEIGHT = 800;
	public static final String HASH_SALT = "$2a$10$3j3gfVtuynuvE6FIVvygdu"; /* A Salt value generated with BCrypt.gensalt(10) */
}
