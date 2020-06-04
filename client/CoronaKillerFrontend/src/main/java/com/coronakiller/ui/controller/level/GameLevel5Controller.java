package com.coronakiller.ui.controller.level;

import com.coronakiller.ui.constants.GameConstants;
import com.coronakiller.ui.constants.GeneralConstants;
import com.coronakiller.ui.model.ShipType;
import com.coronakiller.ui.model.spaceship.BigGunsSpaceShip;
import com.coronakiller.ui.model.virus.BigBoss;
import com.coronakiller.ui.model.virus.Virus;
import com.coronakiller.ui.service.RequestService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXSpinner;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.javatuples.Pair;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import static com.coronakiller.ui.application.StageInitializer.gameDataCookie;
import static com.coronakiller.ui.constants.GeneralConstants.*;

public class GameLevel5Controller extends GameLevelController {

	@FXML
	public StackPane stackPane;

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

	@FXML
	public JFXSpinner loadingSpinner;

	/* Socket streams for listening and sending threads */
	private DataInputStream socketDataInputStream;
	private DataOutputStream socketDataOutputStream;

	/* Matchmaking Progress Variables */
	private JFXDialog matchmakingInProgressDialog = new JFXDialog();
	private JFXDialogLayout matchmakingInProgressLayout = new JFXDialogLayout();

	/* Multiplayer transfer variables */
	public static volatile String otherPlayerUsername;
	public static volatile Integer otherPlayerSessionScore;
	public static volatile Integer otherPlayerSpaceshipHealth;
	public static volatile Double otherPlayerSpaceshipX;
	public static volatile Double otherPlayerSpaceshipY;

