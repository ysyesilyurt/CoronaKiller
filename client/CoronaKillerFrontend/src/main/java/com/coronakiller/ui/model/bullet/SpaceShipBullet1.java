package com.coronakiller.ui.model.bullet;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

import static com.coronakiller.ui.constants.UiConstants.BULLET1_VELOCITY;

@Getter
@Setter
/**
 * This class represents first type of bullet that can be shot by spaceship(doctor).
 */
public class SpaceShipBullet1 extends Rectangle {

	private int damage;
	private Timeline bulletTimeline;

	/**
	 * Constructor method of the SpaceShipBullet1 object.
	 * @param xPosition position on the x axis
	 * @param yPosition position of the y axis
	 * @param width width of the SpaceShipBullet1
	 * @param height height of the SpaceShipBullet1
	 */
	public SpaceShipBullet1(double xPosition, double yPosition, int width, int height){
		super(xPosition, yPosition, width, height);
		this.damage = 1;
	}

	public void moveBullet(){
		this.bulletTimeline = new Timeline(
				new KeyFrame( Duration.millis(10), e ->{
					this.setY(this.getY()- BULLET1_VELOCITY);
				})
		);
		this.bulletTimeline.setCycleCount(Timeline.INDEFINITE);
		this.bulletTimeline.play();
	}
}
