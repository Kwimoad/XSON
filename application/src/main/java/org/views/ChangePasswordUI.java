package org.views;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.controller.AccountController;
import org.utils.ValidationUtils;
import org.dto.Account;

/**
 * ChangePasswordUI
 *
 * This class represents the user interface for changing a user's account password in XSON.
 * It provides fields for the old password, new password, and confirmation of the new password.
 * The UI validates password strength, shows errors or success messages, and interacts with
 * AccountController to update the password for the given Account.
 *
 * The class extends JavaFX Application and can be run as a standalone window.
 */

public class ChangePasswordUI extends Application {

    private static final String APP_STYLESHEET = "/styles/app.css";

    private PasswordField oldPasswordField;
    private PasswordField newPasswordField;
    private PasswordField confirmationPasswordField;
    private Button validButton;
    private Label errorLabel;

    private VBox vbox;

    private AccountController accountController;

    private Account account;

    public ChangePasswordUI(Account account){
        this.account = account;
    }

    @Override
    public void start(Stage primaryStage) {
        accountController = new AccountController();
        configureUI();

        Scene scene = new Scene(vbox, 500, 600);
        scene.getStylesheets().add(getClass().getResource(APP_STYLESHEET).toExternalForm());

        primaryStage.setTitle("Change Password – XSON");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private void configureUI(){
        Label oldPasswordLabel = new Label("Old password * :");
        oldPasswordLabel.getStyleClass().add("form-label");

        Label newPasswordLabel = new Label("New password * :");
        newPasswordLabel.getStyleClass().add("form-label");

        Label confirmationPasswordLabel = new Label("Confirm password * :");
        confirmationPasswordLabel.getStyleClass().add("form-label");

        oldPasswordField = new PasswordField();
        oldPasswordField.getStyleClass().add("text-input");
        oldPasswordField.setPrefHeight(40);
        oldPasswordField.setPrefWidth(250);
        oldPasswordField.setPromptText("Enter your old password");

        newPasswordField = new PasswordField();
        newPasswordField.getStyleClass().add("text-input");
        newPasswordField.setPrefHeight(40);
        newPasswordField.setPrefWidth(250);
        newPasswordField.setPromptText("Minimum 8 characters");

        confirmationPasswordField = new PasswordField();
        confirmationPasswordField.getStyleClass().add("text-input");
        confirmationPasswordField.setPrefHeight(40);
        confirmationPasswordField.setPrefWidth(250);
        confirmationPasswordField.setPromptText("Repeat the new password");

        errorLabel = new Label();
        errorLabel.getStyleClass().add("error-text");
        errorLabel.setVisible(false);

        validButton = new Button("Change password");
        validButton.getStyleClass().addAll("button-base", "button-accent");
        validButton.setOnAction(e -> validButtonAction());

        Label passwordStrengthLabel = new Label("Security requirements :");
        passwordStrengthLabel.getStyleClass().add("form-label");
        passwordStrengthLabel.setPadding(new Insets(10, 0, 5, 0));

        VBox criteriaBox = new VBox(5);

        Label criteria1 = new Label("• At least 8 characters");
        criteria1.getStyleClass().add("criteria-text");

        Label criteria2 = new Label("• At least one uppercase and one lowercase letter");
        criteria2.getStyleClass().add("criteria-text");

        Label criteria3 = new Label("• At least one digit");
        criteria3.getStyleClass().add("criteria-text");

        Label criteria4 = new Label("• At least one special character (@#$%^&+=)");
        criteria4.getStyleClass().add("criteria-text");

        criteriaBox.getChildren().addAll(criteria1, criteria2, criteria3, criteria4);
        criteriaBox.setPadding(new Insets(0, 0, 15, 0));

        vbox = new VBox(12);
        vbox.setPadding(new Insets(30));
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.getStyleClass().add("page-background");

        vbox.getChildren().addAll(
                oldPasswordLabel,
                oldPasswordField,
                newPasswordLabel,
                newPasswordField,
                passwordStrengthLabel,
                criteriaBox,
                confirmationPasswordLabel,
                confirmationPasswordField,
                errorLabel,
                validButton
        );

        newPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            validatePasswordStrength(newValue);
        });

        confirmationPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            validatePasswordConfirmation();
        });
    }

    private void validButtonAction() {
        System.out.println("Validating password change...");
        String oldPassword = oldPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmationPassword = confirmationPasswordField.getText();
        if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmationPassword.isEmpty()) {
            showError("Please fill in all required fields (*)");
            return;
        }
        if (oldPassword.equals(newPassword)) {
            showError("The new password must be different from the old one");
            return;
        }
        if (!newPassword.equals(confirmationPassword)) {
            showError("Passwords do not match");
            return;
        }
        if (!ValidationUtils.isPasswordStrong(newPassword)) {
            showError("The password does not meet security requirements");
            return;
        }
        clearError();
        boolean success = accountController.changePassword(account,oldPassword,newPassword);
        if (success) {
            showSuccess("Password changed successfully!");
            closeWindowAfterDelay();
        } else {
            showError("The old password is incorrect");
        }
    }

    private void validatePasswordStrength(String password) {
        if (password.length() < 8) {
            setFieldState(newPasswordField, "input-error");
        } else if (ValidationUtils.isPasswordStrong(password)) {
            setFieldState(newPasswordField, "input-success");
        } else {
            setFieldState(newPasswordField, null);
        }
    }

    private void validatePasswordConfirmation() {
        String newPassword = newPasswordField.getText();
        String confirmation = confirmationPasswordField.getText();

        if (!confirmation.isEmpty() && !newPassword.equals(confirmation)) {
            setFieldState(confirmationPasswordField, "input-error");
        } else if (!confirmation.isEmpty() && newPassword.equals(confirmation)) {
            setFieldState(confirmationPasswordField, "input-success");
        } else {
            setFieldState(confirmationPasswordField, null);
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);

        switchButtonState(true);
    }

    private void clearError() {
        errorLabel.setVisible(false);

        switchButtonState(false);
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStyleClass().add("alert-pane-success");

        alert.showAndWait();
    }

    private void closeWindowAfterDelay() {
        new Thread(() -> {
            try {
                Thread.sleep(1000);

                javafx.application.Platform.runLater(() -> {
                    Stage stage = (Stage) validButton.getScene().getWindow();
                    stage.close();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void setFieldState(Control field, String stateClass) {
        field.getStyleClass().removeAll("input-error", "input-success");
        if (stateClass != null && !field.getStyleClass().contains(stateClass)) {
            field.getStyleClass().add(stateClass);
        }
    }

    private void switchButtonState(boolean error) {
        validButton.getStyleClass().removeAll("button-error", "button-accent");
        validButton.getStyleClass().add(error ? "button-error" : "button-accent");
    }

}