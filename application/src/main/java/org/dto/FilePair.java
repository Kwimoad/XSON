package org.dto;

import java.sql.Date;

public class FilePair {
    private final int FilePairID;
    private Date LastModified;
    private int ArchivesID;
    private int XmlFileID;
    private int JsonFileID;

    public FilePair(int jsonFileID, int xmlFileID, int archivesID, Date lastModified, int filePairID) {
        JsonFileID = jsonFileID;
        XmlFileID = xmlFileID;
        ArchivesID = archivesID;
        LastModified = lastModified;
        FilePairID = filePairID;
    }

    public FilePair(int jsonFileID, int xmlFileID, int archivesID, Date lastModified) {
        JsonFileID = jsonFileID;
        XmlFileID = xmlFileID;
        ArchivesID = archivesID;
        LastModified = lastModified;
        FilePairID = -1;
    }

    public int getJsonFileID() {
        return JsonFileID;
    }

    public void setJsonFileID(int jsonFileID) {
        JsonFileID = jsonFileID;
    }

    public int getXmlFileID() {
        return XmlFileID;
    }

    public void setXmlFileID(int xmlFileID) {
        XmlFileID = xmlFileID;
    }

    public int getArchivesID() {
        return ArchivesID;
    }

    public void setArchivesID(int archivesID) {
        ArchivesID = archivesID;
    }

    public Date getLastModified() {
        return LastModified;
    }

    public void setLastModified(Date lastModified) {
        LastModified = lastModified;
    }

    public int getFilePairID() {
        return FilePairID;
    }
}
