package com.coronakiller.ui.application;

import com.coronakiller.ui.application.FxApplication.StageReadyEvent;
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
	private final String applicationTitle;
	private final ApplicationContext applicationContext;

	public StageInitializer(@Value("${spring.application.ui.window.title}") String title,
							ApplicationContext applicationContext) {
		this.applicationTitle = title;
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
			stage.setScene(new Scene(parent, 800, 600));
			stage.setTitle(this.applicationTitle);
			stage.show();
		} catch (IOException e) {
			log.error("IOException while trying to set the stage from stage initializer.");
			e.printStackTrace();
		}
	}
}
