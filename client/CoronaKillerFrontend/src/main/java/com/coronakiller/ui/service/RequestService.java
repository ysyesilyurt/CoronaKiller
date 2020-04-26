package com.coronakiller.ui.service;

import com.coronakiller.ui.application.StageInitializer;
import com.coronakiller.ui.constants.UiConstants;
import com.coronakiller.ui.model.GameSession;
import com.coronakiller.ui.model.Player;
import com.coronakiller.ui.model.RestApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ConnectException;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RequestService {

	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final OkHttpClient client = new OkHttpClient();

	/**
	 * A static helper method for generic determination of String to respond
	 * to caller method that is calling the request method
	 * @param httpCode
	 * @return String
	 */
	public static String resolveHttpCodeResponse(Integer httpCode) {
		switch (httpCode) {
			case 400:
				return UiConstants.HTTP_400;
			case 401:
				return UiConstants.HTTP_401;
			case 404:
				return UiConstants.HTTP_404;
			case 500:
				return UiConstants.HTTP_500;
			default: {
				log.warn("Got {} HTTP code after a request", httpCode);
				return "Oops! Something went wrong - " + httpCode;
			}
		}
	}

	/**
	 * Static login request method. Can be called from any controller code.
	 * Creates initial Player credentials and makes the request to the backend.
	 * Responses with logged in player and response message to the caller code.
	 * @param player
	 * @return Pair<Player, String>
	 */
	public static Pair<Player, String> login(Player player) {
		/* Construct the password first */
		String encodedPassword = BCrypt.hashpw(player.getPassword(), UiConstants.HASH_SALT);
		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = RequestBody.create(mediaType, "");
		String authorizationHeader = Credentials.basic(player.getUsername(), encodedPassword);
		Request request = new Request.Builder()
				.url(UiConstants.BACKEND_BASE_URL + "/players/login")
				.method("POST", body)
				.addHeader("Authorization", authorizationHeader)
				.build();
		try {
			Response response = client.newCall(request).execute();
			if (response.code() == 200) {
				String responseBody = response.body().string();
				RestApiResponse mappedResponse = objectMapper.readValue(responseBody, RestApiResponse.class);
				if (mappedResponse.getResult().equals("success")) {
					Player loggedInPlayer = objectMapper.convertValue(mappedResponse.getData(), Player.class);
					return Pair.with(loggedInPlayer, mappedResponse.getMessage());
				} else {
					return Pair.with(null, mappedResponse.getMessage());
				}
			} else {
				String responseString = resolveHttpCodeResponse(response.code());
				return Pair.with(null, responseString);
			}
		} catch (ConnectException e) {
			return Pair.with(null, UiConstants.HTTP_CONN_ERROR);
		} catch (IOException e) {
			e.printStackTrace();
			return Pair.with(null, UiConstants.CLIENT_ERROR);
		}
	}

	/**
	 * Static register request method. Can be called from any controller code.
	 * Creates initial Player credentials and makes the request to the backend.
	 * Responses with created player and response message to the caller code.
	 * Beware no authorization header is provided, since this endpoint is not authorized.
	 *
	 * @param player
	 * @return Pair<Player, String>
	 */
	public static Pair<Player, String> register(Player player) {
		/* Construct the password first */
		String encodedPassword = BCrypt.hashpw(player.getPassword(), UiConstants.HASH_SALT);
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType,
				"{\n\t\"username\": \"" + player.getUsername() + "\"," +
						"\n\t\"password\": \"" + encodedPassword + "\"\n}");
		Request request = new Request.Builder()
				.url(UiConstants.BACKEND_BASE_URL + "/players/register")
				.method("POST", body)
				.addHeader("Content-Type", "application/json")
				.build();

		try {
			Response response = client.newCall(request).execute();
			if (response.code() == 200) {
				String responseBody = response.body().string();
				RestApiResponse mappedResponse = objectMapper.readValue(responseBody, RestApiResponse.class);
				if (mappedResponse.getResult().equals("success")) {
					Player createdPlayer = objectMapper.convertValue(mappedResponse.getData(), Player.class);
					return Pair.with(createdPlayer, mappedResponse.getMessage());
				} else {
					return Pair.with(null, mappedResponse.getMessage());
				}
			} else {
				String responseString = resolveHttpCodeResponse(response.code());
				return Pair.with(null, responseString);
			}
		} catch (ConnectException e) {
			return Pair.with(null, UiConstants.HTTP_CONN_ERROR);
		} catch (IOException e) {
			e.printStackTrace();
			return Pair.with(null, UiConstants.CLIENT_ERROR);
		}
	}

	/**
	 * Dummy request for example usage
	 */
	@Deprecated // TODO: Remove before submission
	public static Player getPlayerById(Long playerIdToGet) {
		if (StageInitializer.currentPlayer != null) {
			String authorizationHeader = Credentials.basic(StageInitializer.currentPlayer.getUsername(), StageInitializer.currentPlayer.getPassword());
			Request request = new Request.Builder()
					.url(UiConstants.BACKEND_BASE_URL + "/players/" + playerIdToGet.toString())
					.method("GET", null)
					.addHeader("Authorization", authorizationHeader)
					.build();
			try {
				Response response = client.newCall(request).execute();
				String responseBody = response.body().string();
				RestApiResponse mappedResponse = objectMapper.readValue(responseBody, RestApiResponse.class);
				if (mappedResponse.getResult().equals("success")) {
					Player player = objectMapper.convertValue(mappedResponse.getData(), Player.class);
					return player;
				} else {
					return null;
				}
			} catch (IOException e) {
				e.printStackTrace();
				return null; // TODO
			}
		} else {
			return null; // TODO ERROR
		}
	}

	/**
	 * Static getLeaderBoard request method. Can be called from any controller code.
	 * Creates a GET request with the specified type of leaderboard as the GET parameter,
	 * fetches current Player credentials and makes the request to the backend.
	 * Responses with created leaderboard and response message to the caller code.
	 *
	 * @param leaderBoardType
	 * @return Pair<List<Map<String, Long>>, String>
	 */
	public static Pair<List<Map<String, Long>>, String> getLeaderBoard(String leaderBoardType) {
		if (StageInitializer.currentPlayer != null) {
			String authorizationHeader = Credentials.basic(StageInitializer.currentPlayer.getUsername(), StageInitializer.currentPlayer.getPassword());
			Request request = new Request.Builder()
					.url(UiConstants.BACKEND_BASE_URL + "/scoreboard?type=" + leaderBoardType)
					.method("GET", null)
					.addHeader("Authorization", authorizationHeader)
					.build();
			try {
				Response response = client.newCall(request).execute();
				if (response.code() == 200) {
					String responseBody = response.body().string();
					RestApiResponse mappedResponse = objectMapper.readValue(responseBody, RestApiResponse.class);
					if (mappedResponse.getResult().equals("success")) {
						List<Map<String, Long>> leaderBoard = objectMapper.convertValue(mappedResponse.getData(), List.class);
						return Pair.with(leaderBoard, mappedResponse.getMessage());
					} else {
						return Pair.with(null, mappedResponse.getMessage());
					}
				} else {
					String responseString = resolveHttpCodeResponse(response.code());
					return Pair.with(null, responseString);
				}
			} catch (ConnectException e) {
				return Pair.with(null, UiConstants.HTTP_CONN_ERROR);
			} catch (IOException e) {
				e.printStackTrace();
				return Pair.with(null, UiConstants.CLIENT_ERROR);
			}
		} else {
			return Pair.with(null, UiConstants.COOKIE_NOTFOUND);
		}
	}

	public static Pair<GameSession, String> continueGameSession() {
		// TODO: CHANGE PUT TO GET
		if (StageInitializer.currentPlayer != null) {
			String authorizationHeader = Credentials.basic(StageInitializer.currentPlayer.getUsername(), StageInitializer.currentPlayer.getPassword());
			MediaType mediaType = MediaType.parse("text/plain");
			RequestBody body = RequestBody.create(mediaType, "");
			Request request = new Request.Builder()
					.url(UiConstants.BACKEND_BASE_URL + "/game/continue/" + StageInitializer.currentPlayer.getId().toString())
					.method("PUT", body)
					.addHeader("Authorization", authorizationHeader)
					.build();
			try {
				Response response = client.newCall(request).execute();
				if (response.code() == 200) {
					String responseBody = response.body().string();
					RestApiResponse mappedResponse = objectMapper.readValue(responseBody, RestApiResponse.class);
					if (mappedResponse.getResult().equals("success")) {
						GameSession gameSession = objectMapper.convertValue(mappedResponse.getData(), GameSession.class);
						return Pair.with(gameSession, mappedResponse.getMessage());
					} else {
						return Pair.with(null, mappedResponse.getMessage());
					}
				} else {
					String responseString = resolveHttpCodeResponse(response.code());
					return Pair.with(null, responseString);
				}
			} catch (ConnectException e) {
				return Pair.with(null, UiConstants.HTTP_CONN_ERROR);
			} catch (IOException e) {
				e.printStackTrace();
				return Pair.with(null, UiConstants.CLIENT_ERROR);
			}
		} else {
			return Pair.with(null, UiConstants.COOKIE_NOTFOUND);
		}
	}

	public static Pair<GameSession, String> startNewGame() {
		if (StageInitializer.currentPlayer != null) {
			String authorizationHeader = Credentials.basic(StageInitializer.currentPlayer.getUsername(), StageInitializer.currentPlayer.getPassword());
			MediaType mediaType = MediaType.parse("text/plain");
			RequestBody body = RequestBody.create(mediaType, "");
			Request request = new Request.Builder()
					.url(UiConstants.BACKEND_BASE_URL + "/game/start/" + StageInitializer.currentPlayer.getId().toString())
					.method("POST", body)
					.addHeader("Authorization", authorizationHeader)
					.build();
			try {
				Response response = client.newCall(request).execute();
				if (response.code() == 200) {
					String responseBody = response.body().string();
					RestApiResponse mappedResponse = objectMapper.readValue(responseBody, RestApiResponse.class);
					if (mappedResponse.getResult().equals("success")) {
						GameSession gameSession = objectMapper.convertValue(mappedResponse.getData(), GameSession.class);
						return Pair.with(gameSession, mappedResponse.getMessage());
					} else {
						return Pair.with(null, mappedResponse.getMessage());
					}
				} else {
					String responseString = resolveHttpCodeResponse(response.code());
					return Pair.with(null, responseString);
				}
			} catch (ConnectException e) {
				return Pair.with(null, UiConstants.HTTP_CONN_ERROR);
			} catch (IOException e) {
				e.printStackTrace();
				return Pair.with(null, UiConstants.CLIENT_ERROR);
			}
		} else {
//			log.warn("Player cookie not found");
			return Pair.with(null, UiConstants.COOKIE_NOTFOUND);
		}
	}
}
