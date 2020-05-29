package com.coronakiller.ui.model.virus;

import com.coronakiller.ui.model.bullet.VirusBullet2;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;

import java.util.Random;

import static com.coronakiller.ui.constants.GameConstants.*;

public class BigBoss extends Virus{

	private Timeline bigBossMoveTimeline;
	private Timeline bigBossFireTimeline;
	private int moveCounter = 5;
	Image bigBossIcon = new Image(BIG_BOSS_ICON_URL);

	public BigBoss(double xPosition, double yPosition) {
		super(xPosition, yPosition, BIG_BOSS_WIDTH, BIG_BOSS_HEIGHT, BIG_BOSS_HEALTH);
	}
	@Override
	public void virusAutoFire(Pane currentPane) {
		Random rand = new Random();
		int ms = 600 + rand.nextInt(800);
		bigBossFireTimeline = new Timeline(
				new KeyFrame(Duration.millis(ms), e -> {
					VirusBullet2 bullet = new VirusBullet2(this.getX(), this.getY());
					VirusBullet2 bullet2 = new VirusBullet2(this.getX()+this.getWidth(), this.getY());
					bullet.changeIconOfBullet();
					bullet2.changeIconOfBullet();
					currentPane.getChildren().addAll(bullet,bullet2);
					bullet.moveBullet(currentPane);
					bullet2.moveBullet(currentPane);
				}));
		bigBossFireTimeline.setCycleCount(Timeline.INDEFINITE);
		bigBossFireTimeline.play();
	}

	@Override
	public void virusAutoMove() {
		this.bigBossMoveTimeline = new Timeline(
				new KeyFrame( Duration.millis(400), e ->{
					if(moveCounter == 0){
						this.setX(this.getX() + BIG_BOSS_VELOCITY);
						moveCounter++;
					}
					else if(moveCounter == 10){
						this.setX(this.getX() - BIG_BOSS_VELOCITY);
						moveCounter--;
					}
					else if(Math.random()>0.5){
						this.setX(this.getX() + BIG_BOSS_VELOCITY);
						moveCounter++;
					}
					else{
						this.setX(this.getX() - BIG_BOSS_VELOCITY);
						moveCounter--;
					}
				})
		);
		this.bigBossMoveTimeline.setCycleCount(Timeline.INDEFINITE);
		this.bigBossMoveTimeline.play();
	}

	@Override
	public void stopFireAndMove() {
		this.bigBossMoveTimeline.stop();
		this.bigBossFireTimeline.stop();
	}

	@Override
	public void changeIconOfVirus() {
		this.setFill(new ImagePattern(bigBossIcon));
	}

	@Override
	public int getPointsFromVirus(){
		return 10;
	}
}
