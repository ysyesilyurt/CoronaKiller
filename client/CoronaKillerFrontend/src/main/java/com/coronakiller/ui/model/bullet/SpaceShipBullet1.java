package com.coronakiller.ui.model.bullet;

import com.coronakiller.ui.controller.level.GameLevel1Controller;
import com.coronakiller.ui.model.virus.Virus;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;

import static com.coronakiller.ui.constants.GameConstants.*;

@Getter
@Setter
/**
 * This class represents first type of bullet that can be shot by spaceship(doctor).
 */
public class SpaceShipBullet1 extends SpaceShipBullet {

	/**
	 * Constructor method of the SpaceShipBullet1 object.
	 * @param xPosition position on the x axis
	 * @param yPosition position of the y axis
	 * @param width width of the SpaceShipBullet1
	 * @param height height of the SpaceShipBullet1
	 */
	public SpaceShipBullet1(double xPosition, double yPosition, int width, int height){
		super(xPosition, yPosition, width, height, SPACESHIP_BULLET1_DAMAGE, SPACESHIP_BULLET1_VELOCITY);
	}

	@Override
	public void changeIconOfBullet() {
		Image spaceshipBullet1Icon = new Image(SPACESHIP_BULLET1_ICON_URL);
		this.setFill(new ImagePattern(spaceshipBullet1Icon));
	}
}
