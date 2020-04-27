package com.coronakiller.ui.controller.level;

import com.coronakiller.ui.application.StageInitializer;
import com.coronakiller.ui.constants.UiConstants;
import com.coronakiller.ui.model.ShipType;
import com.coronakiller.ui.model.spaceship.NormalSpaceShip;
import com.coronakiller.ui.model.spaceship.PowerfulGunsSpaceShip;
import com.coronakiller.ui.model.spaceship.VeteranSpaceShip;
import com.coronakiller.ui.model.virus.EasyVirus;
import com.coronakiller.ui.model.virus.HardVirus;
import com.coronakiller.ui.model.virus.MediumVirus;
import com.coronakiller.ui.model.virus.Virus;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameLevel3Controller extends GameLevelController {

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
		nextLevel = new StringBuilder(UiConstants.GAME_LEVEL4_PAGE);
		GameLevelController.hpValue = this.hpValue;
		GameLevelController.scoreValue = this.scoreValue;
		GameLevelController.updateHpValue();
		GameLevelController.updateScoreValue();
		GameLevelController.currentLevel = 3;
		GameLevelController.shipType = ShipType.VETERAN;
		GameLevelController.currentSessionScore = StageInitializer.gameDataCookie.getGameSessionDTO().getSessionScore();
	}

	public void handleSpaceInitialization(){
		spaceShip = null;
		spaceShip = new VeteranSpaceShip(StageInitializer.gameDataCookie.getGameSessionDTO().getShipHealth());
		//spaceShip.changeIconofSpaceShip();
		spaceShip.setMouseDraggableObject();
		spaceShip.autofire(anchorPane);
	}

	public void handleVirusInitialization(){
		levelViruses.clear();
		levelViruses = new ArrayList<>();
		for(int i=1; i<11 ; ++i){
			Virus virus;
			switch (i){
				case 1:
					virus = new MediumVirus(100 , 100);
					break;
				case 2:
					virus = new HardVirus(200, 100);
					break;
				case 3:
					virus = new HardVirus(300, 100);
					break;
				case 4:
					virus = new EasyVirus(400, 100);
					break;
				case 5:
					virus = new MediumVirus(500, 100);
					break;
				case 6:
					virus = new EasyVirus(100, 200);
					break;
				case 7:
					virus = new MediumVirus(200, 200);
					break;
				case 8:
					virus = new HardVirus(300, 200);
					break;
				case 9:
					virus = new EasyVirus(400, 200);
					break;
				case 10:
					virus = new HardVirus(500, 200);
					break;
				default:
					// shouldn't execute below line
					virus = new EasyVirus(300, 20);
			}
			virus.virusAutoMove();
			virus.virusAutoFire(anchorPane);
			levelViruses.add(virus);
			anchorPane.getChildren().add(virus);
		}
	}
}