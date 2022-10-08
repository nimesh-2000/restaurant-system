package model;

import java.util.ArrayList;

public class Order {
     private String deliverId;
    private String customerId;
    private String orderId;
    private String orderName;
    private double totalPrice;
    private String orderDate;
    private ArrayList<OrderDetail> items;

    public Order(String deliverId, String customerId, String orderId, String orderName, double totalPrice, String orderDate, ArrayList<OrderDetail> items) {
        this.deliverId = deliverId;
        this.customerId = customerId;
        this.orderId = orderId;
        this.orderName = orderName;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.items = items;
    }

    public Order(String deliverId, String customerId, String orderId, String orderName, double totalPrice, ArrayList<OrderDetail> items) {
        this.deliverId = deliverId;
        this.customerId = customerId;
        this.orderId = orderId;
        this.orderName = orderName;
        this.totalPrice = totalPrice;
        this.items = items;
    }

    public Order() {
    }

    public Order(String customerId, String orderId, String orderName, double totalPrice) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.orderName = orderName;
        this.totalPrice = totalPrice;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "customerId='" + customerId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderName='" + orderName + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public ArrayList<OrderDetail> getItems() {
        return items;
    }

    public void setItems(ArrayList<OrderDetail> items) {
        this.items = items;
    }

    public String getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
