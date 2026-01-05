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
import static org.views.style.AppColors.*;

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
        archiveButton.setStyle("-fx-background-color: " + ARCHIVE_COLOR + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 8 20; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 13px; " +
                "-fx-cursor: hand;");
        accountButton.setStyle("-fx-background-color: " + ACCENT_GREEN + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 8 20; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 13px; " +
                "-fx-cursor: hand;");
        logoutButton.setStyle("-fx-background-color: " + "#DC3545" + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 8 20; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 13px; " +
                "-fx-cursor: hand;");

        setupHoverEffects();

        toolBar.getItems().addAll(newFileButton, importFileButton, archiveButton, accountButton, logoutButton);

        nameUserField.setDisable(true);
        nameUserField.setPrefWidth(120);
        nameUserField.setStyle("-fx-background-color: " + LIGHT_BACKGROUND + "; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-width: 1; " +
                "-fx-border-radius: 4; " +
                "-fx-padding: 6 10; " +
                "-fx-font-weight: 500; " +
                "-fx-text-fill: " + TEXT_DARK + ";");
        nameUserField.setText(user.getLastName()+" "+user.getFirstName());

        HBox.setHgrow(spacer, Priority.ALWAYS);
        topBar.getChildren().addAll(toolBar, spacer, nameUserField);
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
        archiveButton.setOnMouseEntered(e -> archiveButton.setStyle(
                "-fx-background-color: " + HOVER_ORANGE + "; " +
                        "-fx-text-fill: " + TEXT_LIGHT + "; " +
                        "-fx-font-weight: 600; " +
                        "-fx-padding: 8 20; " +
                        "-fx-background-radius: 6; " +
                        "-fx-font-size: 13px; " +
                        "-fx-cursor: hand;"));
        archiveButton.setOnMouseExited(e -> archiveButton.setStyle(
                "-fx-background-color: " + ARCHIVE_COLOR + "; " +
                        "-fx-text-fill: " + TEXT_LIGHT + "; " +
                        "-fx-font-weight: 600; " +
                        "-fx-padding: 8 20; " +
                        "-fx-background-radius: 6; " +
                        "-fx-font-size: 13px; " +
                        "-fx-cursor: hand;"));
        accountButton.setOnMouseEntered(e -> accountButton.setStyle(
                "-fx-background-color: " + HOVER_GREEN + "; " +
                        "-fx-text-fill: " + TEXT_LIGHT + "; " +
                        "-fx-font-weight: 600; " +
                        "-fx-padding: 8 20; " +
                        "-fx-background-radius: 6; " +
                        "-fx-font-size: 13px; " +
                        "-fx-cursor: hand;"));
        accountButton.setOnMouseExited(e -> accountButton.setStyle(
                "-fx-background-color: " + ACCENT_GREEN + "; " +
                        "-fx-text-fill: " + TEXT_LIGHT + "; " +
                        "-fx-font-weight: 600; " +
                        "-fx-padding: 8 20; " +
                        "-fx-background-radius: 6; " +
                        "-fx-font-size: 13px; " +
                        "-fx-cursor: hand;"));
        logoutButton.setOnMouseEntered(e -> logoutButton.setStyle(
                "-fx-background-color: " + "#C82333" + "; " +
                        "-fx-text-fill: " + TEXT_LIGHT + "; " +
                        "-fx-font-weight: 600; " +
                        "-fx-padding: 8 20; " +
                        "-fx-background-radius: 6; " +
                        "-fx-font-size: 13px; " +
                        "-fx-cursor: hand;"));
        logoutButton.setOnMouseExited(e -> logoutButton.setStyle(
                "-fx-background-color: " + "#DC3545" + "; " +
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