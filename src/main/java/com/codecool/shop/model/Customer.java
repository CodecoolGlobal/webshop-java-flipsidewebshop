package com.codecool.shop.model;

import java.util.List;

public class Customer {
    private Cart cart;

    public Customer(){
        this.cart = new Cart();
    }

    public List<Item> getcartItems() {
        return cart.getShoppingCart();
    }

    public boolean updateCart(Product product, int quantity){
        return cart.update(product, quantity);
    }

    public void emptyCart(){
        cart.emptyCart();
    }

    public void removeItemFromCart(Product product){
        cart.removeItem(product);
    }
}
