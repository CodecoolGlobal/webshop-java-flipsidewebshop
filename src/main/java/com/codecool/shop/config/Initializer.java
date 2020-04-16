package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoJdbc.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJdbc.getInstance();
        SupplierDao supplierDataStore = SupplierDaoJdbc.getInstance();
        //setting up a new supplier
        Supplier DGK = new Supplier("DGK", "Skateboards");
        //supplierDataStore.add(DGK);
        DGK.setId(1);
        Supplier enjoi = new Supplier("Enjoi", "Skateboards");
        //supplierDataStore.add(enjoi);
        enjoi.setId(2);
        Supplier rodriguez = new Supplier("Rodriduez", "Skateboards");
        //supplierDataStore.add(rodriguez);
        rodriguez.setId(3);
        // boards - new stuff
        Supplier burton = new Supplier("Burton", "Snowboards");
        //supplierDataStore.add(burton);
        burton.setId(4);
        Supplier jones = new Supplier("Jones", "Snowboards");
        //supplierDataStore.add(jones);
        jones.setId(5);

        //setting up a new product category
        ProductCategory skateboard = new ProductCategory("Skateboard", "Summer", "A short narrow board with two small wheels fixed to the bottom of either end, on which a person can ride pushing one foot against the ground.");
        //productCategoryDataStore.add(skateboard);
        skateboard.setId(1);
        ProductCategory snowboard = new ProductCategory("Snowboard", "Winter", "A piece of fiberglass, P-TEX, wood compound that makes you surf the slopes in winter.");
        //productCategoryDataStore.add(snowboard);
        snowboard.setId(2);

        //setting up products and printing it
        productDataStore.add(new Product("Bruce Lee", 69.95f, "USD", "Fantastic price. You can truly skate with the power of Bruce Lee", skateboard, DGK));
        productDataStore.add(new Product("Zen", 57, "USD", "May the Zen and balance be with you", skateboard, DGK));
        productDataStore.add(new Product("Naruto", 89, "USD", "With this, you can be the real Ninja on the streets", skateboard, rodriguez));
        productDataStore.add(new Product("Enjoi", 54.95f, "USD", "Ride on the streets in a peaceful way", skateboard, enjoi));
        productDataStore.add(new Product("Bloom", 54.95f, "USD", "Flowers will follow you with this board", skateboard, DGK));
        productDataStore.add(new Product("Yin Yang", 54.95f, "USD", "Ride on the streets with a balanced soul", skateboard, DGK));
        productDataStore.add(new Product("Ghetto Bears", 54.95f, "USD", "Bring the beasts with you", skateboard, DGK));


        productDataStore.add(new Product("Custom", 300, "USD", "All times classic one-quiver all-mountain camber board that fits every rider.", snowboard, burton));
        productDataStore.add(new Product("Flagship", 354, "USD", "This snowboard was created for exactly one reason: to set new freeride standards!", snowboard, jones));
        productDataStore.add(new Product("Dream Catcher", 287, "USD", "This board is designed to shred the entire mountain in the most playful way possible.", snowboard, jones));
        System.out.println(productCategoryDataStore.getAll());
        System.out.println(supplierDataStore.getAll());
    }
}
