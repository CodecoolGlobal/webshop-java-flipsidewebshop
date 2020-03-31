package com.codecool.shop.model;

public class Item {
    private int quantity;
    private Product product;

    public Item(Product product, int quantity){
        this.quantity = quantity;
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setQuantity(int amount){
        quantity += amount;
    }
}
