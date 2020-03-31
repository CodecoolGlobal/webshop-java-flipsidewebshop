package com.codecool.shop.controller;


import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.Item;
import com.codecool.shop.model.Product;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/api/add-to-cart"})
public class APIaddToCart extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        Customer customer = new Customer(); // TODO: get existing customer instance!
        Cart cart = customer.getCartInstance();


        int id = Integer.parseInt(request.getParameter("id"));
        ProductDao allProducts = ProductDaoMem.getInstance();
        Product currentProduct = allProducts.find(id);

        List<Item> itemsInCart = customer.getcartItems();
        boolean successOfAdd = false;
        if (cart.inCart(currentProduct)) {
            for (Item item : itemsInCart) {
                if (item.getProduct() == currentProduct) {
                    int amount = item.getQuantity();
                    successOfAdd = customer.updateCart(currentProduct, amount + 1);
                    System.out.println("quantity " + item.getQuantity());
                }
            }
        } else {
            successOfAdd = customer.updateCart(currentProduct, 1);
        }


        String JSONrepsonse = new Gson().toJson(successOfAdd);

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(JSONrepsonse);
        out.flush();
    }
}
