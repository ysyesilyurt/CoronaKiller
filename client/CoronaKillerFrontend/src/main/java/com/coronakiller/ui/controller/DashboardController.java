package com.coronakiller.ui.controller;

import com.coronakiller.ui.application.StageInitializer;
import com.coronakiller.ui.constants.UiConstants;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
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
		snackbar = new JFXSnackbar(dashboardPane);
		dashboardPane.getStylesheets().add(UiConstants.GENERAL_STYLES);
		username.setText(String.format("Welcome %s!", StageInitializer.currentPlayer.getUsername()));
		totalScore.setText(String.format("Your Total Score: %s", StageInitializer.currentPlayer.getTotalScore()));
		continueGameButton.setDisable(StageInitializer.currentPlayer.getGameSessionId() == null);
	}

	@FXML
	public void onClickContinueGame(ActionEvent event) {

	}

	@FXML
	public void onClickNewGame(ActionEvent event) {

	}

	@FXML
	public void onClickGoToLeaderboard(ActionEvent event) throws IOException {
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent leaderBoardPage = FXMLLoader.load(getClass().getClassLoader().getResource(UiConstants.LEADERBOARD_PAGE));
		Scene scene = new Scene(leaderBoardPage, 600, 800);
		currentStage.setScene(scene);
		currentStage.show();
	}
}
