package com.coronakiller.ui.controller.level;

import com.coronakiller.ui.application.StageInitializer;
import com.coronakiller.ui.constants.UiConstants;
import com.coronakiller.ui.model.ShipType;
import com.coronakiller.ui.model.spaceship.NormalSpaceShip;
import com.coronakiller.ui.model.spaceship.PowerfulGunsSpaceShip;
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

import static com.coronakiller.ui.application.StageInitializer.*;

/**
 * This class controls the fourth level of the game by creating,initializing and using fourth level's objects.
 */
public class GameLevel4Controller extends GameLevelController {

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
		GameLevelController.currentLevel = 4;
		GameLevelController.shipType = ShipType.POWERFUL_GUNS;
		GameLevelController.currentSessionScore = gameDataCookie.getGameSessionDTO().getSessionScore();
		GameLevelController.currentPane = this.anchorPane;
	}

	/**
	 * Spaceship of the level and it's specifications are done in this method.
	 */
	public void handleSpaceInitialization(){
		spaceShip = null;
		spaceShip = new PowerfulGunsSpaceShip(gameDataCookie.getGameSessionDTO().getShipHealth());
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
		Random random = new Random();
		for(int i=1; i<6 ; ++i){
			int mediumVirusNumber = random.nextInt(2);
			if(mediumVirusNumber == 0)
				mediumVirusNumber =1;
			for(int j=1; j<3; ++j) {
				Virus virus;
				if(j == mediumVirusNumber){
					virus = new MediumVirus(100*i, 100*j);
				} else{
					virus = new HardVirus(100*i, 100*j);
				}
				virus.changeIconOfVirus();
				virus.virusAutoMove();
				virus.virusAutoFire(anchorPane);
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
