package com.coronakiller.ui.model.bullet;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import static com.coronakiller.ui.constants.GameConstants.*;

public class SpaceShipBullet2 extends SpaceShipBullet {

	public SpaceShipBullet2(double xPosition, double yPosition){
		super(xPosition, yPosition, SPACESHIP_BULLET2_WIDTH, SPACESHIP_BULLET2_HEIGHT, SPACESHIP_BULLET2_DAMAGE, SPACESHIP_BULLET2_VELOCITY);
	}

	@Override
	public void changeIconOfBullet() {
		Image spaceshipBullet2Icon = new Image(SPACESHIP_BULLET2_ICON_URL);
		this.setFill(new ImagePattern(spaceshipBullet2Icon));
	}
}
