package com.coronakiller.ui.controller;

import com.coronakiller.ui.application.StageInitializer;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DashboardController {

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

	@FXML
	public void initialize() {
		dashboardPane.getStylesheets().add("css/styles.css");
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
		Parent leaderBoardPage = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/leaderBoard.fxml"));
		Scene scene = new Scene(leaderBoardPage, 600, 800);
		currentStage.setScene(scene);
		currentStage.show();
	}
}
