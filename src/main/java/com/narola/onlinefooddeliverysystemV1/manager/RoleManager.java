package com.narola.onlinefooddeliverysystemV1.manager;

import com.narola.onlinefooddeliverysystemV1.enums.UserRoles;
import com.narola.onlinefooddeliverysystemV1.session.CurrentUserCredentials;

public class RoleManager {
    public AdminManager adminManager = new AdminManager();
    public RestaurantManager restaurantManager = new RestaurantManager();
    public CustomerManager customerManager = new CustomerManager();

    public void assignTaskAsPerRole() {
        if (CurrentUserCredentials.currentUser.getRoleId() == UserRoles.ADMIN.getRoleIdValue()) {
            adminManager.handleAdminActions();
        } else if (CurrentUserCredentials.currentUser.getRoleId() == UserRoles.RESTAURANTADMIN.getRoleIdValue()) {
            restaurantManager.handleRestaurantAdminActions();
        } else if (CurrentUserCredentials.currentUser.getRoleId() == UserRoles.CUSTOMER.getRoleIdValue()) {
            customerManager.handleCustomerActions();
        }
    }
}
