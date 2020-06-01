package com.coronakiller.ui.constants;

import javafx.scene.control.TextFormatter;

/**
 * This class keeps the constant values that are related to user interface and some other program constants.
 */
public class GeneralConstants {
	/* General Application Constants */
	public static final String BACKEND_BASE_URL = "http://localhost:8080/api"; // TODO: CHANGE
	public static final Integer SOCKET_PORT = 10000; // TODO: CHANGE
	public static final String WINDOW_TITLE = "Corona Killer";
	public static final int WINDOW_WIDTH = 600;
	public static final int WINDOW_HEIGHT = 800;
	public static final String HASH_SALT = "$2a$10$3j3gfVtuynuvE6FIVvygdu"; /* A Random Salt value generated with BCrypt.gensalt(10) */
	public static final Integer MULTIPLAYER_SEND_INFO_PERIOD = 1000; // TODO: CHANGE
	public static final String MULTIPLAYER_SEND_INFO_FORMAT = "%s:%s:%s:%s:%s"; /* username:score:spaceshipHealth:spaceshipX:spaceshipY */

	/* Request Message Constants */
	public static final String CLIENT_ERROR = "Oops! Something went wrong on client";
	public static final String COOKIE_NOTFOUND = "Oops! Seems like game cookie that has your login credentials is not found.\nPlease try to logout and then login.";
	public static final String HTTP_400 = "Oops! Request failed - BAD REQUEST";
	public static final String HTTP_401 = "Invalid username or password - UNAUTHORIZED";
	public static final String HTTP_404 = "Oops! Requested data is not found on the game server.";
	public static final String HTTP_CONN_ERROR = "Oops! Seems like you can't access the game server.\nPlease check your internet connection and try again.";
	public static final String HTTP_500 = "Oops! Something went wrong on server";

	/* FXML/CSS File Location Constants */
	public static final String GENERAL_STYLES = "css/styles.css";
	public static final String LOGIN_PAGE = "fxml/login.fxml";
	public static final String REGISTER_PAGE = "fxml/register.fxml";
	public static final String DASHBOARD_PAGE = "fxml/dashboard.fxml";
	public static final String LEADERBOARD_PAGE = "fxml/leaderboard.fxml";
	public static final String GAME_LEVEL1_PAGE = "fxml/game-level1.fxml";
	public static final String GAME_LEVEL2_PAGE = "fxml/game-level2.fxml";
	public static final String GAME_LEVEL3_PAGE = "fxml/game-level3.fxml";
	public static final String GAME_LEVEL4_PAGE = "fxml/game-level4.fxml";
	public static final String GAME_LEVEL5_PAGE = "fxml/game-level5.fxml";
}