	private Timeline listenTimeline;
	private Timeline sendTimeline;

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
			waitForMatchmaking();
		} else {
			/* Matched with a player that was already in the queue */
			connectToMatchmakedPlayer(otherPlayerIpAddress);
		}
	}

	private void waitForMatchmaking() {
		Thread socketAcceptThread = new Thread(() -> {
			try {
				ServerSocket serverSocket = new ServerSocket(FIRST_PLAYER_SOCKET_PORT); // TODO: 0.0.0.0

				/* Player waits for matchmaking */
				Socket otherPlayerSocket = serverSocket.accept();
				/* Other player has connected so prepare for the start of the final level! */
				socketDataInputStream = new DataInputStream(otherPlayerSocket.getInputStream());
				socketDataOutputStream = new DataOutputStream(otherPlayerSocket.getOutputStream());
				startSendingInfoPeriodically();
				startListening();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						try {
							loadingSpinner.setVisible(false);
							anchorPane.setDisable(false);
							matchmakingInProgressDialog.close();
							/* Initially send crucial information immediately as socket connections are established */
							TimeUnit.MILLISECONDS.sleep(MATCHMAKING_INITIAL_OFFSET_MSEC);
							startGame();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				});
			} catch (IOException e) {
				e.printStackTrace(); // TODO FAIL LOG
			}
		});
		socketAcceptThread.start();
		displayMatchmakingLoading();
	}

	private void connectToMatchmakedPlayer(String otherPlayerIP) {
		Socket clientSocket = new Socket();
		try {
			clientSocket.connect(new InetSocketAddress(otherPlayerIP, FIRST_PLAYER_SOCKET_PORT));
			/* Initially send crucial information immediately as socket connections are established */
			socketDataInputStream = new DataInputStream(clientSocket.getInputStream());
			socketDataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
			startSendingInfoPeriodically();
			startListening();
			TimeUnit.MILLISECONDS.sleep(MATCHMAKING_INITIAL_OFFSET_MSEC);
			startGame();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace(); // TODO FAIL LOG
		}
	}

	private void displayMatchmakingLoading() {
		loadingSpinner.setVisible(true);
		anchorPane.setDisable(true);
		matchmakingInProgressLayout.setHeading(new Text(MATCHMAKING_HEADING));
		matchmakingInProgressLayout.setBody(new Text(MATCHMAKING_BODY));
		matchmakingInProgressDialog.setContent(matchmakingInProgressLayout);
		matchmakingInProgressDialog.setDialogContainer(stackPane);
		matchmakingInProgressDialog.setOverlayClose(false);
		matchmakingInProgressDialog.show();
	}

	private void startGame() {
		Thread countdownThread = new Thread(() -> {
			try {
				TimeUnit.SECONDS.sleep(MATCHMAKING_COUNTDOWN_SEC);
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						matchmakingInProgressDialog.close();
						initializeGame();
					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace(); // TODO FAIL LOG
			}
		});
		countdownThread.start();
		String readyBody = String.format(MATCH_READY_BODY, otherPlayerUsername, otherPlayerSessionScore, otherPlayerSpaceshipHealth);
		Text body = new Text(readyBody);
		body.setStyle("-fx-font-weight: regular");
		matchmakingInProgressLayout = new JFXDialogLayout();
		matchmakingInProgressLayout.setHeading(new Text(MATCH_READY_HEADING));
		matchmakingInProgressLayout.setBody(body);
		matchmakingInProgressDialog = new JFXDialog();
		matchmakingInProgressDialog.setContent(matchmakingInProgressLayout);
		matchmakingInProgressDialog.setDialogContainer(stackPane);
		matchmakingInProgressDialog.setOverlayClose(false);
		matchmakingInProgressDialog.show();
	}

	private void initializeGame() {
		isGameLevelFinished = false;
		handleVirusInitialization();
		handleSpaceInitialization();
		anchorPane.getChildren().addAll(spaceShip2,spaceShip);
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
	}

	private void startListening() {
		listenTimeline = new Timeline(
				new KeyFrame(Duration.millis(20), e ->{
					/* Listen from the socket's input stream indefinitely, learn about updates on the other
					player's side and update variables as needed. */
					String[] otherPlayerInfo = new String[0];
					try {
						otherPlayerInfo = socketDataInputStream.readUTF().split(":");
					} catch (IOException ioException) {
						ioException.printStackTrace();
					}
					otherPlayerUsername = otherPlayerInfo[0];
					otherPlayerSessionScore = Integer.parseInt(otherPlayerInfo[1]);
					otherPlayerSpaceshipHealth = Integer.parseInt(otherPlayerInfo[2]);
					//if(spaceShip2 != null) {
					//	spaceShip2.setX(Double.parseDouble(otherPlayerInfo[3]));
					//	spaceShip2.setY(Double.parseDouble(otherPlayerInfo[4]));
					//}
					otherPlayerSpaceshipX = Double.parseDouble(otherPlayerInfo[3]);
					otherPlayerSpaceshipY = Double.parseDouble(otherPlayerInfo[4]);
				})
		);
		listenTimeline.setCycleCount(Timeline.INDEFINITE);
		listenTimeline.play();
		/*
		Thread listenThread = new Thread(() -> {
			try {
				while (true) {
					/* Listen from the socket's input stream indefinitely, learn about updates on the other
					player's side and update variables as needed. */ /*
					String[] otherPlayerInfo = socketDataInputStream.readUTF().split(":");
					otherPlayerUsername = otherPlayerInfo[0];
					otherPlayerSessionScore = Integer.parseInt(otherPlayerInfo[1]);
					otherPlayerSpaceshipHealth = Integer.parseInt(otherPlayerInfo[2]);
					//if(spaceShip2 != null) {
					//	spaceShip2.setX(Double.parseDouble(otherPlayerInfo[3]));
					//	spaceShip2.setY(Double.parseDouble(otherPlayerInfo[4]));
					//}
					otherPlayerSpaceshipX = Double.parseDouble(otherPlayerInfo[3]);
					otherPlayerSpaceshipY = Double.parseDouble(otherPlayerInfo[4]);
				}
			} catch (IOException e) {
				e.printStackTrace(); // TODO FAIL LOG
			}
		});
		listenThread.start();
		*/
	}


	private void startSendingInfoPeriodically() {
		sendTimeline = new Timeline(
				new KeyFrame(Duration.millis(20), e ->{
					String dataToSend = String.format(MULTIPLAYER_SEND_INFO_FORMAT,
							gameDataCookie.getPlayerDTO().getUsername(), gameDataCookie.getGameSessionDTO().getSessionScore(),
							gameDataCookie.getGameSessionDTO().getShipHealth(), spaceShip.getX(), spaceShip.getY()); // TODO: CHANGE SKOR VE SHIP COOKIE'DEN ALDIM?
					try {
						socketDataOutputStream.writeUTF(dataToSend);
					} catch (IOException ioException) {
						ioException.printStackTrace();
					}
				})
		);
		sendTimeline.setCycleCount(Timeline.INDEFINITE);
		sendTimeline.play();
		/*
		Thread sendThread = new Thread(() -> {
			while (true) {
				try {
					/* Write to socket's output stream periodically to let other
					player know about the updates on this side */ /*
					String dataToSend = String.format(MULTIPLAYER_SEND_INFO_FORMAT,
							gameDataCookie.getPlayerDTO().getUsername(), gameDataCookie.getGameSessionDTO().getSessionScore(),
							gameDataCookie.getGameSessionDTO().getShipHealth(), spaceShip.getX(), spaceShip.getY()); // TODO: CHANGE SKOR VE SHIP COOKIE'DEN ALDIM?
					socketDataOutputStream.writeUTF(dataToSend);
					TimeUnit.MILLISECONDS.sleep(MULTIPLAYER_SEND_INFO_PERIOD_MSEC);
				} catch (IOException | InterruptedException e) {
					e.printStackTrace(); // TODO FAIL LOG
					return;
				}
			}
		});
		sendThread.start();
		*/
	}

	/**
	 * Spaceship of the level and it's specifications are done in this method.
	 */
	public void handleSpaceInitialization() {
		spaceShip = null;
		spaceShip = new BigGunsSpaceShip(GameConstants.INITIAL_SPACESHIP_X_POSITION-50,
				GameConstants.INITIAL_SPACESHIP_Y_POSITION,
				gameDataCookie.getGameSessionDTO().getShipHealth());
		spaceShip.changeIconofSpaceShip();
		spaceShip.setMouseDraggableObject();
		spaceShip.autofire(anchorPane);

		spaceShip2 = new BigGunsSpaceShip(GameConstants.INITIAL_SPACESHIP_X_POSITION+50,
				GameConstants.INITIAL_SPACESHIP_Y_POSITION,
				otherPlayerSpaceshipHealth);
		spaceShip2.changeIconofSpaceShip2();
		spaceShip2.moveSecondSpaceship();
		spaceShip2.autofire(anchorPane);
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
