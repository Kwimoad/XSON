package org.views;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.controller.CreateAccountController;
import org.utils.ValidationUtils;
import org.dto.User;
import static org.views.style.AppColors.*;

/**
 * RegistrationUI
 *
 * Cette classe crée l'interface pour la création d'un nouveau compte utilisateur dans l'application XSON.
 *
 * Fonctionnalités :
 * - Saisie du prénom, nom, date de naissance, genre, email et mot de passe.
 * - Validation des champs obligatoires (*).
 * - Vérification que le mot de passe contient au moins 8 caractères.
 * - Vérification que l'email est valide.
 * - Création du compte via CreateAccountController.
 * - Affichage d'un message de succès ou d'erreur.
 * - Retour à l'écran de connexion après création ou via le bouton "Back".
 *
 * L'interface utilise JavaFX et applique des styles simples pour les boutons, champs de texte et alertes.
 */

public class RegistrationUI extends Application {

    private Button backButton;
    private Button validButton;
    private Label firstNameLabel;
    private Label lastNameLabel;
    private Label dateOfBirthLabel;
    private Label genderLabel;
    private Label emailLabel;
    private Label passwordLabel;
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField dateOfBirthField;
    private TextField emailField;
    private PasswordField passwordField;
    private ComboBox<String> genderBox;
    private GridPane grid;
    private VBox root;
    private Scene scene;

    @Override
    public void start(Stage stage) {

        configureUI();

        scene = new Scene(root, 500, 600);

        stage.setTitle("Account Creation – XSON");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    private void configureUI(){
        String labelStyle = "-fx-font-weight: 600; " +
                "-fx-text-fill: " + TEXT_DARK + "; " +
                "-fx-font-size: 14px;";

        firstNameLabel = new Label("First name * :");
        firstNameLabel.setStyle(labelStyle);

        lastNameLabel = new Label("Last name * :");
        lastNameLabel.setStyle(labelStyle);

        dateOfBirthLabel = new Label("Date of birth * :");
        dateOfBirthLabel.setStyle(labelStyle);

        genderLabel = new Label("Gender * :");
        genderLabel.setStyle(labelStyle);

        emailLabel = new Label("Email * :");
        emailLabel.setStyle(labelStyle);

        passwordLabel = new Label("Password * :");
        passwordLabel.setStyle(labelStyle);

        String textFieldStyle = "-fx-background-color: " + CARD_BACKGROUND + "; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-width: 1.5; " +
                "-fx-border-radius: 6; " +
                "-fx-padding: 10; " +
                "-fx-font-size: 14px; " +
                "-fx-text-fill: " + TEXT_DARK + "; " +
                "-fx-pref-height: 40;";

        firstNameField = new TextField();
        firstNameField.setStyle(textFieldStyle);
        firstNameField.setPromptText("Enter your first name");

        lastNameField = new TextField();
        lastNameField.setStyle(textFieldStyle);
        lastNameField.setPromptText("Enter your last name");

        dateOfBirthField = new TextField();
        dateOfBirthField.setStyle(textFieldStyle);
        dateOfBirthField.setPromptText("DD/MM/YYYY");

        emailField = new TextField();
        emailField.setStyle(textFieldStyle);
        emailField.setPromptText("exemple@email.com");

        passwordField = new PasswordField();
        passwordField.setStyle(textFieldStyle);
        passwordField.setPromptText("Minimum 8 characters");

        genderBox = new ComboBox<>();
        genderBox.getItems().addAll("Male", "Female");
        genderBox.setPromptText("Select");
        genderBox.setPrefHeight(40);

        genderBox.setStyle("-fx-background-color: " + CARD_BACKGROUND + "; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-width: 1.5; " +
                "-fx-border-radius: 6; " +
                "-fx-padding: 5; " +
                "-fx-font-size: 14px; " +
                "-fx-text-fill: " + TEXT_DARK + ";");

        genderBox.getEditor().setStyle("-fx-text-fill: " + TEXT_DARK + ";");

        grid = new GridPane();
        grid.setPadding(new Insets(25));
        grid.setHgap(20);
        grid.setVgap(15);
        grid.setStyle("-fx-background-color: " + CARD_BACKGROUND + "; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-width: 1; " +
                "-fx-border-radius: 12;");

        grid.add(firstNameLabel, 0, 0);
        grid.add(lastNameLabel, 1, 0);
        grid.add(firstNameField, 0, 1);
        grid.add(lastNameField, 1, 1);

        grid.add(dateOfBirthLabel, 0, 2);
        grid.add(genderLabel, 1, 2);
        grid.add(dateOfBirthField, 0, 3);
        grid.add(genderBox, 1, 3);

        grid.add(emailLabel, 0, 4);
        grid.add(emailField, 0, 5, 2, 1);

        grid.add(passwordLabel, 0, 6);
        grid.add(passwordField, 0, 7, 2, 1);

        String primaryButtonStyle = "-fx-background-color: " + ACCENT_GREEN + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 12 30; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-min-width: 160;";

        validButton = new Button("Create account");
        validButton.setStyle(primaryButtonStyle);
        validButton.setOnAction(e -> validButtonAction());

        validButton.setOnMouseEntered(e -> validButton.setStyle(primaryButtonStyle +
                " -fx-background-color: " + HOVER_GREEN + ";"));
        validButton.setOnMouseExited(e -> validButton.setStyle(primaryButtonStyle));

        String secondaryButtonStyle = "-fx-background-color: " + SECONDARY_BLUE + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 12 30; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-min-width: 160;";

        backButton = new Button("Back");
        backButton.setStyle(secondaryButtonStyle);
        backButton.setOnAction(e -> backButtonAction());

        backButton.setOnMouseEntered(e -> backButton.setStyle(secondaryButtonStyle +
                " -fx-background-color: " + HOVER_BLUE + ";"));
        backButton.setOnMouseExited(e -> backButton.setStyle(secondaryButtonStyle));

        HBox buttonBox = new HBox(15, backButton, validButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));

        Label requiredNote = new Label("* Required fields");
        requiredNote.setStyle("-fx-font-size: 12px; " +
                "-fx-text-fill: #666666; " +
                "-fx-font-style: italic; " +
                "-fx-padding: 5 0 0 0;");

        root = new VBox(15, grid, requiredNote, buttonBox);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: " + LIGHT_BACKGROUND + ";");
    }

