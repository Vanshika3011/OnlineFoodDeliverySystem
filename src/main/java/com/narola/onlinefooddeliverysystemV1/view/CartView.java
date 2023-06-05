package com.narola.onlinefooddeliverysystemV1.view;

import com.narola.onlinefooddeliverysystemV1.dao.RestaurantDao;
import com.narola.onlinefooddeliverysystemV1.dao.RestaurantMenuDao;

import com.narola.onlinefooddeliverysystemV1.exception.DAOLayerException;
import com.narola.onlinefooddeliverysystemV1.input.InputHandler;
import com.narola.onlinefooddeliverysystemV1.model.Cart;
import com.narola.onlinefooddeliverysystemV1.model.CartDetails;
import com.narola.onlinefooddeliverysystemV1.model.CartItem;
import com.narola.onlinefooddeliverysystemV1.model.RestaurantMenu;
import com.narola.onlinefooddeliverysystemV1.service.RestaurantMenuService;
import com.narola.onlinefooddeliverysystemV1.service.SortSpecification.RestaurantMenuSortSpecification;
import com.narola.onlinefooddeliverysystemV1.session.CurrentUserCredentials;

import java.util.Collections;
import java.util.List;

public class CartView {
    private RestaurantDao restaurantDao = new RestaurantDao();
    private RestaurantMenuDao restaurantMenuDao = new RestaurantMenuDao();
    private MenuView menuView = new MenuView();
    private RestaurantMenuService restaurantMenuService = new RestaurantMenuService();

    public Cart getInformationForAddToCart() {
        try {
            Cart cart = new Cart();

            List<RestaurantMenu> restaurantMenuList = restaurantMenuDao.getMenuDetails();
            Collections.sort(restaurantMenuList, new RestaurantMenuSortSpecification.SortByRestaurantId());
            menuView.printListAsPerSearched(restaurantMenuList);

            System.out.println("Enter Food Item : ");
            String foodSearched = InputHandler.getStringInput();
            List<RestaurantMenu> menuList = restaurantMenuService.getFoodListAsSearched(foodSearched);
            Collections.sort(menuList, new RestaurantMenuSortSpecification.SortByPriceAsc());
            menuView.printListAsPerSearched(menuList);

            cart.setCustomerId(CurrentUserCredentials.getCurrentUser().getUserId());

            restaurantMenuService.getRestaurantListAsPerItemSearched(foodSearched);
            System.out.println("Enter Restaurant Id :");
            int restaurantId = InputHandler.getNumberInput();
            while (!restaurantDao.isRestaurantExists(restaurantId)) {
                System.out.println("Enter valid Restaurant id");
                restaurantId = InputHandler.getNumberInput();
            }
            cart.setRestaurantId(restaurantId);

            List<RestaurantMenu> menuItemsList = restaurantMenuDao.getMenuDetails(restaurantId);
            Collections.sort(menuItemsList, new RestaurantMenuSortSpecification.SortByFoodType());
            menuView.printMenuList(menuItemsList);

            return cart;
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CartItem getItemInformationForCart(Cart cart) {
        try {
            CartItem cartItem = new CartItem();
            System.out.print("Enter item id : ");
            int itemId = InputHandler.getNumberInput();
            while (!restaurantMenuDao.isMenuItemOfSameRestaurant(itemId, cart.getRestaurantId())) {
                System.out.println("Enter valid Item id");
                itemId = InputHandler.getNumberInput();
            }
            cartItem.setItemId(itemId);

            int price = restaurantMenuDao.getPriceForMenuItem(itemId);
            cartItem.setPrice(price);

            return cartItem;
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void displayInfoMessage(String message) {
        System.out.println("INFO:" + message);
    }

    public void printCartDetails(List<CartDetails> cartDetailsList) {
        if (cartDetailsList.isEmpty() || cartDetailsList == null) {
            System.out.println("Cart is empty..Add items to cart.");
            return;
        }
        System.out.println("Cart Id : " + cartDetailsList.get(0).getCart_id());
        System.out.println("Restaurant Name : " + cartDetailsList.get(0).getRestaurantName() + "\n");
        System.out.println("ItemId\tPrice\tQuantity \tItemName");
        for (CartDetails cartDetails : cartDetailsList) {
            System.out.println(cartDetails.getCartItemId() + "\t\t" + cartDetails.getPrice() + "  \t" + cartDetails.getQuantity() + "\t\t\t" + cartDetails.getItem_name());
        }
    }

    public CartItem getItemUpdateDetails() {
        CartItem cartItem = new CartItem();

        System.out.println("Enter Item Id : ");
        int itemId = InputHandler.getNumberInput();
        cartItem.setItemId(itemId);

        System.out.println("Enter quantity : ");
        int quantity = InputHandler.getNumberInput();
        cartItem.setQuantity(quantity);

        return cartItem;
    }

    public int getItemIdToDeleteDetails() {
        System.out.println("Enter Item Id : ");
        int itemId = InputHandler.getNumberInput();

        return itemId;
    }
}
