package org.dto;

public class FilePairInfo {
    private String xmlFileName;
    private String jsonFileName;
    private String xmlPath;
    private String jsonPath;

    /**
     * Constructor to create an filePairInfo
     *
     * @param xmlFileName
     * @param jsonFileName
     * @param xmlPath
     * @param jsonPath
     */
    public FilePairInfo(String xmlFileName, String jsonFileName, String xmlPath, String jsonPath) {
        this.xmlFileName = xmlFileName;
        this.jsonFileName = jsonFileName;
        this.xmlPath = xmlPath;
        this.jsonPath = jsonPath;
    }

    // setters & getters

    /**
     *
     * @return xmlFileName
     */
    public String getXmlFileName() {
        return xmlFileName;
    }

    /**
     *
     * @param xmlFileName
     */
    public void setXmlFileName(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    /**
     *
     * @return jsonFileName
     */
    public String getJsonFileName() {
        return jsonFileName;
    }

    /**
     *
     * @param jsonFileName
     */
    public void setJsonFileName(String jsonFileName) {
        this.jsonFileName = jsonFileName;
    }

    /**
     *
     * @return xmlPath
     */
    public String getXmlPath() {
        return xmlPath;
    }

    /**
     *
     * @param xmlPath
     */
    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    /**
     *
     * @return jsonPath
     */
    public String getJsonPath() {
        return jsonPath;
    }

    /**
     *
     * @param jsonPath
     */
    public void setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
    }

}
