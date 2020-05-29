package com.coronakiller.ui.model.bullet;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import static com.coronakiller.ui.constants.GameConstants.*;

/**
 * This class holds second type of bullet which can be shot from spaceship.
 */
public class SpaceShipBullet2 extends SpaceShipBullet {

	Image spaceshipBullet2Icon = new Image(SPACESHIP_BULLET2_ICON_URL);

	/**
	 * Constructor method of second type of bullet.
	 * @param xPosition
	 * @param yPosition
	 */
	public SpaceShipBullet2(double xPosition, double yPosition){
		super(xPosition, yPosition, SPACESHIP_BULLET2_WIDTH, SPACESHIP_BULLET2_HEIGHT, SPACESHIP_BULLET2_DAMAGE, SPACESHIP_BULLET2_VELOCITY);
	}

	@Override
	public void changeIconOfBullet() {
		this.setFill(new ImagePattern(spaceshipBullet2Icon));
	}
}
