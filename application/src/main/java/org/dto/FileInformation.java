package org.dto;

import java.sql.Date;

public class FileInformation {

    private final int id;
    private String fileName;
    private String filePath;
    private Date lastModification;
    private FileExtension extension;
    private Archives archives;

    /**
     * Constructor to create an fileINformation with a specific ID.
     *
     * @param id
     * @param fileName
     * @param filePath
     * @param lastModification
     * @param extension
     */
    public FileInformation(int id, String fileName, String filePath, Date lastModification, FileExtension extension) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.lastModification = lastModification;
        this.extension = extension;
    }

    /**
     * Constructor to create an fileINformation without a specific ID.
     * The ID is set to -1 (undefined).
     *
     * @param fileName
     * @param filePath
     * @param lastModification
     * @param extension
     */
    public FileInformation(String fileName, String filePath, Date lastModification, FileExtension extension) {
        this.id = -1;
        this.fileName = fileName;
        this.filePath = filePath;
        this.lastModification = lastModification;
        this.extension = extension;
    }

    // setters & getters

    /**
     *
     * @return fileInformation id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     *
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     *
     * @return filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     *
     * @param filePath
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     *
     * @return lastModification
     */
    public Date getLastModification() {
        return lastModification;
    }

    /**
     *
     * @param lastModification
     */
    public void setLastModification(Date lastModification) {
        this.lastModification = lastModification;
    }

    /**
     *
     * @return extension of file
     */
    public FileExtension getExtension() {
        return extension;
    }

    /**
     *
     * @param extension
     */
    public void setExtension(FileExtension extension) {
        this.extension = extension;
    }

    /**
     *
     * @return archives object
     */
    public Archives getArchives() {
        return archives;
    }

    /**
     *
     * @param archives
     */
    public void setArchives(Archives archives) {
        this.archives = archives;
    }
}
