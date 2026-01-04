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

import static org.views.style.AppColors.*;

public class ChangePasswordUI extends Application {

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

        primaryStage.setTitle("Change Password – XSON");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private void configureUI(){
        String labelStyle = "-fx-font-weight: 600; " +
                "-fx-text-fill: " + TEXT_DARK + "; " +
                "-fx-font-size: 14px;";

        Label oldPasswordLabel = new Label("Old password * :");
        oldPasswordLabel.setStyle(labelStyle);

        Label newPasswordLabel = new Label("New password * :");
        newPasswordLabel.setStyle(labelStyle);

        Label confirmationPasswordLabel = new Label("Confirm password * :");
        confirmationPasswordLabel.setStyle(labelStyle);

        String passwordFieldStyle = "-fx-background-color: " + CARD_BACKGROUND + "; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-width: 1.5; " +
                "-fx-border-radius: 6; " +
                "-fx-padding: 10; " +
                "-fx-font-size: 14px; " +
                "-fx-text-fill: " + TEXT_DARK + "; " +
                "-fx-pref-height: 40; " +
                "-fx-pref-width: 250;";

        oldPasswordField = new PasswordField();
        oldPasswordField.setStyle(passwordFieldStyle);
        oldPasswordField.setPromptText("Enter your old password");

        newPasswordField = new PasswordField();
        newPasswordField.setStyle(passwordFieldStyle);
        newPasswordField.setPromptText("Minimum 8 characters");

        confirmationPasswordField = new PasswordField();
        confirmationPasswordField.setStyle(passwordFieldStyle);
        confirmationPasswordField.setPromptText("Repeat the new password");

        errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: " + ERROR_COLOR + "; " +
                "-fx-font-size: 13px; " +
                "-fx-font-weight: 500; " +
                "-fx-padding: 5 0 10 0;");
        errorLabel.setVisible(false);
        String buttonStyle = "-fx-background-color: " + ACCENT_GREEN + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 12 40; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand;";

        validButton = new Button("Change password");
        validButton.setStyle(buttonStyle);
        validButton.setOnAction(e -> validButtonAction());

        validButton.setOnMouseEntered(e -> validButton.setStyle(buttonStyle +
                " -fx-background-color: " + HOVER_GREEN + ";"));
        validButton.setOnMouseExited(e -> validButton.setStyle(buttonStyle));

        Label passwordStrengthLabel = new Label("Security requirements :");
        passwordStrengthLabel.setStyle("-fx-font-weight: 600; " +
                "-fx-text-fill: " + TEXT_DARK + "; " +
                "-fx-font-size: 13px; " +
                "-fx-padding: 10 0 5 0;");

        VBox criteriaBox = new VBox(5);

        Label criteria1 = new Label("• At least 8 characters");
        criteria1.setStyle("-fx-text-fill: #666666; -fx-font-size: 12px;");

        Label criteria2 = new Label("• At least one uppercase and one lowercase letter");
        criteria2.setStyle("-fx-text-fill: #666666; -fx-font-size: 12px;");

        Label criteria3 = new Label("• At least one digit");
        criteria3.setStyle("-fx-text-fill: #666666; -fx-font-size: 12px;");

        Label criteria4 = new Label("• At least one special character (@#$%^&+=)");
        criteria4.setStyle("-fx-text-fill: #666666; -fx-font-size: 12px;");

        criteriaBox.getChildren().addAll(criteria1, criteria2, criteria3, criteria4);
        criteriaBox.setPadding(new Insets(0, 0, 15, 0));

