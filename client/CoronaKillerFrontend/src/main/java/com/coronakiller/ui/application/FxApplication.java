package com.coronakiller.ui.application;

import com.coronakiller.ui.UiApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

public class FxApplication extends Application {
	private ConfigurableApplicationContext applicationContext;

	/**
	 * Initialize the SpringBoot ApplicationContext using UiApplication.java
	 * @throws Exception
	 */
	@Override
	public void init() {
		applicationContext = new SpringApplicationBuilder(UiApplication.class).run();
	}

	/**
	 * Close the SpringBoot ApplicationContext
	 * and Exit the JavaFX Application
	 * @throws Exception
	 */
	@Override
	public void stop() {
		applicationContext.close();
		Platform.exit();
	}

	@Override
	public void start(Stage stage) {
		applicationContext.publishEvent(new StageReadyEvent(stage));
	}

	static class StageReadyEvent extends ApplicationEvent {
		public StageReadyEvent(Stage stage) {
			super(stage);
		}

		public Stage getStage() {
			/* Since we initialized super() with stage, our Source is a Stage,
			 we can directly return that */
			return ((Stage) getSource());
		}
	}
}
