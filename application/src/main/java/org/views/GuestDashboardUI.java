package org.views;

import org.controller.PrincipalController;
import org.Models.FileEntity;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import static org.views.style.AppColors.*;

/**
 * GuestDashboardUI
 *
 * This class represents the user interface for the guest dashboard in XSON.
 * It provides a workspace where guests can create new files, import files,
 * convert between XML and JSON, and save content. The interface includes
 * two text areas for editing file content and toolbar buttons for main actions.
 *
 * The class extends JavaFX Application and interacts with PrincipalController
 * to manage the file operations and conversions.
 */

public class GuestDashboardUI extends Application {

    private ToolBar toolBar = new ToolBar();
    private Button newFileButton = new Button("new");
    private Button importFileButton = new Button("import");
    private Button createAccountButton = new Button("create account");
    private Region spacer = new Region();
    private HBox topBar = new HBox(10);
    private TextArea leftTextArea = new TextArea();
    private TextArea rightTextArea = new TextArea();
    private Button toXmlButton = new Button("← to XML");
    private Button toJsonButton = new Button("to JSON →");
    private VBox middleButtons = new VBox(15);
    private Button saveButton = new Button("save");
    private HBox bottomBox = new HBox();
    private BorderPane root = new BorderPane();

    private FileEntity model;
    private PrincipalController controller;

    @Override
    public void start(Stage stage) {
        model = new FileEntity();
        controller = new PrincipalController(model, stage);

        configureUI();

        bindData();

        setupActions();

        Scene scene = new Scene(root, 1000, 650);
        stage.setTitle("XSON");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    private void configureUI() {

        toolBar.setStyle("-fx-background-color: " + CARD_BACKGROUND + "; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-width: 0 0 1 0;");

        String toolbarButtonStyle = "-fx-background-color: " + SECONDARY_BLUE + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 8 20; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 13px; " +
                "-fx-cursor: hand;";

        newFileButton.setStyle(toolbarButtonStyle);
        importFileButton.setStyle(toolbarButtonStyle);
        createAccountButton.setStyle("-fx-background-color: " + ACCENT_GREEN + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 8 20; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 13px; " +
                "-fx-cursor: hand;");

        setupHoverEffects();

        toolBar.getItems().addAll(newFileButton, importFileButton, createAccountButton);

        HBox.setHgrow(spacer, Priority.ALWAYS);
        topBar.getChildren().addAll(toolBar, spacer);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(5));
        topBar.setStyle("-fx-background-color: " + CARD_BACKGROUND + ";");

        String textAreaStyle = "-fx-control-inner-background: " + CARD_BACKGROUND + "; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-width: 1.5; " +
                "-fx-border-radius: 8; " +
                "-fx-font-size: 14px; " +
                "-fx-text-fill: " + TEXT_DARK + "; " +
                "-fx-font-family: 'Segoe UI', 'Arial', sans-serif; " +
                "-fx-padding: 12;";

        leftTextArea.setStyle(textAreaStyle);
        rightTextArea.setStyle(textAreaStyle);

        String middleButtonStyle = "-fx-background-color: " + ACCENT_PURPLE + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 12 24; " +
                "-fx-background-radius: 6; " +
                "-fx-min-width: 120; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand;";

        toXmlButton.setStyle(middleButtonStyle);
        toJsonButton.setStyle(middleButtonStyle);

        toXmlButton.setOnMouseEntered(e -> toXmlButton.setStyle(middleButtonStyle + " -fx-background-color: " + HOVER_PURPLE + ";"));
        toXmlButton.setOnMouseExited(e -> toXmlButton.setStyle(middleButtonStyle));

        toJsonButton.setOnMouseEntered(e -> toJsonButton.setStyle(middleButtonStyle + " -fx-background-color: " + HOVER_PURPLE + ";"));
        toJsonButton.setOnMouseExited(e -> toJsonButton.setStyle(middleButtonStyle));

        middleButtons.getChildren().addAll(toXmlButton, toJsonButton);
        middleButtons.setAlignment(Pos.CENTER);
        middleButtons.setStyle("-fx-background-color: " + LIGHT_BACKGROUND + "; " +
                "-fx-padding: 25; " +
                "-fx-background-radius: 12; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-width: 1; " +
                "-fx-border-radius: 12;");

        HBox centerBox = new HBox(20, leftTextArea, middleButtons, rightTextArea);
        centerBox.setPadding(new Insets(15));
        centerBox.setStyle("-fx-background-color: " + LIGHT_BACKGROUND + ";");
        HBox.setHgrow(leftTextArea, Priority.ALWAYS);
        HBox.setHgrow(rightTextArea, Priority.ALWAYS);

        String saveButtonStyle = "-fx-background-color: " + PRIMARY_BLUE + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 12 35; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand;";

        saveButton.setStyle(saveButtonStyle);
        saveButton.setOnMouseEntered(e -> saveButton.setStyle(saveButtonStyle + " -fx-background-color: " + HOVER_BLUE + ";"));
        saveButton.setOnMouseExited(e -> saveButton.setStyle(saveButtonStyle));

        bottomBox.getChildren().add(saveButton);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        bottomBox.setPadding(new Insets(15));
        bottomBox.setStyle("-fx-background-color: " + CARD_BACKGROUND + "; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-width: 1 0 0 0;");

        root.setTop(topBar);
        root.setCenter(centerBox);
        root.setBottom(bottomBox);
        root.setStyle("-fx-background-color: linear-gradient(135deg, " + LIGHT_BACKGROUND + ", #F0F4F8);");
    }

