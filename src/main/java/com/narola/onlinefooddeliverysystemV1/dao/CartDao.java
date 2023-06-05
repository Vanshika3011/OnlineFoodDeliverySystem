package com.narola.onlinefooddeliverysystemV1.dao;

import com.narola.onlinefooddeliverysystemV1.exception.DAOLayerException;
import com.narola.onlinefooddeliverysystemV1.model.Cart;
import com.narola.onlinefooddeliverysystemV1.model.CartDetails;
import com.narola.onlinefooddeliverysystemV1.model.CartItem;
import com.narola.onlinefooddeliverysystemV1.session.CurrentUserCredentials;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDao {

    public int createCart(Cart cart) throws DAOLayerException {
        try {
            String query = "Insert into cart(customer_id, restaurant_id, created_by, updated_by)" +
                    "values(?,?,?,?)";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, cart.getCustomerId());
            stmt.setInt(2, cart.getRestaurantId());
            stmt.setInt(3, CurrentUserCredentials.currentUser.getUserId());
            stmt.setInt(4, CurrentUserCredentials.currentUser.getUserId());

            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            int generatedId = -1;
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
            }

            return generatedId;

        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while creating cart details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while creating cart details", e);
        }
    }

    public void doAddItemsToCart(CartItem cartItem, int cartId) throws DAOLayerException {
        try {
            String query = "Insert into cart_items(cart_id, item_id, price, created_by, updated_by) values(?,?,?,?,?)";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, cartId);
            stmt.setInt(2, cartItem.getItemId());
            stmt.setDouble(3, cartItem.getPrice());
            stmt.setInt(4, CurrentUserCredentials.currentUser.getUserId());
            stmt.setInt(5, CurrentUserCredentials.currentUser.getUserId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while adding cart items", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while adding cart items", e);
        }
    }

    public boolean doesUserCartAlreadyExist(int restaurantId, int userId) throws DAOLayerException {
        try {
            int count = 0;

            String query = "Select count(*) from cart where customer_id = ? and restaurant_id = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setInt(2, restaurantId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            return count > 0;

        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching cart items", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching cart items", e);
        }
    }

    public void deleteUserCart(int userId) throws DAOLayerException {
        try {
            String query = "Delete from cart where customer_id = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, userId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while deleting cart items", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while deleting cart items", e);
        }
    }

    public int getCartIdOfUser(int userId, int restaurantId) throws DAOLayerException {
        try {
            int cartId = 0;
            String query = "Select cart_id from cart where customer_id = ? and restaurant_id = ?";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, userId);
            stmt.setInt(2, restaurantId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cartId = rs.getInt(1);
            }
            return cartId;
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching cart items", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching cart items", e);
        }
    }

    public List<CartDetails> getUserCartDetails(int userId) throws DAOLayerException {
        List<CartDetails> cartDetailsList = new ArrayList<>();
        try {
            String query = "Select i.cart_id, i.quantity, i.price, r.restaurant_name, m.item_name, r.id, i.cart_item_id, i.item_id from cart_items i join cart c on c.cart_id = i.cart_id join restaurant r on c.restaurant_id = r.id join menu m on m.item_id = i.item_id where customer_id = ? ";
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return cartDetailsList;
            }

            rs.previous();
            while (rs.next()) {
                CartDetails cartDetails = new CartDetails();

                cartDetails.setCart_id(rs.getInt(1));
                cartDetails.setQuantity(rs.getInt(2));
                cartDetails.setPrice(rs.getDouble(3));
                cartDetails.setRestaurantName(rs.getString(4));
                cartDetails.setItem_name(rs.getString(5));
                cartDetails.setRestaurant_id(rs.getInt(6));
                cartDetails.setCartItemId(rs.getInt(7));
                cartDetails.setItem_id(rs.getInt(8));
                cartDetailsList.add(cartDetails);
            }
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while fetching cart items details", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching cart items details", e);
        }
        return cartDetailsList;
    }
}