package com.coronakiller.ui.controller.level;

import com.coronakiller.ui.constants.UiConstants;
import com.coronakiller.ui.model.ShipType;
import com.coronakiller.ui.model.spaceship.BigGunsSpaceShip;
import com.coronakiller.ui.model.spaceship.PowerfulGunsSpaceShip;
import com.coronakiller.ui.model.virus.BigBoss;
import com.coronakiller.ui.model.virus.HardVirus;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import static com.coronakiller.ui.application.StageInitializer.gameDataCookie;

public class GameLevel5Controller extends GameLevelController{

	@FXML
	public AnchorPane anchorPane;

	@FXML
	public Text scoreValue;

	@FXML
	public Text hpValue;

	@FXML
	public Text teammateHpValue;

	@FXML
	public Text alienHpValue;

	@FXML
	public JFXButton backToLoginButton;

	/**
	 * Overwritten initialize method from Initializable interface. It is responsible for initializing ui related objects.
	 * @param url original parameter of initialize
	 * @param resourceBundle original parameter of initialize
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		isGameLevelFinished = false;
		handleVirusInitialization();
		handleSpaceInitialization();
		anchorPane.getChildren().add(spaceShip);
		nextLevel = null;
		nextLevel = new StringBuilder(UiConstants.DASHBOARD_PAGE);
		GameLevelController.hpValue = this.hpValue;
		GameLevelController.scoreValue = this.scoreValue;
		GameLevelController.updateHpValue();
		GameLevelController.updateScoreValue();
		this.updateTeammateHpValue();
		this.updateAlienHpValue();
		GameLevelController.currentLevel = 5;
		GameLevelController.shipType = ShipType.BIG_GUNS;
		GameLevelController.currentSessionScore = gameDataCookie.getGameSessionDTO().getSessionScore();
		GameLevelController.currentPane = this.anchorPane;
	}

	/**
	 * Spaceship of the level and it's specifications are done in this method.
	 */
	public void handleSpaceInitialization(){
		spaceShip = null;
		spaceShip = new BigGunsSpaceShip(gameDataCookie.getGameSessionDTO().getShipHealth());
		spaceShip.changeIconofSpaceShip();
		spaceShip.setMouseDraggableObject();
		spaceShip.autofire(anchorPane);
	}

	/**
	 * Viruses and their specifications are done in this method.
	 */
	public void handleVirusInitialization(){
		levelViruses.clear();
		levelViruses = new ArrayList<>();
		Virus virus = new BigBoss(300, 100);
		virus.changeIconOfVirus();
		virus.virusAutoMove();
		virus.virusAutoFire(anchorPane);
		levelViruses.add(virus);
		anchorPane.getChildren().add(virus);
	}

	public void updateTeammateHpValue(){
		teammateHpValue.setText(String.valueOf(100));
	}

	public void updateAlienHpValue(){
		alienHpValue.setText(String.valueOf(100));
	}

	//TODO : player with higher score also need to get K points bonus.

	public void onClickGoDash(ActionEvent event) throws IOException {
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent dashboardPage = FXMLLoader.load(getClass().getClassLoader().getResource(UiConstants.DASHBOARD_PAGE));
		Scene scene = new Scene(dashboardPage, 600, 800);
		currentStage.setScene(scene);
		currentStage.show();
	}

}
