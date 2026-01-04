package org.Models;

import org.database.DatabaseConnection;
import org.dto.Account;
import org.dto.Gender;
import org.dto.User;

import java.sql.*;

public class UserRepository implements BaseRepository<User> {

    //@Override
    public User add(User o, Account account) throws SQLException {
        String sql = "INSERT INTO User(firstName, lastName, gender, dateOfBirth, accountID) values(?,?,?,?,?)";
        Connection cn = DatabaseConnection.getInstance().getConnection();
        try(
                //Connection cn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ){
            ps.setString(1, o.getFirstName());
            ps.setString(2, o.getLastName());
            String gender = (o.getGender()== Gender.MALE) ? "MALE" : "FEMALE";
            ps.setString(3, gender);
            ps.setDate(4, o.getDateOfBirth());
            ps.setInt(5, account.getId());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return new User(rs.getInt(1), o.getFirstName(), o.getLastName(), o.getDateOfBirth(), o.getGender(), account);
            }
        }catch (Exception e){
            return  null;
        }
        return null;
    }

    @Override
    public boolean remove(User o) throws SQLException {
        String sql = "DELETE FROM User where UserID=?";
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
    public boolean update(User o) throws SQLException {
        String sql = "UPDATE `User` set Firstname=?, Lastname=?, Gender=?, Dateofbirth=? where UserID = ?";
        Connection cn = DatabaseConnection.getInstance().getConnection();
        try(
                //Connection cn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = cn.prepareStatement(sql);
        ){
            ps.setString(1, o.getFirstName());
            ps.setString(2, o.getLastName());
            String gender = (o.getGender()== Gender.MALE) ? "MALE" : "FEMALE";
            ps.setString(3, gender);
            ps.setDate(4, o.getDateOfBirth());
            ps.setInt(5, o.getId());
            ps.executeUpdate();
        }catch (Exception e){
            return  false;
        }
        return true;
    }

    @Override
    public User findById(int id) throws SQLException {
        String sql = "SELECT * FROM User WHERE UserID = ?";
        Connection cn = DatabaseConnection.getInstance().getConnection();
        try (
                //Connection cn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = cn.prepareStatement(sql);
        ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Gender gender = (rs.getString("gender").equals("MALE")) ? Gender.MALE : Gender.FEMALE;
                User e = new User(
                        rs.getInt("UserID"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        gender,
                        rs.getDate("dateofbirth")
                );
                return e;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User findByFk(int fk) throws SQLException {
        String sql = "SELECT * FROM User WHERE AccountID = ?";
        Connection cn = DatabaseConnection.getInstance().getConnection();
        try (
                //Connection cn = DatabaseConnection.getInstance().getConnection();
                PreparedStatement ps = cn.prepareStatement(sql);
        ) {
            ps.setInt(1, fk);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Gender gender = (rs.getString("gender").equals("MALE")) ? Gender.MALE : Gender.FEMALE;
                User e = new User(
                        rs.getInt("UserID"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        gender,
                        rs.getDate("dateofbirth")
                );
                return e;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
