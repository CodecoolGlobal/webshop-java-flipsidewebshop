package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Get request received on /.");

        HttpSession session = req.getSession(false);
        Cart cart;

        if (session == null) {
            logger.debug("No session received, creating anonymous.");
            cart = new Cart();
            session = req.getSession();
            session.setAttribute("userName", "anonymous");
            session.setAttribute("cart", cart);
        } else {
            logger.debug("Session received with id {}.", session.getId());
            cart = (Cart) session.getAttribute("cart");
        }

        ProductDao productDataStore = ProductDaoJdbc.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJdbc.getInstance();
        SupplierDao supplierDataStore = SupplierDaoJdbc.getInstance();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("numberOfProductsInCart", cart.getNumberOfProductsInCart());
        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("products", productDataStore);
        context.setVariable("suppliers", supplierDataStore.getAll());
        context.setVariable("cart", ((Cart) session.getAttribute("cart")).getShoppingCart());

        engine.process("product/index.html", context, resp.getWriter());
    }
}