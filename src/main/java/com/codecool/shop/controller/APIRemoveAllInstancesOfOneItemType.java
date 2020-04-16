package com.codecool.shop.controller;


import com.codecool.shop.controller.json.requestIdContainer;
import com.codecool.shop.controller.json.requestRemoveContainer;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoJdbc;
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

@WebServlet(urlPatterns = {"/api/remove-item"})
public class APIRemoveAllInstancesOfOneItemType extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        /* Get request body */
        String param = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        requestRemoveContainer requestContainer = new Gson().fromJson(param, requestRemoveContainer.class);
        int id = Integer.parseInt(requestContainer.getId());

        /* method body*/
        HttpSession session = request.getSession(false);
        Cart cart = (Cart) session.getAttribute("cart");
        ProductDao allProducts = ProductDaoJdbc.getInstance();
        Product currentProduct = allProducts.find(id);
        cart.removeItem(currentProduct);
        //customer.removeItemFromCart(currentProduct);

        /* Generate and send response */
        String JSONrepsonse = new Gson().toJson(true);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(JSONrepsonse);
        out.flush();
    }
}
