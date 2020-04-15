package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJdbc implements ProductDao {
    private PSQLConnection psqlConnection;
    private static ProductDaoJdbc instance = null;
    private SupplierDaoJdbc supplierDaoJdbc;
    private ProductCategoryDaoJdbc productCategoryDaoJdbc;

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
        supplierDaoJdbc = SupplierDaoJdbc.getInstance();
        productCategoryDaoJdbc = ProductCategoryDaoJdbc.getInstance();

        String supplierName = product.getSupplier().getName();
        int supplierId = supplierDaoJdbc.getSupplierId(supplierName);
        String productCategoryName = product.getProductCategory().getName();
        int productCategoryId = productCategoryDaoJdbc.getProductCategoryId(productCategoryName);

        String sql = "INSERT INTO product (supplier_id, name, description, price, default_currency, product_category)" +
                " VALUES(?,?,?,?,?,?)";

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
            e.printStackTrace();
        }
    }

    @Override
    public Product find(int id) {
        psqlConnection = PSQLConnection.getInstance();
        String sql = "SELECT * FROM product WHERE product_id =?";

        try (Connection conn = psqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                Product product = createNewProductFromSQLResult(resultSet);
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(int id) {
        psqlConnection = PSQLConnection.getInstance();
        String sql ="DELETE FROM product WHERE product_id = ?";

        try (Connection conn = psqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        psqlConnection = PSQLConnection.getInstance();
        String sql = "SELECT * FROM product";

        try (Connection conn = psqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Product product = createNewProductFromSQLResult(resultSet);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
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

    private Product createNewProductFromSQLResult(ResultSet resultSet) throws SQLException {
        supplierDaoJdbc = SupplierDaoJdbc.getInstance();
        productCategoryDaoJdbc = ProductCategoryDaoJdbc.getInstance();

        resultSet.getString("name");
        String name = resultSet.getString("name");
        float price = resultSet.getFloat("price");
        String currencyString = resultSet.getString("default_currency");
        String description = resultSet.getString("description");
        int productCategoryId = resultSet.getInt("product_category");
        int supplierId = resultSet.getInt("supplier_id");
        ProductCategory productCategory = productCategoryDaoJdbc.find(productCategoryId);
        Supplier supplier = supplierDaoJdbc.find(supplierId);

        return new Product(name, price, currencyString, description, productCategory, supplier);
    }
}
