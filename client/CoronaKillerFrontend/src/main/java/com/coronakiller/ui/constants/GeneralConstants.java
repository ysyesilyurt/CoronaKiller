package com.coronakiller.ui.constants;

import javafx.scene.control.TextFormatter;

/**
 * This class keeps the constant values that are related to user interface and some other program constants.
 */
public class GeneralConstants {
	/* General Application Constants */
	public static final String BACKEND_BASE_URL = "http://localhost:8080/api"; // TODO: CHANGE
	public static final Integer FIRST_PLAYER_SOCKET_PORT = 10000; // TODO: CHANGE
	public static final String WINDOW_TITLE = "Corona Killer";
	public static final int WINDOW_WIDTH = 600;
	public static final int WINDOW_HEIGHT = 800;
	public static final String HASH_SALT = "$2a$10$3j3gfVtuynuvE6FIVvygdu"; /* A Random Salt value generated with BCrypt.gensalt(10) */

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

	public static final String MATCHMAKING_HEADING = "Matchmaking In Progress...";
	public static final String MATCHMAKING_BODY = "Congratulations! You've reached the final level.\n\n" +
			"Your final task is to defeat the Notorious EVIL BIG KING coronavirus, with that you\n" +
			"will be able to put an epic end to this deadly pandemic.\n\n" +
			"But, since your chances of defeating this notorious kind coronavirus on your own\n" +
			"is very low(even though you fought very well up to this point), we (as the creators\n" +
			"of the game) decided to give you a companion to defeat this beast.\n" +
			"So this level is a multiplayer level.\n\n" +
			"Currently you're the only one in the queue, we'll match you with a worthy\n" +
			"companion as soon as queue gets populated.\n\n" +
			"Get ready for your final clash with the notorious king!";
	public static final String MATCH_READY_HEADING = "Match is Found!";
	public static final String MATCH_READY_BODY =
			"We matched you with %s.\n\n" +
			"Your companion has gathered %s points up to this level and\n" +
			"has %s HP left for his/her ship.\n\n" +
			"Your match will start automatically in 5 seconds, Get Ready!";
	public static final String INITIAL_SEND_INFO_FORMAT = "%s:%s:%s"; /* username:score:spaceshipHealth */
	public static final String MULTIPLAYER_SEND_INFO_FORMAT = "%s:%s:%s:%s:%s"; /* username:score:spaceshipHealth:spaceshipX:spaceshipY */
	public static final Integer MULTIPLAYER_SEND_INFO_PERIOD_MSEC = 20;
	public static final int MATCHMAKING_INITIAL_OFFSET_MSEC = 50; /* Users wait a 50 ms to get their username dialogs ready */
	public static final int MATCHMAKING_COUNTDOWN_SEC = 5;
}
