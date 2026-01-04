package org.dto;

import java.util.List;

public class Archives {

    private final int id;
    private List<FileInformation> fileInformation;

    public Archives(int id) {
        this.id = id;
    }

    public Archives(int id, List<FileInformation> fileInformation){
        this.id = id;
        this.fileInformation = fileInformation;
    }

    public int getId() {
        return id;
    }

    public List<FileInformation> getFileInformation() {
        return fileInformation;
    }

    public void setFileInformation(List<FileInformation> fileInformation) {
        this.fileInformation = fileInformation;
    }

}
