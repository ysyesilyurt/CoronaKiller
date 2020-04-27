package com.coronakiller.ui.controller.level;

import com.coronakiller.ui.constants.UiConstants;
import com.coronakiller.ui.model.ShipType;
import com.coronakiller.ui.model.spaceship.RookieSpaceShip;
import com.coronakiller.ui.model.virus.EasyVirus;
import com.coronakiller.ui.model.virus.MediumVirus;
import com.coronakiller.ui.model.virus.Virus;
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
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import static com.coronakiller.ui.application.StageInitializer.gameDataCookie;

/**
 * This class controls the first level of the game by creating,initializing and using first level's objects.
 */
@Setter
@Getter
public class GameLevel1Controller extends GameLevelController {

	@FXML
	public AnchorPane anchorPane;

	@FXML
	public Text scoreValue;

	@FXML
	public Text hpValue;

	@FXML
	public JFXButton backToLoginButton;

	/**
	 * Overwritten initialize method from Initializable interface. It is responsible for initializing ui related objects.
	 *
	 * @param url            original parameter of initialize
	 * @param resourceBundle original parameter of initialize
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		isGameLevelFinished = false;
		handleVirusInitialization();
		handleSpaceInitialization();
		anchorPane.getChildren().add(spaceShip);
		nextLevel.append(UiConstants.GAME_LEVEL2_PAGE);
		GameLevelController.hpValue = this.hpValue;
		GameLevelController.scoreValue = this.scoreValue;
		GameLevelController.updateHpValue();
		GameLevelController.updateScoreValue();
		GameLevelController.currentLevel = 1;
		GameLevelController.shipType = ShipType.ROOKIE;
		GameLevelController.currentSessionScore = gameDataCookie.getGameSessionDTO().getSessionScore();
		GameLevelController.currentPane = this.anchorPane;
		//GameLevelController.cheatImplementation();
	}

	/**
	 * Spaceship of the level and it's specifications are done in this method.
	 */
	public void handleSpaceInitialization() {
		spaceShip = new RookieSpaceShip(gameDataCookie.getGameSessionDTO().getShipHealth());
		//spaceShip.changeIconofSpaceShip();
		spaceShip.setMouseDraggableObject();
		spaceShip.autofire(anchorPane);
	}

	/**
	 * Viruses and their specifications are done in this method.
	 */
	public void handleVirusInitialization() {
		Random rand = new Random();
		for (int j = 1; j < 3; ++j) {
			int mediumVirusPosition = rand.nextInt(6);
			if (mediumVirusPosition == 0)
				mediumVirusPosition = 1;
			for (int i = 1; i < 6; ++i) {
				Virus virus;
				if (i == mediumVirusPosition) {
					virus = new MediumVirus(100 * i, j * 100);
				} else {
					virus = new EasyVirus(100 * i, j * 100);
				}
				virus.virusAutoMove();
				virus.virusAutoFire(anchorPane);
				virus.changeIconOfVirus();
				levelViruses.add(virus);
				anchorPane.getChildren().add(virus);
			}
		}
	}

	public void onClickGoDash(ActionEvent event) throws IOException {
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent dashboardPage = FXMLLoader.load(getClass().getClassLoader().getResource(UiConstants.DASHBOARD_PAGE));
		Scene scene = new Scene(dashboardPage, 600, 800);
		currentStage.setScene(scene);
		currentStage.show();
	}
}
