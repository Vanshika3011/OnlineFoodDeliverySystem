package com.narola.onlinefooddeliverysystemV1.manager;

import com.narola.onlinefooddeliverysystemV1.menu.AppMenu;
import com.narola.onlinefooddeliverysystemV1.model.RestaurantMenu;
import com.narola.onlinefooddeliverysystemV1.service.CartService;
import com.narola.onlinefooddeliverysystemV1.service.RestaurantMenuService;
import com.narola.onlinefooddeliverysystemV1.service.RestaurantService;
import com.narola.onlinefooddeliverysystemV1.utility.ProgramTerminator;
import com.narola.onlinefooddeliverysystemV1.view.CartView;
import com.narola.onlinefooddeliverysystemV1.view.MenuView;
import com.narola.onlinefooddeliverysystemV1.view.RestaurantView;
import com.narola.onlinefooddeliverysystemV1.view.UserView;

import java.util.List;

import static com.narola.onlinefooddeliverysystemV1.constant.Constants.*;

public class RestaurantMenuManager {

    private MenuView menuView = new MenuView();
    private UserView userView = new UserView();
    private static CartView cartView = new CartView();
    private RestaurantMenuService restaurantMenuService = new RestaurantMenuService();
    private AppMenu appMenu = new AppMenu();
    private static RestaurantManager restaurantManager = new RestaurantManager();
    private RestaurantService restaurantService = new RestaurantService();
    private RestaurantView restaurantView = new RestaurantView();
    private CartService cartService = new CartService();

    public void addMenu() {
        if (!restaurantService.doesUserOwnsRestaurant()) {
            restaurantView.displayErrorMessage("Restaurant doesn't exist.Please register a restaurant.");
            return;
        }
        RestaurantMenu restaurantMenu = menuView.getMenuInformationFromRestaurantAdmin();
        restaurantMenuService.addRestaurantMenu(restaurantMenu);
    }

    public void viewMenu() {
        int choice = appMenu.displayRestaurantMenuItemsAndTakeInputFromAdmin();
        switch (choice) {

            case MENU_ITEMS_ALPHABETICALLY_ASC:
                restaurantMenuService.getItemsAlphabetically();
                break;

            case MENU_ITEMS_ALPHABETICALLY_DESC:
                restaurantMenuService.getItemsAlphabeticallyReversed();
                break;

            case MENU_ITEMS_BY_PRICE_HIGH_TO_LOW:
                restaurantMenuService.getItemsByPriceDesc();
                break;

            case MENU_ITEMS_BY_PRICE_LOW_TO_HIGH:
                restaurantMenuService.getItemsByPriceAsc();
                break;

            case MENU_ITEMS_RECENTLY_ADDED:
                restaurantMenuService.getItemsByRecentlyAdded();
                break;

            case BACK_TO_ADMIN_ACTIONS:
                restaurantManager.handleRestaurantAdminActions();
                break;

            case EXIT:
                ProgramTerminator.exit();
                break;

            default:
                System.out.println("Invalid choice!!");
        }
        viewMenu();
    }

    public void doSearchFoodItems() {
        String foodSearched = userView.getItemNameFromCustomer();
        List<RestaurantMenu> menuList = restaurantMenuService.getFoodListAsSearched(foodSearched);
        menuView.printListAsPerSearched(menuList);
    }

}
