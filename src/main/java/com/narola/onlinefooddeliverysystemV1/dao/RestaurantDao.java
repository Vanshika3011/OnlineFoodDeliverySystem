package com.narola.onlinefooddeliverysystemV1.dao;

import com.narola.onlinefooddeliverysystemV1.enums.UserRoles;
import com.narola.onlinefooddeliverysystemV1.exception.DAOLayerException;
import com.narola.onlinefooddeliverysystemV1.model.City;
import com.narola.onlinefooddeliverysystemV1.model.Restaurant;
import com.narola.onlinefooddeliverysystemV1.model.RestaurantAddress;
import com.narola.onlinefooddeliverysystemV1.model.State;
import com.narola.onlinefooddeliverysystemV1.session.CurrentUserCredentials;

import java.sql.*;
import java.util.*;

public class RestaurantDao {

    public int doAddRestaurant(Restaurant rest, int generatedId) throws DAOLayerException {
        try {
            String query = "Insert into restaurant(restaurant_name, restaurant_address_id, restaurant_contact, restaurant_email, owner_id, opens_at, closes_at, gst_number, created_by, updated_by)" +
                    "values(?,?,?,?,?,?,?,?,?,?)";
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
            stmt.setInt(9, CurrentUserCredentials.currentUser.getUserId());
            stmt.setInt(10, CurrentUserCredentials.currentUser.getUserId());

            return stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while inserting restaurant details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while inserting restaurant details", e);
        }
    }

