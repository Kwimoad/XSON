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

        private static final String APP_STYLESHEET = "/styles/app.css";

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
                scene.getStylesheets().add(getClass().getResource(APP_STYLESHEET).toExternalForm());
        stage.setTitle("XSON");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    private void configureUI() {
        toolBar.getStyleClass().add("xson-toolbar");

        newFileButton.getStyleClass().addAll("button-base", "toolbar-button");
        importFileButton.getStyleClass().addAll("button-base", "toolbar-button");
        createAccountButton.getStyleClass().addAll("button-base", "account-button");

        toolBar.getItems().addAll(newFileButton, importFileButton, createAccountButton);

        HBox.setHgrow(spacer, Priority.ALWAYS);
        topBar.getChildren().addAll(toolBar, spacer);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(5));
        topBar.getStyleClass().add("top-bar");

        leftTextArea.getStyleClass().add("content-area");
        rightTextArea.getStyleClass().add("content-area");

        toXmlButton.getStyleClass().addAll("button-base", "middle-action");
        toJsonButton.getStyleClass().addAll("button-base", "middle-action");

        middleButtons.getChildren().addAll(toXmlButton, toJsonButton);
        middleButtons.setAlignment(Pos.CENTER);
        middleButtons.getStyleClass().add("middle-buttons");

        HBox centerBox = new HBox(20, leftTextArea, middleButtons, rightTextArea);
        centerBox.setPadding(new Insets(15));
        centerBox.getStyleClass().add("content-container");
        HBox.setHgrow(leftTextArea, Priority.ALWAYS);
        HBox.setHgrow(rightTextArea, Priority.ALWAYS);

        saveButton.getStyleClass().addAll("button-base", "save-button");

        bottomBox.getChildren().add(saveButton);
        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        bottomBox.setPadding(new Insets(15));
        bottomBox.getStyleClass().add("bottom-bar");

        root.setTop(topBar);
        root.setCenter(centerBox);
        root.setBottom(bottomBox);
        root.getStyleClass().add("dashboard-root");
    }

    private void bindData() {
        leftTextArea.textProperty().bindBidirectional(model.leftContentProperty());
        rightTextArea.textProperty().bindBidirectional(model.rightContentProperty());
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