    public void backButtonAction() {
        System.out.println("Back to authentication");
        Stage currentStage = (Stage) backButton.getScene().getWindow();
        Stage authentificationStage = new Stage();
        LoginUI loginUI = new LoginUI();
        try {
            loginUI.start(authentificationStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        currentStage.close();
    }

    public void validButtonAction() {
        System.out.println("Account creation in progress...");
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String dateOfBirth = dateOfBirthField.getText();
        String gender = genderBox.getValue();
        String email = emailField.getText();
        String password = passwordField.getText();
        if (ValidationUtils.isNotEmpty(firstName) || ValidationUtils.isNotEmpty(lastName) || ValidationUtils.isNotEmpty(email) || ValidationUtils.isNotEmpty(password)) {
            showError("Please fill in all required fields (*)");
            return;
        }
        if (password.length() < 8) {
            showError("Password must contain at least 8 characters");
            return;
        }
        if (!ValidationUtils.isEmail(email)) {
            showError("Please enter a valid email address");
            return;
        }
        try {
            CreateAccountController createaccountcontroller = new CreateAccountController();
            User success = createaccountcontroller.create(firstName, lastName, dateOfBirth, gender, email, password);
            if (success!=null) {
                showSuccess("Account created successfully!");
                redirectToLogin();
            } else {
                showError("Account creation failed. Please try again.");
            }
        } catch (Exception e) {
            showError("Error during account creation : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Creation error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: " + CARD_BACKGROUND + "; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-width: 1; " +
                "-fx-border-radius: 8;");

        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: " + CARD_BACKGROUND + "; " +
                "-fx-border-color: " + ACCENT_GREEN + "; " +
                "-fx-border-width: 1; " +
                "-fx-border-radius: 8;");

        alert.showAndWait();
    }

    private void redirectToLogin() {
        new Thread(() -> {
            try {
                Thread.sleep(1500);
                javafx.application.Platform.runLater(() -> {
                    Stage currentStage = (Stage) validButton.getScene().getWindow();
                    Stage authentificationStage = new Stage();
                    LoginUI loginUI = new LoginUI();
                    try {
                        loginUI.start(authentificationStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    currentStage.close();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}