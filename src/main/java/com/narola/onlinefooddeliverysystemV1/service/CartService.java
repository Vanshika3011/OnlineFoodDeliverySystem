package com.narola.onlinefooddeliverysystemV1.service;

import com.narola.onlinefooddeliverysystemV1.dao.CartDao;
import com.narola.onlinefooddeliverysystemV1.dao.CartItemDao;
import com.narola.onlinefooddeliverysystemV1.exception.DAOLayerException;
import com.narola.onlinefooddeliverysystemV1.manager.CustomerManager;
import com.narola.onlinefooddeliverysystemV1.model.Cart;
import com.narola.onlinefooddeliverysystemV1.model.CartDetails;
import com.narola.onlinefooddeliverysystemV1.model.CartItem;
import com.narola.onlinefooddeliverysystemV1.session.CurrentUserCredentials;
import com.narola.onlinefooddeliverysystemV1.view.CartView;

import java.util.Collections;
import java.util.List;

public class CartService {

    private CartDao cartDao = new CartDao();
    private CartItemDao cartItemDao = new CartItemDao();
    private CartView cartView = new CartView();
    private CustomerManager customerManager = new CustomerManager();

    public void addToCart(Cart cart, CartItem cartItem) {
        try {
            int restaurantId = cart.getRestaurantId();
            int customerId = CurrentUserCredentials.getCurrentUser().getUserId();
            int itemId = cartItem.getItemId();

            if (cartDao.doesUserCartAlreadyExist(restaurantId, customerId)) {
                int cartId = cartDao.getCartIdOfUser(customerId, restaurantId);
                System.out.println(cartId);

                if (cartItemDao.doesItemAlreadyExist(itemId, cartId)) {
                    cartItemDao.updateItemCount(itemId, customerId);
                } else {
                    cartDao.doAddItemsToCart(cartItem, cartId);
                }
                cartView.displayInfoMessage("Cart item added successfully!");
            } else {
                cartDao.deleteUserCart(customerId);
                int generatedCartId = cartDao.createCart(cart);
                cartDao.doAddItemsToCart(cartItem, generatedCartId);
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    public List<CartDetails> getUserCartDetails() {
        try {
            List<CartDetails> cartDetailsList = cartDao.getUserCartDetails(CurrentUserCredentials.getCurrentUser().getUserId());
            return cartDetailsList;
        } catch (DAOLayerException e) {
            e.printStackTrace();
        } return Collections.emptyList();
    }

    public void updateCartItemQuantity() {
        try {
            List<CartDetails> cartDetailsList = getUserCartDetails();
            cartView.printCartDetails(cartDetailsList);

            if (!cartDetailsList.isEmpty()) {
                CartItem cartItem = cartView.getItemUpdateDetails();
                cartItemDao.updateItemQuantity(cartItem.getQuantity(), cartItem.getItemId(), CurrentUserCredentials.getCurrentUser().getUserId());
            } else {
                System.out.println("No items exist in cart.");
                customerManager.handleCustomerActions();
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    public void deleteCartItem() {
        try {
            List<CartDetails> cartDetailsList = getUserCartDetails();
            cartView.printCartDetails(cartDetailsList);

            if (!cartDetailsList.isEmpty()) {
                int itemId = cartView.getItemIdToDeleteDetails();
                cartItemDao.deleteUserCartItem(CurrentUserCredentials.getCurrentUser().getUserId(), itemId);
            } else {
                System.out.println("No items exist in cart.");
                customerManager.handleCustomerActions();
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }


}
