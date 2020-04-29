package com.codecool.shop.controller;


import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.OrderDAOJdbc;
import com.codecool.shop.model.Cart;
import com.codecool.shop.dao.OrderDAO;
import com.codecool.shop.dao.implementation.OrderDAOJdbc;
import com.google.gson.Gson;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/api/reg-order"})
public class APIregisterOrder extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession(false);
        Cart cart = (Cart) session.getAttribute("cart");

        OrderDAO orderDao = OrderDAOJdbc.getInstance();
        boolean isSuccesful = orderDao.addNewOrder(cart);

        boolean status = false;
        if (isSuccesful){
            // empty the cart in case of successful order
            cart.emptyCart();
            session.setAttribute("cart", cart);
            status = true;
        }

        String JSONrepsonse = new Gson().toJson(status);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(JSONrepsonse);
        out.flush();
    }
}
