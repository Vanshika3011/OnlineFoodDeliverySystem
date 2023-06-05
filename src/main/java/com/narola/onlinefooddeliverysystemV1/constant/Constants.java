package com.narola.onlinefooddeliverysystemV1.constant;

public class Constants {

    public final static int EXIT = 0;

    public final static int MAX_TRIALS_FOR_LOGIN = 3;
    public final static int MAX_TRIALS_FOR_VERIFICATION = 3;

    public final static int MENU_SIGNUP = 1;
    public final static int MENU_LOGIN = 2;

    public final static int USER_MANAGEMENT = 1;
    public final static int RESTAURANT_MANAGEMENT = 2;
    public final static int ADMIN_LOGOUT = 3;


    public final static int VIEW_REST_OWNED = 1;
    public final static int VIEW_REST_DETAILS = 2;
    public final static int EDIT_OWNED_REST_DETAILS = 3;
    public final static int ADD_RESTAURANT_MENU_ITEMS = 4;
    public final static int VIEW_RESTAURANT_MENU_ITEMS = 5;
    public final static int REGISTER_REST = 6;
    public final static int GO_BACK_TO_LOGIN_SIGNUP = 7;


    public final static int VIEW_USERS_DETAILS_BY_ROLE = 1;
    public final static int BACK_TO_ADMIN_MAIN_MENU = 2;

    public final static int VIEW_ALL_RESTAURANTS = 1;
    public final static int VIEW_RESTAURANT_OWNED_BY_OWNERID = 2;
    public final static int VIEW_RESTAURANT_DETAILS = 3;
    public final static int GO_BACK_TO_ADMIN_MAIN_MENU = 4;

    public final static int EDIT_RESTAURANT_DETAILS = 1;
    public final static int EDIT_RESTAURANT_ADDRESS_DETAILS = 2;
    public final static int BACK_TO_RESTAURANT_ADMIN_MAIN_MENU = 3;

    public final static int MENU_ITEMS_ALPHABETICALLY_ASC = 1;
    public final static int MENU_ITEMS_ALPHABETICALLY_DESC = 2;
    public final static int MENU_ITEMS_BY_PRICE_HIGH_TO_LOW = 3;
    public final static int MENU_ITEMS_BY_PRICE_LOW_TO_HIGH = 4;
    public final static int MENU_ITEMS_RECENTLY_ADDED = 5;
    public final static int BACK_TO_ADMIN_ACTIONS = 6;

    public final static int SEARCH_FOOD_ITEMS = 1;
    public final static int ADD_TO_CART = 2;
    public final static int VIEW_CART = 3;
    public final static int UPDATE_CART = 4;
    public final static int CHECKOUT = 5;
    public final static int MY_ORDERS = 6;
    public final static int CUSTOMER_LOGOUT = 7;

    public final static int UPDATE_ITEM_QUANTITY = 1;
    public final static int DELETE_ITEM = 2;
    public final static int BACK_TO_ORDER_ITEM = 3;

    public static void messageForSelectInput() {
        System.out.println("\nSELECT SELECT AN OPTION FROM BELOW");
    }
}
