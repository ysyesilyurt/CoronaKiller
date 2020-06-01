package com.coronakiller.ui.controller.level;

import com.coronakiller.ui.constants.GeneralConstants;
import com.coronakiller.ui.model.ShipType;
import com.coronakiller.ui.model.spaceship.BigGunsSpaceShip;
import com.coronakiller.ui.model.virus.BigBoss;
import com.coronakiller.ui.model.virus.Virus;
import com.coronakiller.ui.service.RequestService;
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
import org.javatuples.Pair;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.coronakiller.ui.application.StageInitializer.gameDataCookie;
import static com.coronakiller.ui.constants.GeneralConstants.*;

public class GameLevel5Controller extends GameLevelController {

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

	private DataInputStream socketDataInputStream;
	private DataOutputStream socketDataOutputStream;

	/**
	 * Overwritten initialize method from Initializable interface. It is responsible for initializing ui related objects.
	 *
	 * @param url            original parameter of initialize
	 * @param resourceBundle original parameter of initialize
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		Pair<String, String> result = RequestService.startMatchmaking();
		String otherPlayerIpAddress = result.getValue0();
		if (otherPlayerIpAddress == null) {
			/* Queue is empty, added this player to the queue, waiting for matchmaking... */

//			// TODO: CHECK WHAT TO CHANGE IN BELOW INITIALIZATIONS
			isGameLevelFinished = false;
			handleVirusInitialization();
			handleSpaceInitialization();
			anchorPane.getChildren().add(spaceShip);
			nextLevel = null;
			nextLevel = new StringBuilder(GeneralConstants.DASHBOARD_PAGE);
			GameLevelController.hpValue = this.hpValue;
			GameLevelController.scoreValue = this.scoreValue;
			GameLevelController.alienHpValue = this.alienHpValue;
			GameLevelController.teammateHpValue = this.teammateHpValue;
			GameLevelController.updateHpValue();
			GameLevelController.updateScoreValue();
			this.updateTeammateHpValue();
			this.updateAlienHpValue();
			GameLevelController.currentLevel = 5;
			GameLevelController.shipType = ShipType.BIG_GUNS;
			GameLevelController.currentSessionScore = gameDataCookie.getGameSessionDTO().getSessionScore();
			GameLevelController.currentPane = this.anchorPane;

			waitForMatchmaking();
		} else {
			/* Matched with a player that was already in the queue */

//			// TODO: CHECK WHAT TO CHANGE IN BELOW INITIALIZATIONS
			isGameLevelFinished = false;
			handleVirusInitialization();
			handleSpaceInitialization();
			anchorPane.getChildren().add(spaceShip);
			nextLevel = null;
			nextLevel = new StringBuilder(GeneralConstants.DASHBOARD_PAGE);
			GameLevelController.hpValue = this.hpValue;
			GameLevelController.scoreValue = this.scoreValue;
			GameLevelController.alienHpValue = this.alienHpValue;
			GameLevelController.teammateHpValue = this.teammateHpValue;
			GameLevelController.updateHpValue();
			GameLevelController.updateScoreValue();
			this.updateTeammateHpValue();
			this.updateAlienHpValue();
			GameLevelController.currentLevel = 5;
			GameLevelController.shipType = ShipType.BIG_GUNS;
			GameLevelController.currentSessionScore = gameDataCookie.getGameSessionDTO().getSessionScore();
			GameLevelController.currentPane = this.anchorPane;

			connectToMatchmakedPlayer(otherPlayerIpAddress);
		}
	}

	private void waitForMatchmaking() {
		try {
			ServerSocket serverSocket = new ServerSocket(SOCKET_PORT); // TODO: 0.0.0.0
			/* Player waits for matchmaking */
			Socket otherPlayerSocket = serverSocket.accept();

			/* Other player has connected so prepare for the start of the final level! */
			socketDataInputStream = new DataInputStream(otherPlayerSocket.getInputStream());
			socketDataOutputStream = new DataOutputStream(otherPlayerSocket.getOutputStream());
			startListening();
			startSendingInfoPeriodically();

			// TODO: maybe show you're connecting.. falan filan
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void connectToMatchmakedPlayer(String otherPlayerIP) {
		Socket clientSocket = new Socket();
		try {
			clientSocket.connect(new InetSocketAddress(otherPlayerIP, SOCKET_PORT));
			socketDataInputStream = new DataInputStream(clientSocket.getInputStream());
			socketDataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
			startListening();
			startSendingInfoPeriodically();

			// TODO: maybe show you're connecting.. falan filan
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void startListening() {
		Thread listenThread = new Thread(() -> {
			try {
				while (true) {
					/* Listen from the socket's input stream indefinitely, learn about updates on the other
					player's side and update variables as needed. */
//					double x = socketDataInputStream.readDouble();
//					double y = socketDataInputStream.readDouble();
//					spaceShip2.setX(x);
//					spaceShip2.setY(y);
					System.out.println(socketDataInputStream.readUTF());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		});
		listenThread.start();
	}


	private void startSendingInfoPeriodically() {
		Thread sendThread = new Thread(() -> {
			while (true) {
				try {
					/* Write to socket's output stream periodically to let other
					player know about the updates on this side */
//					String a = flag ? "HEY THIS IS SECOND APP SENDING INFO" : "HEY THIS IS FIRST APP SENDING INFO";
//					socketDataOutputStream.writeUTF(a);
//					Thread.sleep(1000);
					String dataToSend = String.format(MULTIPLAYER_SEND_INFO_FORMAT,
							gameDataCookie.getPlayerDTO().getUsername(), gameDataCookie.getGameSessionDTO().getSessionScore(),
							gameDataCookie.getGameSessionDTO().getShipHealth(), "SPACESHIP-X", "SPACESHIP-Y"); // TODO: CHANGE SKOR VE SHIP COOKIE'DEN ALDIM?
					socketDataOutputStream.writeUTF(dataToSend);
					Thread.sleep(MULTIPLAYER_SEND_INFO_PERIOD);
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
					return;
				}
			}
		});
		sendThread.start();
	}

	/**
	 * Spaceship of the level and it's specifications are done in this method.
	 */
	public void handleSpaceInitialization() {
		spaceShip = null;
		spaceShip = new BigGunsSpaceShip(gameDataCookie.getGameSessionDTO().getShipHealth());
		spaceShip.changeIconofSpaceShip();
		spaceShip.setMouseDraggableObject();
		spaceShip.autofire(anchorPane);
	}

	/**
	 * Viruses and their specifications are done in this method.
	 */
	public void handleVirusInitialization() {
		levelViruses.clear();
		levelViruses = new ArrayList<>();
		Virus virus = new BigBoss(300, 100);
		virus.changeIconOfVirus();
		virus.virusAutoMove();
		virus.virusAutoFire(anchorPane);
		levelViruses.add(virus);
		anchorPane.getChildren().add(virus);
	}

	//TODO : player with higher score also need to get K points bonus.

	public void onClickGoDash(ActionEvent event) throws IOException {
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent dashboardPage = FXMLLoader.load(getClass().getClassLoader().getResource(GeneralConstants.DASHBOARD_PAGE));
		Scene scene = new Scene(dashboardPage, 600, 800);
		currentStage.setScene(scene);
		currentStage.show();
	}

}
