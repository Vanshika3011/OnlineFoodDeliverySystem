/*
package com.narola.onlinefooddeliverysystem.dao;

import com.narola.onlinefooddeliverysystemV1.exception.DAOLayerException;
import com.narola.onlinefooddeliverysystemV1.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.narola.onlinefooddeliverysystemV1.utility.Utility.encryptPassword;

public class UserDao {

    public static boolean doesUsernameExist(String username) throws DAOLayerException {
        try {
            int count = 0;
            String query = "select count(*) from user where username = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            return count > 0;
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while verifying user details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while verifying user details", e);
        }
    }

    public static boolean doesUserEmailExist(String emailId) throws DAOLayerException{
        try {
            int count = 0;
            String query = "select count(*) from user where email = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, emailId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            return count > 0;
        }catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while verifying user details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while verifying user details", e);
        }
    }

    public static List<User> getUsersDetails(int roleId) throws DAOLayerException{
        List<User> userList = new ArrayList<>();
        try {
            String query = "Select u.id, u.username, u.firstName, u.lastName, u.contact, u.email from user u where role_id = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, roleId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setContact(rs.getString(5));
                user.setEmail(rs.getString(6));
                user.setFirstName(rs.getString(3));
                user.setLastName(rs.getString(4));
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching user details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching user details", e);
        }return userList;
    }

    public static void createUser(User user) throws DAOLayerException{
        try {
            String query = "Insert into `user` (username, password, firstName, lastName, contact, email, role_id,verification_code) " +
                    "values(?,?,?,?,?,?,?,?)";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, user.getUsername());
            stmt.setString(2, encryptPassword(user.getPassword()));
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.setString(5, user.getContact());
            stmt.setString(6, user.getEmail());
            stmt.setInt(7, user.getRoleId());
            stmt.setInt(8, user.getVerificationCode());

            stmt.executeUpdate();


        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while updating user details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating user details", e);
        }
    }

    public static User validateUser(User user) throws DAOLayerException{
        try {
            String query = "Select * from user where username = ?  and password = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, encryptPassword(user.getPassword()));
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            rs.previous();
            while (rs.next()) {
                user.setUserId(rs.getInt(1));
                user.setFirstName(rs.getString(4));
                user.setLastName(rs.getString(5));
                user.setContact(rs.getString(6));
                user.setEmail(rs.getString(7));
                user.setRoleId(rs.getInt(8));
                user.setVerificationCode(rs.getInt(9));
                user.setVerified(rs.getBoolean(10));
                user.setCreatedAt(rs.getTimestamp(11).toLocalDateTime());
                user.setUpdatedAt(rs.getTimestamp(12).toLocalDateTime());
                user.setCreatedBy(rs.getInt(13));
                user.setUpdatedBy(rs.getInt(14));
            }
            return user;

        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching user details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching user details", e);
        }
    }

    public static void updateIsVerified(String username) throws DAOLayerException{
        String query = "Update user SET is_verified = 1, verification_code=null where username=?";
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while updating user details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating user details", e);
        }
    }
}*/
