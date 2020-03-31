package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoMem;
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

@WebServlet(urlPatterns = {"/api/update-cart"})
public class APIupdateCart extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        boolean successOfUpdate = false;
        int id = Integer.parseInt(request.getParameter("id"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String operation = request.getParameter("operation");

        ProductDao allProducts = ProductDaoMem.getInstance();

        Customer customer = new Customer(); // TODO: get existing customer instance!
        List<Item> itemsInCart = customer.getcartItems();
        Product currentProduct = allProducts.find(id);
        for (Item item : itemsInCart) {
            if (item.getProduct() == currentProduct) {
                int currentQuantity = item.getQuantity();
                switch (operation) {
                    case "add":
                        successOfUpdate = update(customer, currentProduct, currentQuantity + 1);
                        break;
                    case "remove":
                        successOfUpdate = update(customer, currentProduct, currentQuantity - 1);
                        break;
                    case "update":
                        successOfUpdate = update(customer, currentProduct, quantity);
                        break;
                    default:
                        successOfUpdate = false;
                        break;
                }
            }
        }

        String JSONrepsonse = new Gson().toJson(successOfUpdate);

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(JSONrepsonse);
        out.flush();
    }

    private boolean update(Customer customer, Product product, int newAmount) {
        if (newAmount > 0) {
            customer.updateCart(product, newAmount);
            return true;
        } else if (newAmount == 0) {
            customer.removeItemFromCart(product);
            return true;
        } else {
            return false;
        }
    }

}