    private void bindData() {
        leftTextArea.textProperty().bindBidirectional(model.leftContentProperty());
        rightTextArea.textProperty().bindBidirectional(model.rightContentProperty());
    }

    private void setupHoverEffects() {

        newFileButton.setOnMouseEntered(e -> newFileButton.setStyle(
                "-fx-background-color: " + HOVER_BLUE + "; " +
                        "-fx-text-fill: " + TEXT_LIGHT + "; " +
                        "-fx-font-weight: 600; " +
                        "-fx-padding: 8 20; " +
                        "-fx-background-radius: 6; " +
                        "-fx-font-size: 13px; " +
                        "-fx-cursor: hand;"));
        newFileButton.setOnMouseExited(e -> newFileButton.setStyle(
                "-fx-background-color: " + SECONDARY_BLUE + "; " +
                        "-fx-text-fill: " + TEXT_LIGHT + "; " +
                        "-fx-font-weight: 600; " +
                        "-fx-padding: 8 20; " +
                        "-fx-background-radius: 6; " +
                        "-fx-font-size: 13px; " +
                        "-fx-cursor: hand;"));

        importFileButton.setOnMouseEntered(e -> importFileButton.setStyle(
                "-fx-background-color: " + HOVER_BLUE + "; " +
                        "-fx-text-fill: " + TEXT_LIGHT + "; " +
                        "-fx-font-weight: 600; " +
                        "-fx-padding: 8 20; " +
                        "-fx-background-radius: 6; " +
                        "-fx-font-size: 13px; " +
                        "-fx-cursor: hand;"));
        importFileButton.setOnMouseExited(e -> importFileButton.setStyle(
                "-fx-background-color: " + SECONDARY_BLUE + "; " +
                        "-fx-text-fill: " + TEXT_LIGHT + "; " +
                        "-fx-font-weight: 600; " +
                        "-fx-padding: 8 20; " +
                        "-fx-background-radius: 6; " +
                        "-fx-font-size: 13px; " +
                        "-fx-cursor: hand;"));

        createAccountButton.setOnMouseEntered(e -> createAccountButton.setStyle(
                "-fx-background-color: " + HOVER_GREEN + "; " +
                        "-fx-text-fill: " + TEXT_LIGHT + "; " +
                        "-fx-font-weight: 600; " +
                        "-fx-padding: 8 20; " +
                        "-fx-background-radius: 6; " +
                        "-fx-font-size: 13px; " +
                        "-fx-cursor: hand;"));
        createAccountButton.setOnMouseExited(e -> createAccountButton.setStyle(
                "-fx-background-color: " + ACCENT_GREEN + "; " +
                        "-fx-text-fill: " + TEXT_LIGHT + "; " +
                        "-fx-font-weight: 600; " +
                        "-fx-padding: 8 20; " +
                        "-fx-background-radius: 6; " +
                        "-fx-font-size: 13px; " +
                        "-fx-cursor: hand;"));
    }

    private void setupActions() {
        newFileButton.setOnAction(e -> {
            controller.newFile();
            leftTextArea.requestFocus(); // Focus sur leftTextArea comme demandé
        });

        importFileButton.setOnAction(e -> controller.importFile());

        createAccountButton.setOnAction(e -> {
            Stage currentStage = (Stage) createAccountButton.getScene().getWindow();
            Stage mainStage = new Stage();
            RegistrationUI mainUI = new RegistrationUI();
            try {
                mainUI.start(mainStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            currentStage.close();
        });
        saveButton.setOnAction(e -> controller.saveFile());
        toXmlButton.setOnAction(e -> controller.toXMLButtonAction());
        toJsonButton.setOnAction(e -> controller.toJSONButtonAction());
    }

}