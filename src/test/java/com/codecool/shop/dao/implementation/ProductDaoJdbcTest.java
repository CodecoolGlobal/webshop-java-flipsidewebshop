package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoJdbcTest {

    @Test
    void testIsProductAdded() {
        Supplier DGK = new Supplier("DGK", "Skateboards");
        ProductCategory skateboard = new ProductCategory("Skateboard", "Summer",
                "A short narrow board with two small wheels fixed to the bottom of either end, " +
                        "on which a person can ride pushing one foot against the ground.");
        Product product = new Product("Bruce Lee", 69.95f, "USD",
                "Fantastic price. You can truly skate with the power of Bruce Lee", skateboard, DGK);

        ProductDao productDaoJdbc = ProductDaoJdbc.getInstance();
        productDaoJdbc.add(product);

        System.out.println(productDaoJdbc.getBy(DGK));





    }


}