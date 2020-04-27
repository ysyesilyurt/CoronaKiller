package com.coronakiller.ui.controller.level;

import com.coronakiller.ui.application.StageInitializer;
import com.coronakiller.ui.constants.UiConstants;
import com.coronakiller.ui.model.ShipType;
import com.coronakiller.ui.model.spaceship.NormalSpaceShip;
import com.coronakiller.ui.model.spaceship.PowerfulGunsSpaceShip;
import com.coronakiller.ui.model.virus.HardVirus;
import com.coronakiller.ui.model.virus.MediumVirus;
import com.coronakiller.ui.model.virus.Virus;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import static com.coronakiller.ui.application.StageInitializer.*;

public class GameLevel4Controller extends GameLevelController {

	@FXML
	public AnchorPane anchorPane;

	@FXML
	public Text scoreValue;

	@FXML
	public Text hpValue;

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

	public void handleSpaceInitialization(){
		spaceShip = null;
		spaceShip = new PowerfulGunsSpaceShip(gameDataCookie.getGameSessionDTO().getShipHealth());
		//spaceShip.changeIconofSpaceShip();
		spaceShip.setMouseDraggableObject();
		spaceShip.autofire(anchorPane);
	}

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
				virus.virusAutoMove();
				virus.virusAutoFire(anchorPane);
				levelViruses.add(virus);
				anchorPane.getChildren().add(virus);
			}
		}
	}
}
