package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDaoMemTest {
    private ProductCategoryDaoMem pcDAO;

    @BeforeEach
    public void setUp() {
        pcDAO = ProductCategoryDaoMem.getInstance();
    }


    @Test
    public void testAdd(){
        pcDAO.add(new ProductCategory("board", "winter", "blah"));
        assertFalse(pcDAO.getAll().isEmpty());
    }

    @Test
    public void testFindIsNotNull() {
        assertNotNull(pcDAO.find(pcDAO.getAll().size()));
    }

    @Test
    public void testFindTheGivenId() {
        assertEquals("board", pcDAO.find(1).getName());
    }

    @Test
    public void testRemove() {
        pcDAO.remove(1);
        assertTrue(pcDAO.getAll().isEmpty());
    }

}