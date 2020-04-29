package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderDAOJdbcTest {
    OrderDAOJdbc odao = new OrderDAOJdbc();



    @Test
    public void testAddNewOrder(){
        Cart cart = new Cart();
        ProductCategory pc = new ProductCategory("testcategory", "TEST", "no description");
        Supplier sup = new Supplier("testsupplier", "no description here either");

        cart.update(new Product("testproduct", 10, "USD", "nothing", pc, sup), 1);

        assertTrue(odao.addNewOrder(cart));

    }

}