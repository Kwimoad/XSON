package org.Models;

import org.database.DatabaseConnection;
import org.dto.Archives;

import java.sql.*;

/**
 * This class manages database operations for Archives.
 */
public class ArchivesRepository implements BaseRepository<Archives> {

    /**
     * Add a new archive in the database.
     *
     * @return the newly created Archives object with generated ID
     * @throws SQLException
     */
    public Archives add() throws SQLException {
        String sql = "INSERT INTO Archives VALUES ()";
        try (
                Connection cn = DatabaseConnection.getInstance().getConnection();
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

    /**
     *
     * @param o the object to remove
     * @return
     * @throws SQLException
     */
    @Override
    public boolean remove(Archives o) throws SQLException {
        return false;
    }

    /**
     *
     * @param o the object to update
     * @return
     * @throws SQLException
     */
    @Override
    public boolean update(Archives o) throws SQLException {
        return false;
    }

    /**
     *
     * @param id the object ID
     * @return
     * @throws SQLException
     */
    @Override
    public Archives findById(int id) throws SQLException {
        return null;
    }

}
