package com.coronakiller.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginController {

//	@FXML
//	private TextField nameField;
//
//	@FXML
//	private PasswordField passwordField;
//
	@FXML
	private Button loginButton;

	@Value("classpath:/fxml/dashboard.fxml")
	private Resource dashboardResource;

	@FXML
	protected void handleLoginButtonAction(ActionEvent event) throws IOException {
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent registerPage = FXMLLoader.load(dashboardResource.getURL());
		Scene scene = new Scene(registerPage, 600, 800);
		currentStage.setScene(scene);
		currentStage.show();
	}
}
