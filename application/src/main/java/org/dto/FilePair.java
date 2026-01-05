package org.dto;

import java.sql.Date;

public class FilePair {
    private final int FilePairID;
    private Date LastModified;
    private int ArchivesID;
    private int XmlFileID;
    private int JsonFileID;

    /**
     * Constructor to create an filePair with a specific filePair ID.
     *
     * @param jsonFileID
     * @param xmlFileID
     * @param archivesID
     * @param lastModified
     * @param filePairID
     */
    public FilePair(int jsonFileID, int xmlFileID, int archivesID, Date lastModified, int filePairID) {
        JsonFileID = jsonFileID;
        XmlFileID = xmlFileID;
        ArchivesID = archivesID;
        LastModified = lastModified;
        FilePairID = filePairID;
    }

    /**
     * Constructor to create an filePair without a specific filePair ID.
     * The ID is set to -1 (undefined).
     *
     * @param jsonFileID
     * @param xmlFileID
     * @param archivesID
     * @param lastModified
     */
    public FilePair(int jsonFileID, int xmlFileID, int archivesID, Date lastModified) {
        JsonFileID = jsonFileID;
        XmlFileID = xmlFileID;
        ArchivesID = archivesID;
        LastModified = lastModified;
        FilePairID = -1;
    }

    // setters & getters

    /**
     *
     * @return jsonFile ID
     */
    public int getJsonFileID() {
        return JsonFileID;
    }

    /**
     *
     * @param jsonFileID
     */
    public void setJsonFileID(int jsonFileID) {
        JsonFileID = jsonFileID;
    }

    /**
     *
     * @return xmlFile ID
     */
    public int getXmlFileID() {
        return XmlFileID;
    }

    /**
     *
     * @param xmlFileID
     */
    public void setXmlFileID(int xmlFileID) {
        XmlFileID = xmlFileID;
    }

    /**
     *
     * @return archives ID
     */
    public int getArchivesID() {
        return ArchivesID;
    }

    /**
     *
     * @param archivesID
     */
    public void setArchivesID(int archivesID) {
        ArchivesID = archivesID;
    }

    /**
     *
     * @return lastModified
     */
    public Date getLastModified() {
        return LastModified;
    }

    /**
     *
     * @param lastModified
     */
    public void setLastModified(Date lastModified) {
        LastModified = lastModified;
    }

    /**
     *
     * @return filePair ID
     */
    public int getFilePairID() {
        return FilePairID;
    }
}
