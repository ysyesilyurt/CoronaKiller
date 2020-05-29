package com.coronakiller.ui.model.bullet;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import static com.coronakiller.ui.constants.GameConstants.*;
import static com.coronakiller.ui.constants.GameConstants.VIRUS_BULLET1_VELOCITY;

public class VirusBullet2 extends VirusBullet{

	Image virusBullet2Icon = new Image(VIRUS_BULLET2_ICON_URL);
	/**
	 * Constructor method of the SpaceShipBullet1 object.
	 * @param xPosition position on the x axis
	 * @param yPosition position of the y axis
	 */
	public VirusBullet2(double xPosition, double yPosition){
		super(xPosition, yPosition, VIRUS_BULLET2_WIDTH, VIRUS_BULLET2_HEIGHT, VIRUS_BULLET2_DAMAGE, VIRUS_BULLET2_VELOCITY);
	}

	@Override
	public void changeIconOfBullet() {
		this.setFill(new ImagePattern(virusBullet2Icon));
	}
}
