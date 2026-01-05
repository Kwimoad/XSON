package org.views;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.controller.AuthenticationController;
import org.dto.User;
import static org.views.style.AppColors.*;

/**
 * LoginUI
 *
 * Cette classe crée l'interface pour se connecter à l'application XSON.
 *
 * L'utilisateur peut :
 * - Se connecter avec email et mot de passe
 * - Créer un nouveau compte
 * - Essayer l'application sans compte
 *
 * Après connexion réussie, elle ouvre le tableau de bord correspondant.
 */
public class LoginUI extends Application {

    private TextField emailField;
    private PasswordField passwordField;
    private Label emailLabel;
    private Label passwordLabel;
    private Button connectionButton;
    private Button createAccountButton;
    private Button tryButton;
    private HBox buttonBox;
    private HBox tryButtonBox;
    private VBox vbox;
    private Scene scene;
    private String primaryButtonStyle;
    private AuthenticationController authenticationController;

    @Override
    public void start(Stage stage) {
        authenticationController = new AuthenticationController();

        configureUI();
        setupActions();

        scene = new Scene(vbox, 420, 400);

        stage.setTitle("Authentication – XSON");
        stage.setScene(scene);
        stage.setResizable(false);

        stage.centerOnScreen();
        stage.show();
    }

    public void configureUI(){
        vbox = new VBox(15);
        vbox.setPadding(new Insets(30));
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: " + LIGHT_BACKGROUND + "; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-width: 1; " +
                "-fx-border-radius: 12;");

        emailLabel = new Label("Email :");
        emailLabel.setStyle("-fx-font-weight: 600; " +
                "-fx-text-fill: " + TEXT_DARK + "; " +
                "-fx-font-size: 14px;");

        emailField = new TextField();
        emailField.setPromptText("Enter your email");
        emailField.setPrefHeight(40);
        emailField.setStyle("-fx-background-color: " + CARD_BACKGROUND + "; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-width: 1.5; " +
                "-fx-border-radius: 6; " +
                "-fx-padding: 10; " +
                "-fx-font-size: 14px; " +
                "-fx-text-fill: " + TEXT_DARK + ";");

        passwordLabel = new Label("password :");
        passwordLabel.setStyle("-fx-font-weight: 600; " +
                "-fx-text-fill: " + TEXT_DARK + "; " +
                "-fx-font-size: 14px;");

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setPrefHeight(40);
        passwordField.setStyle("-fx-background-color: " + CARD_BACKGROUND + "; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-width: 1.5; " +
                "-fx-border-radius: 6; " +
                "-fx-padding: 10; " +
                "-fx-font-size: 14px; " +
                "-fx-text-fill: " + TEXT_DARK + ";");

        primaryButtonStyle = "-fx-background-color: " + PRIMARY_BLUE + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 12 25; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-min-width: 140;";

        connectionButton = new Button("Log in");
        connectionButton.setStyle(primaryButtonStyle);
        connectionButton.setOnMouseEntered(e -> connectionButton.setStyle(primaryButtonStyle +
                " -fx-background-color: " + HOVER_BLUE + ";"));
        connectionButton.setOnMouseExited(e -> connectionButton.setStyle(primaryButtonStyle));

        createAccountButton = new Button("Create an account");
        createAccountButton.setStyle("-fx-background-color: " + ACCENT_GREEN + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 12 25; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-min-width: 140;");
        createAccountButton.setOnMouseEntered(e -> createAccountButton.setStyle("-fx-background-color: " + HOVER_GREEN + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 12 25; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-min-width: 140;"));
        createAccountButton.setOnMouseExited(e -> createAccountButton.setStyle("-fx-background-color: " + ACCENT_GREEN + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 12 25; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-min-width: 140;"));

        buttonBox = new HBox(15, createAccountButton, connectionButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(15, 0, 5, 0));

        tryButton = new Button("Try without an account");
        tryButton.setStyle("-fx-background-color: " + NEUTRAL_GRAY + "; " +
                "-fx-text-fill: " + TEXT_DARK + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 10 20; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 13px; " +
                "-fx-cursor: hand; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-width: 1;");
        tryButton.setOnMouseEntered(e -> tryButton.setStyle("-fx-background-color: " + SECONDARY_BLUE + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 10 20; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 13px; " +
                "-fx-cursor: hand; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-width: 1;"));
        tryButton.setOnMouseExited(e -> tryButton.setStyle("-fx-background-color: " + NEUTRAL_GRAY + "; " +
                "-fx-text-fill: " + TEXT_DARK + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 10 20; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 13px; " +
                "-fx-cursor: hand; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-width: 1;"));

        Separator separator = new Separator();
        separator.setPadding(new Insets(10, 0, 10, 0));
        separator.setStyle("-fx-background-color: " + BORDER_COLOR + ";");

        tryButtonBox = new HBox(tryButton);
        tryButtonBox.setAlignment(Pos.CENTER);
        tryButtonBox.setPadding(new Insets(10, 0, 0, 0));

        vbox.getChildren().addAll(
                emailLabel, emailField,
                passwordLabel, passwordField,
                buttonBox,
                separator,
                tryButtonBox
        );
    }

    private void setupActions(){
        connectionButton.setOnAction(e -> connectionAction());
        createAccountButton.setOnAction(e -> createAccountAction());
        tryButton.setOnAction(e -> tryAction());
    }

    private void connectionAction() {
        String email = emailField.getText();
        String password = passwordField.getText();
        User user = authenticationController.login(email, password);
        if(user != null){
            principalUI(user);
        } else {
            showError("Incorrect email or password");
        }
    }

    private void principalUI(User user){
        Stage currentStage = (Stage) connectionButton.getScene().getWindow();
        Stage connectionStage = new Stage();
        MainDashboardUI principal = new MainDashboardUI(user);
        try {
            principal.start(connectionStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        currentStage.close();
    }

    private void createAccountAction() {
        Stage currentStage = (Stage) createAccountButton.getScene().getWindow();
        Stage createAccountStage = new Stage();
        RegistrationUI registrationUI = new RegistrationUI();
        try {
            registrationUI.start(createAccountStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        currentStage.close();
    }

    private void tryAction() {
        System.out.println("Try without account – Redirecting...");
        Stage currentStage = (Stage) tryButton.getScene().getWindow();
        Stage mainStage = new Stage();
        GuestDashboardUI mainUI = new GuestDashboardUI();
        try {
            mainUI.start(mainStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        currentStage.close();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Authentication error");
        alert.setHeaderText(null);
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: " + CARD_BACKGROUND + "; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-width: 1; " +
                "-fx-border-radius: 8;");

        alert.showAndWait();
    }

}