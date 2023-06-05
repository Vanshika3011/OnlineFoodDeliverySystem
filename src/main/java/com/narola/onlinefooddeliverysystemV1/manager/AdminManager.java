package com.narola.onlinefooddeliverysystemV1.manager;

import com.narola.onlinefooddeliverysystemV1.OnlineFoodDelivery;
import com.narola.onlinefooddeliverysystemV1.menu.AppMenu;
import com.narola.onlinefooddeliverysystemV1.utility.ProgramTerminator;
import com.narola.onlinefooddeliverysystemV1.view.RestaurantView;
import com.narola.onlinefooddeliverysystemV1.view.UserView;

import static com.narola.onlinefooddeliverysystemV1.constant.Constants.*;

public class AdminManager {

    private RestaurantView restaurantView = new RestaurantView();
    private UserView userView = new UserView();
    private RestaurantManager restaurantManager = new RestaurantManager();
    private static UserManager userManager = new UserManager();
    private AppMenu appMenu = new AppMenu();

    public void handleAdminActions() {
        int choice = appMenu.adminMenu();
        switch (choice) {
            case USER_MANAGEMENT:
                doUserManagement();
                break;

            case RESTAURANT_MANAGEMENT:
                doRestaurantManagement();
                break;

            case ADMIN_LOGOUT:
                OnlineFoodDelivery.main(null);
                break;

            case EXIT:
                ProgramTerminator.exit();
                break;

            default:
                userView.displayErrorMessage("Invalid Choice!");
        }
        handleAdminActions();
    }

    public void doUserManagement() {
        int input = appMenu.displayUserManagementMenuAndTakeInput();
        switch (input) {
            case VIEW_USERS_DETAILS_BY_ROLE: {
                userManager.getUserListByRole();
                break;
            }
            case BACK_TO_ADMIN_MAIN_MENU:
                handleAdminActions();
                break;

            case EXIT:
                ProgramTerminator.exit();
                break;

            default:
                userView.displayErrorMessage("Invalid Choice!");
        }
        doUserManagement();
    }

    public void doRestaurantManagement() {
        int input = appMenu.displayRestaurantManagementMenuAndTakeInputFromAdmin();
        switch (input) {
            case VIEW_ALL_RESTAURANTS:
                restaurantManager.getAllRestaurantList();
                break;

            case VIEW_RESTAURANT_OWNED_BY_OWNERID:
                restaurantManager.getRestaurantListByOwnerId();
                break;

            case VIEW_RESTAURANT_DETAILS:
                restaurantManager.getRestaurantDetailsByRestaurantId();
                break;

            case GO_BACK_TO_ADMIN_MAIN_MENU:
                handleAdminActions();
                break;

            case EXIT:
                ProgramTerminator.exit();
                break;

            default:
                restaurantView.displayErrorMessage("Invalid Choice!");
        }
        doRestaurantManagement();
    }
}


