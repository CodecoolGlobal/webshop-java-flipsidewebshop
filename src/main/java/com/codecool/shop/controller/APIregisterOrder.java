package com.codecool.shop.controller;


import com.codecool.shop.dao.implementation.OrderDAOJdbc;
import com.codecool.shop.model.Cart;
import com.codecool.shop.dao.OrderDAO;
import com.codecool.shop.dao.implementation.OrderDAOJdbc;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/api/reg-order"})
public class APIregisterOrder extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String billAddress = request.getParameter("baddress");
        String shipAddress = request.getParameter("shipaddress");

        HttpSession session = request.getSession(false);
        Cart cart = (Cart) session.getAttribute("cart");

        OrderDAO orderDao = OrderDAOJdbc.getInstance();
        boolean isSuccesful = orderDao.addNewOrder(cart);
        System.out.println(isSuccesful);
    }
}
