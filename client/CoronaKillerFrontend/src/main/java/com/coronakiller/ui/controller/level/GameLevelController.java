package com.coronakiller.ui.controller.level;

import com.coronakiller.ui.model.ShipType;
import com.coronakiller.ui.model.spaceship.SpaceShip;
import com.coronakiller.ui.model.virus.Virus;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public abstract class GameLevelController implements Initializable {

	public static SpaceShip spaceShip;
	public static ArrayList<Virus> levelViruses = new ArrayList<>();
	public static boolean isGameLevelFinished = false;
	public static StringBuilder nextLevel = new StringBuilder();
	public static long currentSessionScore = 0;
	public static int currentLevel;
	public static ShipType shipType;

	public static Text hpValue;
	public static Text scoreValue;

	@Override
	public abstract void initialize(URL url, ResourceBundle resourceBundle);

	public abstract void handleSpaceInitialization();

	public abstract void handleVirusInitialization();

	public static void updateHpValue(){
		hpValue.setText(String.valueOf(spaceShip.getCurrentHealth()));
	}

	public static void updateScoreValue(){scoreValue.setText(String.valueOf(GameLevelController.currentSessionScore));}
}
