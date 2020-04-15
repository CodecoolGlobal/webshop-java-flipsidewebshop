package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ProductDaoJdbc implements ProductDao {
    private PSQLConnection psqlConnection;
    private static ProductDaoJdbc instance = null;
    // private SupplierDaoJdbc supplierDaoJdbc;
    // private ProductCategoryDaoJdbc productCategoryDaoJdbc;

    private ProductDaoJdbc() {
    }

    public static ProductDaoJdbc getInstance() {
        if (instance == null) {
            instance = new ProductDaoJdbc();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        psqlConnection = PSQLConnection.getInstance();
        // supplierDaoJdbc = SupplierDaoJdbc.getInstance();
        // productCategoryJdbc = ProductCategoryJdbc.getInstance();

        String supplierName = product.getSupplier().getName();
        int supplierId = 1; //suplierDaoJdbc.getSupplierId(supplierName);
        String productCategoryName = product.getProductCategory().getName();
        int productCategoryId = 1; //productCategoryJdbc.getProductCategoryId(productCategoryName);



        String sql = "INSERT INTO product (supplier_id, name, description, price, default_currency, product_category) VALUES(?,?,?,?,?,?)";

        try (Connection conn = psqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, supplierId);
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getDescription());
            pstmt.setFloat(4, product.getDefaultPrice());
            pstmt.setString(5, product.getDefaultCurrency().toString());
            pstmt.setInt(6, productCategoryId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Product find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Product> getAll() {
        return null;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory, Supplier supplier) {
        return null;
    }
}
