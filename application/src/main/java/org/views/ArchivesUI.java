package org.views;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.controller.FileController;
import org.dto.FilePairInfo;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import static org.views.style.AppColors.*;

public class ArchivesUI extends Application {

    private TreeView<String> archiveTree;
    private Map<Date, List<FilePairInfo>> filePairsByDate;

    private Label xmlValue;
    private Label jsonValue;
    private Label pathsValue;

    private static Map<Date, List<FilePairInfo>> staticFileArchives;

    public ArchivesUI() {
        // Constructeur vide pour Application
    }

    public ArchivesUI(Map<Date, List<FilePairInfo>> filePairsByDate) {
        this.filePairsByDate = filePairsByDate;
    }

    public void setFileArchives(Map<Date, List<FilePairInfo>> fileArchives) {
        this.filePairsByDate = fileArchives;
    }

    @Override
    public void start(Stage primaryStage) {
        if (filePairsByDate == null && staticFileArchives != null) {
            filePairsByDate = staticFileArchives;
        }

        FileController fileController = new FileController();

        Label titleLabel = new Label("Archives");
        titleLabel.setStyle("-fx-font-size: 24px; " +
                "-fx-font-weight: 700; " +
                "-fx-text-fill: " + PRIMARY_BLUE + "; " +
                "-fx-padding: 20 0 20 0;");

        Button backButton = new Button("← Back");
        backButton.setStyle("-fx-background-color: " + SECONDARY_BLUE + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 8 15; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 13px; " +
                "-fx-cursor: hand;");

        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-background-color: " + HOVER_BLUE + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 8 15; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 13px; " +
                "-fx-cursor: hand;"));
        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-background-color: " + SECONDARY_BLUE + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 8 15; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 13px; " +
                "-fx-cursor: hand;"));

        backButton.setOnAction(e -> primaryStage.close());

        archiveTree = new TreeView<>();
        archiveTree.setStyle("-fx-control-inner-background: " + CARD_BACKGROUND + "; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-width: 1; " +
                "-fx-border-radius: 8;");

        archiveTree.setCellFactory(tv -> new TreeCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle(null);
                } else {
                    setText(item);

                    TreeItem<String> treeItem = getTreeItem();
                    if (treeItem != null) {
                        int level = getTreeItemLevel(treeItem);
                        if (level == 0) {
                            setStyle("-fx-font-weight: bold; " +
                                    "-fx-font-size: 16px; " +
                                    "-fx-text-fill: " + PRIMARY_BLUE + "; " +
                                    "-fx-padding: 5 0 5 10;");
                        } else if (level == 1) { // Dates
                            setStyle("-fx-font-weight: 600; " +
                                    "-fx-font-size: 14px; " +
                                    "-fx-text-fill: " + TEXT_DARK + "; " +
                                    "-fx-padding: 5 0 5 20;");
                        } else {
                            setStyle("-fx-font-weight: normal; " +
                                    "-fx-font-size: 13px; " +
                                    "-fx-text-fill: #555555; " +
                                    "-fx-padding: 3 0 3 30;");
                        }
                    }
                }
            }

            private int getTreeItemLevel(TreeItem<String> item) {
                int level = 0;
                TreeItem<String> parent = item.getParent();
                while (parent != null) {
                    level++;
                    parent = parent.getParent();
                }
                return level;
            }
        });

        ScrollPane scrollPane = new ScrollPane(archiveTree);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; " +
                "-fx-border-color: transparent;");

        Button refreshButton = new Button("Refresh");
        refreshButton.setStyle("-fx-background-color: " + ACCENT_GREEN + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 8 20; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 13px; " +
                "-fx-cursor: hand;");

        refreshButton.setOnMouseEntered(e -> refreshButton.setStyle("-fx-background-color: " + ACCENT_GREEN + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 8 20; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 13px; " +
                "-fx-cursor: hand; " +
                "-fx-scale-x: 1.05; " +
                "-fx-scale-y: 1.05;"));
        refreshButton.setOnMouseExited(e -> refreshButton.setStyle("-fx-background-color: " + ACCENT_GREEN + "; " +
                "-fx-text-fill: " + TEXT_LIGHT + "; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 8 20; " +
                "-fx-background-radius: 6; " +
                "-fx-font-size: 13px; " +
                "-fx-cursor: hand;"));

        refreshButton.setOnAction(e -> loadArchiveData(fileController));

        VBox fileInfoBox = new VBox(10);
        fileInfoBox.setPadding(new Insets(15));
        fileInfoBox.setStyle("-fx-background-color: " + CARD_BACKGROUND + "; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-width: 1; " +
                "-fx-border-radius: 8;");

        Label infoTitle = new Label("File details");
        infoTitle.setStyle("-fx-font-weight: bold; " +
                "-fx-font-size: 16px; " +
                "-fx-text-fill: " + PRIMARY_BLUE + ";");

        Label xmlLabel = new Label("XML file:");
        xmlLabel.setStyle("-fx-font-weight: 600; -fx-text-fill: " + TEXT_DARK + ";");

        xmlValue = new Label();
        xmlValue.setStyle("-fx-text-fill: #555555; -fx-wrap-text: true;");
        xmlValue.setMaxWidth(300);

        Label jsonLabel = new Label("JSON file:");
        jsonLabel.setStyle("-fx-font-weight: 600; -fx-text-fill: " + TEXT_DARK + ";");

        jsonValue = new Label();
        jsonValue.setStyle("-fx-text-fill: #555555; -fx-wrap-text: true;");
        jsonValue.setMaxWidth(300);

        Label pathsLabel = new Label("Paths:");
        pathsLabel.setStyle("-fx-font-weight: 600; -fx-text-fill: " + TEXT_DARK + ";");

        pathsValue = new Label();
        pathsValue.setStyle("-fx-text-fill: #555555; -fx-font-size: 12px; -fx-wrap-text: true;");
        pathsValue.setMaxWidth(300);

        fileInfoBox.getChildren().addAll(infoTitle, xmlLabel, xmlValue, jsonLabel, jsonValue, pathsLabel, pathsValue);

        archiveTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handleTreeSelection(newValue);
        });

        BorderPane mainPane = new BorderPane();
        mainPane.setStyle("-fx-background-color: " + LIGHT_BACKGROUND + ";");

        HBox headerBox = new HBox(15);
        headerBox.setPadding(new Insets(20, 20, 10, 20));
        headerBox.setStyle("-fx-background-color: " + CARD_BACKGROUND + "; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-width: 0 0 1 0;");

        HBox leftHeader = new HBox(10, backButton);
        leftHeader.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        HBox centerHeader = new HBox(titleLabel);
        centerHeader.setAlignment(javafx.geometry.Pos.CENTER);

        HBox rightHeader = new HBox(10, refreshButton);
        rightHeader.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);

        HBox.setHgrow(centerHeader, Priority.ALWAYS);
        headerBox.getChildren().addAll(leftHeader, centerHeader, rightHeader);

        HBox contentBox = new HBox(20);
        contentBox.setPadding(new Insets(20));

        VBox treeBox = new VBox(10);
        treeBox.setPrefWidth(400);

        Label treeLabel = new Label("Archives by date:");
        treeLabel.setStyle("-fx-font-weight: 600; -fx-font-size: 14px; -fx-text-fill: " + TEXT_DARK + ";");

        treeBox.getChildren().addAll(treeLabel, scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        VBox infoContainer = new VBox(10);
        infoContainer.setPrefWidth(350);
        infoContainer.getChildren().add(fileInfoBox);

        contentBox.getChildren().addAll(treeBox, infoContainer);
        HBox.setHgrow(treeBox, Priority.ALWAYS);
        HBox.setHgrow(infoContainer, Priority.ALWAYS);

        mainPane.setTop(headerBox);
        mainPane.setCenter(contentBox);

        Scene scene = new Scene(mainPane, 1000, 650);

        primaryStage.setTitle("Archives - XSON");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setMaximized(true);
        loadArchiveData(fileController);

        primaryStage.show();
    }

    private void handleTreeSelection(TreeItem<String> selectedItem) {
        if (selectedItem == null || filePairsByDate == null) return;

        TreeItem<String> parent = selectedItem.getParent();
        if (parent == null) return;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(parent.getValue(), formatter);
            Date date = Date.valueOf(localDate);

            List<FilePairInfo> filePairs = filePairsByDate.get(date);
            if (filePairs == null) return;

            String selectedValue = selectedItem.getValue();

            for (FilePairInfo filePair : filePairs) {
                String expected = filePair.getXmlFileName() + " & " + filePair.getJsonFileName();
                if (expected.equals(selectedValue)) {

                    xmlValue.setText(filePair.getXmlFileName());
                    jsonValue.setText(filePair.getJsonFileName());

                    pathsValue.setText(
                            "XML: " + filePair.getXmlPath() + "\n" +
                                    "JSON: " + filePair.getJsonPath()
                    );
                    break;
                }
            }
        } catch (DateTimeParseException e) {
            showError("Invalid date format");
        }
    }


    private void loadArchiveData(FileController fileController) {
        try {
            if (filePairsByDate != null && !filePairsByDate.isEmpty()) {
                buildTreeView();
            } else {
                showError("No archive data available");
            }
        } catch (Exception e) {
            showError("Error while loading archives: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void buildTreeView() {
        TreeItem<String> rootItem = new TreeItem<>("Archives");
        rootItem.setExpanded(true);
        if (filePairsByDate != null && !filePairsByDate.isEmpty()) {
            for (Map.Entry<Date, List<FilePairInfo>> entry : filePairsByDate.entrySet()) {
                Date date = entry.getKey();
                List<FilePairInfo> filePairs = entry.getValue();
                String dateStr = date.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                TreeItem<String> dateItem = new TreeItem<>(dateStr);
                for (FilePairInfo filePair : filePairs) {
                    String pairLabel = filePair.getXmlFileName() + " & " + filePair.getJsonFileName();
                    TreeItem<String> pairItem = new TreeItem<>(pairLabel);
                    dateItem.getChildren().add(pairItem);
                }
                rootItem.getChildren().add(dateItem);
            }
        } else {
            TreeItem<String> emptyItem = new TreeItem<>("No archives found");
            rootItem.getChildren().add(emptyItem);
        }
        archiveTree.setRoot(rootItem);
        clearFileDetails();
    }

    private void clearFileDetails() {
        if (xmlValue != null) xmlValue.setText("");
        if (jsonValue != null) jsonValue.setText("");
        if (pathsValue != null) pathsValue.setText("");
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: " + CARD_BACKGROUND + "; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-width: 1; " +
                "-fx-border-radius: 8;");

        alert.showAndWait();
    }

    public static void launchWithData(Map<Date, List<FilePairInfo>> fileArchives, Stage stage) {
        staticFileArchives = fileArchives;
        ArchivesUI archivesUI = new ArchivesUI(fileArchives);
        archivesUI.start(stage);
    }

}