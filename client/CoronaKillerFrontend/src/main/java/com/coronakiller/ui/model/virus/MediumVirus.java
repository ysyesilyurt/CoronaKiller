package com.coronakiller.ui.model.virus;

import com.coronakiller.ui.model.bullet.SpaceShipBullet1;
import com.coronakiller.ui.model.bullet.VirusBullet1;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;

import java.awt.*;
import java.util.Random;

import static com.coronakiller.ui.constants.GameConstants.*;
import static java.awt.Color.DARK_GRAY;

public class MediumVirus extends Virus {

	private int virusHealth;
	private Timeline mediumVirusMoveTimeline;
	private Timeline mediumVirusFireTimeline;
	private int moveCounter = 4;
	private boolean moveDirectionFlag = true;

	public MediumVirus(double xPosition, double yPosition) {
		super(xPosition, yPosition, MEDIUM_VIRUS_WIDTH, MEDIUM_VIRUS_HEIGHT, MEDIUM_VIRUS_HEALTH);
	}
	@Override
	public void virusAutoFire(Pane currentPane) {
		Random rand = new Random();
		int ms = 1000 + rand.nextInt(1000);
		mediumVirusFireTimeline = new Timeline(
				new KeyFrame(Duration.millis(ms), e -> {
					VirusBullet1 bullet = new VirusBullet1(this.getX()+this.getWidth()/2, this.getY());
					currentPane.getChildren().add(bullet);
					bullet.moveBullet(currentPane);
				}));
		mediumVirusFireTimeline.setCycleCount(Timeline.INDEFINITE);
		mediumVirusFireTimeline.play();
	}

	@Override
	public void virusAutoMove() {
		this.mediumVirusMoveTimeline = new Timeline(
				new KeyFrame( Duration.millis(750), e ->{
					if(moveDirectionFlag) {
						this.setX(this.getX() - MEDIUM_VIRUS_VELOCITY);
						moveCounter--;
						if(moveCounter == 0)
							moveDirectionFlag = false;
					}
					else{
						this.setX(this.getX() + MEDIUM_VIRUS_VELOCITY);
						moveCounter++;
						if(moveCounter == 4)
							moveDirectionFlag = true;
					}
				})
		);
		this.mediumVirusMoveTimeline.setCycleCount(Timeline.INDEFINITE);
		this.mediumVirusMoveTimeline.play();
	}

	@Override
	public void stopFireAndMove() {
		this.mediumVirusMoveTimeline.stop();
		this.mediumVirusFireTimeline.stop();
	}

	@Override
	public void changeIconOfVirus() {
		//Image mediumVirusIcon = new Image(MEDIUM_VIRUS_ICON_URL);
		this.setFill(javafx.scene.paint.Color.RED);
	}

	@Override
	public int getPointsFromVirus(){
		return 30;
	}
}
