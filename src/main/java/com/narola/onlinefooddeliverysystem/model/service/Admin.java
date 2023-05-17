package com.narola.onlinefooddeliverysystem.model.service;

import com.narola.onlinefooddeliverysystem.constant.Constants;
import com.narola.onlinefooddeliverysystem.dao.UserDao;
import com.narola.onlinefooddeliverysystem.exception.DAOLayerException;
import com.narola.onlinefooddeliverysystem.menu.Menu;
import com.narola.onlinefooddeliverysystem.OnlineFoodDelivery;
import com.narola.onlinefooddeliverysystem.utility.ProgramTerminator;
import com.narola.onlinefooddeliverysystem.model.service.restaurant.RestaurantAdminManager;

public class Admin {

    public static void adminFunc() {

        int choice = Menu.adminMenu();
        switch (choice) {
            case Constants.USER_MANAGEMENT:
                Admin.displayUsers();
                break;

            case Constants.RESTAURANT_MANAGEMENT:
                RestaurantAdminManager.restAdminFunc();
                break;

            case Constants.ADMIN_LOGOUT:
                OnlineFoodDelivery.main(null);
                break;

            case Constants.EXIT:
                ProgramTerminator.exit();
                break;

            default:
                System.out.println("Invalid choice!!");
                Admin.adminFunc();
        }
    }

    public static void displayUsers() {
        try {
            int choice = Menu.displayUserMenu();

            switch (choice) {
                case Constants.VIEW_ADMIN:
                    UserDao.getUsersDetails(1);
                    break;

                case Constants.VIEW_RESTAURANT_OWNER:
                    UserDao.getUsersDetails(2);
                    break;

                case Constants.VIEW_CUSTOMER:
                    UserDao.getUsersDetails(3);
                    break;

                case Constants.VIEW_DELIVERY_AGENT:
                    UserDao.getUsersDetails(4);
                    break;

                case Constants.VIEW_BACK_ADMIN_MENU:
                    adminFunc();
                    break;

                case Constants.EXIT:
                    ProgramTerminator.exit();
                    break;

                default:
                    System.out.println("Invalid choice!!");
                    System.out.println("Please select valid option from below");
            }
            Admin.displayUsers();
        } catch (DAOLayerException e) {
               e.printStackTrace();
        }
    }
}