        vbox = new VBox(12);
        vbox.setPadding(new Insets(30));
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setStyle("-fx-background-color: " + LIGHT_BACKGROUND + ";");

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
            newPasswordField.setStyle("-fx-border-color: " + ERROR_COLOR + "; " +
                    "-fx-background-color: " + CARD_BACKGROUND + "; " +
                    "-fx-border-width: 1.5; " +
                    "-fx-border-radius: 6; " +
                    "-fx-padding: 10; " +
                    "-fx-font-size: 14px; " +
                    "-fx-text-fill: " + TEXT_DARK + "; " +
                    "-fx-pref-height: 40; " +
                    "-fx-pref-width: 250;");
        } else if (ValidationUtils.isPasswordStrong(password)) {
            newPasswordField.setStyle("-fx-border-color: " + ACCENT_GREEN + "; " +
                    "-fx-background-color: " + CARD_BACKGROUND + "; " +
                    "-fx-border-width: 1.5; " +
                    "-fx-border-radius: 6; " +
                    "-fx-padding: 10; " +
                    "-fx-font-size: 14px; " +
                    "-fx-text-fill: " + TEXT_DARK + "; " +
                    "-fx-pref-height: 40; " +
                    "-fx-pref-width: 250;");
        } else {
            newPasswordField.setStyle("-fx-border-color: " + BORDER_COLOR + "; " +
                    "-fx-background-color: " + CARD_BACKGROUND + "; " +
                    "-fx-border-width: 1.5; " +
                    "-fx-border-radius: 6; " +
                    "-fx-padding: 10; " +
                    "-fx-font-size: 14px; " +
                    "-fx-text-fill: " + TEXT_DARK + "; " +
                    "-fx-pref-height: 40; " +
                    "-fx-pref-width: 250;");
        }
    }

    private void validatePasswordConfirmation() {
        String newPassword = newPasswordField.getText();
        String confirmation = confirmationPasswordField.getText();

        if (!confirmation.isEmpty() && !newPassword.equals(confirmation)) {
            confirmationPasswordField.setStyle("-fx-border-color: " + ERROR_COLOR + "; " +
                    "-fx-background-color: " + CARD_BACKGROUND + "; " +
                    "-fx-border-width: 1.5; " +
                    "-fx-border-radius: 6; " +
                    "-fx-padding: 10; " +
                    "-fx-font-size: 14px; " +
                    "-fx-text-fill: " + TEXT_DARK + "; " +
                    "-fx-pref-height: 40; " +
                    "-fx-pref-width: 250;");
        } else if (!confirmation.isEmpty() && newPassword.equals(confirmation)) {
            confirmationPasswordField.setStyle("-fx-border-color: " + ACCENT_GREEN + "; " +
                    "-fx-background-color: " + CARD_BACKGROUND + "; " +
                    "-fx-border-width: 1.5; " +
                    "-fx-border-radius: 6; " +
                    "-fx-padding: 10; " +
                    "-fx-font-size: 14px; " +
                    "-fx-text-fill: " + TEXT_DARK + "; " +
                    "-fx-pref-height: 40; " +
                    "-fx-pref-width: 250;");
        } else {
            confirmationPasswordField.setStyle("-fx-border-color: " + BORDER_COLOR + "; " +
                    "-fx-background-color: " + CARD_BACKGROUND + "; " +
                    "-fx-border-width: 1.5; " +
                    "-fx-border-radius: 6; " +
                    "-fx-padding: 10; " +
                    "-fx-font-size: 14px; " +
                    "-fx-text-fill: " + TEXT_DARK + "; " +
                    "-fx-pref-height: 40; " +
                    "-fx-pref-width: 250;");
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);

        validButton.setStyle("-fx-background-color: " + ERROR_COLOR + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 12 40; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand;");

        validButton.setOnMouseEntered(e -> validButton.setStyle("-fx-background-color: " + HOVER_ERROR + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 12 40; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand;"));
    }

    private void clearError() {
        errorLabel.setVisible(false);

        validButton.setStyle("-fx-background-color: " + ACCENT_GREEN + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 12 40; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand;");

        validButton.setOnMouseEntered(e -> validButton.setStyle("-fx-background-color: " + HOVER_GREEN + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 12 40; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand;"));
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

}