    public int doAddRestaurantAddressAndGetAddressId(Restaurant restaurant) throws DAOLayerException {
        try {
            String query = "Insert into restaurant_address(restaurant_address_line1, restaurant_address_line2, city, state, pincode, created_by, updated_by) values(?,?,?,?,?,?,?)";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, restaurant.getRestaurantAddress().getRestaurantAddLine1());
            stmt.setString(2, restaurant.getRestaurantAddress().getRestaurantAddLine2());
            stmt.setInt(3, restaurant.getRestaurantAddress().getCityId());
            stmt.setInt(4, restaurant.getRestaurantAddress().getStateId());
            stmt.setInt(5, restaurant.getRestaurantAddress().getPincode());
            stmt.setInt(6, CurrentUserCredentials.currentUser.getUserId());
            stmt.setInt(7, CurrentUserCredentials.currentUser.getUserId());
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

    public List<Restaurant> getAllRestaurantsDetails() throws DAOLayerException {
        List<Restaurant> restaurantList = new ArrayList<>();
        try {
            String query = "Select * from restaurant";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setRestaurantId(rs.getInt(1));
                restaurant.setRestaurantName(rs.getString(2));
                restaurant.setRestaurantAddressId(rs.getInt(3));
                restaurant.setRestaurantContact(rs.getString(4));
                restaurant.setRestaurantEmail(rs.getString(5));
                restaurant.setOpensAt(rs.getString(6));
                restaurant.setClosesAt(rs.getString(7));
                restaurant.setGstNo(rs.getString(8));
                restaurantList.add(restaurant);
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching restaurant details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching restaurant details", e);
        }
        return restaurantList;
    }

    public List<Restaurant> getAllRestaurantsDetails(int ownerID) throws DAOLayerException {
        List<Restaurant> restaurantList = new ArrayList<>();
        try {
            String query = "Select * from restaurant where owner_id = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, ownerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setRestaurantId(rs.getInt(1));
                restaurant.setRestaurantName(rs.getString(2));
                restaurant.setRestaurantAddressId(rs.getInt(3));
                restaurant.setRestaurantContact(rs.getString(4));
                restaurant.setRestaurantEmail(rs.getString(5));
                restaurant.setOpensAt(rs.getString(6));
                restaurant.setClosesAt(rs.getString(7));
                restaurant.setGstNo(rs.getString(8));
                restaurantList.add(restaurant);
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching restaurant details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching restaurant details", e);
        }
        return restaurantList;
    }

    public Restaurant getAllRestaurantsDetailsByRestaurantID(int restaurantID) throws DAOLayerException {
        Restaurant restaurant = new Restaurant();
        try {
            String query = "Select r.restaurant_name, r.restaurant_contact, r.restaurant_email, r.opens_at, r.closes_at, r.gst_number, a.restaurant_address_line1, a.restaurant_address_line2, a.pincode, c.city_name, s.state_name, r.restaurant_address_id from restaurant r  join restaurant_address a on  r.restaurant_address_id = a.id join city c on a.city = c.id join state s on a.state = s.id where r.id= ? and (r.owner_id =? or ?=(Select role_id from user u where u.id = ?) ) ";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.setInt(1, restaurantID);
            stmt.setInt(2, CurrentUserCredentials.currentUser.getUserId());
            stmt.setInt(3, UserRoles.ADMIN.getRoleIdValue());
            stmt.setInt(4, CurrentUserCredentials.currentUser.getUserId());

            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }

            RestaurantAddress restaurantAddress = new RestaurantAddress();
            City city = new City();
            State state = new State();
            restaurant.setRestaurantId(restaurantID);

            rs.previous();
            while (rs.next()) {

                restaurant.setRestaurantName(rs.getString(1));
                restaurant.setRestaurantContact(rs.getString(2));
                restaurant.setRestaurantEmail(rs.getString(3));
                restaurant.setOpensAt(rs.getString(4));
                restaurant.setClosesAt(rs.getString(5));
                restaurant.setGstNo(rs.getString(6));

                restaurantAddress.setRestaurantAddLine1(rs.getString(7));
                restaurantAddress.setRestaurantAddLine2(rs.getString(8));
                restaurantAddress.setPincode(rs.getInt(9));

                city.setCityName(rs.getString(10));
                state.setStateName(rs.getString(11));
                restaurant.setRestaurantAddressId(rs.getInt(12));

                restaurantAddress.setCity(city);
                restaurantAddress.setState(state);

                restaurant.setRestaurantAddress(restaurantAddress);

                return restaurant;
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching restaurant details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching restaurant details", e);
        }
        return restaurant;
    }

    public int editRestaurantDetails(Restaurant restaurant, int restId, int ownerId) throws DAOLayerException {
        try {
            String query1 = "Update restaurant SET restaurant_name = ?, restaurant_contact = ?, restaurant_email = ?,opens_at = ?, closes_at = ?, updated_by = ?, updated_at = default where id=? and owner_id=?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query1);

            stmt.setString(1, restaurant.getRestaurantName());
            stmt.setString(2, restaurant.getRestaurantContact());
            stmt.setString(3, restaurant.getRestaurantEmail());
            stmt.setString(4, restaurant.getOpensAt());
            stmt.setString(5, restaurant.getClosesAt());
            stmt.setInt(6, CurrentUserCredentials.currentUser.getUserId());
            stmt.setInt(7, restId);
            stmt.setInt(8, ownerId);

            return stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while updating restaurant details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating restaurant details", e);
        }
    }

    public int editRestaurantAddress(Restaurant restaurant) throws DAOLayerException {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            String query = "Update restaurant_address SET restaurant_address_line1 = ?, restaurant_address_line2 = ?, pincode = ?, updated_at = default, updated_by = ? where id=? and (? =(select owner_id from restaurant r where id = ?))";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, restaurant.getRestaurantAddress().getRestaurantAddLine1());
            stmt.setString(2, restaurant.getRestaurantAddress().getRestaurantAddLine2());
            stmt.setInt(3, restaurant.getRestaurantAddress().getPincode());
            stmt.setInt(4, CurrentUserCredentials.getCurrentUser().getUserId());
            stmt.setInt(5, restaurant.getRestaurantAddressId());
            stmt.setInt(6, CurrentUserCredentials.getCurrentUser().getUserId());
            stmt.setInt(7, restaurant.getRestaurantId());

            return stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while updating restaurant details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating restaurant details", e);
        }
    }

    public boolean doesUserOwnRestaurant() throws DAOLayerException {
        try {
            String query = "Select count(*) from restaurant where owner_id = ? ";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, CurrentUserCredentials.getCurrentUser().getUserId());

            ResultSet rs = stmt.executeQuery();

            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            return count > 0;

        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while updating restaurant details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating restaurant details", e);
        }
    }

    public int getRestaurantIdOfCurrentUser(int userId) throws DAOLayerException {
        try {
            String query = "Select * from restaurant where owner_id = ? ";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while updating restaurant details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating restaurant details", e);
        }
        return 0;
    }

    public boolean isRestaurantExists(int restaurantId) throws DAOLayerException {
        try {
            int count = 0;
            String query = "Select count(*) from restaurant where id = ? ";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, restaurantId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            return count > 0;
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching restaurant", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching restaurant", e);
        }
    }
}