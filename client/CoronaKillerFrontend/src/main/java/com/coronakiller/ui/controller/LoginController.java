package com.coronakiller.ui.controller;

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
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.coronakiller.ui.application.StageInitializer.gameDataCookie;
import static com.coronakiller.ui.constants.GeneralConstants.*;
import static com.coronakiller.ui.constants.Utils.returnInputTextFormatter;

/**
 * Controller that manages Login Page (Default first scene of the application)
 */
@Component
public class LoginController implements Initializable {

	private JFXSnackbar snackbar;

	@FXML
	private TextField nameField;

	@FXML
	private PasswordField passwordField;

	@FXML
	public JFXSpinner loadingSpinner;

	@FXML
	public AnchorPane innerPane;

	@FXML
	private Button loginButton;

	@FXML
	private Button goToRegisterButton;

	@FXML
	private Text snackbarContent;

	@FXML
	private AnchorPane loginPane;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		loginPane.getStylesheets().add(GENERAL_STYLES);
		snackbar = new JFXSnackbar(loginPane);
		/* Set textfield config for rejection when space is entered */
		nameField.setTextFormatter(returnInputTextFormatter());
		passwordField.setTextFormatter(returnInputTextFormatter());
	}

	/**
	 * Method that is fired on the login button click action.
	 * It first validates the values of the inputs then it build a player with provided data.
	 * Then it makes a login request to backend via static login method.
	 * On successful login it sets the currentPlayer Cookie and redirects user to Dashboard page.
	 * Else displays an error toast to user.
	 *
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void onClickLogin(ActionEvent event) throws IOException {
		loadingSpinner.setVisible(true);
		innerPane.setDisable(true);
		if (nameField.getText().isEmpty()) {
			snackbarContent.setText("Please enter your username");
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent(snackbarContent));
		} else if (passwordField.getText().isEmpty()) {
			snackbarContent.setText("Please enter your password");
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent(snackbarContent));
		} else {
			Player player = Player.builder()
					.username(nameField.getText())
					.password(passwordField.getText())
					.build();

			Pair<Player, String> result = RequestService.login(player);
			snackbarContent.setText(result.getValue1());
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent(snackbarContent));
			if (result.getValue0() != null) {
				/* Set the application's current user for global access */
				gameDataCookie.setPlayerDTO(result.getValue0());
				/* Then Route to Dashboard */
				Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				Parent dashboardPage = FXMLLoader.load(getClass().getClassLoader().getResource(DASHBOARD_PAGE));
				Scene scene = new Scene(dashboardPage, 600, 800);
				currentStage.setScene(scene);
				currentStage.show();
			}
		}
		loadingSpinner.setVisible(false);
		innerPane.setDisable(false);
	}

	/**
	 * Method that is fired on the sign-up button click action.
	 * Redirects user to Register page.
	 *
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void onClickGoToRegister(ActionEvent event) throws IOException {
		loadingSpinner.setVisible(true);
		innerPane.setDisable(true);
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent registerPage = FXMLLoader.load(getClass().getClassLoader().getResource(REGISTER_PAGE));
		Scene scene = new Scene(registerPage, 600, 800);
		currentStage.setScene(scene);
		currentStage.show();
		loadingSpinner.setVisible(false);
		innerPane.setDisable(false);
	}
}
