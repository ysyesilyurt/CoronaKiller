package com.coronakiller.ui.controller;

import com.coronakiller.ui.application.StageInitializer;
import com.coronakiller.ui.service.RequestService;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * This class implements the mechanism that is responsible for the demonstration of requested type(all, monthly, weekly) leaderboard.
 * It utilizes choiceBox and tableView in JavaFx.
 * This view can be accessible from dashboard.
 */

public class LeaderBoardController implements Initializable {

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private ChoiceBox<String> timeBox;

	@FXML
	public JFXButton backToDashboardButton;

	private TableView<Integer> leaderboard;
	private List<Map<String, Long>> leaderBoardData = new ArrayList<>();
	private List<Long> scores;
	private List<String> userNames;

	public LeaderBoardController(){

	}

	/**
	 * When the class is loaded, the first method called is overwritten initialize method.
	 * Thanks to this method, fields in this class does not remain NULL.
	 * @param url default parameter of Initializable interface's initialize method
	 * @param resourceBundle default parameter of Initializable interface's initialize method
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle){
		leaderBoardData = RequestService.getLeaderBoard(StageInitializer.currentPlayer, "all");
		leaderboard = new TableView<>();
		scores = new ArrayList<>();
		userNames = new ArrayList<>();
		parseLeaderBoardData();
		formLeaderBoardTable();

		leaderboard.setLayoutX(150);
		leaderboard.setLayoutY(200);
		leaderboard.setMinWidth(250);

		anchorPane.getChildren().add(leaderboard);
	}

	/**
	 * This method constitutes the details and data of the leaderboard table.
	 */
	private void formLeaderBoardTable() {
		for(int i=0; i<userNames.size(); ++i){
			leaderboard.getItems().add(i);
		}

		TableColumn<Integer, Number> rankColumn = new TableColumn<>("Rank");
		rankColumn.setCellValueFactory(cellData -> {
			Integer rowIndex = cellData.getValue();
			return new ReadOnlyIntegerWrapper(rowIndex+1);
		});
		TableColumn<Integer, String> userNameColumn = new TableColumn<>("User Name");
		userNameColumn.setCellValueFactory(cellData -> {
			Integer rowIndex = cellData.getValue();
			return new ReadOnlyStringWrapper(userNames.get(rowIndex));
		});
		TableColumn<Integer, Number> scoreColumn = new TableColumn<>("Reputation Points");
		scoreColumn.setCellValueFactory(cellData -> {
			Integer rowIndex = cellData.getValue();
			return new ReadOnlyLongWrapper(scores.get(rowIndex));
		});

		rankColumn.setMinWidth(10);
		userNameColumn.setMinWidth(100);
		scoreColumn.setMinWidth(200);

		leaderboard.getColumns().add(rankColumn);
		leaderboard.getColumns().add(userNameColumn);
		leaderboard.getColumns().add(scoreColumn);
	}

	/**
	 * This method separates the incoming List<Map<String,Long>> data to userName and scores arrays
	 since we don't have a class which field's are only username and score.
	 */
	private void parseLeaderBoardData(){
		scores.clear();
		userNames.clear();
		for (Map<String,Long> leaderBoardDatum : leaderBoardData) {
			Object[] tempArray = leaderBoardDatum.values().toArray();
			userNames.add((String) tempArray[0]);
			Double d = (Double) tempArray[1];
			scores.add(d.longValue());
		}
	}

	/**
	 * When user changes the requested type of leaderboard by using choiceBox in the view, this method is triggered.
	 * It requests leaderboard data from the backend of the application.
	 * This method changes the contents of the leaderboard.
	 */
	@FXML
	public void getLeaderBoardContents(){
		String selectedItem = timeBox.getSelectionModel().getSelectedItem();
		if(selectedItem.equals("All Time Leaderboard")){
			leaderBoardData.clear();
			leaderBoardData = RequestService.getLeaderBoard(StageInitializer.currentPlayer, "all");
		}
		else if(selectedItem.equals("Monthly Leaderboard")){
			leaderBoardData.clear();
			leaderBoardData = RequestService.getLeaderBoard(StageInitializer.currentPlayer, "monthly");
		}
		else{
			leaderBoardData.clear();
			leaderBoardData = RequestService.getLeaderBoard(StageInitializer.currentPlayer, "weekly");
		}

		loadLeaderBoardContents();
	}

	/**
	 * When contents of the leaderboard is changed, this method loads the new content to the leaderboard.
	 */
	public void loadLeaderBoardContents(){
		leaderboard.getItems().clear();
		leaderboard.getColumns().clear();
		parseLeaderBoardData();
		formLeaderBoardTable();
	}

	/**
	 * After investigating leaderboard, if user wants to go back to the dashboard, this method is used by the relevant button.
	 * @param event Button fired and this event represents this
	 * @throws IOException ,This method may throw exception.
	 */
	@FXML
	protected void handleBackToDashboardButtonAction(ActionEvent event) throws IOException {
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fxml/dashboard.fxml"));
		Parent dashboardPage = loader.load();
		Scene scene = new Scene(dashboardPage, 600, 800);
		currentStage.setScene(scene);
		currentStage.show();
	}
}
