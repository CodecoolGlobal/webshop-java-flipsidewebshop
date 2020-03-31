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

    private Object ArrayList;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        boolean successOfUpdate;
        int id = Integer.parseInt(request.getParameter("item_id"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String operation = request.getParameter("operation");

        ProductDao allProducts = ProductDaoMem.getInstance();
        ProductCategoryDao allProductCategories = ProductCategoryDaoMem.getInstance();

        Customer customer = new Customer();
        List itemsInCart = customer.getcartItems();
        Product currentProduct = allProducts.find(id);
        for (Object item : itemsInCart) {
            Item currentItem = (Item) item;
            if (currentItem.getProduct() == currentProduct) {
                if (operation.equals("add")) {
                    successOfUpdate = add((Item) item);
                } else if (operation.equals("remove")) {
                    successOfUpdate = remove((Item) item);
                } else if (operation.equals("update")) {
                    successOfUpdate = update((Item) item, quantity);
                } else {
                    successOfUpdate = false;
                }
            }
        }


        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, resp, request.getServletContext());

        context.setVariable("categories", allProductCategories.getAll());
        context.setVariable("products", allProducts.getAll());

        engine.process("product/index.html", context, resp.getWriter());
    }

    private boolean add(Item item) {
        int currentQuantity = item.getQuantity();
        item.setQuantity(currentQuantity + 1);
        return true;
    }

    private boolean remove(Item item) {
        int currentQuantity = item.getQuantity();
        if (currentQuantity > 0) {
            item.setQuantity(currentQuantity - 1);
            return true;
        }
        return false;
    }

    private boolean update(Item item, int newAmount) {
        if (newAmount > 0) {
            item.setQuantity(newAmount);
            return true;
        }
        return false;
    }

}
