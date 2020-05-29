package com.coronakiller.ui.model.spaceship;

import com.coronakiller.ui.controller.level.GameLevelController;
import com.coronakiller.ui.model.bullet.SpaceShipBullet3;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;

import static com.coronakiller.ui.constants.GameConstants.*;

public class BigGunsSpaceShip extends SpaceShip{

	private Timeline autofireTimeline;
	Image spaceshipIcon = new Image(BIG_GUNS_SPACESHIP_ICON_URL);

	public BigGunsSpaceShip(int currentHealth) {
		super(BIG_GUNS_SPACESHIP_WIDTH, BIG_GUNS_SPACESHIP_HEIGHT, currentHealth);
	}

	@Override
	public void changeIconofSpaceShip(){
		this.setFill(new ImagePattern(spaceshipIcon));
	}

	@Override
	public void autofire(Pane currentPane){
		autofireTimeline = new Timeline(
				new KeyFrame(Duration.millis(400), e -> {
					SpaceShipBullet3 bullet = new SpaceShipBullet3(this.getX(), this.getY());
					SpaceShipBullet3 bullet2 = new SpaceShipBullet3(this.getX() + this.getWidth(), this.getY());
					bullet.changeIconOfBullet();
					bullet2.changeIconOfBullet();
					currentPane.getChildren().addAll(bullet, bullet2);
					bullet.moveBullet(currentPane);
					bullet2.moveBullet(currentPane);
					GameLevelController.updateAlienHpValue();
					GameLevelController.updateTeammateHpValue();
				}));
		autofireTimeline.setCycleCount(Timeline.INDEFINITE);
		autofireTimeline.play();
	}

	@Override
	public void stopFire(){
		autofireTimeline.stop();
	}
}
