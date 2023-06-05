package com.narola.onlinefooddeliverysystemV1.menu;

import com.narola.onlinefooddeliverysystemV1.constant.Constants;
import com.narola.onlinefooddeliverysystemV1.input.InputHandler;

public class AppMenu {

    public static int displayMainMenuAndTakeInput() {
        System.out.println("\nPlease select to Login/ Sign-up");
        System.out.println("1. Sign-up");
        System.out.println("2. Login");
        System.out.println("0. Exit");

        return InputHandler.getNumberInput();
    }

    public static int adminMenu(){
        Constants.messageForSelectInput();
        System.out.println("1. User Management");
        System.out.println("2. Restaurant Management");
        System.out.println("3. Logout");
        System.out.println("0. Exit");

        return InputHandler.getNumberInput();
    }

    public static int displayUserManagementMenuAndTakeInput(){
        Constants.messageForSelectInput();
        System.out.println("1. View list of users");
        System.out.println("2. Back");
        System.out.println("0. Exit");
        return InputHandler.getNumberInput();
    }
    public static int displayUserDetailsMenuAndTakeInput(){
        Constants.messageForSelectInput();
        System.out.println("1. View list of Admin");
        System.out.println("2. View list of Restaurant Owners");
        System.out.println("3. View list of Customers");
        System.out.println("4. View list of delivery agents");

        return InputHandler.getNumberInput();
    }

    public static int displayRestaurantManagementMenuAndTakeInputFromAdmin(){
        Constants.messageForSelectInput();
        System.out.println("1. View all restaurants");
        System.out.println("2. View all restaurants owned by a User");
        System.out.println("3. View Restaurant details");
        System.out.println("4. Back");
        System.out.println("5. Exit");

        return InputHandler.getNumberInput();
    }

    public static int displayRestaurantManagementMenuAndTakeInputFromRestaurantAdmin() {
        Constants.messageForSelectInput();
        System.out.println("1. View all restaurants owned");
        System.out.println("2. View Restaurant details");
        System.out.println("3. Edit owned Restaurant details");
        System.out.println("4. Add Restaurant Menu Items");
        System.out.println("5. View Restaurant Menu Items");
        System.out.println("6. Register Restaurant");
        System.out.println("7. Logout");
        System.out.println("0. Exit");

        return InputHandler.getNumberInput();
    }

    public static int displayEditRestaurantMenuAndTakeInputFromRestaurantAdmin(){
        Constants.messageForSelectInput();
        System.out.println("1. To update restaurant name, contact details, hours of operation");
        System.out.println("2. To update restaurant address details");
        System.out.println("3. Back");
        System.out.println("0. Exit");

        return InputHandler.getNumberInput();
    }

    public static int displayRestaurantMenuItemsAndTakeInputFromAdmin(){
        Constants.messageForSelectInput();
        System.out.println("1. View all items alphabetically asc");
        System.out.println("2. View all items alphabetically desc");
        System.out.println("3. View items by price high to low");
        System.out.println("4. View items by price low to high");
        System.out.println("5. View items recently added");
        System.out.println("6. Back");
        System.out.println("0. Exit");

        return InputHandler.getNumberInput();
    }

    public static int displayCustomerMainMenuAndTakeInput(){
        Constants.messageForSelectInput();
        System.out.println("1. Search for food items");
        System.out.println("2. Add items to my cart");
        System.out.println("3. View Cart");
        System.out.println("4. Update Cart");
        System.out.println("5. Checkout");
        System.out.println("6. My Orders");
        System.out.println("7. Logout");
        System.out.println("0. Exit");

        return InputHandler.getNumberInput();

    }

    public static int displayUpdateCartMenuAndTakeInput() {
        Constants.messageForSelectInput();
        System.out.println("1. Update item Quantity");
        System.out.println("2. Delete Item");
        System.out.println("3. Back");
        System.out.println("0. Exit");

        return InputHandler.getNumberInput();
    }
}