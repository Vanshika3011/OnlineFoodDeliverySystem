package com.narola.onlinefooddeliverysystem.dao;

import com.narola.onlinefooddeliverysystem.enums.UserRoles;
import com.narola.onlinefooddeliverysystem.exception.DAOLayerException;
import com.narola.onlinefooddeliverysystem.model.Restaurant;
import com.narola.onlinefooddeliverysystem.model.RestaurantAddress;
import com.narola.onlinefooddeliverysystem.session.CurrentUserCredentials;
import com.narola.onlinefooddeliverysystem.model.service.restaurant.RestaurantService;

import java.sql.*;

public class RestaurantDao {
    public static void registerRestaurant(Restaurant rest, int generatedId) throws DAOLayerException {
        try {
            String query = "Insert into restaurant(restaurant_name, restaurant_address_id, restaurant_contact, restaurant_email, owner_id, opens_at, closes_at, gst_number)" +
                    "values(?,?,?,?,?,?,?,?)";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, rest.getRestaurantName());
            stmt.setInt(2, generatedId);
            stmt.setString(3, rest.getRestaurantContact());
            stmt.setString(4, rest.getRestaurantEmail());
            stmt.setInt(5, CurrentUserCredentials.getCurrentUser().getUserId());
            stmt.setString(6, (rest.getOpensAt()));
            stmt.setString(7, (rest.getClosesAt()));
            stmt.setString(8, rest.getGstNo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while inserting restaurant details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while inserting restaurant details", e);
        }
    }

