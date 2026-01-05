package org.views;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.controller.UserController;
import org.utils.ValidationUtils;
import org.dto.Gender;
import org.dto.User;

import java.sql.Date;

import static org.views.style.AppColors.*;

/**
 * AccountUI
 *
 * This class represents the user interface for managing a user account.
 * It allows users to view and edit their personal information such as
 * first name, last name, date of birth, gender, and email.
 * Users can also open a window to change their password.
 *
 * The UI is built using JavaFX and organized with GridPane and VBox layouts.
 */
public class AccountUI extends Application {

    private VBox root;
    private VBox buttonsBox;
    private GridPane grid;

    private Label firstNameLabel;
    private Label lastNameLabel;
    private Label dateOfBirthLabel;
    private Label genderLabel;
    private Label emailLabel;

    private TextField firstNameField;
    private TextField lastNameField;
    private TextField dateOfBirthField;
    private TextField genderField;
    private TextField emailField;

    private Button modifyButton;
    private Button changePasswordButton;

    private ComboBox<String> genderBox;
    private boolean isModified = false;

    private UserController userController = new UserController();
    private User user;

    public AccountUI(User user){
        this.user = user;
    }

    @Override
    public void start(Stage stage) {
        configureUI();

        Scene scene = new Scene(root, 550, 550);
        stage.setTitle("Account Management – XSON");
        stage.setScene(scene);
        stage.setResizable(false);

        stage.centerOnScreen();
        stage.show();
    }

