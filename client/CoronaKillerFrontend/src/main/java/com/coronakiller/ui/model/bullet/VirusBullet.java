package com.coronakiller.ui.model.bullet;

import com.coronakiller.ui.constants.UiConstants;
import com.coronakiller.ui.controller.level.GameLevel1Controller;
import com.coronakiller.ui.controller.level.GameLevelController;
import com.coronakiller.ui.model.GameSession;
import com.coronakiller.ui.model.virus.Virus;
import com.coronakiller.ui.service.RequestService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.javatuples.Pair;

import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

import static com.coronakiller.ui.constants.UiConstants.DASHBOARD_PAGE;

public abstract class VirusBullet extends Rectangle {

	private int bulletDamage;
	private int bulletVelocity;
	private Timeline bulletTimeline;

	/**
	 * Constructor method of the VirusBullet object.
	 * @param xPosition position on the x axis
	 * @param yPosition position of the y axis
	 * @param width width of the Bullet
	 * @param height height of the Bullet
	 */
	public VirusBullet(double xPosition, double yPosition, int width, int height, int damage, int bulletVelocity){
		super(xPosition, yPosition, width, height);
		this.bulletDamage = damage;
		this.bulletVelocity = bulletVelocity;
	}

	/**
	 * Thanks to this method, our bullets can move.
	 * @param currentPane current pane value of the scene
	 */
	public void moveBullet(Pane currentPane){
		this.bulletTimeline = new Timeline(
				new KeyFrame( Duration.millis(20), e ->{
					this.setY(this.getY() + bulletVelocity);
					try {
						this.checkCollision(currentPane);
					} catch (IOException ioException) {
						ioException.printStackTrace();
					}
				})
		);
		this.bulletTimeline.setCycleCount(Timeline.INDEFINITE);
		this.bulletTimeline.play();
	}

	/**
	 * This method checks whether the virus bullet coincides with spaceship or not.
	 * If they are colliding, decrease bullet damage from spaceship and check for the end of the game.
	 * @param currentPane current pane value of the scene
	 * @throws IOException
	 */
	public void checkCollision(Pane currentPane) throws IOException {
		if(GameLevelController.spaceShip.getBoundsInParent().intersects(this.getBoundsInParent())){
			this.bulletTimeline.stop();
			currentPane.getChildren().remove(this);
			GameLevelController.updateHpValue();
			if(GameLevelController.spaceShip.getShot(this.bulletDamage) <= 0){
				if(!GameLevelController.isGameLevelFinished) {
					GameLevelController.isGameLevelFinished = true;
					GameSession gameSessionDTO = new GameSession(
							GameLevelController.currentLevel + 1,
							(long) GameLevelController.currentSessionScore,
							GameLevelController.spaceShip.getCurrentHealth(),
							GameLevelController.shipType
					);
					Pair<Boolean, String> result = RequestService.finishGameSession(gameSessionDTO);
					Stage currentStage = (Stage) currentPane.getScene().getWindow();
					Parent leaderBoardPage = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(DASHBOARD_PAGE)));
					Scene scene = new Scene(leaderBoardPage, 600, 800);
					currentStage.setScene(scene);
					currentStage.show();
				}
			}
		}
	}

	public abstract void changeIconOfBullet();

}
