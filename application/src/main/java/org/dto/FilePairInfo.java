package org.dto;

public class FilePairInfo {
    private String xmlFileName;
    private String jsonFileName;
    private String xmlPath;
    private String jsonPath;

    public FilePairInfo(String xmlFileName, String jsonFileName, String xmlPath, String jsonPath) {
        this.xmlFileName = xmlFileName;
        this.jsonFileName = jsonFileName;
        this.xmlPath = xmlPath;
        this.jsonPath = jsonPath;
    }

    public String getXmlFileName() {
        return xmlFileName;
    }

    public void setXmlFileName(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public String getJsonFileName() {
        return jsonFileName;
    }

    public void setJsonFileName(String jsonFileName) {
        this.jsonFileName = jsonFileName;
    }

    public String getXmlPath() {
        return xmlPath;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public String getJsonPath() {
        return jsonPath;
    }

    public void setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
    }

}
