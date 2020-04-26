package com.coronakiller.ui.controller.level;

import com.coronakiller.ui.constants.UiConstants;
import com.coronakiller.ui.model.SpaceShip;
import com.coronakiller.ui.model.virus.EasyVirus;
import com.coronakiller.ui.model.virus.MediumVirus;
import com.coronakiller.ui.model.virus.Virus;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import lombok.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.coronakiller.ui.constants.GameConstants.*;

@Setter
@Getter
public class GameLevel1Controller implements Initializable {

	@FXML
	public AnchorPane anchorPane;

	// TODO : handle lombok issue
	public static SpaceShip spaceShip;
	public static ArrayList<Virus> levelViruses = new ArrayList<>();

	public static void levelSuccessfullyCompleted() {
		System.out.println("LEVEL SUCCESSFULLY COMPLETED");
	}

	public static void levelFailed() {
		System.out.println("LEVEL FAILED");
	}


	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		handleVirusInitialization();
		handleSpaceInitialization();
		anchorPane.getChildren().add(spaceShip);
	}

	public void handleSpaceInitialization(){
		spaceShip = new SpaceShip(INITIAL_SPACESHIP_X_POSITION,
				INITIAL_SPACESHIP_Y_POSITION,
				SPACESHIP_WIDTH,
				SPACESHIP_HEIGHT);
		//spaceShip.changeIconofSpaceShip();
		spaceShip.setMouseDraggableObject();
		spaceShip.autofire(anchorPane);
	}

	public void handleVirusInitialization(){
		 for(int i=1; i<6 ; ++i){
		 	MediumVirus virus = new MediumVirus(100*i, 50);
		 	virus.virusAutoMove();
		 	virus.virusAutoFire(anchorPane);
		 	levelViruses.add(virus);
		 	anchorPane.getChildren().add(virus);
		 }
	}
}
