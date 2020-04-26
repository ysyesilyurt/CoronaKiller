package com.coronakiller.ui.model.bullet;

import com.coronakiller.ui.controller.level.GameLevel1Controller;
import com.coronakiller.ui.model.virus.Virus;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Iterator;

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

	public void moveBullet(Pane currentPane){
		this.bulletTimeline = new Timeline(
				new KeyFrame( Duration.millis(10), e ->{
					this.setY(this.getY() + bulletVelocity);
					this.checkCollision(currentPane);
				})
		);
		this.bulletTimeline.setCycleCount(Timeline.INDEFINITE);
		this.bulletTimeline.play();
	}

	public void checkCollision(Pane currentPane){
		if(GameLevel1Controller.spaceShip.getBoundsInParent().intersects(this.getBoundsInParent())){
			this.bulletTimeline.stop();
			currentPane.getChildren().remove(this);
			if(GameLevel1Controller.spaceShip.getShot(this.bulletDamage) <= 0){
				GameLevel1Controller.levelFailed();
			}
		}
	}

	public abstract void changeIconOfBullet();

}
