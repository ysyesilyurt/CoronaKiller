package com.coronakiller.ui.model;

import com.coronakiller.ui.model.bullet.SpaceShipBullet1;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

import java.net.URISyntaxException;

import static com.coronakiller.ui.constants.UiConstants.*;

@Getter
@Setter
public class SpaceShip extends Rectangle {

	private int currentHealth;
	private Timeline autofireTimeline;

	/**
	 * Constructor method of the SpaceShip object.
	 * @param xPosition position on the x axis
	 * @param yPosition position of the y axis
	 * @param width width of the spaceship
	 * @param height height of the spaceship
	 */
	public SpaceShip(int xPosition, int yPosition, int width, int height){
		super(xPosition, yPosition, width, height);
		this.currentHealth = 100;
	}

	public void setMouseDraggableObject(){
		this.setOnMouseDragged(me -> {
			this.setX(me.getX() - this.getWidth()/2);
			this.setY(me.getY() - this.getHeight()/2);
		});
	}

	public void changeIconofSpaceShip(){
		Image spaceshipIcon = new Image(SPACESHIP_ICON_URL);
		this.setFill(new ImagePattern(spaceshipIcon));
	}

	public void autofire(Pane currentPane){
		autofireTimeline = new Timeline(
				new KeyFrame(Duration.millis(500), e -> {
					SpaceShipBullet1 bullet = new SpaceShipBullet1(this.getX()+this.getWidth()/2,
							this.getY(), SPACESHIP_BULLET1_WIDTH, SPACESHIP_BULLET1_HEIGHT );
					currentPane.getChildren().add(bullet);
					bullet.moveBullet(currentPane);
				}));
		autofireTimeline.setCycleCount(Timeline.INDEFINITE);
		autofireTimeline.play();
	}

}
