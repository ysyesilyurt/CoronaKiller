package com.coronakiller.ui.model.bullet;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import static com.coronakiller.ui.constants.GameConstants.*;

public class VirusBullet1 extends VirusBullet{

	/**
	 * Constructor method of the SpaceShipBullet1 object.
	 * @param xPosition position on the x axis
	 * @param yPosition position of the y axis
	 */
	public VirusBullet1(double xPosition, double yPosition){
		super(xPosition, yPosition, VIRUS_BULLET1_WIDTH, VIRUS_BULLET1_HEIGHT, VIRUS_BULLET1_DAMAGE, VIRUS_BULLET1_VELOCITY);
	}

	@Override
	public void changeIconOfBullet() {
		Image virusBullet1Icon = new Image(VIRUS_BULLET1_ICON_URL);
		this.setFill(new ImagePattern(virusBullet1Icon));
	}
}
