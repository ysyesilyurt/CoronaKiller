package com.coronakiller.ui.model.bullet;

import com.coronakiller.ui.controller.level.GameLevel1Controller;
import com.coronakiller.ui.model.virus.Virus;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

import java.util.Iterator;

import static com.coronakiller.ui.constants.GameConstants.SPACESHIP_BULLET1_DAMAGE;

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
					this.checkCollision(currentPane);
				})
		);
		this.bulletTimeline.setCycleCount(Timeline.INDEFINITE);
		this.bulletTimeline.play();
	}

	public void checkCollision(Pane currentPane){
		Iterator<Virus> virusIterator = GameLevel1Controller.levelViruses.iterator();
		while (virusIterator.hasNext()) {
			Virus virus = virusIterator.next();
			if(virus.getBoundsInParent().intersects(this.getBoundsInParent())){
				this.bulletTimeline.stop();
				currentPane.getChildren().remove(this);
				virus.getShot(this.bulletDamage);
				if(virus.getVirusHealth() <= 0){
					virus.stopFireAndMove();
					currentPane.getChildren().remove(virus);
					virusIterator.remove();
				}
				break;
			}
		}
		//TODO: change gameLevel1 to gameLevel
		if(GameLevel1Controller.levelViruses.isEmpty()) GameLevel1Controller.levelCompleted();
	}

	public abstract void changeIconOfBullet();


}
