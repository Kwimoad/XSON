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

    private static final String APP_STYLESHEET = "/styles/app.css";

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
    private AuthenticationController authenticationController;

    @Override
    public void start(Stage stage) {
        authenticationController = new AuthenticationController();

        configureUI();
        setupActions();

        scene = new Scene(vbox, 420, 400);
        scene.getStylesheets().add(getClass().getResource(APP_STYLESHEET).toExternalForm());

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
        vbox.getStyleClass().add("login-container");

        emailLabel = new Label("Email :");
        emailLabel.getStyleClass().add("input-label");

        emailField = new TextField();
        emailField.setPromptText("Enter your email");
        emailField.setPrefHeight(40);
        emailField.getStyleClass().addAll("text-input");

        passwordLabel = new Label("password :");
        passwordLabel.getStyleClass().add("input-label");

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setPrefHeight(40);
        passwordField.getStyleClass().addAll("text-input");

        connectionButton = new Button("Log in");
        connectionButton.getStyleClass().addAll("button-base", "button-primary");

        createAccountButton = new Button("Create an account");
        createAccountButton.getStyleClass().addAll("button-base", "button-accent");

        buttonBox = new HBox(15, createAccountButton, connectionButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(15, 0, 5, 0));

        tryButton = new Button("Try without an account");
        tryButton.getStyleClass().addAll("button-base", "button-neutral");

        Separator separator = new Separator();
        separator.getStyleClass().add("separator-thin");

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
        dialogPane.getStyleClass().add("alert-pane");

        alert.showAndWait();
    }

}