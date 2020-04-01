package com.codecool.shop.controller;


import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.Item;
import com.codecool.shop.model.Product;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.JsonElement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebServlet(urlPatterns = {"/api/add-to-cart"})
public class APIaddToCart extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        Customer customer = new Customer(); // TODO: get existing customer instance!
        //Cart cart = customer.getCartInstance();
        System.out.println(request.getParameterMap());

        //StringBuffer jb = new StringBuffer();
        try {
            Reader reader = request.getReader();
            //String  line = request.getParameter("id");
            Gson gson = new Gson();
            //String returned = line.toString();
            String object = gson.fromJson(reader, String.class);
            System.out.println("were here " + object); // mit adjak neki?

        } catch (Error e) {
            // crash and burn
            System.out.println(e);
        }





        int id = Integer.parseInt(request.getParameter("id"));
        ProductDao allProducts = ProductDaoMem.getInstance();
        Product currentProduct = allProducts.find(id);

        boolean successOfAdd = customer.updateCart(currentProduct, 1);
        
        String JSONrepsonse = new Gson().toJson(successOfAdd);

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(JSONrepsonse);
        out.flush();
    }
}
