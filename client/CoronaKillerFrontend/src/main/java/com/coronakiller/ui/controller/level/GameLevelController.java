package com.coronakiller.ui.controller.level;

import com.coronakiller.ui.application.StageInitializer;
import com.coronakiller.ui.model.GameSession;
import com.coronakiller.ui.model.ShipType;
import com.coronakiller.ui.model.spaceship.SpaceShip;
import com.coronakiller.ui.model.virus.Virus;
import com.coronakiller.ui.service.RequestService;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.javatuples.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.coronakiller.ui.application.StageInitializer.gameDataCookie;

/**
 * This class is the general type of level.It keeps common level elements and other level classes extends this class.
 */
public abstract class GameLevelController implements Initializable {

	public static SpaceShip spaceShip;
	public static ArrayList<Virus> levelViruses = new ArrayList<>();
	public static boolean isGameLevelFinished = false;
	public static StringBuilder nextLevel = new StringBuilder();
	public static long currentSessionScore = gameDataCookie.getGameSessionDTO().getSessionScore();
	public static int currentLevel;
	public static ShipType shipType;

	public static Pane currentPane;
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

	/**
	 * This is the method when user successfully finish game.
	 * @throws IOException
	 */
	public static void finishLevelSuccessfully() throws IOException {
		GameLevelController.spaceShip.stopFire();
		GameLevelController.isGameLevelFinished = true;
		GameSession gameSessionDTO = new GameSession(
				GameLevelController.currentLevel + 1,
				(long) GameLevelController.currentSessionScore,
				GameLevelController.spaceShip.getCurrentHealth(),
				GameLevelController.shipType
		);
		if(GameLevelController.currentLevel == 5){
			Pair<Boolean, String> result = RequestService.finishGameSession(gameSessionDTO);
		}
		else if(GameLevelController.currentLevel == 4){
			Pair<Boolean, String> result = RequestService.updateGameSession(gameSessionDTO);
			//TODO : wait for matchmaking here!!!
		}
		else{
			Pair<Boolean, String> result = RequestService.updateGameSession(gameSessionDTO);
		}
		StageInitializer.gameDataCookie.setGameSessionDTO(gameSessionDTO);
		Stage currentStage = (Stage) currentPane.getScene().getWindow();
		Parent leaderBoardPage = FXMLLoader.load(Objects.requireNonNull(GameLevelController.class.getClassLoader().getResource(String.valueOf(GameLevelController.nextLevel))));
		Scene scene = new Scene(leaderBoardPage, 600, 800);
		currentStage.setScene(scene);
		currentStage.show();
	}

	public static void cheatImplementation(){
		//TODO : implement cheat
	}

}
