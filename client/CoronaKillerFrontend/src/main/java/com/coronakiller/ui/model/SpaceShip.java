package com.coronakiller.ui.model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static com.coronakiller.ui.constants.UiConstants.SPACESHIP_ICON_URL;

@Getter
@Setter
public class SpaceShip extends Rectangle {

	private int currentHealth;

	/**
	 * Constructor method of the SpaceShip object.
	 * @param xPosition position on the x axis
	 * @param yPosition position of the y axis
	 * @param width width of the spaceship
	 * @param height height of the spaceship
	 */
	public SpaceShip(int xPosition, int yPosition, int width, int height){
		super(xPosition, yPosition, width, height);
		this.currentHealth = 100;
	}

	public void setMouseDraggableObject(){
		this.setOnMouseDragged(me -> {
			this.setX(me.getX() - this.getWidth()/2);
			this.setY(me.getY() - this.getHeight()/2);
		});
	}

	public void changeIconofSpaceShip(){
		Image spaceshipIcon = new Image(SPACESHIP_ICON_URL);
		this.setFill(new ImagePattern(spaceshipIcon));
	}

}
