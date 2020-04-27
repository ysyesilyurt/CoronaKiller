package com.coronakiller.ui.model.virus;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
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
	private int moveCounter = 4;
	private boolean moveDirectionFlag = true;

	public EasyVirus(double xPosition, double yPosition) {
		super(xPosition, yPosition, EASY_VIRUS_WIDTH, EASY_VIRUS_HEIGHT, EASY_VIRUS_HEALTH);
	}

	@Override
	public void virusAutoFire(Pane pane) {

	}

	@Override
	public void virusAutoMove() {
		this.easyVirusTimeline = new Timeline(
				new KeyFrame( Duration.millis(1000), e ->{
					if(moveDirectionFlag) {
						this.setX(this.getX() - EASY_VIRUS_VELOCITY);
						moveCounter--;
						if(moveCounter == 0)
							moveDirectionFlag = false;
					}
					else{
						this.setX(this.getX() + EASY_VIRUS_VELOCITY);
						moveCounter++;
						if(moveCounter == 4)
							moveDirectionFlag = true;
					}
				})
		);
		this.easyVirusTimeline.setCycleCount(Timeline.INDEFINITE);
		this.easyVirusTimeline.play();
	}

	@Override
	public void stopFireAndMove() {
		this.easyVirusTimeline.stop();
	}

	@Override
	public void changeIconOfVirus() {
		Image easyVirusIcon = new Image(EASY_VIRUS_ICON_URL);
		this.setFill(new ImagePattern(easyVirusIcon));
	}

	@Override
	public int getPointsFromVirus(){
		return 10;
	}
}
