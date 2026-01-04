package org.dto;

import java.sql.Date;

public class FileInformation {

    private final int id;
    private String fileName;
    private String filePath;
    private Date lastModification;
    private FileExtension extension;
    private Archives archives;

    public FileInformation(int id, String fileName, String filePath, Date lastModification, FileExtension extension) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.lastModification = lastModification;
        this.extension = extension;
    }

    public FileInformation(String fileName, String filePath, Date lastModification, FileExtension extension) {
        this.id = -1;
        this.fileName = fileName;
        this.filePath = filePath;
        this.lastModification = lastModification;
        this.extension = extension;
    }

    public int getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getLastModification() {
        return lastModification;
    }

    public void setLastModification(Date lastModification) {
        this.lastModification = lastModification;
    }

    public FileExtension getExtension() {
        return extension;
    }

    public void setExtension(FileExtension extension) {
        this.extension = extension;
    }

    public Archives getArchives() {
        return archives;
    }

    public void setArchives(Archives archives) {
        this.archives = archives;
    }
}
