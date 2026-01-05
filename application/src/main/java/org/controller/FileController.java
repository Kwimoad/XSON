package org.controller;

import org.dto.FileInformation;
import org.dto.FilePair;
import org.dto.FilePairInfo;
import org.Models.FileInformationRepository;
import org.Models.FilePairRepository;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * This class manages file operations.
 */
public class FileController {

    private FilePairRepository filePairRepository = new FilePairRepository();
    private FileInformationRepository fileInformationRepository = new FileInformationRepository();

    /**
     * Add a pair of files (XML and JSON).
     *
     * @param xmlFile XML file information
     * @param jsonFile JSON file information
     * @param archiveId archive identifier
     */
    public void addFiles(FileInformation xmlFile, FileInformation jsonFile, int archiveId){
        try {
            FileInformation newXmlFile = fileInformationRepository.add(xmlFile, archiveId);
            FileInformation newJsonFile = fileInformationRepository.add(jsonFile, archiveId);
            FilePair filePair = new FilePair(newJsonFile.getId(), newXmlFile.getId(), archiveId, newXmlFile.getLastModification());
            filePairRepository.add(filePair);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get files grouped by date for a specific archive.
     *
     * @param archiveID archive identifier
     * @return files grouped by date
     */
    public Map<Date, List<FilePairInfo>> findFileByDate(int archiveID){
        try {
            return filePairRepository.findFilePairsGroupedByDate(archiveID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
