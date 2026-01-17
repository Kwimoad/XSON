package org.Models;

import org.database.DatabaseConnection;
import org.dto.FileExtension;
import org.dto.FileInformation;

import java.sql.*;

/**
 * This class manages database operations for FileInformation objects.
 */
public class FileInformationRepository implements BaseRepository<FileInformation> {

    /**
     * Add a new file record to the database.
     *
     * @param o fileInformation the FileInformation object
     * @param archiveId the ID of the archive to link the file
     * @return the FileInformation object with generated ID
     * @throws SQLException
     */
    public FileInformation add(FileInformation o, int archiveId) throws SQLException {
        String sql = "INSERT INTO Fileinformation(fileName, filePath, lastModification, extension, ArchivesID) VALUES (?, ?, ?, ?, ?)";
        FileInformation fileInformation = null;
        try (
                Connection cn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            ps.setString(1, o.getFileName());
            ps.setString(2, o.getFilePath());
            ps.setDate(3, o.getLastModification());
            String extension = (o.getExtension() == FileExtension.XML) ? "XML" : "JSON";
            ps.setString(4, extension);
            ps.setInt(5, archiveId);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Insert failed, no ID obtained.");
            }
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    fileInformation = new FileInformation(
                            generatedKeys.getInt(1),
                            o.getFileName(),
                            o.getFilePath(),
                            o.getLastModification(),
                            o.getExtension()
                    );
                } else {
                    throw new SQLException("Insert failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return fileInformation;
    }

    /**
     * Remove a file record from the database.
     *
     * @param o fileInformation the FileInformation object to remove
     * @return true if removed successfully, false otherwise
     * @throws SQLException
     */
    @Override
    public boolean remove(FileInformation o) throws SQLException {
        String sql = "DELETE FROM Fileinformation where FileinformationID=?";
        try(
                Connection cn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = cn.prepareStatement(sql);
        ){
            ps.setInt(1, o.getId());
            ps.execute();
        }catch (Exception e){
            return  false;
        }
        return true;
    }

    /**
     *
     * @param o the object to update
     * @return
     * @throws SQLException
     */
    @Override
    public boolean update(FileInformation o) throws SQLException {
        return false;
    }

    /**
     *
     * @param id the object ID
     * @return
     * @throws SQLException
     */
    @Override
    public FileInformation findById(int id) throws SQLException {
        return null;
    }

}
