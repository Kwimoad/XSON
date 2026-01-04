package org.Models;

import org.database.DatabaseConnection;
import org.dto.Account;

import java.sql.*;

public class AccountRepository implements BaseRepository<Account> {

    public Account add(Account o) throws SQLException {
        String sql = "INSERT INTO Account(email, password) values(?,?)";
        Connection cn = DatabaseConnection.getInstance().getConnection();
        try(
                PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ){
            ps.setString(1, o.getEmail());
            ps.setString(2, o.getPassword());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                return new Account(id, o.getEmail(), o.getPassword());
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean remove(Account o) throws SQLException {
        String sql = "DELETE FROM Account where AccountID=?";
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
    public boolean update(Account o) throws SQLException {
        String sql = "UPDATE Account set email=?, password=?";
        Connection cn = DatabaseConnection.getInstance().getConnection();
        try(
                //Connection cn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = cn.prepareStatement(sql);
        ){
            //PreparedStatement ps = cnx.prepareStatement(sql,PreparedStatment.RETURN_GENERATED_KEYS);
            ps.setString(1, o.getEmail());
            ps.setString(2, o.getPassword());
            ps.executeUpdate();
        }catch (Exception e){
            return  false;
        }
        return true;
    }

    @Override
    public Account findById(int id) throws SQLException {
        String sql = "SELECT * FROM Account WHERE AccountID = ?";
        Connection cn = DatabaseConnection.getInstance().getConnection();
        try (
                //Connection cn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = cn.prepareStatement(sql);
        ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Account e = new Account(
                        rs.getInt("AccountID"),
                        rs.getString("email"),
                        rs.getString("password")
                );
                return e;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account find(String email) throws SQLException {
        String sql = "SELECT * FROM Account WHERE email = ?";
        Connection cn = DatabaseConnection.getInstance().getConnection();
        try (
                PreparedStatement ps = cn.prepareStatement(sql);
        ) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Account e = new Account(
                        rs.getInt("AccountID"),
                        rs.getString("email"),
                        rs.getString("password")
                );
                return e;
            }
        } catch (Exception e) {
            e.printStackTrace(); return null;
        }
        return null;
    }

}
