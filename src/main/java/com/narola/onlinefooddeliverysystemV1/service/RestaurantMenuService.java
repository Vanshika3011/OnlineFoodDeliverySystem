package com.narola.onlinefooddeliverysystemV1.service;

import com.narola.onlinefooddeliverysystemV1.dao.RestaurantDao;
import com.narola.onlinefooddeliverysystemV1.dao.RestaurantMenuDao;
import com.narola.onlinefooddeliverysystemV1.exception.DAOLayerException;
import com.narola.onlinefooddeliverysystemV1.model.RestaurantMenu;
import com.narola.onlinefooddeliverysystemV1.service.SortSpecification.RestaurantMenuSortSpecification;
import com.narola.onlinefooddeliverysystemV1.session.CurrentUserCredentials;
import com.narola.onlinefooddeliverysystemV1.view.MenuView;
import com.narola.onlinefooddeliverysystemV1.view.RestaurantView;
import com.narola.onlinefooddeliverysystemV1.view.UserView;

import java.util.*;

public class RestaurantMenuService {

    private RestaurantMenuDao restaurantMenuDao = new RestaurantMenuDao();
    private MenuView menuView = new MenuView();
    private RestaurantDao restaurantDao = new RestaurantDao();
    private RestaurantView restaurantView = new RestaurantView();

    public void addRestaurantMenu(RestaurantMenu restaurantMenu) {
        try {
            int isAdded = restaurantMenuDao.addMenu(restaurantMenu);
            if (isAdded > 0) {
                System.out.println("Menu item added successfully");
            } else {
                System.out.println("Error adding menu item details.");
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    public List<RestaurantMenu> getMenuList() {
        List<RestaurantMenu> restaurantMenuList = null;
        try {
            int restaurantId = restaurantDao.getRestaurantIdOfCurrentUser(CurrentUserCredentials.getCurrentUser().getUserId());
            restaurantMenuList = restaurantMenuDao.getMenuDetails(restaurantId);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        return restaurantMenuList;
    }


    public void getItemsAlphabetically() {
        List<RestaurantMenu> restaurantMenuList = getMenuList();
        Collections.sort(restaurantMenuList);
        menuView.printMenuList(restaurantMenuList);
    }

    public void getItemsAlphabeticallyReversed() {
        List<RestaurantMenu> restaurantMenuList = getMenuList();
        Collections.sort(restaurantMenuList, Comparator.reverseOrder());
        menuView.printMenuList(restaurantMenuList);
    }

    public void getItemsByPriceAsc() {
        List<RestaurantMenu> restaurantMenuList = getMenuList();
        Collections.sort(restaurantMenuList, new RestaurantMenuSortSpecification.SortByPriceAsc());
        menuView.printMenuList(restaurantMenuList);
    }

    public void getItemsByPriceDesc() {
        List<RestaurantMenu> restaurantMenuList = getMenuList();
        Collections.sort(restaurantMenuList, new RestaurantMenuSortSpecification.SortByPriceDesc());
        menuView.printMenuList(restaurantMenuList);
    }

    public void getItemsByRecentlyAdded() {
        List<RestaurantMenu> restaurantMenuList = getMenuList();
        Collections.sort(restaurantMenuList, new RestaurantMenuSortSpecification.SortByLatestAdded());
        menuView.printMenuList(restaurantMenuList);
    }

    public List<RestaurantMenu> getFoodListAsSearched(String foodSearched) {
        List<RestaurantMenu> menuList = new ArrayList<>();
        try {
            menuList = restaurantMenuDao.getMenuDetails();
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        List<RestaurantMenu> desiredMenuItemsList = new ArrayList<>();

        for (RestaurantMenu item : menuList) {
            String[] itemsList = item.getItemName().split(" ");
            for (String item1 : itemsList) {
                if (foodSearched.toLowerCase().contains(item1.toLowerCase()) || item1.toLowerCase().contains(foodSearched.toLowerCase())) {
                    desiredMenuItemsList.add(item);
                    break;
                }
            }
        }
        return desiredMenuItemsList;
    }


    public void getRestaurantListAsPerItemSearched(String foodSearched) {
        List<RestaurantMenu> menuList = getFoodListAsSearched(foodSearched);

        Map<Integer, String> map = new HashMap<>();
        for (RestaurantMenu list : menuList) {
            if (!map.containsKey(list.getRestaurantId())) {
                map.put(list.getRestaurantId(), list.getRestaurant().getRestaurantName());
            }
        }
        restaurantView.printRestaurants(map);
    }

}
