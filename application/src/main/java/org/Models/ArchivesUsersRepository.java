package org.Models;

import org.database.DatabaseConnection;
import org.dto.Archives;
import org.dto.User;

import java.sql.*;

/**
 * This class manages the relationship between Users and Archives in the database.
 */
public class ArchivesUsersRepository {

    /**
     * Add a record linking a user to an archive.
     *
     * @param user the user
     * @param archives the archive
     * @return true if the link was added successfully, false otherwise
     * @throws SQLException
     */
    public boolean add(User user, Archives archives) throws SQLException {
        String sql = "INSERT INTO ArchivesUser VALUES (?,?)";
        try(
                Connection cn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ){
            ps.setInt(1, user.getId());
            ps.setInt(2, archives.getId());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return true;
            }
        }catch (Exception e){
            return  false;
        }
        return false;
    }

    /**
     * Find the archive ID associated with a given user ID.
     *
     * @param userID the user ID
     * @return the archive ID if found, -1 otherwise
     */
    public int findByUserId(int userID) {
        int archivesId = -1;
        String sql = "SELECT ArchivesID FROM ArchivesUser WHERE UserID = ?";
        try (
                Connection cn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = cn.prepareStatement(sql)
        ) {
            ps.setInt(1, userID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    archivesId = rs.getInt("ArchivesID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return archivesId;
    }

}
