package com.narola.onlinefooddeliverysystemV1.service;

import com.narola.onlinefooddeliverysystemV1.dao.CartDao;
import com.narola.onlinefooddeliverysystemV1.dao.OrderDao;
import com.narola.onlinefooddeliverysystemV1.dao.OrderItemsDao;
import com.narola.onlinefooddeliverysystemV1.exception.DAOLayerException;
import com.narola.onlinefooddeliverysystemV1.input.InputHandler;
import com.narola.onlinefooddeliverysystemV1.manager.CustomerManager;
import com.narola.onlinefooddeliverysystemV1.model.CartDetails;
import com.narola.onlinefooddeliverysystemV1.model.Order;
import com.narola.onlinefooddeliverysystemV1.model.OrderDetail;
import com.narola.onlinefooddeliverysystemV1.session.CurrentUserCredentials;
import com.narola.onlinefooddeliverysystemV1.view.OrderView;

import java.util.Collections;
import java.util.List;

public class OrderService {

    private OrderView orderView = new OrderView();
    private OrderDao orderDao = new OrderDao();
    private CartDao cartDao = new CartDao();
    private OrderItemsDao orderItemsDao = new OrderItemsDao();
    private static CustomerManager customerManager = new CustomerManager();

    public void placeUserOrder() {
        try {
            List<CartDetails> cartDetailsList = cartDao.getUserCartDetails(CurrentUserCredentials.getCurrentUser().getUserId());
            if (!cartDetailsList.isEmpty()) {
                int choice = orderView.takeInputForOrder();

                if (choice == 1) {
                    int customerId = CurrentUserCredentials.getCurrentUser().getUserId();
                    double totalAmount = orderDao.getTotalAmountForOrder(customerId);
                    int orderId = orderDao.placeOrder(customerId, totalAmount);

                    for (CartDetails cartList : cartDetailsList) {
                        orderItemsDao.addItemsToOrder(cartList, orderId, customerId);
                    }
                    cartDao.deleteUserCart(customerId);
                    System.out.println("Order placed successfully");
                } else {
                    System.out.println("No items exist in cart.");
                    customerManager.handleCustomerActions();
                }
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    public List<Order> getUserOrderList(){
        try {
            return orderDao.getAllUserOrders(CurrentUserCredentials.getCurrentUser().getUserId());
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();

    }
    public void displayOrderDetails() {
        try {
            List<Order> orderList = getUserOrderList();
            orderView.printOrderList(orderList);

            System.out.println("Enter order Id : ");
            int orderId = InputHandler.getNumberInput();

            List<OrderDetail> orderDetailList = orderDao.getAllOrderDetails(orderId);
            orderView.printOrderDetails(orderDetailList);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }
}
