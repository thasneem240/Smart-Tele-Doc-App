package com.example.capstoneprojectgroup4.ssearch_pharmacy;


public class Order {


    String OrderId;
    String OrderStatus;

    Order()
    {

    }

    public Order(String OrderId, String OrderStatus)
    {
        this.OrderId = OrderId;
        this.OrderStatus = OrderStatus;
    }


    public String getOrderStatus() {
        return OrderStatus;
    }

    public String getOrderId() {
        return OrderId;
    }




}
