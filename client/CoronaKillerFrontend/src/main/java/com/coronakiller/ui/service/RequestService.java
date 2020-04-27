package com.coronakiller.ui.service;

import com.coronakiller.ui.constants.UiConstants;
import com.coronakiller.ui.model.*;
import com.fasterxml.jackson.core.type.TypeReference;
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

import static com.coronakiller.ui.application.StageInitializer.gameDataCookie;

@Slf4j
@Service
public class RequestService {

	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final OkHttpClient client = new OkHttpClient();

	/**
	 * A static helper method for generic determination of String to respond
	 * to caller method that is calling the request method
	 *
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
	 *
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
					log.warn("Request result is 'fail' on login request");
					return Pair.with(null, mappedResponse.getMessage());
				}
			} else {
				log.warn("Got HTTP {} from login request", response.code());
				String responseString = resolveHttpCodeResponse(response.code());
				return Pair.with(null, responseString);
			}
		} catch (ConnectException e) {
			log.warn("HTTP Connection Error on request");
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
					log.warn("Request result is 'fail' on register request");
					return Pair.with(null, mappedResponse.getMessage());
				}
			} else {
				log.warn("Got HTTP {} from register request", response.code());
				String responseString = resolveHttpCodeResponse(response.code());
				return Pair.with(null, responseString);
			}
		} catch (ConnectException e) {
			log.warn("HTTP Connection Error on request");
			return Pair.with(null, UiConstants.HTTP_CONN_ERROR);
		} catch (IOException e) {
			e.printStackTrace();
			return Pair.with(null, UiConstants.CLIENT_ERROR);
		}
	}

	/**
	 * Static getLeaderBoard request method. Can be called from any controller code.
	 * Creates a GET request with the specified type of leaderboard as the GET parameter,
	 * fetches current Player credentials and makes the request to the backend.
	 * Responses with leaderboard and response message to the caller code.
	 *
	 * @param leaderBoardType
	 * @return Pair<List<Score>, String>
	 */
	public static Pair<List<Score>, String> getLeaderBoard(String leaderBoardType) {
		if (gameDataCookie.getPlayerDTO() != null) {
			String authorizationHeader = Credentials.basic(gameDataCookie.getPlayerDTO().getUsername(),
					gameDataCookie.getPlayerDTO().getPassword());
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
						List<Map<String, Long>> rawLeaderboard = objectMapper.convertValue(mappedResponse.getData(), List.class);
						List<Score> leaderBoard = objectMapper.convertValue(rawLeaderboard, new TypeReference<List<Score>>() {});
						return Pair.with(leaderBoard, mappedResponse.getMessage());
					} else {
						log.warn("Request result is 'fail' on getLeaderBoard request");
						return Pair.with(null, mappedResponse.getMessage());
					}
				} else {
					log.warn("Got HTTP {} from getLeaderBoard request", response.code());
					String responseString = resolveHttpCodeResponse(response.code());
					return Pair.with(null, responseString);
				}
			} catch (ConnectException e) {
				log.warn("HTTP Connection Error on request");
				return Pair.with(null, UiConstants.HTTP_CONN_ERROR);
			} catch (IOException e) {
				e.printStackTrace();
				return Pair.with(null, UiConstants.CLIENT_ERROR);
			}
		} else {
			log.warn("Player cookie not found");
			return Pair.with(null, UiConstants.COOKIE_NOTFOUND);
		}
	}

	/**
	 * Static getGameDataById request method. Can be called from any controller code.
	 * Creates a POST request with player Id in the cookie and makes the request to the backend.
	 * Responses with the newly created GameSession of the player and response message to the caller code.
	 *
	 * @return Pair<GameData, String>
	 */
	public static Pair<GameData, String> getGameDataById() {
		if (gameDataCookie.getPlayerDTO() != null) {
			String authorizationHeader = Credentials.basic(gameDataCookie.getPlayerDTO().getUsername(),
					gameDataCookie.getPlayerDTO().getPassword());
			Request request = new Request.Builder()
					.url(UiConstants.BACKEND_BASE_URL + "/game/" + gameDataCookie.getPlayerDTO().getId())
					.method("GET", null)
					.addHeader("Authorization", authorizationHeader)
					.build();
			try {
				Response response = client.newCall(request).execute();
				if (response.code() == 200) {
					String responseBody = response.body().string();
					RestApiResponse mappedResponse = objectMapper.readValue(responseBody, RestApiResponse.class);
					if (mappedResponse.getResult().equals("success")) {
						GameData gameData = objectMapper.convertValue(mappedResponse.getData(), GameData.class);
						return Pair.with(gameData, mappedResponse.getMessage());
					} else {
						log.warn("Request result is 'fail' on getGameDataById request");
						return Pair.with(null, mappedResponse.getMessage());
					}
				} else {
					log.warn("Got HTTP {} from getGameDataById request", response.code());
					String responseString = resolveHttpCodeResponse(response.code());
					return Pair.with(null, responseString);
				}
			} catch (ConnectException e) {
				log.warn("HTTP Connection Error on request");
				return Pair.with(null, UiConstants.HTTP_CONN_ERROR);
			} catch (IOException e) {
				e.printStackTrace();
				return Pair.with(null, UiConstants.CLIENT_ERROR);
			}
		} else {
			log.warn("Player cookie not found");
			return Pair.with(null, UiConstants.COOKIE_NOTFOUND);
		}
	}

	/**
	 * Static startNewGame request method. Can be called from any controller code.
	 * Creates a POST request with player Id in the cookie and makes the request to the backend.
	 * Responses with the newly created GameSession of the player and response message to the caller code.
	 *
	 * @return Pair<GameSession, String>
	 */
	public static Pair<GameSession, String> startNewGame() {
		if (gameDataCookie.getPlayerDTO() != null) {
			String authorizationHeader = Credentials.basic(gameDataCookie.getPlayerDTO().getUsername(),
					gameDataCookie.getPlayerDTO().getPassword());
			MediaType mediaType = MediaType.parse("text/plain");
			RequestBody body = RequestBody.create(mediaType, "");
			Request request = new Request.Builder()
					.url(UiConstants.BACKEND_BASE_URL + "/game/start/" + gameDataCookie.getPlayerDTO().getId())
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
						log.warn("Request result is 'fail' on startNewGame request");
						return Pair.with(null, mappedResponse.getMessage());
					}
				} else {
					log.warn("Got HTTP {} from startNewGame request", response.code());
					String responseString = resolveHttpCodeResponse(response.code());
					return Pair.with(null, responseString);
				}
			} catch (ConnectException e) {
				log.warn("HTTP Connection Error on request");
				return Pair.with(null, UiConstants.HTTP_CONN_ERROR);
			} catch (IOException e) {
				e.printStackTrace();
				return Pair.with(null, UiConstants.CLIENT_ERROR);
			}
		} else {
			log.warn("Player cookie not found");
			return Pair.with(null, UiConstants.COOKIE_NOTFOUND);
		}
	}

	public static Pair<Boolean, String> updateGameSession(GameSession gameSession) {
		if (gameDataCookie.getPlayerDTO() != null) {
			String authorizationHeader = Credentials.basic(gameDataCookie.getPlayerDTO().getUsername(),
					gameDataCookie.getPlayerDTO().getPassword());
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType,
					"{\n\t\"currentLevel\":" + gameSession.getCurrentLevel() + "," +
							"\n\t\"sessionScore\":" + gameSession.getSessionScore() + "," +
							"\n\t\"shipHealth\":" + gameSession.getShipHealth() + "," +
							"\n\t\"shipType\":" + gameSession.getShipType().resolveEnumCode() + "\n}");
			Request request = new Request.Builder()
					.url(UiConstants.BACKEND_BASE_URL + "/game/update/" + gameDataCookie.getPlayerDTO().getId())
					.method("PUT", body)
					.addHeader("Authorization", authorizationHeader)
					.addHeader("Content-Type", "application/json")
					.build();
			try {
				Response response = client.newCall(request).execute();
				if (response.code() == 200) {
					String responseBody = response.body().string();
					RestApiResponse mappedResponse = objectMapper.readValue(responseBody, RestApiResponse.class);
					if (mappedResponse.getResult().equals("success")) {
						return Pair.with(true, mappedResponse.getMessage());
					} else {
						log.warn("Request result is 'fail' on startNewGame request");
						return Pair.with(false, mappedResponse.getMessage());
					}
				} else {
					log.warn("Got HTTP {} from startNewGame request", response.code());
					String responseString = resolveHttpCodeResponse(response.code());
					return Pair.with(false, responseString);
				}
			} catch (ConnectException e) {
				log.warn("HTTP Connection Error on request");
				return Pair.with(false, UiConstants.HTTP_CONN_ERROR);
			} catch (IOException e) {
				e.printStackTrace();
				return Pair.with(false, UiConstants.CLIENT_ERROR);
			}
		} else {
			log.warn("Player cookie not found");
			return Pair.with(false, UiConstants.COOKIE_NOTFOUND);
		}
	}

	public static Pair<Boolean, String> finishGameSession(GameSession gameSession) {
		if (gameDataCookie.getPlayerDTO() != null) {
			String authorizationHeader = Credentials.basic(gameDataCookie.getPlayerDTO().getUsername(),
					gameDataCookie.getPlayerDTO().getPassword());
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType,
					"{\n\t\"currentLevel\":" + gameSession.getCurrentLevel() + "," +
							"\n\t\"sessionScore\":" + gameSession.getSessionScore() + "," +
							"\n\t\"shipHealth\":" + gameSession.getShipHealth() + "," +
							"\n\t\"shipType\":" + gameSession.getShipType().resolveEnumCode() + "\n}");
			Request request = new Request.Builder()
					.url(UiConstants.BACKEND_BASE_URL + "/game/finish/" + gameDataCookie.getPlayerDTO().getId())
					.method("PUT", body)
					.addHeader("Authorization", authorizationHeader)
					.addHeader("Content-Type", "application/json")
					.build();
			try {
				Response response = client.newCall(request).execute();
				if (response.code() == 200) {
					String responseBody = response.body().string();
					RestApiResponse mappedResponse = objectMapper.readValue(responseBody, RestApiResponse.class);
					if (mappedResponse.getResult().equals("success")) {
						return Pair.with(true, mappedResponse.getMessage());
					} else {
						log.warn("Request result is 'fail' on startNewGame request");
						return Pair.with(false, mappedResponse.getMessage());
					}
				} else {
					log.warn("Got HTTP {} from startNewGame request", response.code());
					String responseString = resolveHttpCodeResponse(response.code());
					return Pair.with(false, responseString);
				}
			} catch (ConnectException e) {
				log.warn("HTTP Connection Error on request");
				return Pair.with(false, UiConstants.HTTP_CONN_ERROR);
			} catch (IOException e) {
				e.printStackTrace();
				return Pair.with(false, UiConstants.CLIENT_ERROR);
			}
		} else {
			log.warn("Player cookie not found");
			return Pair.with(false, UiConstants.COOKIE_NOTFOUND);
		}
	}
}
