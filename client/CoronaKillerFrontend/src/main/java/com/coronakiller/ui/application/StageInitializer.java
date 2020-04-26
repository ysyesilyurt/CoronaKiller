package com.coronakiller.ui.application;

import com.coronakiller.ui.application.FxApplication.StageReadyEvent;
import com.coronakiller.ui.constants.UiConstants;
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
 * This class sets up the JavaFX stage when it's ready.
 * Namely it listens the stage ready event that is created
 * and fired in FxApplication.java, then sets up the stage accordingly.
 */
@Slf4j
@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

	@Value("classpath:/fxml/login.fxml")
	private Resource loginResource;
	private final ApplicationContext applicationContext;
	/* We keep current user information in this static variable
	* to access it from everywhere through the application. (A Cookie-like mechanism) */
	public static Player currentPlayer;

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
			stage.setScene(new Scene(parent, 600, 800));
			stage.setTitle(UiConstants.WINDOW_TITLE);
			stage.show();
		} catch (IOException e) {
			log.error("IOException while trying to set the stage from stage initializer.");
			e.printStackTrace();
		}
	}
}
