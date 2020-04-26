package com.coronakiller.ui.controller.level;

import com.coronakiller.ui.constants.UiConstants;
import com.coronakiller.ui.model.SpaceShip;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

import static com.coronakiller.ui.constants.UiConstants.*;

public class GameLevel1Controller implements Initializable {

	@FXML
	public AnchorPane anchorPane;

	private SpaceShip spaceShip;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		handleSpaceInitialization();
		anchorPane.getChildren().add(spaceShip);
	}

	public void handleSpaceInitialization(){
		spaceShip = new SpaceShip(INITIAL_SPACESHIP_X_POSITION,
				INITIAL_SPACESHIP_Y_POSITION,
				SPACESHIP_WIDTH,
				SPACESHIP_HEIGHT);
		spaceShip.changeIconofSpaceShip();
		spaceShip.setMouseDraggableObject();
	}
}
