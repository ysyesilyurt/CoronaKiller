package com.coronakiller.ui.constants;

import javafx.scene.control.TextFormatter;
import lombok.extern.slf4j.Slf4j;

import static com.coronakiller.ui.constants.GeneralConstants.*;

@Slf4j
public class Utils {
	public static TextFormatter<String> returnInputTextFormatter() {
		return new TextFormatter<>(change -> {
			/* Reject input if space is entered */
			if (change.getText().equals(" ")) {
				change.setText("");
			}
			return change;
		});
	}

	public static String resolveGameSessionLevel(Integer level) {
		switch (level) {
			case 1:
				return GAME_LEVEL1_PAGE;
			case 2:
				return GAME_LEVEL2_PAGE;
			case 3:
				return GAME_LEVEL3_PAGE;
			case 4:
				return GAME_LEVEL4_PAGE;
			default: {
				/* Error case, log and redirect to Dashboard */
				log.error("Invalid level:{} when trying to resolve game session level", level);
				return DASHBOARD_PAGE;
			}
		}
	}

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
				return GeneralConstants.HTTP_400;
			case 401:
				return GeneralConstants.HTTP_401;
			case 404:
				return GeneralConstants.HTTP_404;
			case 500:
				return GeneralConstants.HTTP_500;
			default: {
				log.warn("Got {} HTTP code after a request", httpCode);
				return "Oops! Something went wrong - " + httpCode;
			}
		}
	}
}
