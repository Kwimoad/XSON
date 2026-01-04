package org.Models;

import org.database.DatabaseConnection;
import org.dto.Archives;
import org.dto.User;

import java.sql.*;

public class ArchivesUsersRepository {

    public boolean add(User user, Archives archives) throws SQLException {
        String sql = "INSERT INTO ArchivesUser VALUES (?,?)";
        Connection cn = DatabaseConnection.getInstance().getConnection();
        try(
                //Connection cn = DatabaseConnection.getInstance().getConnection();
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

    public int findByUserId(int userID) {
        int archivesId = -1; // valeur par défaut si rien trouvé
        String sql = "SELECT ArchivesID FROM ArchivesUser WHERE UserID = ?";
        Connection cn = DatabaseConnection.getInstance().getConnection();
        try (
                //Connection cn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = cn.prepareStatement(sql)
        ) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                archivesId = rs.getInt("ArchivesID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return archivesId;
    }

}
