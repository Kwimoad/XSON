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

    private static final String APP_STYLESHEET = "/styles/app.css";

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
        scene.getStylesheets().add(getClass().getResource(APP_STYLESHEET).toExternalForm());
        stage.setTitle("Account Management – XSON");
        stage.setScene(scene);
        stage.setResizable(false);

        stage.centerOnScreen();
        stage.show();
    }

    private void configureUI(){
        firstNameLabel = new Label("First name :");
        firstNameLabel.getStyleClass().add("form-label");

        lastNameLabel = new Label("Last name :");
        lastNameLabel.getStyleClass().add("form-label");

        dateOfBirthLabel = new Label("Date of birth :");
        dateOfBirthLabel.getStyleClass().add("form-label");

        genderLabel = new Label("Gender :");
        genderLabel.getStyleClass().add("form-label");

        emailLabel = new Label("Email :");
        emailLabel.getStyleClass().add("form-label");

        firstNameField = new TextField();
        firstNameField.getStyleClass().add("text-input");
        firstNameField.setPrefHeight(40);
        firstNameField.setText(user.getFirstName());
        firstNameField.setEditable(false);
        firstNameField.setFocusTraversable(false);

        lastNameField = new TextField();
        lastNameField.getStyleClass().add("text-input");
        lastNameField.setPrefHeight(40);
        lastNameField.setText(user.getLastName());
        lastNameField.setEditable(false);
        lastNameField.setFocusTraversable(false);

        dateOfBirthField = new TextField();
        dateOfBirthField.getStyleClass().add("text-input");
        dateOfBirthField.setPrefHeight(40);
        dateOfBirthField.setText(String.valueOf(user.getDateOfBirth()));
        dateOfBirthField.setEditable(false);
        dateOfBirthField.setFocusTraversable(false);

        genderField = new TextField();
        genderField.getStyleClass().add("text-input");
        genderField.setPrefHeight(40);
        String gender = (user.getGender()== Gender.MALE) ? "MALE" : "FEMALE";
        genderField.setText(gender);
        genderField.setEditable(false);
        genderField.setFocusTraversable(false);

        emailField = new TextField();
        emailField.getStyleClass().add("text-input");
        emailField.setPrefHeight(40);
        emailField.setText(user.getAccount().getEmail());
        emailField.setEditable(false);
        emailField.setFocusTraversable(false);

        grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(15);
        grid.setPadding(new Insets(25));
        grid.getStyleClass().add("form-grid");

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

        modifyButton = new Button("Modify");
        modifyButton.getStyleClass().addAll("button-base", "button-warning");
        modifyButton.setOnAction(e -> modifyButtonAction());

        changePasswordButton = new Button("Change password");
        changePasswordButton.getStyleClass().addAll("button-base", "button-accent");
        changePasswordButton.setOnAction(e -> changePasswordButtonAction());

        buttonsBox = new VBox(10, changePasswordButton, modifyButton/*, okButton*/);
        buttonsBox.setAlignment(Pos.CENTER_RIGHT);
        buttonsBox.setPadding(new Insets(20, 0, 0, 0));

        root = new VBox(15, grid, buttonsBox);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);
        root.getStyleClass().add("page-background");

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
        genderBox.getStyleClass().addAll("combo-input", "editable-field");
        String gender = (user.getGender()== Gender.MALE) ? "MALE" : "FEMALE";
        genderBox.setValue(gender);
        grid.getChildren().remove(genderField);
        grid.add(genderBox, 1, 3);

        genderBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != gender) {
                isModified = true;
            }
        });

        modifyButton.getStyleClass().remove("button-warning");
        if (!modifyButton.getStyleClass().contains("button-accent")) {
            modifyButton.getStyleClass().add("button-accent");
        }
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

            grid.getChildren().remove(genderBox);
            grid.add(genderField, 1, 3);
            enableEditing(false);

            modifyButton.setText("Modify");
            modifyButton.setOnAction(e -> modifyButtonAction());

            modifyButton.getStyleClass().remove("button-accent");
            if (!modifyButton.getStyleClass().contains("button-warning")) {
                modifyButton.getStyleClass().add("button-warning");
            }
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

        modifyButton.getStyleClass().remove("button-accent");
        if (!modifyButton.getStyleClass().contains("button-warning")) {
            modifyButton.getStyleClass().add("button-warning");
        }

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

        toggleEditableClass(firstNameField, enable);
        toggleEditableClass(lastNameField, enable);
        toggleEditableClass(dateOfBirthField, enable);
        toggleEditableClass(genderField, enable);
        toggleEditableClass(emailField, enable);
    }

    private void toggleEditableClass(Control control, boolean enable) {
        if (enable) {
            if (!control.getStyleClass().contains("editable-field")) {
                control.getStyleClass().add("editable-field");
            }
        } else {
            control.getStyleClass().remove("editable-field");
        }
    }

    private void openChangePasswordDialog() {
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
        dialogPane.getStyleClass().add("alert-pane-error");

        alert.showAndWait();
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

}