package com.coronakiller.ui.controller.level;

import com.coronakiller.ui.application.StageInitializer;
import com.coronakiller.ui.model.GameSession;
import com.coronakiller.ui.model.ShipType;
import com.coronakiller.ui.model.spaceship.BigGunsSpaceShip;
import com.coronakiller.ui.model.spaceship.SpaceShip;
import com.coronakiller.ui.model.virus.Virus;
import com.coronakiller.ui.service.RequestService;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.coronakiller.ui.application.StageInitializer.gameDataCookie;
import static com.coronakiller.ui.constants.GeneralConstants.MATCHMAKING_BODY;
import static com.coronakiller.ui.constants.GeneralConstants.MATCHMAKING_HEADING;
import static com.coronakiller.ui.controller.level.GameLevel5Controller.otherPlayerSpaceshipHealth;

/**
 * This class is the general type of level.It keeps common level elements and other level classes extends this class.
 */
@Slf4j
public abstract class GameLevelController implements Initializable {

	public static SpaceShip spaceShip;
	public static ArrayList<Virus> levelViruses = new ArrayList<>();
	public static boolean isGameLevelFinished = false;
	public static StringBuilder nextLevel = new StringBuilder();
	public static long currentSessionScore = gameDataCookie.getGameSessionDTO().getSessionScore();
	public static int currentLevel;
	public static ShipType shipType;
	public static BigGunsSpaceShip spaceShip2;

	public static Pane currentPane;
	public static Text hpValue;
	public static Text scoreValue;
	/* For level 5*/
	public static Text teammateHpValue;
	public static Text alienHpValue;
	public static Text nameOfTeammate;

	private static JFXDialog gameFinishedDialog = new JFXDialog();
	private static JFXDialogLayout gameFinishedLayout = new JFXDialogLayout();

	@Override
	public abstract void initialize(URL url, ResourceBundle resourceBundle);

	public abstract void handleSpaceInitialization();

	public abstract void handleVirusInitialization();

	public static void updateHpValue() {
		hpValue.setText(String.valueOf(spaceShip.getCurrentHealth()));
	}

	public static void updateScoreValue() {
		scoreValue.setText(String.valueOf(GameLevelController.currentSessionScore));
	}

	public static void updateTeammateHpValue() {
		//teammateHpValue.setText(String.valueOf(otherPlayerSpaceshipHealth));
	}

	public static void updateAlienHpValue() {
		//alienHpValue.setText(String.valueOf(levelViruses.get(0).getVirusHealth()));
	}

	/**
	 * This is the method when user successfully finish game.
	 *
	 * @throws IOException
	 */
	public static void finishLevelSuccessfully() throws IOException {
		GameLevelController.spaceShip.stopFire();
		if(spaceShip2 != null) {
			GameLevelController.spaceShip2.stopFire();
			displayFinish();
		}

		GameLevelController.isGameLevelFinished = true;
		GameSession gameSessionDTO = new GameSession(
				GameLevelController.currentLevel + 1,
				(long) GameLevelController.currentSessionScore,
				GameLevelController.spaceShip.getCurrentHealth(),
				GameLevelController.shipType
		);
		if (GameLevelController.currentLevel == 5) {
			Pair<Boolean, String> result = RequestService.finishGameSession(gameSessionDTO);
			if (!result.getValue0()) {
				log.error("Finish Game Session request in Game Level Controller returned false!");
			}
		} else {
			Pair<Boolean, String> result = RequestService.updateGameSession(gameSessionDTO);
			if (!result.getValue0()) {
				log.error("Update Game Session request in Game Level Controller returned false!");
			}
		}
		StageInitializer.gameDataCookie.setGameSessionDTO(gameSessionDTO);
		Stage currentStage = (Stage) currentPane.getScene().getWindow();
		Parent nextPage = FXMLLoader.load(Objects.requireNonNull(GameLevelController.class.getClassLoader().getResource(String.valueOf(GameLevelController.nextLevel))));
		Scene scene = new Scene(nextPage, 600, 800);
		currentStage.setScene(scene);
		currentStage.show();
	}

	public static void cheatImplementation() {
		//TODO : implement cheat
	}

	private static void displayFinish() {

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("CONGRATS!");
		alert.setHeaderText(null);
		StringBuilder stringBuilder = new StringBuilder("You have completed the game successfully with your friend!\n");
		stringBuilder.append("You have earned" + currentSessionScore + "many points!\n" );
		stringBuilder.append(GameLevel5Controller.otherPlayerUsername + " has earned" + GameLevel5Controller.otherPlayerSessionScore + "many points!\n");
		alert.setContentText(stringBuilder.toString());
		alert.show();
	}
}
