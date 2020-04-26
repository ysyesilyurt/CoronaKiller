package com.coronakiller.ui.controller;

import com.coronakiller.ui.application.StageInitializer;
import com.coronakiller.ui.constants.UiConstants;
import com.coronakiller.ui.model.Player;
import com.coronakiller.ui.service.RequestService;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSpinner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.javatuples.Pair;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class RegisterController implements Initializable {

	private JFXSnackbar snackbar;

	@FXML
	public PasswordField secondPasswordField;

	@FXML
	public JFXSpinner loadingSpinner;

	@FXML
	public AnchorPane innerPane;

	@FXML
	private TextField nameField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Button backToLoginButton;

	@FXML
	private Button registerButton;

	@FXML
	private Text snackbarContent;

	@FXML
	private AnchorPane registerPane;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		registerPane.getStylesheets().add("css/styles.css");
		snackbar = new JFXSnackbar(registerPane);
	}

	@FXML
	protected void onClickRegister(ActionEvent event) throws IOException {
		loadingSpinner.setVisible(true);
		innerPane.setDisable(true);
		if (nameField.getText().isEmpty()) {
			snackbarContent.setText("Please enter your username");
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent(snackbarContent));
		} else if (passwordField.getText().isEmpty()) {
			snackbarContent.setText("Please enter your password");
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent(snackbarContent));
		} else if (secondPasswordField.getText().isEmpty()) {
			snackbarContent.setText("Please enter your password again");
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent(snackbarContent));
		} else if (!secondPasswordField.getText().equals(passwordField.getText())) {
			snackbarContent.setText("Passwords does not match");
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent(snackbarContent));
		} else {
			Player player = Player.builder()
					.username(nameField.getText())
					.password(passwordField.getText())
					.build();

			Pair<Player, String> result = RequestService.register(player);
			snackbarContent.setText(result.getValue1());
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent(snackbarContent));
			if (result.getValue0() != null) {
				/* Set the application's current user for global access */
				StageInitializer.currentPlayer = result.getValue0();
				/* Then Route to Dashboard */
				Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				Parent dashboardPage = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/dashboard.fxml"));
				Scene scene = new Scene(dashboardPage, 600, 800);
				currentStage.setScene(scene);
				currentStage.show();
			}
		}
		loadingSpinner.setVisible(false);
		innerPane.setDisable(false);
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
