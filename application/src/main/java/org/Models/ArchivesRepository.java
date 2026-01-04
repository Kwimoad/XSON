package org.Models;

import org.database.DatabaseConnection;
import org.dto.Archives;

import java.sql.*;

public class ArchivesRepository implements BaseRepository<Archives> {

    public Archives add() throws SQLException {
        String sql = "INSERT INTO Archives VALUES ()";
        Connection cn = DatabaseConnection.getInstance().getConnection();
        try (
                //Connection cn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return new Archives(rs.getInt(1));
            }
        }
        return null;
    }

    @Override
    public boolean remove(Archives o) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Archives o) throws SQLException {
        return false;
    }

    @Override
    public Archives findById(int id) throws SQLException {
        return null;
    }

}
