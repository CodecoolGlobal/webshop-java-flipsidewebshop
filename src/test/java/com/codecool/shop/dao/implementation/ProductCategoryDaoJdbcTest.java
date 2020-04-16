package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDaoJdbcTest {
    ProductCategoryDaoJdbc pcDAO = ProductCategoryDaoJdbc.getInstance();
    ProductCategory category = new ProductCategory("test_tent", "summer", "waterproof");
    int insertId;



    private void addRecord(){
        String sql = "INSERT INTO product_category (name, description, department) VALUES ('test_tent', 'waterproof', 'summer')";
        try (Connection conn = PSQLConnection.getInstance().getConnection()){
            PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int rowAffected = pst.executeUpdate();
            if (rowAffected == 1) {
                ResultSet rs = pst.getGeneratedKeys();
                if(rs.next()){
                    insertId = rs.getInt(1);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    @AfterEach
    public void deleteRecord(){
        String sql = "DELETE FROM product_category " +
                     "WHERE name LIKE 'test_tent' AND description LIKE 'waterproof'";
        try (Connection conn = PSQLConnection.getInstance().getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
        insertId = -1;
    }


    @Test
    public void testAdd(){
        pcDAO.add(category);
        String sql = "SELECT * FROM product_category " +
                     "WHERE name ='test_tent' AND description = 'waterproof'";
        try (Connection conn = PSQLConnection.getInstance().getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                assertEquals("test_tent", rs.getString("name"));
            }
            rs.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

     @Test
    public void testFind() {
        addRecord();
        ProductCategory categoryToTest = pcDAO.find(insertId);
        assertEquals("test_tent", categoryToTest.getName());
     }

    @Test
    public void testRemove() {
        addRecord();
        pcDAO.remove(insertId);
        String sql = "SELECT name FROM product_category WHERE category_id = ?";
        try (Connection conn = PSQLConnection.getInstance().getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, insertId);
            ResultSet rs = ps.executeQuery();

            assertFalse(rs.next());
            rs.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testGetIDByCategoryName() {
        addRecord();
        assertEquals(insertId, pcDAO.getProductCategoryId("test_tent"));
    }

}