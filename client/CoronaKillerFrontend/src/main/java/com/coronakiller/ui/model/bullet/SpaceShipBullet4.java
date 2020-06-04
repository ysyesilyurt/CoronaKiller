package com.coronakiller.ui.model.bullet;

import com.coronakiller.ui.controller.level.GameLevel1Controller;
import com.coronakiller.ui.controller.level.GameLevelController;
import com.coronakiller.ui.model.virus.Virus;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;

import java.io.IOException;

import static com.coronakiller.ui.constants.GameConstants.*;

public class SpaceShipBullet4 extends SpaceShipBullet{

	Image spaceshipBullet3Icon = new Image(SPACESHIP_BULLET3_ICON_URL);

	/**
	 * Constructor method of second type of bullet.
	 * @param xPosition
	 * @param yPosition
	 */
	public SpaceShipBullet4(double xPosition, double yPosition){
		super(xPosition, yPosition, SPACESHIP_BULLET3_WIDTH, SPACESHIP_BULLET3_HEIGHT, SPACESHIP_BULLET3_DAMAGE, SPACESHIP_BULLET3_VELOCITY);
	}

	@Override
	public void checkCollision(Pane currentPane) throws IOException {
		Virus virus = GameLevel1Controller.levelViruses.get(0);
		if(virus.getBoundsInParent().intersects(this.getBoundsInParent())){
			this.getBulletTimeline().stop();
			currentPane.getChildren().remove(this);
			virus.getShot(this.getBulletDamage());
			GameLevelController.otherPlayerScore += virus.getPointsFromVirus();
			GameLevelController.updateScoreValue();
			if(virus.getVirusHealth() <= 0){
				virus.stopFireAndMove();
				currentPane.getChildren().remove(virus);
				if(!GameLevelController.isGameLevelFinished) {
					GameLevelController.finishLevelSuccessfully();
				}
			}
		}
	}

	@Override
	public void changeIconOfBullet() {
		this.setFill(new ImagePattern(spaceshipBullet3Icon));
	}
}
