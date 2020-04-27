package com.coronakiller.ui.model.bullet;

import com.coronakiller.ui.application.StageInitializer;
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
import lombok.Getter;
import lombok.Setter;
import org.javatuples.Pair;

import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

@Getter
@Setter
public abstract class SpaceShipBullet extends Rectangle {

	private int bulletDamage;
	private int bulletVelocity;
	private Timeline bulletTimeline;

	/**
	 * Constructor method of the Bullet object.
	 * @param xPosition position on the x axis
	 * @param yPosition position of the y axis
	 * @param width width of the Bullet
	 * @param height height of the Bullet
	 */
	public SpaceShipBullet(double xPosition, double yPosition, int width, int height, int damage, int bulletVelocity){
		super(xPosition, yPosition, width, height);
		this.bulletDamage = damage;
		this.bulletVelocity = bulletVelocity;
	}

	public void moveBullet(Pane currentPane){
		this.bulletTimeline = new Timeline(
				new KeyFrame( Duration.millis(10), e ->{
					this.setY(this.getY()- bulletVelocity);
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

	public void checkCollision(Pane currentPane) throws IOException {
		Iterator<Virus> virusIterator = GameLevel1Controller.levelViruses.iterator();
		while (virusIterator.hasNext()) {
			Virus virus = virusIterator.next();
			if(virus.getBoundsInParent().intersects(this.getBoundsInParent())){
				this.bulletTimeline.stop();
				currentPane.getChildren().remove(this);
				virus.getShot(this.bulletDamage);
				if(virus.getVirusHealth() <= 0){
					GameLevelController.currentSessionScore += virus.getPointsFromVirus();
					GameLevelController.updateScoreValue();
					virus.stopFireAndMove();
					currentPane.getChildren().remove(virus);
					virusIterator.remove();
				}
				break;
			}
		}

		if(GameLevelController.levelViruses.isEmpty()) {
			this.bulletTimeline.stop();
			if(!GameLevelController.isGameLevelFinished) {
				GameLevelController.finishLevelSuccessfully();
			}
		}
	}

	public abstract void changeIconOfBullet();
}