    //TODO : Change implementation. Return restaurant list. and remove sysout
    public static void getAllRestaurants() throws DAOLayerException {
        try {
            String query = "Select * from restaurant";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            System.out.println("id\t\tRestaurant Name");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2));
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching restaurant details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching restaurant details", e);
        }
    }

    //TODO : Change implementation. Return restaurant. and remove sysout
    public static void viewCurrentUsersRestaurants(int ownerId) throws DAOLayerException {
        try {
            String query = "Select * from restaurant where owner_id = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, ownerId);
            ResultSet rs = stmt.executeQuery();

            System.out.println("Restaurant Id\t\tRestaurant Name");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "\t\t\t\t\t" + rs.getString(2));
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching restaurant details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching restaurant details", e);
        }
    }

    //TODO: Change method name...
    public static Restaurant viewRestaurantById(int restId) throws DAOLayerException {
        try {
            String query = "Select * from restaurant where id = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, restId);
            ResultSet rs = stmt.executeQuery();
            Restaurant restaurant = new Restaurant();
            while (rs.next()) {
                restaurant.setRestaurantName(rs.getString(2));
                restaurant.setRestaurantAddressId(rs.getInt(3));
                restaurant.setRestaurantContact(rs.getString(4));
                restaurant.setRestaurantEmail(rs.getString(5));
                restaurant.setOpensAt(rs.getString(7));
                restaurant.setClosesAt(rs.getString(8));
            }
            return restaurant;
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching restaurant details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching restaurant details", e);
        }
    }

    public static int editRestaurantContact(int restId, int ownerId) throws DAOLayerException {
        try {
            Restaurant restDetails = viewRestaurantById(restId);

            RestaurantService.getRestUpdateDetails(restDetails);

            String query1 = "Update restaurant SET restaurant_name = ?, restaurant_contact = ?, restaurant_email = ?,opens_at = ?, closes_at = ? where id=? and owner_id=?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query1);

            stmt.setString(1, restDetails.getRestaurantName());
            stmt.setString(2, restDetails.getRestaurantContact());
            stmt.setString(3, restDetails.getRestaurantEmail());
            stmt.setString(4, restDetails.getOpensAt());
            stmt.setString(5, restDetails.getClosesAt());
            stmt.setInt(6, restId);
            stmt.setInt(7, ownerId);
            int isUpdated = stmt.executeUpdate();

            return isUpdated;
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while updating restaurant details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating restaurant details", e);
        }
    }

    public static void editRestaurantAddress(int restId, int currentUserId) throws DAOLayerException {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            Restaurant restDetails = viewRestaurantById(restId);

            int getRestAddId = restDetails.getRestaurantAddressId();
            String selectRestDetails = "select * from restaurant_address where id = ?";

            PreparedStatement ps = conn.prepareStatement(selectRestDetails);

            ps.setInt(1, getRestAddId);
            ResultSet rms = ps.executeQuery();

            RestaurantAddress restAdd = new RestaurantAddress();

            while (rms.next()) {
                restAdd.setRestaurantAddLine1(rms.getString(2));
                restAdd.setRestaurantAddLine1(rms.getString(3));
                restAdd.setPincode(rms.getInt(6));
            }
            RestaurantService.getRestAddUpdateDetails(restAdd);

            String query = "Update restaurant_address SET restaurant_address_line1 = ?, restaurant_address_line2 = ?, pincode = ? where id=? and (? =(select owner_id from restaurant r where id = ?) or ?=(select role_id from user where id = ?))";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, restAdd.getRestaurantAddLine1());
            stmt.setString(2, restAdd.getRestaurantAddLine2());
            stmt.setInt(3, restAdd.getPincode());
            stmt.setInt(4, getRestAddId);
            stmt.setInt(5, currentUserId);
            stmt.setInt(6, restId);
            stmt.setInt(7, 1);
            stmt.setInt(8, UserRoles.ADMIN.getRoleIdValue());

            int isUpdate = stmt.executeUpdate();

            if (isUpdate > 0) {
                System.out.println("Updated Restaurant Address Details!");
            } else
                System.out.println("Enter valid restaurant id");
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while updating restaurant details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating restaurant details", e);
        }
    }

    //TODO : Change implementation. Return restaurant. and remove sysout
    public static void viewRestaurantDetails(int restaurantId) throws DAOLayerException {
        try {
            String query = "Select r.restaurant_name, r.restaurant_contact, r.restaurant_email, r.opens_at, r.closes_at, r.gst_number, a.restaurant_address_line1, a.restaurant_address_line2, a.pincode, c.city_name, s.state_name from restaurant r   \n" +
                    "join restaurant_address a on  r.restaurant_address_id = a.id\n" +
                    "join city c on a.city = c.id\n" +
                    "join state s on a.state = s.id\n" +
                    "where r.id= ? and (r.owner_id =? or ?=(Select role_id from user u where u.id = ?) ) ";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, restaurantId);
            stmt.setInt(2, CurrentUserCredentials.currentUser.getUserId());
            stmt.setInt(3, 1);
            stmt.setInt(4, CurrentUserCredentials.currentUser.getUserId());

            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                System.out.println("Invalid restaurant id.");
                return;
            }
            System.out.println(rs.getString(1) + " Details");
            System.out.println("Name                : " + rs.getString(1));
            System.out.println("Contact No          : " + rs.getString(2));
            System.out.println("Email Id            : " + rs.getString(3));
            System.out.println("Address             : " + rs.getString(7) + ", " + rs.getString(8));
            System.out.println("City                : " + rs.getString(10) + ", " + rs.getString(9));
            System.out.println("State               : " + rs.getString(11));
            System.out.println("Hours of Operation  : " + rs.getTime(4) + " to " + rs.getTime(5));
            System.out.println("GST number          : " + rs.getString(6));
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching restaurant details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching restaurant details", e);
        }
    }

    public static int insertRestDetails(RestaurantAddress restAdd) throws DAOLayerException {
        try {
            String query = "Insert into restaurant_address(restaurant_address_line1, restaurant_address_line2, city, state, pincode) values(?,?,?,?,?)";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, restAdd.getRestaurantAddLine1());
            stmt.setString(2, restAdd.getRestaurantAddLine2());
            stmt.setInt(3, restAdd.getCityId());
            stmt.setInt(4, restAdd.getStateId());
            stmt.setInt(5, restAdd.getPincode());
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            int generatedId = -1;
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
            }
            return generatedId;
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while updating restaurant details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating restaurant details", e);
        }
    }
}
