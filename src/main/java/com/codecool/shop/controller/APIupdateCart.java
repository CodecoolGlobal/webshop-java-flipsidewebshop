package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.Item;
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

@WebServlet(urlPatterns = {"/api/update-cart"})
public class APIaddItemToCart extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        boolean successOfUpdate;
        int id = Integer.parseInt(request.getParameter("item_id"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String operation = request.getParameter("operation");

        ProductDao allProducts = ProductDaoMem.getInstance();
        ProductCategoryDao allProductCategories = ProductCategoryDaoMem.getInstance();

        Customer customer = new Customer();
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


        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, resp, request.getServletContext());

        context.setVariable("categories", allProductCategories.getAll());
        context.setVariable("products", allProducts.getAll());

        engine.process("product/index.html", context, resp.getWriter());
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
