package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/api/add-item-to-cart"})
public class APIaddItemToCart extends HttpServlet {

    private Object ArrayList;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("item_id"));

        ProductDao allProducts = ProductDaoMem.getInstance();
        ProductCategoryDao allProductCategories = ProductCategoryDaoMem.getInstance();

        Cart cart = Customer.getCart();
        Product product = allProducts.find(id);
        boolean successOfAddToCart = cart.add(product);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, resp, request.getServletContext());

        context.setVariable("categories", allProductCategories.getAll());
        context.setVariable("products", allProducts.getAll());

        engine.process("product/index.html", context, resp.getWriter());
    }

}
