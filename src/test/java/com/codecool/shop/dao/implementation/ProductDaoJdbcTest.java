package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoJdbcTest {

    @Test
    void testIsProductAdded() {
        Supplier DGK = new Supplier("DGK", "Skateboards"); // should add to SQL!
        DGK.setId(63);

        ProductCategory skateboard = new ProductCategory("Skateboard", "Summer",
                "A short narrow board with two small wheels fixed to the bottom of either end, " +
                        "on which a person can ride pushing one foot against the ground.");
        Product product = new Product("Bruce Lee", 69.95f, "USD",
                "Fantastic price. You can truly skate with the power of Bruce Lee", skateboard, DGK);

        ProductDao productDaoJdbc = ProductDaoJdbc.getInstance();
        System.out.println(productDaoJdbc.toString());
        productDaoJdbc.add(product);
        System.out.println("We added a product");
        DGK.setId(1); // once in SQL, no need to add it here
        List<Product> testProduct = productDaoJdbc.getBy(DGK);
        System.out.println(testProduct.toString());
        System.out.println("We got a product");

        for (int i = 0; i < testProduct.size(); i++) {
            System.out.println(testProduct.get(i));
            System.out.println("We are here");
        }


    }


}