package com.coronakiller.ui.model.virus;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

import static com.coronakiller.ui.constants.GameConstants.*;

@Getter
@Setter
/**
 * This class represents the most basic virus(alien) type.
 */
public class EasyVirus extends Virus {

	private int virusHealth;
	private Timeline easyVirusTimeline;

	public EasyVirus(double xPosition, double yPosition, int width, int height) {
		super(xPosition, yPosition, width, height, EASY_VIRUS_HEALTH);
	}

	@Override
	public void virusAutoFire(Pane pane) {

	}

	@Override
	public void virusAutoMove() {
		this.easyVirusTimeline = new Timeline(
				new KeyFrame( Duration.millis(10), e ->{
					this.setX(this.getX()- EASY_VIRUS_VELOCITY);
				})
		);
		this.easyVirusTimeline.setCycleCount(Timeline.INDEFINITE);
		this.easyVirusTimeline.play();
	}

	@Override
	public void stopFireAndMove() {

	}
}
