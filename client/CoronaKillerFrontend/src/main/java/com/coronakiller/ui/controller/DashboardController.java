package com.coronakiller.ui.controller;

import com.coronakiller.ui.application.StageInitializer;
import com.coronakiller.ui.constants.UiConstants;
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
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class DashboardController implements Initializable {
	private JFXSnackbar snackbar;

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
		dashboardPane.getStylesheets().add(UiConstants.GENERAL_STYLES);
		username.setText(String.format("Welcome %s!", StageInitializer.currentPlayer.getUsername()));
		totalScore.setText(String.format("Your Total Score: %s", StageInitializer.currentPlayer.getTotalScore()));
		continueGameButton.setDisable(StageInitializer.currentPlayer.getGameSessionId() == null);
		loadingSpinner.setVisible(false);
		innerPane.setDisable(false);
	}

	@FXML
	public void onClickContinueGame(ActionEvent event) {
		loadingSpinner.setVisible(true);
		innerPane.setDisable(true);
		// TODO: WHEN PLAYERS RETURNS TO DASHBOARD FROM GAME REQUEST TO GET UPDATED SCORE + GAMESESSION FROM BACKEND OR USE UPDATED PLAYER..
		loadingSpinner.setVisible(false);
		innerPane.setDisable(false);
	}

	@FXML
	public void onClickNewGame(ActionEvent event) throws IOException {
		loadingSpinner.setVisible(true);
		innerPane.setDisable(true);
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent level1Page = FXMLLoader.load(getClass().getClassLoader().getResource(UiConstants.GAME_LEVEL1_PAGE));
		Scene scene = new Scene(level1Page, 600, 800);
		currentStage.setScene(scene);
		currentStage.show();
		// TODO: WHEN PLAYERS RETURNS TO DASHBOARD FROM GAME REQUEST TO GET UPDATED SCORE + GAMESESSION FROM BACKEND OR USE UPDATED PLAYER..
		loadingSpinner.setVisible(false);
		innerPane.setDisable(false);
	}

	@FXML
	public void onClickGoToLeaderboard(ActionEvent event) throws IOException {
		loadingSpinner.setVisible(true);
		innerPane.setDisable(true);
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent leaderBoardPage = FXMLLoader.load(getClass().getClassLoader().getResource(UiConstants.LEADERBOARD_PAGE));
		Scene scene = new Scene(leaderBoardPage, 600, 800);
		currentStage.setScene(scene);
		currentStage.show();
		loadingSpinner.setVisible(false);
		innerPane.setDisable(false);
	}

	@FXML
	public void onClickLogout(ActionEvent event) throws IOException {
		/* Remove player cookie */
		StageInitializer.currentPlayer = null;
		/* Then Route to Login */
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent dashboardPage = FXMLLoader.load(getClass().getClassLoader().getResource(UiConstants.LOGIN_PAGE));
		Scene scene = new Scene(dashboardPage, 600, 800);
		currentStage.setScene(scene);
		currentStage.show();
	}
}
