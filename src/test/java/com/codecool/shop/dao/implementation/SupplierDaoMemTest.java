package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SupplierDaoMemTest {
    private SupplierDaoMem sDAO = SupplierDaoMem.getInstance();
    Supplier supplier = new Supplier("Burton", "blahblah");

    @Test
    public void testAdd(){
        sDAO.add(supplier);
        assertFalse(sDAO.getAll().isEmpty());
    }

    @Test
    public void testFind() {
        assertEquals("Burton", sDAO.find(1).getName());
    }

    @Test
    public void testRemove(){
        sDAO.remove(1);
        assertTrue(sDAO.getAll().isEmpty());
    }

}