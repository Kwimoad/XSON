package org.Models;

import org.database.DatabaseConnection;
import org.dto.FileExtension;
import org.dto.FileInformation;

import java.sql.*;

public class FileInformationRepository implements BaseRepository<FileInformation> {

    public FileInformation add(FileInformation o, int archiveId) throws SQLException {
        String sql = "INSERT INTO Fileinformation(fileName, filePath, lastModification, extension, ArchivesID) VALUES (?, ?, ?, ?, ?)";
        FileInformation fileInformation = null;
        Connection cn = DatabaseConnection.getInstance().getConnection();
        try (
                //Connection cn = DatabaseConnection.getInstance().getConnection();
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
                throw new SQLException("L'insertion a échoué, aucune ligne affectée.");
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
                    throw new SQLException("L'insertion a échoué, aucun ID généré.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return fileInformation;
    }

    @Override
    public boolean remove(FileInformation o) throws SQLException {
        String sql = "DELETE FROM Fileinformation where FileinformationID=?";
        Connection cn = DatabaseConnection.getInstance().getConnection();
        try(
                //Connection cn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = cn.prepareStatement(sql);
        ){
            ps.setInt(1, o.getId());
            ps.execute();
        }catch (Exception e){
            return  false;
        }
        return true;
    }

    @Override
    public boolean update(FileInformation o) throws SQLException {
        return false;
    }

    @Override
    public FileInformation findById(int id) throws SQLException {
        return null;
    }

}
