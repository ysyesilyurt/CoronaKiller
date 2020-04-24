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
public class RegisterController {

	@FXML
	private TextField nameField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Button backToLoginButton;

	@FXML
	private Button registerButton;

	@FXML
	protected void onClickRegister(ActionEvent event) throws IOException {
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent dashboardPage = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/dashboard.fxml"));
		Scene scene = new Scene(dashboardPage, 600, 800);
		currentStage.setScene(scene);
		currentStage.show();
	}

	@FXML
	protected void onClickBackToLogin(ActionEvent event) throws IOException {
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent loginPage = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/login.fxml"));
		Scene scene = new Scene(loginPage, 600, 800);
		currentStage.setScene(scene);
		currentStage.show();
	}
}
