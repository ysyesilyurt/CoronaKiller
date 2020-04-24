package com.coronakiller.ui;

import com.coronakiller.ui.application.FxApplication;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The SpringBoot Application that launches and manages JavaFX
 * Application that is given by FxApplication.java
 */
@SpringBootApplication
public class UiApplication {

	public static void main(String[] args) {
		Application.launch(FxApplication.class, args);
	}

}
