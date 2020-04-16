package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ProductDaoMemTest {
    ProductDaoMem pDAO = ProductDaoMem.getInstance();
    Supplier supplier = new Supplier("Burton", "old company");
    ProductCategory category = new ProductCategory("board", "winter", "for fun in the snow");
    Product product = new Product("Board",
            10,
            "USD",
            "Allround",
            category,
            supplier
    );

    @BeforeEach
    public void addData() {
        pDAO.add(product);
    }

    @AfterEach
    public void clearData(){
        pDAO.remove(1);
    }



    @Test
    public void testAdd() {
        assertFalse(pDAO.getAll().isEmpty());
    }

    @Test
    public void testRemove() {
        pDAO.remove(1);
        assertTrue(pDAO.getAll().isEmpty());
    }

    @Test
    public void testFind() {
        assertEquals("Board", pDAO.find(1).getName());
    }

    @Test
    public void testGetBySupplier() {
        assertEquals("Board", pDAO.getBy(supplier).get(0).getName());
    }

    @Test
    public void testGetByProductCategory() {
        assertEquals("Board", pDAO.getBy(category).get(0).getName());
    }

    @Test
    public void testGetByProductCategoryAndSupplier() {
        assertEquals("Board", pDAO.getBy(category, supplier).get(0).getName());
    }
}