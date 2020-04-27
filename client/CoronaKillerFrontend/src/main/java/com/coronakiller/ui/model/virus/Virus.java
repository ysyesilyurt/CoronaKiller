package com.coronakiller.ui.model.virus;

import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

/**
 * This Virus class can represent general virus object in the game.
 */
@Getter
@Setter
public abstract class Virus extends Rectangle {

	private int virusHealth;

	/**
	 * Constructor method of virus class.
	 * @param xPosition
	 * @param yPosition
	 * @param width
	 * @param height
	 * @param virusHealth
	 */
	public Virus(double xPosition, double yPosition, int width, int height, int virusHealth) {
		super(xPosition, yPosition, width, height);
		this.virusHealth = virusHealth;
	}

	public void getShot(int bulletDamage){
		this.virusHealth-= bulletDamage;
	}

	/**
	 * Soome Viruses can shoot by using this method.
	 * @param pane
	 */
	public abstract void virusAutoFire(Pane pane);

	public abstract void virusAutoMove();

	public abstract void stopFireAndMove();

	public abstract void changeIconOfVirus();

	public abstract int getPointsFromVirus();
}
