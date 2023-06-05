package com.narola.onlinefooddeliverysystemV1.view;

import com.narola.onlinefooddeliverysystemV1.input.InputHandler;
import com.narola.onlinefooddeliverysystemV1.model.Order;
import com.narola.onlinefooddeliverysystemV1.model.OrderDetail;

import java.util.List;

public class OrderView {

    public int takeInputForOrder() {
        System.out.println("Press 1 to place order : ");
        int choice = InputHandler.getNumberInput();

        return choice;
    }

    public void printOrderList(List<Order> orderList) {
        if (orderList == null || orderList.isEmpty()) {
            System.out.println("No orders found.");
            return;
        } else {
            System.out.println("OrderId\tTotal Amount\tOrder Date\t\t\tOrder Status Name");
            for (Order orderDetailList : orderList) {
                System.out.println(orderDetailList.getOrderId() + "\t\t" + orderDetailList.getTotalAmount() + "\t\t\t" + orderDetailList.getOrderDate() + "\t" + orderDetailList.getOrderStatusName());
            }
        }
    }

    public void printOrderDetails(List<OrderDetail> orderDetailList) {
        if (orderDetailList == null || orderDetailList.isEmpty()) {
            System.out.println("No orders found.");
            return;
        } else {
            System.out.println("Restaurant Name : " + orderDetailList.get(0).getRestaurantName());
            System.out.println("Order Id        : " + orderDetailList.get(0).getOrderId());
            System.out.println("Order Status    : " + orderDetailList.get(0).getOrderStatusName() + "\n");

            System.out.println("ItemId\tQuantity\tPrice\tItem Name");
            for (OrderDetail orderList : orderDetailList) {
                System.out.println(orderList.getOrderItemId() + "\t\t" + orderList.getQuantity() + "\t\t\t" + orderList.getPrice() + "\t" + orderList.getItemName());
            }
            System.out.println("\nTotal Amount    : " + orderDetailList.get(0).getTotalAmount());
        }

    }
}
