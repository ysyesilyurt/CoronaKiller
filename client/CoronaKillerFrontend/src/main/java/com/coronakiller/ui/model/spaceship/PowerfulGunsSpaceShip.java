package com.coronakiller.ui.model.spaceship;

import com.coronakiller.ui.model.bullet.SpaceShipBullet1;
import com.coronakiller.ui.model.bullet.SpaceShipBullet2;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;

import static com.coronakiller.ui.constants.GameConstants.*;

public class PowerfulGunsSpaceShip extends SpaceShip{

	private Timeline autofireTimeline;
	/**
	 * Constructor method of the SpaceShip object.
	 * @param currentHealth
	 */
	public PowerfulGunsSpaceShip(int currentHealth) {
		super(POWERFUL_GUNS_SPACESHIP_WIDTH, POWERFUL_GUNS_SPACESHIP_HEIGHT, currentHealth);
	}

	@Override
	public void changeIconofSpaceShip(){
		Image spaceshipIcon = new Image(POWERFUL_GUNS_SPACESHIP_ICON_URL);
		this.setFill(new ImagePattern(spaceshipIcon));
	}

	@Override
	public void autofire(Pane currentPane){
		autofireTimeline = new Timeline(
				new KeyFrame(Duration.millis(450), e -> {
					SpaceShipBullet2 bullet = new SpaceShipBullet2(this.getX(), this.getY());
					SpaceShipBullet2 bullet2 = new SpaceShipBullet2(this.getX() + this.getWidth(), this.getY());
					currentPane.getChildren().addAll(bullet, bullet2);
					bullet.moveBullet(currentPane);
					bullet2.moveBullet(currentPane);
				}));
		autofireTimeline.setCycleCount(Timeline.INDEFINITE);
		autofireTimeline.play();
	}

	@Override
	public void stopFire(){
		autofireTimeline.stop();
	}
}
