package com.coronakiller.ui.model.spaceship;

import com.coronakiller.ui.model.bullet.SpaceShipBullet1;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

import static com.coronakiller.ui.constants.GameConstants.*;

@Getter
@Setter
public abstract class SpaceShip extends Rectangle {

	private int currentHealth;
	private Timeline autofireTimeline;

	/**
	 * Constructor method of the SpaceShip object.
	 * @param width width of the spaceship
	 * @param height height of the spaceship
	 */
	public SpaceShip(int width, int height, int currentHealth){
		super(INITIAL_SPACESHIP_X_POSITION, INITIAL_SPACESHIP_Y_POSITION, width, height);
		this.currentHealth = currentHealth;
	}

	public int getShot(int damage){
		this.currentHealth -= damage;
		return this.currentHealth;
	}

	public void setMouseDraggableObject(){
		this.setOnMouseDragged(me -> {
			this.setX(me.getX() - this.getWidth()/2);
			this.setY(me.getY() - this.getHeight()/2);
		});
	}

	public abstract void changeIconofSpaceShip();

	public abstract void autofire(Pane currentPane);
}
