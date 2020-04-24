package com.coronakiller.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginController {

	@FXML
	private TextField nameField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Button loginButton;

	@FXML
	private Button goToRegisterButton;

	@FXML
	protected void onClickLogin(ActionEvent event) throws IOException {
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent dashboardPage = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/dashboard.fxml"));
		Scene scene = new Scene(dashboardPage, 600, 800);
		currentStage.setScene(scene);
		currentStage.show();
	}

	@FXML
	protected void onClickGoToRegister(ActionEvent event) throws IOException {
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent registerPage = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/register.fxml"));
		Scene scene = new Scene(registerPage, 600, 800);
		currentStage.setScene(scene);
		currentStage.show();
	}
}
