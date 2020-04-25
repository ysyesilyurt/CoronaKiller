package com.coronakiller.ui.controller;

import com.coronakiller.ui.constants.UiConstants;
import com.coronakiller.ui.model.Player;
import com.coronakiller.ui.service.RequestService;
import com.jfoenix.controls.JFXSnackbar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
	private Text snackbarContent;

	@FXML
	private AnchorPane loginPane;

	@FXML
	protected void onClickLogin(ActionEvent event) throws IOException {
		JFXSnackbar snackbar = new JFXSnackbar(loginPane);
		if (nameField.getText().isEmpty()) {
			snackbarContent.setText("Please enter your username!");
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent(snackbarContent));
			return;
		} else if (passwordField.getText().isEmpty()) {
			snackbarContent.setText("Please enter your password!");
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent(snackbarContent));
			return;
		}

		Player player = Player.builder()
				.username(nameField.getText())
				.password(passwordField.getText())
				.build();

		player = RequestService.login(player);
		if (player == null) {
			snackbarContent.setText("Username or password is wrong!");
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent(snackbarContent));
			return;
		} else {
			/* Route to Dashboard */
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Parent dashboardPage = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/dashboard.fxml"));
			Scene scene = new Scene(dashboardPage, 600, 800);
			currentStage.setScene(scene);
			currentStage.show();
		}
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
