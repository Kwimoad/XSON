package org.controller;

import javafx.scene.control.Button;
import org.dto.FileExtension;
import org.dto.FileInformation;
import org.dto.FilePairInfo;
import org.dto.User;
import org.Models.ArchivesUsersRepository;
import org.Models.FileEntity;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.utils.withAPI.JsonToXml;
import org.utils.withoutAPI.XmlToJson;
import org.views.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class PrincipalController {

    private FileEntity model;
    private Stage stage;
    private boolean isXml = false;
    private boolean isJson = false;
    private File xmlFile = null;
    private File jsonFile = null;
    private User user;
    private FileController fileController;

    public PrincipalController(FileEntity model, Stage stage, User user) {
        this.model = model;
        this.stage = stage;
        this.user = user;
        this.fileController = new FileController();
    }

    public PrincipalController(FileEntity model, Stage stage) {
        this.model = model;
        this.stage = stage;
    }

    public void newFile() {
        model.setLeftContent("");
        model.setRightContent("");
        model.setCurrentFile(null);
        model.setCurrentFileType(null);
        isXml = false;
        isJson = false;
    }

    public void importFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ouvrir un fichier");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Fichiers XML et JSON (*.xml, *.json)", "*.xml", "*.json"
                )
        );
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try {
                String content = Files.readString(selectedFile.toPath());
                model.setCurrentFile(selectedFile);
                String fileName = selectedFile.getName().toLowerCase();
                if (fileName.endsWith(".xml")) {
                    xmlFile = selectedFile;
                    isXml = true;
                    isJson = false;
                    model.setCurrentFileType(FileExtension.XML);
                    model.setLeftContent(content);
                    model.setRightContent("");
                } else if (fileName.endsWith(".json")) {
                    jsonFile = selectedFile;
                    isJson = true;
                    isXml = false;
                    model.setCurrentFileType(FileExtension.JSON);
                    model.setRightContent(content);
                    model.setLeftContent("");
                }
            } catch (IOException e) {
                showError("Erreur de lecture", e.getMessage());
            }
        }
    }

    public void saveFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Sauvegarder le fichier");
            if (model.getCurrentFile() == null) {
                FileChooser sourceChooser = new FileChooser();
                sourceChooser.setTitle("Sauvegarder le fichier source");
                if (isXml) {
                    sourceChooser.getExtensionFilters().add(
                            new FileChooser.ExtensionFilter("Fichier XML", "*.xml")
                    );
                    sourceChooser.setInitialFileName("nouveau_fichier.xml");
                } else {
                    sourceChooser.getExtensionFilters().add(
                            new FileChooser.ExtensionFilter("Fichier JSON", "*.json")
                    );
                    sourceChooser.setInitialFileName("nouveau_fichier.json");
                }
                File sourceFile = sourceChooser.showSaveDialog(stage);
                if (sourceFile == null) return;
                String sourceContent = isXml ? model.getLeftContent() : model.getRightContent();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(sourceFile))) {
                    writer.write(sourceContent);
                }
                if (isXml) xmlFile = sourceFile;
                else jsonFile = sourceFile;
                System.out.println("Fichier source créé : " + sourceFile.getName() + " | Chemin : " + sourceFile.getAbsolutePath());
                FileChooser transformedChooser = new FileChooser();
                transformedChooser.setTitle("Sauvegarder le fichier transformé");
                if (isXml) {
                    transformedChooser.getExtensionFilters().add(
                            new FileChooser.ExtensionFilter("Fichier JSON", "*.json")
                    );
                    transformedChooser.setInitialFileName("nouveau_fichier.json");
                } else {
                    transformedChooser.getExtensionFilters().add(
                            new FileChooser.ExtensionFilter("Fichier XML", "*.xml")
                    );
                    transformedChooser.setInitialFileName("nouveau_fichier.xml");
                }
                File transformedFile = transformedChooser.showSaveDialog(stage);
                if (transformedFile == null) return;
                String transformedContent = isXml ? model.getRightContent() : model.getLeftContent();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(transformedFile))) {
                    writer.write(transformedContent);
                }
                if (isXml) jsonFile = transformedFile;
                else xmlFile = transformedFile;
                System.out.println("Fichier transformé créé : " + transformedFile.getName() + " | Chemin : " + transformedFile.getAbsolutePath());
                return;
            }
            String contentToSave;
            String defaultFileName;
            FileExtension fileType;
            if (isXml) {
                contentToSave = model.getRightContent();
                fileType = FileExtension.JSON;
                defaultFileName = model.getCurrentFile().getName().replaceAll("\\.xml$", ".json");
            } else if (isJson) {
                contentToSave = model.getLeftContent();
                fileType = FileExtension.XML;
                defaultFileName = model.getCurrentFile().getName().replaceAll("\\.json$", ".xml");
            } else {
                showError("Erreur", "Aucun contenu à sauvegarder !");
                return;
            }
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(
                            "Fichier " + fileType,
                            fileType == FileExtension.XML ? "*.xml" : "*.json"
                    )
            );
            fileChooser.setInitialFileName(defaultFileName);
            File fileToSave = fileChooser.showSaveDialog(stage);
            if (fileToSave != null) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                    writer.write(contentToSave);
                }
                if (isXml) jsonFile = fileToSave;
                else xmlFile = fileToSave;
                System.out.println("Fichier sauvegardé : " + fileToSave.getName() + " | Chemin : " + fileToSave.getAbsolutePath());
            }
            System.out.println("=== Récapitulatif ===");
            System.out.println("Fichier XML : " + (xmlFile != null ? xmlFile.getAbsolutePath() : "non créé"));
            System.out.println("Fichier JSON : " + (jsonFile != null ? jsonFile.getAbsolutePath() : "non créé"));
            if(user!=null){
                addSavedFile();
            }
        } catch (IOException e) {
            showError("Erreur de sauvegarde", e.getMessage());
        }
    }

    private void addSavedFile(){

        String nameXml = xmlFile.getName();
        String xmlPath = xmlFile.getPath();
        FileExtension xmlExtension = FileExtension.XML;

        String nameJson = jsonFile.getName();
        String jsonPath = jsonFile.getPath();
        FileExtension jsonExtension = FileExtension.JSON;

        Date lastModification = new Date();
        java.sql.Date sqlDate = new java.sql.Date(lastModification.getTime());

        int archivesID = archivesID(user);

        FileInformation xmlNewFile = new FileInformation(nameXml, xmlPath, sqlDate, xmlExtension);
        FileInformation jsonNewFile = new FileInformation(nameJson, jsonPath, sqlDate, jsonExtension);

        fileController.addFiles(xmlNewFile, jsonNewFile, archivesID);

    }

    public void archiveAction() {
        int archivesID = archivesID(user);
        Map<java.sql.Date, List<FilePairInfo>> fileArchives = fileController.findFileByDate(archivesID);
        Stage ArchivesStage = new Stage();
        ArchivesUI archivesUI = new ArchivesUI();
        archivesUI.setFileArchives(fileArchives);
        try {
            archivesUI.start(ArchivesStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void accountAction(User user, Button accountButton) {
        Stage accountStage = new Stage();
        AccountUI accountUI = new AccountUI(user);
        try {
            accountUI.start(accountStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logoutAction(Button logoutButton){
        Stage currentStage = (Stage) logoutButton.getScene().getWindow();
        Stage loginStage = new Stage();
        LoginUI loginUI = new LoginUI();
        try {
            loginUI.start(loginStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        currentStage.close();
    }

    public void toXMLButtonAction() {
        model.setCurrentFileType(FileExtension.JSON);
        isXml = false;
        isJson = true;
        if(user!=null){
            toXmlWithApi();
        }else{
            toXmlWithoutApi();
        }
    }

    public void toXmlWithApi(){
        if (model.getCurrentFileType() == FileExtension.JSON && !model.getRightContent().isEmpty()) {
            try {
                String xmlFormatted = org.utils.withAPI.JsonToXml.convertJsonToXmlPretty(
                        model.getRightContent(),
                        "root"
                );
                model.setLeftContent(xmlFormatted);
            } catch (Exception e) {
                showError("Erreur de conversion", e.getMessage());
            }
        } else {
            showError("Erreur", "Aucun fichier JSON ouvert !");
        }
    }

    public void toXmlWithoutApi() {
        if (model.getCurrentFileType() == FileExtension.JSON && !model.getRightContent().isEmpty()) {
            try {
                // Utilisation de la classe JsonToXml
                String xmlFormatted = org.utils.withoutAPI.JsonToXml.convertJsonToXmlPretty(
                        model.getRightContent(),
                        "root"
                );
                model.setLeftContent(xmlFormatted);
            } catch (Exception e) {
                showError("Erreur de conversion", e.getMessage());
            }
        } else {
            showError("Erreur", "Aucun fichier JSON ouvert !");
        }
    }

    public void toJSONButtonAction() {
        model.setCurrentFileType(FileExtension.XML);
        isXml = true;
        isJson = false;
        if(user!=null){
            toJsonWithApi();
        }else{
            toJsonWithoutApi();
        }
    }

    public void toJsonWithApi(){
        if (model.getCurrentFileType() == FileExtension.XML && !model.getLeftContent().isEmpty()) {
            try {
                String jsonFormatted = org.utils.withAPI.XmlToJson.convertPretty(model.getLeftContent());
                model.setRightContent(jsonFormatted);
            } catch (Exception e) {
                showError("Erreur de conversion", e.getMessage());
            }
        } else {
            showError("Erreur", "Aucun fichier XML ouvert !");
        }
    }

    public void toJsonWithoutApi(){
        if (model.getCurrentFileType() == FileExtension.XML && !model.getLeftContent().isEmpty()) {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new ByteArrayInputStream(model.getLeftContent().getBytes()));
                Element root = doc.getDocumentElement();
                XmlToJson.JsonNode jsonNode = XmlToJson.xmlToObject(root);
                String jsonFormatted = org.utils.withoutAPI.XmlToJson.toPrettyJSONString(jsonNode);
                model.setRightContent(jsonFormatted);
            } catch (Exception e) {
                showError("Erreur de conversion", e.getMessage());
            }
        } else {
            showError("Erreur", "Aucun fichier XML ouvert !");
        }
    }

    private void showError(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public int archivesID(User user){
        ArchivesUsersRepository archivesUsersRepository = new ArchivesUsersRepository();
        return archivesUsersRepository.findByUserId(user.getId());
    }

}