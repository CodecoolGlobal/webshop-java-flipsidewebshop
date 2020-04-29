package com.codecool.shop.model;

import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private List<Item> items;
    private Date orderTime;
    private String status;

    public Order(List<Item> items) {
        this.items = items;
        this.orderTime = new Date();
        this.status = "incoming";
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public List<Item> getItems() {
        return items;
    }
}
