package com.coronakiller.ui.service;

import com.coronakiller.ui.constants.UiConstants;
import com.coronakiller.ui.model.Player;
import com.coronakiller.ui.model.RestApiResponse;
import com.google.gson.Gson;
import com.squareup.okhttp.*;
import org.javatuples.Pair;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RequestService {

	/**
	 * This method post request to rest service and return the result as a player.
	 *
	 * @param user User to login
	 * @return User
	 */
	public static Player login(Player player) {
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = RequestBody.create(mediaType, "");
		String encodedPassword = BCrypt.hashpw(player.getPassword(), UiConstants.HASH_SALT);
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
			if (mappedResponse.getResult().equals("success")) {
				return gson.fromJson(mappedResponse.getData().toString(), Player.class);
			} else {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null; // TODO
		}
	}

	/**
	 * Register player by given credentials.
	 * Beware no authorization header is provided, since this endpoint is not authorized.
	 * @param user Credentials of Player to Register
	 * @return Pair<Boolean, String> - first field indicates operation result, second field returns its message
	 */
	public static Pair<Boolean, String> register(Player player) {
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/json");
		String encodedPswd = BCrypt.hashpw(player.getPassword(), UiConstants.HASH_SALT);
		RequestBody body = RequestBody.create(mediaType,
				"{\n\t\"username\": \"" + player.getUsername() + "\",\n\t\"password\": \"" + encodedPswd + "\"\n}");
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
			if (mappedResponse.getResult().equals("success")) {
				return Pair.with(true, mappedResponse.getMessage());
			} else {
				return Pair.with(false, mappedResponse.getMessage());
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null; // TODO
		}
	}
}
