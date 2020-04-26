package com.coronakiller.ui.application;

import com.coronakiller.ui.application.FxApplication.StageReadyEvent;
import com.coronakiller.ui.constants.UiConstants;
import com.coronakiller.ui.model.GameSession;
import com.coronakiller.ui.model.Player;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * This is a listener class that sets up the JavaFX stage when it's ready.
 * Namely it listens the stage ready event that is created and fired in
 * FxApplication.java, then sets up the stage (and application variables) accordingly.
 * Default first scene is Login Page, so this class first renders the Login Page.
 */
@Slf4j
@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

	@Value("classpath:/fxml/login.fxml")
	private Resource loginResource;
	private final ApplicationContext applicationContext;
	/* We keep current user information and game session information (if exists) in these
	 * static variables to access them from everywhere through the application. (A Cookie-like mechanism) */
	public static Player currentPlayer;
	public static GameSession currentPlayerGameSession;

	public StageInitializer(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public void onApplicationEvent(StageReadyEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(loginResource.getURL());
			/* Tell fxmlLoader where to get its controllers from (use Spring Beans) */
			fxmlLoader.setControllerFactory(controller -> applicationContext.getBean(controller));
			Parent parent = fxmlLoader.load();
			/* Get the JavaFX stage from the event */
			Stage stage = event.getStage();
			Scene firstScene = new Scene(parent, 600, 800);
			stage.setScene(firstScene);
			stage.setTitle(UiConstants.WINDOW_TITLE);
			stage.setMinWidth(600);
			stage.setMaxWidth(600);
			stage.setMinHeight(830); // TODO: CHECK HERE - IF SET HEIGHT TO 800 then a shift occurs on pages
			stage.setMaxHeight(830);
			stage.show();
		} catch (IOException e) {
			log.error("IOException while trying to set the stage from stage initializer.");
			e.printStackTrace();
		}
	}
}
