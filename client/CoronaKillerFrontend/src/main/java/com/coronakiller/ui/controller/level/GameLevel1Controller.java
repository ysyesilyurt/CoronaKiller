package com.coronakiller.ui.controller.level;

import com.coronakiller.ui.application.StageInitializer;
import com.coronakiller.ui.constants.UiConstants;
import com.coronakiller.ui.model.ShipType;
import com.coronakiller.ui.model.spaceship.RookieSpaceShip;
import com.coronakiller.ui.model.virus.EasyVirus;
import com.coronakiller.ui.model.virus.MediumVirus;
import com.coronakiller.ui.model.virus.Virus;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import static com.coronakiller.ui.application.StageInitializer.*;

@Setter
@Getter
public class GameLevel1Controller extends GameLevelController {

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
		nextLevel.append(UiConstants.GAME_LEVEL2_PAGE);
		GameLevelController.hpValue = this.hpValue;
		GameLevelController.scoreValue = this.scoreValue;
		GameLevelController.updateHpValue();
		GameLevelController.updateScoreValue();
		GameLevelController.currentLevel = 1;
		GameLevelController.shipType = ShipType.ROOKIE;
		GameLevelController.currentSessionScore = gameDataCookie.getGameSessionDTO().getSessionScore();
	}

	public void handleSpaceInitialization(){
		spaceShip = new RookieSpaceShip(gameDataCookie.getGameSessionDTO().getShipHealth());
		//spaceShip.changeIconofSpaceShip();
		spaceShip.setMouseDraggableObject();
		spaceShip.autofire(anchorPane);
	}

	public void handleVirusInitialization() {
		Random rand = new Random();
		for (int j = 1; j < 3; ++j) {
			int mediumVirusPosition = rand.nextInt(6);
			if(mediumVirusPosition == 0)
				mediumVirusPosition = 1;
			for (int i = 1; i < 6; ++i) {
				Virus virus;
				if(i == mediumVirusPosition){
					virus = new MediumVirus(100 * i, j*100);
				} else {
					virus = new EasyVirus(100 * i, j * 100);
				}
				virus.virusAutoMove();
				virus.virusAutoFire(anchorPane);
				levelViruses.add(virus);
				anchorPane.getChildren().add(virus);
			}
		}
	}
}
