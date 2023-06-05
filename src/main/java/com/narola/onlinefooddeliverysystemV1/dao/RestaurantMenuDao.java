package com.narola.onlinefooddeliverysystemV1.dao;

import com.narola.onlinefooddeliverysystemV1.exception.DAOLayerException;
import com.narola.onlinefooddeliverysystemV1.model.CuisineCategory;
import com.narola.onlinefooddeliverysystemV1.model.Restaurant;
import com.narola.onlinefooddeliverysystemV1.model.RestaurantMenu;
import com.narola.onlinefooddeliverysystemV1.session.CurrentUserCredentials;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class RestaurantMenuDao {

    public int addMenu(RestaurantMenu restaurantMenu) throws DAOLayerException {
        try {
            String query = "Insert into `menu` ( item_name, restaurant_id, category_id, is_veg, price, ingredients, created_by, updated_by) " +
                    "values(?,?,?,?,?,?,?,?)";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, restaurantMenu.getItemName());
            stmt.setInt(2, restaurantMenu.getRestaurantId());
            stmt.setInt(3, restaurantMenu.getCategoryId());
            stmt.setBoolean(4, restaurantMenu.isVeg());
            stmt.setDouble(5, restaurantMenu.getPrice());
            stmt.setString(6, restaurantMenu.getIngredients());
            stmt.setInt(7, CurrentUserCredentials.getCurrentUser().getUserId());
            stmt.setInt(8, CurrentUserCredentials.getCurrentUser().getUserId());

            return stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while updating menu details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating menu details", e);
        }
    }

    public List<RestaurantMenu> getMenuDetails(int restaurantId) throws DAOLayerException {
        List<RestaurantMenu> restaurantMenuList = new ArrayList<>();
        try {
            String query = "Select m.item_id, m.item_name, m.category_id, m.is_veg, m.price, m.ingredients, m.availibility, c.cuisine_name, r.restaurant_name, m.restaurant_id  from menu m join cuisine_category c on m.category_id = c.cuisine_category_id join restaurant r on r.id = m.restaurant_id where m.restaurant_id = ?";


            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, restaurantId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RestaurantMenu restaurantMenu = new RestaurantMenu();
                CuisineCategory cuisineCategory = new CuisineCategory();
                Restaurant restaurant = new Restaurant();

                restaurantMenu.setItemId(rs.getInt(1));
                restaurantMenu.setItemName(rs.getString(2));
                restaurantMenu.setRestaurantId(restaurantId);
                restaurantMenu.setCategoryId(rs.getInt(3));
                restaurantMenu.setVeg(rs.getBoolean(4));
                restaurantMenu.setPrice(rs.getDouble(5));
                restaurantMenu.setIngredients(rs.getString(6));
                restaurantMenu.setAvailability(rs.getBoolean(7));

                cuisineCategory.setCuisineName(rs.getString(8));
                restaurant.setRestaurantName(rs.getString(9));
                restaurantMenu.setCuisineCategory(cuisineCategory);
                restaurantMenu.setRestaurant(restaurant);

                restaurantMenuList.add(restaurantMenu);
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while retrieving menu details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while retrieving menu details", e);
        }
        return restaurantMenuList;
    }

    public List<RestaurantMenu> getMenuDetails() throws DAOLayerException {
        List<RestaurantMenu> restaurantMenuList = new ArrayList<>();
        try {
            String query = "Select m.item_id, m.item_name, m.category_id, m.is_veg, m.price, m.ingredients, m.availibility, c.cuisine_name, r.restaurant_name ,m.restaurant_id  from menu m\n" +
                    "join cuisine_category c on m.category_id = c.cuisine_category_id\n" +
                    "join restaurant r on r.id = m.restaurant_id";

            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RestaurantMenu restaurantMenu = new RestaurantMenu();
                CuisineCategory cuisineCategory = new CuisineCategory();
                Restaurant restaurant = new Restaurant();

                restaurantMenu.setItemId(rs.getInt(1));
                restaurantMenu.setItemName(rs.getString(2));
                restaurantMenu.setRestaurantId(rs.getInt(10));
                restaurantMenu.setCategoryId(rs.getInt(3));
                restaurantMenu.setVeg(rs.getBoolean(4));
                restaurantMenu.setPrice(rs.getDouble(5));
                restaurantMenu.setIngredients(rs.getString(6));
                restaurantMenu.setAvailability(rs.getBoolean(7));

                cuisineCategory.setCuisineName(rs.getString(8));
                restaurantMenu.setCuisineCategory(cuisineCategory);
                restaurant.setRestaurantName(rs.getString(9));

                restaurantMenu.setRestaurant(restaurant);
                restaurantMenuList.add(restaurantMenu);

            }
            return restaurantMenuList;
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while retrieving menu details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while retrieving menu details", e);
        }
    }

    public int getPriceForMenuItem(int itemId) throws DAOLayerException {
        try {
            String query = "Select price from menu where item_id = ?";

            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, itemId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while retrieving menu details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while retrieving menu details", e);
        }
        return 0;
    }

    public boolean isMenuItemOfSameRestaurant(int itemId, int restaurantId) throws DAOLayerException {
        try {
            int count = 0;
            String query = "Select count(*) from menu where item_id = ? and restaurant_id = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, itemId);
            stmt.setInt(2, restaurantId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            return count > 0;
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching cities", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching cities", e);
        }
    }

}
