package org.Models;

import org.database.DatabaseConnection;
import org.dto.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FilePairRepository {

    public boolean add(FilePair o) throws SQLException {
        String sql = "INSERT INTO FilePairs(LastModified, ArchivesID, XmlFileID, JsonFileID) values(?,?,?,?)";
        Connection cn = DatabaseConnection.getInstance().getConnection();
        try(
                //Connection cn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ){
            ps.setDate(1, o.getLastModified());
            ps.setInt(2, o.getArchivesID());
            ps.setInt(3, o.getXmlFileID());
            ps.setInt(4, o.getJsonFileID());
            ps.execute();
        }catch (Exception e){
            return  false;
        }
        return true;
    }

    public List<Date> findGroupDate(Archives archives) throws SQLException {
        List<Date> dates = new ArrayList<>();
        String sql = "SELECT DISTINCT LastModified FROM FilePairs ORDER BY LastModified where ArchivesID = ?";
        Connection cn = DatabaseConnection.getInstance().getConnection();
        try (
                //Connection cn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = cn.prepareStatement(sql);
        ) {
            ps.setInt(1, archives.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dates.add(rs.getDate("LastModified"));
            }
        }
        return dates;
    }

    public Map<Date, List<FilePairInfo>> findFilePairsGroupedByDate(int archiveID) throws SQLException {
        Map<Date, List<FilePairInfo>> result = new LinkedHashMap<>();
        String sql =
                "SELECT fp.LastModified, " +
                        "       fx.Filename AS xmlName, fx.Filepath AS xmlPath, " +
                        "       fj.Filename AS jsonName, fj.Filepath AS jsonPath " +
                        "FROM FilePairs fp " +
                        "LEFT JOIN Fileinformation fx ON fp.XmlFileID = fx.FileinformationID " +
                        "LEFT JOIN Fileinformation fj ON fp.JsonFileID = fj.FileinformationID " +
                        "WHERE fp.ArchivesID IN ( " +
                        "    SELECT ArchivesID FROM Archives WHERE ArchivesID = ? " +
                        ") " +
                        "ORDER BY fp.LastModified";
        Connection cn = DatabaseConnection.getInstance().getConnection();
        try (
                //Connection cn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = cn.prepareStatement(sql)
        ) {
            ps.setInt(1, archiveID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Date date = rs.getDate("LastModified");
                    FilePairInfo info = new FilePairInfo(
                            rs.getString("xmlName"),
                            rs.getString("jsonName"),
                            rs.getString("xmlPath"),
                            rs.getString("jsonPath")
                    );
                    result.computeIfAbsent(date, d -> new ArrayList<>()).add(info);
                }
            }
        }
        return result;
    }

}
