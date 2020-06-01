package com.coronakiller.ui.controller;

import com.coronakiller.ui.constants.GeneralConstants;
import com.coronakiller.ui.constants.Utils;
import com.coronakiller.ui.model.GameData;
import com.coronakiller.ui.model.GameSession;
import com.coronakiller.ui.service.RequestService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSpinner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.coronakiller.ui.application.StageInitializer.gameDataCookie;
import static com.coronakiller.ui.constants.GeneralConstants.*;
import static com.coronakiller.ui.constants.Utils.*;

/**
 * Controller that manages Dashboard Page
 */
@Slf4j
@Component
public class DashboardController implements Initializable {
	private JFXSnackbar snackbar;

	@FXML
	public Text ongoingSessionScore;

	@FXML
	public JFXButton logoutButton;

	@FXML
	public JFXSpinner loadingSpinner;

	@FXML
	public AnchorPane dashboardPane;

	@FXML
	public AnchorPane innerPane;

	@FXML
	public JFXButton continueGameButton;

	@FXML
	public JFXButton newGameButton;

	@FXML
	public JFXButton leaderboardButton;

	@FXML
	public Text snackbarContent;

	@FXML
	public Text totalScore;

	@FXML
	public Text username;

	@FXML
	private JFXButton leaderBoardButton;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		loadingSpinner.setVisible(true);
		innerPane.setDisable(true);
		snackbar = new JFXSnackbar(dashboardPane);
		dashboardPane.getStylesheets().add(GENERAL_STYLES);

		/* On each render, this page requests the current "GameData" from the Backend API */
		Pair<GameData, String> result = RequestService.getGameDataById();
		snackbarContent.setText(result.getValue1());
		snackbar.enqueue(new JFXSnackbar.SnackbarEvent(snackbarContent));
		if (result.getValue0() != null) {
			/* Update Cookie in case of a change in the values */
			gameDataCookie = result.getValue0();
		}

		username.setText(String.format("Welcome %s!", gameDataCookie.getPlayerDTO().getUsername()));
		totalScore.setText(String.format("Your Total Score: %s", gameDataCookie.getPlayerDTO().getTotalScore()));
		if (gameDataCookie.getPlayerDTO().getHasOngoingSession()) {
			ongoingSessionScore.setText(String.format("Ongoing Session Score: %s", gameDataCookie.getGameSessionDTO().getSessionScore()));
		} else {
			continueGameButton.setDisable(true);
		}
		loadingSpinner.setVisible(false);
		innerPane.setDisable(false);
	}

	/**
	 * Method that is fired on the continue-game button click action.
	 * First checks if cookie has a game session of the player (even though button will be disabled
	 * if he/she does not have any) then redirects user to corresponding Game Level page (if has a game session).
	 * Otherwise notifies user that he/she does not have an ongoing game session.
	 *
	 * @param event
	 */
	@FXML
	public void onClickContinueGame(ActionEvent event) throws IOException {
		loadingSpinner.setVisible(true);
		innerPane.setDisable(true);
		if (gameDataCookie.getPlayerDTO().getHasOngoingSession()) {
			/* Redirect to The Last Checkpoint (Saved Level) */
			String lastSavedLevel = resolveGameSessionLevel(gameDataCookie.getGameSessionDTO().getCurrentLevel());
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Parent dashboardPage = FXMLLoader.load(getClass().getClassLoader().getResource(lastSavedLevel));
			Scene scene = new Scene(dashboardPage, 600, 800);
			currentStage.setScene(scene);
			currentStage.show();
		}
		snackbarContent.setText("You dont have an ongoing Game Session!");
		snackbar.enqueue(new JFXSnackbar.SnackbarEvent(snackbarContent));
		loadingSpinner.setVisible(false);
		innerPane.setDisable(false);
	}

	/**
	 * Method that is fired on the new-game button click action.
	 * First makes a startGameSession request to backend to get the an initial GameSession of the Player.
	 * Then sets the player game session cookie and redirects user to first level of the game.
	 *
	 * @param event
	 */
	@FXML
	public void onClickNewGame(ActionEvent event) throws IOException {
		loadingSpinner.setVisible(true);
		innerPane.setDisable(true);
		Pair<GameSession, String> result = RequestService.startNewGame();
		snackbarContent.setText(result.getValue1());
		snackbar.enqueue(new JFXSnackbar.SnackbarEvent(snackbarContent));
		if (result.getValue0() != null) {
			/* Set the application's current player game session for global access */
			gameDataCookie.setGameSessionDTO(result.getValue0());
			/* Redirect to The Last Checkpoint (Saved Level) */
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Parent level1Page = FXMLLoader.load(getClass().getClassLoader().getResource(GAME_LEVEL1_PAGE));
			Scene scene = new Scene(level1Page, 600, 800);
			currentStage.setScene(scene);
			currentStage.show();
		}
		loadingSpinner.setVisible(false);
		innerPane.setDisable(false);
	}

	/**
	 * Method that is fired on the leaderboard button click action.
	 * Redirects user to Leaderboard page.
	 *
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void onClickGoToLeaderboard(ActionEvent event) throws IOException {
		loadingSpinner.setVisible(true);
		innerPane.setDisable(true);
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent leaderBoardPage = FXMLLoader.load(getClass().getClassLoader().getResource(LEADERBOARD_PAGE));
		Scene scene = new Scene(leaderBoardPage, 600, 800);
		currentStage.setScene(scene);
		currentStage.show();
		loadingSpinner.setVisible(false);
		innerPane.setDisable(false);
	}

	/**
	 * Method that is fired on the logout button click action.
	 * Clears the application currentPlayer cookie and redirects user to Login page.
	 *
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void onClickLogout(ActionEvent event) throws IOException {
		/* Remove player cookie */
		gameDataCookie.setPlayerDTO(null);
		/* Then Route to Login */
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent dashboardPage = FXMLLoader.load(getClass().getClassLoader().getResource(LOGIN_PAGE));
		Scene scene = new Scene(dashboardPage, 600, 800);
		currentStage.setScene(scene);
		currentStage.show();
	}
}
