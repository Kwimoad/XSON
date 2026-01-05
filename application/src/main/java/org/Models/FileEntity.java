package org.Models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.dto.FileExtension;

import java.io.File;

/**
 * The FileEntity class represents a file and its related data in the application.
 * It uses JavaFX properties to allow data binding with the user interface.
 */
public class FileEntity {
    private StringProperty leftContent = new SimpleStringProperty("");
    private StringProperty rightContent = new SimpleStringProperty("");
    private ObjectProperty<File> currentFile = new SimpleObjectProperty<>();

    private ObjectProperty<FileExtension> currentFileType = new SimpleObjectProperty<>();

    private StringProperty userName = new SimpleStringProperty("");

    /**
     * Returns the left content property.
     *
     * @return leftContent property
     */
    public StringProperty leftContentProperty() { return leftContent; }

    /**
     * Returns the right content property.
     *
     * @return rightContent property
     */

    public StringProperty rightContentProperty() { return rightContent; }

    /**
     * Returns the current file property.
     *
     * @return currentFile property
     */
    public ObjectProperty<File> currentFileProperty() { return currentFile; }

    /**
     * Returns the current file type property.
     *
     * @return currentFileType property
     */
    public ObjectProperty<FileExtension> currentFileTypeProperty() { return currentFileType; }

    /**
     * Returns the username property.
     *
     * @return userName property
     */
    public StringProperty userNameProperty() { return userName; }

    /**
     *
     * @return left content
     */
    public String getLeftContent() { return leftContent.get(); }

    /**
     *
     * @param content new left content
     */
    public void setLeftContent(String content) { this.leftContent.set(content); }

    /**
     *
     * @return right content
     */
    public String getRightContent() { return rightContent.get(); }

    /**
     *
     * @param content new right content
     */
    public void setRightContent(String content) { this.rightContent.set(content); }

    /**
     *
     * @return current file
     */
    public File getCurrentFile() { return currentFile.get(); }

    /**
     *
     * @param file new current file
     */
    public void setCurrentFile(File file) { this.currentFile.set(file); }

    /**
     *
     * @return file type
     */
    public FileExtension getCurrentFileType() { return currentFileType.get(); }

    /**
     *
     * @param type new file type
     */
    public void setCurrentFileType(FileExtension type) { this.currentFileType.set(type); }

    /**
     *
     * @return user name
     */
    public String getUserName() { return userName.get(); }

    /**
     *
     * @param name new user name
     */
    public void setUserName(String name) { this.userName.set(name); }

}