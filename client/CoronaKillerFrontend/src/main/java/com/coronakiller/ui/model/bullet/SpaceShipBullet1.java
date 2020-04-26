package com.coronakiller.ui.model.bullet;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import lombok.Getter;
import lombok.Setter;

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
	 */
	public SpaceShipBullet1(double xPosition, double yPosition){
		super(xPosition, yPosition, SPACESHIP_BULLET1_WIDTH, SPACESHIP_BULLET1_HEIGHT, SPACESHIP_BULLET1_DAMAGE, SPACESHIP_BULLET1_VELOCITY);
	}

	@Override
	public void changeIconOfBullet() {
		Image spaceshipBullet1Icon = new Image(SPACESHIP_BULLET1_ICON_URL);
		this.setFill(new ImagePattern(spaceshipBullet1Icon));
	}
}
