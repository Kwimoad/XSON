package org.dto;

import java.util.List;

/**
 * The Archives class represents a collection of files stored under an archive.
 * Each archive has a unique ID and can contain multiple FileInformation objects.
 */
public class Archives {

    private final int id;
    private List<FileInformation> fileInformation;

    /**
     * Constructor to create an archive with a specific ID.
     * Initially, the archive does not contain any files.
     *
     * @param id Unique archive ID
     */
    public Archives(int id) {
        this.id = id;
    }

    /**
     * Constructor to create an archive with an ID and a list of files.
     *
     * @param id               Unique archive ID
     * @param fileInformation  List of files in the archive
     */
    public Archives(int id, List<FileInformation> fileInformation){
        this.id = id;
        this.fileInformation = fileInformation;
    }

    // setters & getters

    /**
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return list of FileInformation objects
     */
    public List<FileInformation> getFileInformation() {
        return fileInformation;
    }

    /**
     *
     * @param fileInformation new list of files
     */
    public void setFileInformation(List<FileInformation> fileInformation) {
        this.fileInformation = fileInformation;
    }

}
