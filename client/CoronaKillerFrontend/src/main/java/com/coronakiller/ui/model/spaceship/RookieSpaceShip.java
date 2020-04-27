package com.coronakiller.ui.model.spaceship;

import com.coronakiller.ui.model.bullet.SpaceShipBullet1;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;

import static com.coronakiller.ui.constants.GameConstants.*;

public class RookieSpaceShip extends SpaceShip {

	private Timeline autofireTimeline;

	public RookieSpaceShip(int currentHealth) {
		super(ROOKIE_SPACESHIP_WIDTH, ROOKIE_SPACESHIP_HEIGHT, currentHealth);
	}

	@Override
	public void changeIconofSpaceShip(){
		Image spaceshipIcon = new Image(ROOKIE_SPACESHIP_ICON_URL);
		this.setFill(new ImagePattern(spaceshipIcon));
	}

	@Override
	public void autofire(Pane currentPane){
		autofireTimeline = new Timeline(
				new KeyFrame(Duration.millis(500), e -> {
					SpaceShipBullet1 bullet = new SpaceShipBullet1(this.getX()+this.getWidth()/2, this.getY());
					currentPane.getChildren().add(bullet);
					bullet.moveBullet(currentPane);
				}));
		autofireTimeline.setCycleCount(Timeline.INDEFINITE);
		autofireTimeline.play();
	}

	@Override
	public void stopFire(){
		autofireTimeline.stop();
	}
}
