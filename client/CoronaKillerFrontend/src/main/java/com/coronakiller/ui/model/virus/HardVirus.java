package com.coronakiller.ui.model.virus;

import com.coronakiller.ui.model.bullet.VirusBullet1;
import com.coronakiller.ui.model.bullet.VirusBullet2;
import com.coronakiller.ui.model.virus.Virus;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;

import java.util.Random;

import static com.coronakiller.ui.constants.GameConstants.*;
import static com.coronakiller.ui.constants.GameConstants.MEDIUM_VIRUS_VELOCITY;

public class HardVirus extends Virus {
	private int virusHealth;
	private Timeline hardVirusMoveTimeline;
	private Timeline hardVirusFireTimeline;
	private int moveCounter = 2;
	Image hardVirusIcon = new Image(HARD_VIRUS_ICON_URL);

	public HardVirus(double xPosition, double yPosition) {
		super(xPosition, yPosition, HARD_VIRUS_WIDTH, HARD_VIRUS_HEIGHT, HARD_VIRUS_HEALTH);
	}
	@Override
	public void virusAutoFire(Pane currentPane) {
		Random rand = new Random();
		int ms = 800 + rand.nextInt(800);
		hardVirusFireTimeline = new Timeline(
				new KeyFrame(Duration.millis(ms), e -> {
					VirusBullet2 bullet = new VirusBullet2(this.getX()+this.getWidth()/2, this.getY());
					bullet.changeIconOfBullet();
					currentPane.getChildren().add(bullet);
					bullet.moveBullet(currentPane);
				}));
		hardVirusFireTimeline.setCycleCount(Timeline.INDEFINITE);
		hardVirusFireTimeline.play();
	}

	@Override
	public void virusAutoMove() {
		this.hardVirusMoveTimeline = new Timeline(
				new KeyFrame( Duration.millis(500), e ->{
					if(moveCounter == 0){
						this.setX(this.getX() + HARD_VIRUS_VELOCITY);
						moveCounter++;
					}
					else if(moveCounter == 4){
						this.setX(this.getX() - HARD_VIRUS_VELOCITY);
						moveCounter--;
					}
					else if(Math.random()>0.5){
						this.setX(this.getX() + HARD_VIRUS_VELOCITY);
						moveCounter++;
					}
					else{
						this.setX(this.getX() - HARD_VIRUS_VELOCITY);
						moveCounter--;
					}
				})
		);
		this.hardVirusMoveTimeline.setCycleCount(Timeline.INDEFINITE);
		this.hardVirusMoveTimeline.play();
	}

	@Override
	public void stopFireAndMove() {
		this.hardVirusMoveTimeline.stop();
		this.hardVirusFireTimeline.stop();
	}

	@Override
	public void changeIconOfVirus() {
		this.setFill(new ImagePattern(hardVirusIcon));
	}

	@Override
	public int getPointsFromVirus(){
		return 20;
	}
}