    private void configureUI(){
        String labelStyle = "-fx-font-weight: 600; " +
                "-fx-text-fill: " + TEXT_DARK + "; " +
                "-fx-font-size: 14px;";

        firstNameLabel = new Label("First name :");
        firstNameLabel.setStyle(labelStyle);

        lastNameLabel = new Label("Last name :");
        lastNameLabel.setStyle(labelStyle);

        dateOfBirthLabel = new Label("Date of birth :");
        dateOfBirthLabel.setStyle(labelStyle);

        genderLabel = new Label("Gender :");
        genderLabel.setStyle(labelStyle);

        emailLabel = new Label("Email :");
        emailLabel.setStyle(labelStyle);

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
        firstNameField.setText(user.getFirstName());
        firstNameField.setEditable(false);
        firstNameField.setFocusTraversable(false);

        lastNameField = new TextField();
        lastNameField.setStyle(textFieldStyle);
        lastNameField.setText(user.getLastName());
        lastNameField.setEditable(false);
        lastNameField.setFocusTraversable(false);

        dateOfBirthField = new TextField();
        dateOfBirthField.setStyle(textFieldStyle);
        dateOfBirthField.setText(String.valueOf(user.getDateOfBirth()));
        dateOfBirthField.setEditable(false);
        dateOfBirthField.setFocusTraversable(false);

        genderField = new TextField();
        genderField.setStyle(textFieldStyle);
        String gender = (user.getGender()== Gender.MALE) ? "MALE" : "FEMALE";
        genderField.setText(gender);
        genderField.setEditable(false);
        genderField.setFocusTraversable(false);

        emailField = new TextField();
        emailField.setStyle(textFieldStyle);
        emailField.setText(user.getAccount().getEmail());
        emailField.setEditable(false);
        emailField.setFocusTraversable(false);

        grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(15);
        grid.setPadding(new Insets(25));
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
        grid.add(genderField, 1, 3);

        grid.add(emailLabel, 0, 4);
        grid.add(emailField, 0, 5, 2, 1);

        String primaryButtonStyle = "-fx-background-color: " + PRIMARY_BLUE + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 12 25; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-min-width: 160;";

        modifyButton = new Button("Modify");
        modifyButton.setStyle("-fx-background-color: " + WARNING_COLOR + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 12 25; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-min-width: 160;");
        modifyButton.setOnAction(e -> modifyButtonAction());

        modifyButton.setOnMouseEntered(e -> modifyButton.setStyle("-fx-background-color: " + HOVER_WARNING + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 12 25; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-min-width: 160;"));
        modifyButton.setOnMouseExited(e -> modifyButton.setStyle("-fx-background-color: " + WARNING_COLOR + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 12 25; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-min-width: 160;"));

        changePasswordButton = new Button("Change password");
        changePasswordButton.setStyle("-fx-background-color: " + ACCENT_GREEN + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 12 25; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-min-width: 160;");
        changePasswordButton.setOnAction(e -> changePasswordButtonAction());

        changePasswordButton.setOnMouseEntered(e -> changePasswordButton.setStyle("-fx-background-color: " + HOVER_GREEN + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 12 25; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-min-width: 160;"));
        changePasswordButton.setOnMouseExited(e -> changePasswordButton.setStyle("-fx-background-color: " + ACCENT_GREEN + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 12 25; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-min-width: 160;"));

        buttonsBox = new VBox(10, changePasswordButton, modifyButton/*, okButton*/);
        buttonsBox.setAlignment(Pos.CENTER_RIGHT);
        buttonsBox.setPadding(new Insets(20, 0, 0, 0));

        root = new VBox(15, grid, buttonsBox);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: " + LIGHT_BACKGROUND + ";");

        addModificationListener(firstNameField, user.getFirstName());
        addModificationListener(lastNameField, user.getLastName());
        addModificationListener(dateOfBirthField, String.valueOf(user.getDateOfBirth()));

    }

    private void addModificationListener(TextField field, String initialValue) {
        field.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.equals(initialValue)) {
                isModified = true;
            }
        });
    }

    private void modifyButtonAction() {
        System.out.println("Editing mode enabled");
        enableEditing(true);

        modifyButton.setText("Save");
        modifyButton.setOnAction(e -> saveChangesAction());

        genderBox = new ComboBox<>();
        genderBox.getItems().addAll("Male", "Female");
        genderBox.setPromptText("Select");
        genderBox.setPrefHeight(40);
        genderBox.setStyle(
                "-fx-background-color: " + CARD_BACKGROUND + ";" +
                        "-fx-border-color: " + PRIMARY_BLUE + ";" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 6;"
        );
        String gender = (user.getGender()== Gender.MALE) ? "MALE" : "FEMALE";
        genderBox.setValue(gender);
        grid.getChildren().remove(genderField);
        grid.add(genderBox, 1, 3);

        genderBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != gender) {
                isModified = true;
            }
        });

        modifyButton.setStyle("-fx-background-color: " + ACCENT_GREEN + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 12 25; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-min-width: 160;");

        modifyButton.setOnMouseEntered(e -> modifyButton.setStyle("-fx-background-color: " + HOVER_GREEN + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 12 25; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-min-width: 160;"));
    }

    private void saveChangesAction() {
        System.out.println("Saving changes");
        System.out.println(firstNameField.getText()+" "+lastNameField.getText()+
                ""+dateOfBirthField.getText()+" "+genderField.getText()+" "+emailField.getText());

        if( !ValidationUtils.isNotEmpty(firstNameField.getText()) || !ValidationUtils.isNotEmpty(lastNameField.getText()) || !ValidationUtils.isNotEmpty(dateOfBirthField.getText())){
            showError("Please fill in all required fields");
            return;
        }
        if(!isModified){
            showError("No changes detected");
            return;
        }
        user.setFirstName(firstNameField.getText());
        user.setLastName(lastNameField.getText());
        Date newBirth = Date.valueOf(dateOfBirthField.getText());
        user.setDateOfBirth(newBirth);
        Gender selectedGender = (genderBox.getValue().equals("Male")) ? Gender.MALE : Gender.FEMALE;
        user.setGender(selectedGender);
        boolean isUpdate = userController.updateUser(user);
        if(!isUpdate){
            showError("No changes detected");
            return;
        }

        genderField.setText(selectedGender.name());
        grid.getChildren().remove(genderBox);
        grid.add(genderField, 1, 3);
        enableEditing(false);

        modifyButton.setText("Modify");
        modifyButton.setOnAction(e -> modifyButtonAction());

        modifyButton.setStyle("-fx-background-color: " + WARNING_COLOR + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 12 25; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-min-width: 160;");

        modifyButton.setOnMouseEntered(e -> modifyButton.setStyle("-fx-background-color: " + HOVER_WARNING + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 12 25; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-min-width: 160;"));

        showSuccess("Changes saved successfully!");
    }

    private void changePasswordButtonAction() {
        System.out.println("Opening change password window");
        openChangePasswordDialog();
    }

    private void enableEditing(boolean enable) {
        firstNameField.setEditable(enable);
        lastNameField.setEditable(enable);
        dateOfBirthField.setEditable(enable);
        genderField.setEditable(enable);

        String borderColor = enable ? PRIMARY_BLUE : BORDER_COLOR;
        String fieldStyle = "-fx-background-color: " + (enable ? "#F0F7FF" : CARD_BACKGROUND) + "; " +
                "-fx-border-color: " + borderColor + "; " +
                "-fx-border-width: " + (enable ? "2" : "1.5") + "; " +
                "-fx-border-radius: 6; " +
                "-fx-padding: 10; " +
                "-fx-font-size: 14px; " +
                "-fx-text-fill: " + TEXT_DARK + "; " +
                "-fx-pref-height: 40;";

        firstNameField.setStyle(fieldStyle);
        lastNameField.setStyle(fieldStyle);
        dateOfBirthField.setStyle(fieldStyle);
        genderField.setStyle(fieldStyle);
        emailField.setStyle(fieldStyle);
    }

    private void openChangePasswordDialog() {
        // TODO: Implémenter la fenêtre de changement de mot de passe
        Stage accountStage = new Stage();
        ChangePasswordUI changePasswordUI = new ChangePasswordUI(user.getAccount());
        try {
            changePasswordUI.start(accountStage);
        } catch (Exception e) {
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

}