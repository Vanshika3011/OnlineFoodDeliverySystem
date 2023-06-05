package com.narola.onlinefooddeliverysystemV1.view;

import com.narola.onlinefooddeliverysystemV1.dao.RestaurantDao;
import com.narola.onlinefooddeliverysystemV1.exception.DAOLayerException;
import com.narola.onlinefooddeliverysystemV1.input.InputHandler;
import com.narola.onlinefooddeliverysystemV1.model.RestaurantMenu;
import com.narola.onlinefooddeliverysystemV1.session.CurrentUserCredentials;

import java.util.List;

public class MenuView {

    private RestaurantMenu restaurantMenu = new RestaurantMenu();
    private RestaurantDao restaurantDao = new RestaurantDao();
    private CuisineCategoryView cuisineCategoryView = new CuisineCategoryView();

    public RestaurantMenu getMenuInformationFromRestaurantAdmin() {
        try {
            int restaurantId = restaurantDao.getRestaurantIdOfCurrentUser(CurrentUserCredentials.getCurrentUser().getUserId());
            restaurantMenu.setRestaurantId(restaurantId);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }

        int cuisineId = cuisineCategoryView.displayCuisineAndTakeInput();
        restaurantMenu.setCategoryId(cuisineId);

        System.out.print("Enter dish name                         : ");
        String dishName = InputHandler.getStringInput();
        restaurantMenu.setItemName(dishName);

        System.out.print("Press 0 if dish is non-veg else press 1 : ");
        int isVeg = InputHandler.getNumberInput();
        if (isVeg == 0)
            restaurantMenu.setVeg(false);
        else
            restaurantMenu.setVeg(true);

        System.out.print("Enter price per dish(₹)                 : ");
        double pricePerDish = InputHandler.getDoubleInput();
        restaurantMenu.setPrice(pricePerDish);

        System.out.print("Enter ingredients                       : ");
        String ingredients = InputHandler.getStringInput();
        restaurantMenu.setIngredients(ingredients);

        return restaurantMenu;
    }

    public void printMenuList(List<RestaurantMenu> restaurantMenuList) {
        if (restaurantMenuList == null || restaurantMenuList.isEmpty()) {
            System.out.println("No menu items exists.");
            return;
        }
        System.out.println("ItemId\tPrice\tVeg/Non\t\tCuisine\t\tItem Name");
        for (RestaurantMenu restaurantMenu : restaurantMenuList) {
            System.out.println(restaurantMenu.getItemId() + "   \t" + restaurantMenu.getPrice() + " \t" + ((restaurantMenu.isVeg()) ? "Veg\t\t" : "Non-veg\t") + " \t" + restaurantMenu.getCuisineCategory().getCuisineName() + " \t" + restaurantMenu.getItemName());
        }
    }

    public void printListAsPerSearched(List<RestaurantMenu> menuList) {
        if (menuList == null || menuList.isEmpty()) {
            System.out.println("No items exist.");
            return;
        }
        System.out.println("RestaurantId\tMenuItemId\tVeg/Non\t\tIsAvailable\tPrice\tRestaurantName\tItemName");
        for (RestaurantMenu menu : menuList) {
            System.out.println(menu.getRestaurantId() + "\t\t\t\t" + menu.getItemId() + "\t\t\t" + ((menu.isVeg()) ? "Veg\t\t" : "Non-veg\t") + "\t" + ((menu.isAvailability()) ? "Yes" : "No ") + "\t\t\t" + menu.getPrice() + "\t" + menu.getRestaurant().getRestaurantName() + "\t\t\t" + menu.getItemName());
        }
    }
}
