package com.narola.onlinefooddeliverysystem.menu;

import com.narola.onlinefooddeliverysystem.enums.UserRoles;
import com.narola.onlinefooddeliverysystem.input.InputHandler;
import com.narola.onlinefooddeliverysystem.session.CurrentUserCredentials;

public class Menu {

    public static int displayMainMenuAndTakeInput() {
        System.out.println("Please select to Login/ Sign-up");
        System.out.println("1. Sign-up");
        System.out.println("2. Login");
        System.out.println("0. Exit");

        return InputHandler.getNumberInput();
    }

    public static int adminMenu(){
        System.out.println("Please select an option ");
        System.out.println("1. User Management");
        System.out.println("2. Restaurant Management");
        System.out.println("3. Logout");
        System.out.println("0. Exit");

        return InputHandler.getNumberInput();
    }

    public static int displayUserMenu(){
        System.out.println("Please select an option ");
        System.out.println("1. View list of Admin");
        System.out.println("2. View list of Restaurant Owners");
        System.out.println("3. View list of Customers");
        System.out.println("4. View list of delivery agents");
        System.out.println("5. Back");
        System.out.println("0. Exit");

        return InputHandler.getNumberInput();
    }

    public static int restaurantAdminMenu() {
        System.out.println("Select one from below");
        System.out.println("1. Register Restaurant");
        System.out.println("2. View all restaurants owned");
        System.out.println("3. View Restaurant details");
        System.out.println("4. Edit owned Restaurant details");
        if (CurrentUserCredentials.getCurrentUser().getRoleId() == UserRoles.ADMIN.getRoleIdValue()) {
            System.out.println("5. Edit Restaurant details");
            System.out.println("6. View all restaurants");
            System.out.println("7. View restaurants owned by ID");
            System.out.println("8. Back");
        } else {
            System.out.println("5. Logout");
        }
        System.out.println("0. Exit");

         return InputHandler.getNumberInput();
    }

}