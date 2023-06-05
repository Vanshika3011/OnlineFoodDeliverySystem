package com.narola.onlinefooddeliverysystemV1.manager;

import com.narola.onlinefooddeliverysystemV1.OnlineFoodDelivery;
import com.narola.onlinefooddeliverysystemV1.menu.AppMenu;
import com.narola.onlinefooddeliverysystemV1.service.OrderService;
import com.narola.onlinefooddeliverysystemV1.utility.ProgramTerminator;

import static com.narola.onlinefooddeliverysystemV1.constant.Constants.*;

public class CustomerManager {
    private AppMenu appMenu = new AppMenu();
    private static RestaurantMenuManager restaurantMenuManager = new RestaurantMenuManager();
    private CartManager cartManager = new CartManager();
    private OrderService orderService = new OrderService();

    public void handleCustomerActions() {

        int choice = appMenu.displayCustomerMainMenuAndTakeInput();

        switch (choice) {

            case SEARCH_FOOD_ITEMS:
                restaurantMenuManager.doSearchFoodItems();
                break;
            case ADD_TO_CART:
                cartManager.doAddToCart();
                break;
            case VIEW_CART:
                cartManager.displayUserCartDetails();
                break;
            case UPDATE_CART:
                cartManager.updateCart();
                break;
            case CHECKOUT:
                cartManager.placeOrder();
                break;
            case MY_ORDERS:
                orderService.displayOrderDetails();
                break;
            case CUSTOMER_LOGOUT:
                OnlineFoodDelivery.main(null);
                break;
            case EXIT:
                ProgramTerminator.exit();
                break;
            default:
                System.out.println("Invalid choice!!");
        }
        handleCustomerActions();
    }
}
