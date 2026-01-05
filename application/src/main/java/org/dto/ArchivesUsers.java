package org.dto;

/**
 * The ArchivesUsers class represents the relationship between a user and an archive.
 * Each object links a user to a specific archive.
 */
public class ArchivesUsers {
    private int userID;
    private int archivesID;

    /**
     * Constructor to create a link between a user and an archive.
     *
     * @param userID
     * @param archivesID
     */
    public ArchivesUsers(int userID, int archivesID) {
        this.userID = userID;
        this.archivesID = archivesID;
    }

    // setters & getters

    /**
     *
     * @return user id
     */
    public int getUserID() {
        return userID;
    }

    /**
     *
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     *
     * @return archives id
     */
    public int getArchivesID() {
        return archivesID;
    }

    /**
     *
     * @param archivesID
     */
    public void setArchivesID(int archivesID) {
        this.archivesID = archivesID;
    }
}
