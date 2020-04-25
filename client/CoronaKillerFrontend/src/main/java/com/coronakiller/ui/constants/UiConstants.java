package com.coronakiller.ui.constants;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class UiConstants {
	public static final String BACKEND_BASE_URL = "http://localhost:8080/api";
	public static final String WINDOW_TITLE = "Corona Killer";
	public static final int WINDOW_WIDTH = 600;
	public static final int WINDOW_HEIGHT = 800;
	public static final String HASH_SALT = "$2a$10$3j3gfVtuynuvE6FIVvygdu"; /* A Random Salt value generated with BCrypt.gensalt(10) */

	/* Request Messages*/
	public static final String CLIENT_ERROR = "Oops! Something went wrong on client";
	public static final String HTTP_400 = "Oops! Request failed - BAD REQUEST";
	public static final String HTTP_401 = "Invalid username or password - UNAUTHORIZED";
	public static final String HTTP_404 = "Oops! Requested data is not found on the game server.";
	public static final String HTTP_CONN_ERROR = "Oops! Seems like you can't access the game server.\nPlease check your internet connection and try again.";
	public static final String HTTP_500 = "Oops! Something went wrong on server";
}
