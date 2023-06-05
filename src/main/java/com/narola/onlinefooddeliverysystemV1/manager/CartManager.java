package com.narola.onlinefooddeliverysystemV1.manager;

import com.narola.onlinefooddeliverysystemV1.menu.AppMenu;
import com.narola.onlinefooddeliverysystemV1.model.Cart;
import com.narola.onlinefooddeliverysystemV1.model.CartDetails;
import com.narola.onlinefooddeliverysystemV1.model.CartItem;
import com.narola.onlinefooddeliverysystemV1.service.CartService;
import com.narola.onlinefooddeliverysystemV1.service.OrderService;
import com.narola.onlinefooddeliverysystemV1.utility.ProgramTerminator;
import com.narola.onlinefooddeliverysystemV1.view.CartView;

import java.util.List;

import static com.narola.onlinefooddeliverysystemV1.constant.Constants.*;

public class CartManager {

    private static CartService cartService = new CartService();
    private OrderService orderService = new OrderService();
    private static CustomerManager customerManager = new CustomerManager();
    private CartView cartView = new CartView();
    private AppMenu appMenu = new AppMenu();


    public void doAddToCart() {
        Cart cart = cartView.getInformationForAddToCart();
        CartItem cartItem = cartView.getItemInformationForCart(cart);
        cartService.addToCart(cart, cartItem);
    }

    public void displayUserCartDetails() {
        List<CartDetails> cartDetailsList = cartService.getUserCartDetails();
        cartView.printCartDetails(cartDetailsList);
    }

    public void updateCart() {
        int choice = appMenu.displayUpdateCartMenuAndTakeInput();

        switch (choice) {

            case UPDATE_ITEM_QUANTITY:
                cartService.updateCartItemQuantity();
                break;
            case DELETE_ITEM:
                cartService.deleteCartItem();
                break;
            case BACK_TO_ORDER_ITEM:
                customerManager.handleCustomerActions();
                break;
            case EXIT:
                ProgramTerminator.exit();
                break;
            default:
                System.out.println("Invalid Choice");
        }
        updateCart();
    }

    public void placeOrder() {
        displayUserCartDetails();
        orderService.placeUserOrder();
    }
}
