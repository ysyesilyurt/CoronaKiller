package com.coronakiller.ui.service;

import com.coronakiller.ui.application.StageInitializer;
import com.coronakiller.ui.constants.UiConstants;
import com.coronakiller.ui.model.Player;
import com.coronakiller.ui.model.RestApiResponse;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.squareup.okhttp.*;
import org.javatuples.Pair;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.net.ConnectException;
import java.util.List;

@Service
public class RequestService {

	public static String resolveHttpCodeString(Integer httpCode) {
		switch (httpCode) {
			case 400:
				return UiConstants.HTTP_400;
			case 401:
				return UiConstants.HTTP_401;
			case 404:
				return UiConstants.HTTP_404;
			case 500:
				return UiConstants.HTTP_500;
			default:
				return "Oops! Something went wrong - " + httpCode;
		}
	}

	/**
	 * This method post request to rest service and return the result as a player.
	 *
	 * @param player User to login
	 * @return User
	 */
	public static Pair<Player, String> login(Player player) {
		/* Construct the password first */
		String encodedPassword = BCrypt.hashpw(player.getPassword(), UiConstants.HASH_SALT);
		OkHttpClient client = new OkHttpClient();
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
			Gson gson = new Gson();
			ResponseBody responseBody = response.body();
			RestApiResponse mappedResponse = gson.fromJson(responseBody.string(), RestApiResponse.class);
			if (response.code() == 200) {
				if (mappedResponse.getResult().equals("success")) {
					JsonReader reader = new JsonReader(new StringReader(mappedResponse.getData().toString()));
					reader.setLenient(true);
					Player loggedInPlayer = gson.fromJson(reader, Player.class);
					return Pair.with(loggedInPlayer, mappedResponse.getMessage());
				} else {
					return Pair.with(null, mappedResponse.getMessage());
				}
			} else {
				String responseString = resolveHttpCodeString(response.code());
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
	 * Register player by given credentials.
	 * Beware no authorization header is provided, since this endpoint is not authorized.
	 *
	 * @param player Credentials of Player to Register
	 * @return Pair<Boolean, String> - first field indicates operation result, second field returns its message
	 */
	public static Pair<Player, String> register(Player player) {
		/* Construct the password first */
		String encodedPassword = BCrypt.hashpw(player.getPassword(), UiConstants.HASH_SALT);
		OkHttpClient client = new OkHttpClient();
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
			Gson gson = new Gson();
			ResponseBody responseBody = response.body();
			RestApiResponse mappedResponse = gson.fromJson(responseBody.string(), RestApiResponse.class);
			if (response.code() == 200) {
				if (mappedResponse.getResult().equals("success")) {
					JsonReader reader = new JsonReader(new StringReader(mappedResponse.getData().toString()));
					reader.setLenient(true);
					Player createdPlayer = gson.fromJson(reader, Player.class);
					return Pair.with(createdPlayer, mappedResponse.getMessage());
				} else {
					return Pair.with(null, mappedResponse.getMessage());
				}
			} else {
				String responseString = resolveHttpCodeString(response.code());
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
		/* Construct the password first */
		if (StageInitializer.currentPlayer != null) {
			OkHttpClient client = new OkHttpClient();
			String authorizationHeader = Credentials.basic(StageInitializer.currentPlayer.getUsername(), StageInitializer.currentPlayer.getPassword());
			Request request = new Request.Builder()
					.url(UiConstants.BACKEND_BASE_URL + "/players/" + playerIdToGet.toString())
					.method("GET", null)
					.addHeader("Authorization", authorizationHeader)
					.build();
			try {
				Response response = client.newCall(request).execute();
				Gson gson = new Gson();
				ResponseBody responseBody = response.body();
				RestApiResponse mappedResponse = gson.fromJson(responseBody.string(), RestApiResponse.class);
				if (mappedResponse.getResult().equals("success")) {
					JsonReader reader = new JsonReader(new StringReader(mappedResponse.getData().toString()));
					reader.setLenient(true);
					return gson.fromJson(reader, Player.class);
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

	public static List getLeaderBoard(String leaderBoardType) {
		/* Construct the password first */
		OkHttpClient client = new OkHttpClient();
		String authorizationHeader = Credentials.basic(StageInitializer.currentPlayer.getUsername(), StageInitializer.currentPlayer.getPassword());
		Request request = new Request.Builder()
				.url(UiConstants.BACKEND_BASE_URL + "/scoreboard?type=" + leaderBoardType)
				.method("GET", null)
				.addHeader("Authorization", authorizationHeader)
				.build();
		try {
			Response response = client.newCall(request).execute();
			Gson gson = new Gson();
			ResponseBody responseBody = response.body();
			RestApiResponse mappedResponse = gson.fromJson(responseBody.string(), RestApiResponse.class);
			if (mappedResponse.getResult().equals("success")) {
				return gson.fromJson(mappedResponse.getData().toString(), List.class);
			} else {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null; // TODO
		}
	}
}
