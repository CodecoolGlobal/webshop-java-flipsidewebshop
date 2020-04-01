package com.codecool.shop.controller;


import com.codecool.shop.controller.json.requestIdContainer;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.Product;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/api/add-to-cart"})
public class APIaddToCart extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        //Customer customer = new Customer(); // TODO: get existing customer instance!
        //Cart cart = customer.getCartInstance();

        HttpSession session = request.getSession(false);
        Cart cart = (Cart) session.getAttribute("cart");


        String param = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        requestIdContainer requestIdContainer = new Gson().fromJson(param, requestIdContainer.class);

        int id = Integer.parseInt(requestIdContainer.getId());
        int amount = Integer.parseInt(requestIdContainer.getAmount());
        ProductDao allProducts = ProductDaoMem.getInstance();
        Product currentProduct = allProducts.find(id);
        System.out.println("id: "+ id + " amount: " + amount);

        boolean successOfAdd = cart.update(currentProduct, amount);
        System.out.println(cart.getShoppingCart());

        String JSONrepsonse = new Gson().toJson(successOfAdd);

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(JSONrepsonse);
        out.flush();
    }
}
