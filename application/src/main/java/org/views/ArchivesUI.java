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

/**
 * ArchivesUI
 *
 * This class represents the user interface for viewing and managing file archives in XSON.
 * It shows XML and JSON file pairs organized by date in a tree structure.
 * Users can select a file pair to see details like file names and paths.
 *
 * The class extends JavaFX Application and can be run as a standalone window.
 */
public class ArchivesUI extends Application {

    private static final String APP_STYLESHEET = "/styles/app.css";

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
        titleLabel.getStyleClass().add("page-title");

        Button backButton = new Button("← Back");
        backButton.getStyleClass().addAll("button-base", "button-secondary");

        backButton.setOnAction(e -> primaryStage.close());

        archiveTree = new TreeView<>();
        archiveTree.getStyleClass().add("tree-view");

        archiveTree.setCellFactory(tv -> new TreeCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    getStyleClass().removeAll("tree-level-0", "tree-level-1", "tree-level-2");
                } else {
                    setText(item);

                    TreeItem<String> treeItem = getTreeItem();
                    if (treeItem != null) {
                        int level = getTreeItemLevel(treeItem);
                        getStyleClass().removeAll("tree-level-0", "tree-level-1", "tree-level-2");
                        if (level == 0) {
                            getStyleClass().add("tree-level-0");
                        } else if (level == 1) { // Dates
                            getStyleClass().add("tree-level-1");
                        } else {
                            getStyleClass().add("tree-level-2");
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
        scrollPane.getStyleClass().add("scroll-transparent");

        Button refreshButton = new Button("Refresh");
        refreshButton.getStyleClass().addAll("button-base", "button-accent");

        refreshButton.setOnAction(e -> loadArchiveData(fileController));

        VBox fileInfoBox = new VBox(10);
        fileInfoBox.setPadding(new Insets(15));
        fileInfoBox.getStyleClass().add("info-box");

        Label infoTitle = new Label("File details");
        infoTitle.getStyleClass().add("section-title");

        Label xmlLabel = new Label("XML file:");
        xmlLabel.getStyleClass().add("info-label");

        xmlValue = new Label();
        xmlValue.getStyleClass().add("info-value");
        xmlValue.setMaxWidth(300);

        Label jsonLabel = new Label("JSON file:");
        jsonLabel.getStyleClass().add("info-label");

        jsonValue = new Label();
        jsonValue.getStyleClass().add("info-value");
        jsonValue.setMaxWidth(300);

        Label pathsLabel = new Label("Paths:");
        pathsLabel.getStyleClass().add("info-label");

        pathsValue = new Label();
        pathsValue.getStyleClass().add("info-value");
        pathsValue.setMaxWidth(300);

        fileInfoBox.getChildren().addAll(infoTitle, xmlLabel, xmlValue, jsonLabel, jsonValue, pathsLabel, pathsValue);

        archiveTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handleTreeSelection(newValue);
        });

        BorderPane mainPane = new BorderPane();
        mainPane.getStyleClass().add("page-background");

        HBox headerBox = new HBox(15);
        headerBox.setPadding(new Insets(20, 20, 10, 20));
        headerBox.getStyleClass().add("header-bar");

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
        treeLabel.getStyleClass().add("section-title");

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
        scene.getStylesheets().add(getClass().getResource(APP_STYLESHEET).toExternalForm());

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
        dialogPane.getStyleClass().add("alert-pane-error");

        alert.showAndWait();
    }

    public static void launchWithData(Map<Date, List<FilePairInfo>> fileArchives, Stage stage) {
        staticFileArchives = fileArchives;
        ArchivesUI archivesUI = new ArchivesUI(fileArchives);
        archivesUI.start(stage);
    }

}