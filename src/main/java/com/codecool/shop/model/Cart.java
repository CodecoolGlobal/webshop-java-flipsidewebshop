package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart {
    private List<Item> shoppingCart;

    public int getNumberOfProductsInCart() {
        int counter = 0;
        for (Item cartElement : shoppingCart) {
            counter += cartElement.getQuantity();
        }
        return counter;
    }

    public Cart(){
        this.shoppingCart = new ArrayList<Item>();
    }

    public boolean update(Product product, int quantity){
        try{
            if (inCart(product)){
                updateItem(product, quantity);
            } else {
                addItem(product, quantity);
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public List<Item> getShoppingCart(){
        return shoppingCart;
    }

    public boolean inCart(Product product){
        return shoppingCart.stream().anyMatch(item -> product.getId() == (item.getProduct().getId()));
    }

    // TODO prevent possibility of negative quantities.
    private void updateItem(Product product, int quantity){
        Objects.requireNonNull(shoppingCart.stream()
                .filter(item -> item.getProduct().getId() == (product.getId()))
                .findFirst()
                .orElse(null)
        ).setQuantity(quantity);
    }

    public void removeItem(Product product){
        shoppingCart.removeIf(item -> item.getProduct().getId() == (product.getId()));
    }

    private void addItem(Product product, int quantity){
        shoppingCart.add(new Item(product, quantity));
    }

    public void emptyCart(){
        shoppingCart.clear();
    }
}
