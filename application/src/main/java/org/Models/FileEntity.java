package org.Models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.dto.FileExtension;

import java.io.File;

public class FileEntity {
    private StringProperty leftContent = new SimpleStringProperty("");
    private StringProperty rightContent = new SimpleStringProperty("");
    private ObjectProperty<File> currentFile = new SimpleObjectProperty<>();

    private ObjectProperty<FileExtension> currentFileType = new SimpleObjectProperty<>();

    private StringProperty userName = new SimpleStringProperty("");

    public StringProperty leftContentProperty() { return leftContent; }
    public StringProperty rightContentProperty() { return rightContent; }
    public ObjectProperty<File> currentFileProperty() { return currentFile; }
    public ObjectProperty<FileExtension> currentFileTypeProperty() { return currentFileType; }
    public StringProperty userNameProperty() { return userName; }

    public String getLeftContent() { return leftContent.get(); }
    public void setLeftContent(String content) { this.leftContent.set(content); }

    public String getRightContent() { return rightContent.get(); }
    public void setRightContent(String content) { this.rightContent.set(content); }

    public File getCurrentFile() { return currentFile.get(); }
    public void setCurrentFile(File file) { this.currentFile.set(file); }

    public FileExtension getCurrentFileType() { return currentFileType.get(); }
    public void setCurrentFileType(FileExtension type) { this.currentFileType.set(type); }

    public String getUserName() { return userName.get(); }
    public void setUserName(String name) { this.userName.set(name); }
}