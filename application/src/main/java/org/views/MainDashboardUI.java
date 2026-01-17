package org.views;

import org.controller.PrincipalController;
import org.dto.User;
import org.Models.FileEntity;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * MainDashboardUI
 *
 * Cette classe crée l'interface principale pour les utilisateurs connectés à l'application XSON.
 *
 * Fonctionnalités :
 * - Créer un nouveau fichier
 * - Importer un fichier
 * - Accéder aux archives
 * - Modifier les informations du compte
 * - Se déconnecter
 * - Convertir le contenu entre JSON et XML
 * - Sauvegarder les fichiers
 *
 * Les zones de texte à gauche et à droite sont liées aux données du modèle (FileEntity) pour
 * que les modifications soient automatiquement synchronisées.
 *
 * L'interface utilise JavaFX pour l'affichage et le style.
 *
 * Après connexion, le nom de l'utilisateur connecté s'affiche en haut à droite.
 */

public class MainDashboardUI extends Application {

    private static final String APP_STYLESHEET = "/styles/app.css";

    // Composants UI
    private ToolBar toolBar = new ToolBar();
    private Button newFileButton = new Button("new");
    private Button importFileButton = new Button("import");
    private Button archiveButton = new Button("archive");
    private Button accountButton = new Button("account");
    private Button logoutButton = new Button("logout");
    private TextField nameUserField = new TextField();
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
    private User user;

    // Composants MVC
    private FileEntity model;
    private PrincipalController controller;

    public MainDashboardUI(User user){
        this.user = user;
    }

    @Override
    public void start(Stage stage) {
        model = new FileEntity();
        controller = new PrincipalController(model, stage, user);

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
        archiveButton.getStyleClass().addAll("button-base", "archive-button");
        accountButton.getStyleClass().addAll("button-base", "account-button");
        logoutButton.getStyleClass().addAll("button-base", "logout-button");

        toolBar.getItems().addAll(newFileButton, importFileButton, archiveButton, accountButton, logoutButton);

        nameUserField.setDisable(true);
        nameUserField.setPrefWidth(120);
        nameUserField.getStyleClass().add("user-display-field");
        nameUserField.setText(user.getLastName()+" "+user.getFirstName());

        HBox.setHgrow(spacer, Priority.ALWAYS);
        topBar.getChildren().addAll(toolBar, spacer, nameUserField);
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
        leftTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            model.setLeftContent(newValue);
        });

        rightTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            model.setRightContent(newValue);
        });

        model.leftContentProperty().addListener((observable, oldValue, newValue) -> {
            if (!leftTextArea.getText().equals(newValue)) {
                leftTextArea.setText(newValue);
            }
        });

        model.rightContentProperty().addListener((observable, oldValue, newValue) -> {
            if (!rightTextArea.getText().equals(newValue)) {
                rightTextArea.setText(newValue);
            }
        });

        model.leftContentProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.isEmpty() && oldValue != null && !oldValue.isEmpty()) {
                Platform.runLater(() -> leftTextArea.requestFocus());
            }
        });
    }

    private void setupActions() {
        newFileButton.setOnAction(e -> {
            controller.newFile();
            leftTextArea.requestFocus();
        });

        importFileButton.setOnAction(e -> controller.importFile());
        archiveButton.setOnAction(e -> controller.archiveAction());
        accountButton.setOnAction(e -> controller.accountAction(user,accountButton));
        saveButton.setOnAction(e -> controller.saveFile());
        toXmlButton.setOnAction(e -> controller.toXMLButtonAction());
        toJsonButton.setOnAction(e -> controller.toJSONButtonAction());
        logoutButton.setOnAction(e -> controller.logoutAction(logoutButton));
    }

    public void simulateUserLogin(String userName) {
        model.setUserName(userName);
    }

}