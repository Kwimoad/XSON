package org.dto;

public class ArchivesUsers {
    private int userID;
    private int archivesID;

    public ArchivesUsers(int userID, int archivesID) {
        this.userID = userID;
        this.archivesID = archivesID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getArchivesID() {
        return archivesID;
    }

    public void setArchivesID(int archivesID) {
        this.archivesID = archivesID;
    }
}
