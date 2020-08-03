package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.ProductController;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJdbc implements ProductDao {
    private static final Logger logger = LoggerFactory.getLogger(ProductDaoJdbc.class);

    private PSQLConnection psqlConnection;
    private static ProductDaoJdbc instance = null;
    private SupplierDaoJdbc supplierDaoJdbc;
    private ProductCategoryDaoJdbc productCategoryDaoJdbc;

    private ProductDaoJdbc() {
        logger.debug("Product DAO JDBC created.");
    }

    public static ProductDaoJdbc getInstance() {
        if (instance == null) {
            logger.debug("No instance was found (lazy initialization) of Product DAO JDBC, need to create one.");
            instance = new ProductDaoJdbc();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        logger.debug("New product {} arrived for addition.", product.getName());
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
            logger.debug("Connection with SQL established successfully.");
            pstmt.setInt(1, supplierId);
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getDescription());
            pstmt.setFloat(4, product.getDefaultPrice());
            pstmt.setString(5, product.getDefaultCurrency().toString());
            pstmt.setInt(6, productCategoryId);
            pstmt.executeUpdate();
            logger.debug("New item added to DB.");
        } catch (SQLException e) {
            logger.debug("Could not establish connection with DB.");
            e.printStackTrace();
        }
    }

    @Override
    public Product find(int id) {
        logger.debug("Have to find product with id {} in db.", id);
        psqlConnection = PSQLConnection.getInstance();
        String sql = "SELECT * FROM product WHERE product_id=?";

        try (Connection conn = psqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            logger.debug("Connection with SQL established successfully.");
            pstmt.setInt(1, id);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                Product product = createNewProductFromSQLResult(resultSet);
                return product;
            }
        } catch (SQLException e) {
            logger.debug("Could not establish connection with DB.");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(int id) {
        logger.debug("Have to remove product with id {} from DB.", id);
        psqlConnection = PSQLConnection.getInstance();
        String sql = "DELETE FROM product WHERE product_id=?";

        try (Connection conn = psqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            logger.debug("Connection with SQL established successfully.");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.debug("Could not establish connection with DB.");
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> getAll() {
        logger.debug("Have to get all products in DB.");
        List<Product> products = new ArrayList<>();
        psqlConnection = PSQLConnection.getInstance();
        String sql = "SELECT * FROM product";

        try (Connection conn = psqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            logger.debug("Connection with SQL established successfully.");
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Product product = createNewProductFromSQLResult(resultSet);
                products.add(product);
            }
        } catch (SQLException e) {
            logger.debug("Could not establish connection with DB.");
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        logger.debug("Have to get all products by supplier {}.", supplier.getName());
        List<Product> products = new ArrayList<>();
        psqlConnection = PSQLConnection.getInstance();
        String sql = "SELECT * FROM product WHERE supplier_id=?";

        try (Connection conn = psqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            logger.debug("Connection with SQL established successfully.");
            pstmt.setInt(1, supplier.getId());
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Product product = createNewProductFromSQLResult(resultSet);
                products.add(product);
            }
        } catch (SQLException e) {
            logger.debug("Could not establish connection with DB.");
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        logger.debug("Have to get all products in category {} from DB.", productCategory.getName());
        List<Product> products = new ArrayList<>();
        psqlConnection = PSQLConnection.getInstance();
        String sql = "SELECT * FROM product WHERE product_category=?";

        try (Connection conn = psqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            logger.debug("Connection with SQL established successfully.");
            pstmt.setInt(1, productCategory.getId());
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Product product = createNewProductFromSQLResult(resultSet);
                products.add(product);
            }
        } catch (SQLException e) {
            logger.debug("Could not establish connection with DB.");
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory, Supplier supplier) {
        logger.debug("Have to get all products with category {} and supplier {} from DB.", productCategory.getName(), supplier.getName());

        List<Product> products = new ArrayList<>();
        psqlConnection = PSQLConnection.getInstance();
        String sql = "SELECT * FROM product WHERE product_category=? AND supplier_id=?";

        try (Connection conn = psqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            logger.debug("Connection with SQL established successfully.");
            pstmt.setInt(1, productCategory.getId());
            pstmt.setInt(2, supplier.getId());
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Product product = createNewProductFromSQLResult(resultSet);
                products.add(product);
            }
        } catch (SQLException e) {
            logger.debug("Could not establish connection with DB.");
            e.printStackTrace();
        }
        return products;
    }

    private Product createNewProductFromSQLResult(ResultSet resultSet) throws SQLException {
        logger.debug("Have to create new instance of Product to give back for further use.");
        supplierDaoJdbc = SupplierDaoJdbc.getInstance();
        productCategoryDaoJdbc = ProductCategoryDaoJdbc.getInstance();

        String name = resultSet.getString("name");
        float price = resultSet.getFloat("price");
        String currencyString = resultSet.getString("default_currency");
        String description = resultSet.getString("description");
        int productCategoryId = resultSet.getInt("product_category");
        int supplierId = resultSet.getInt("supplier_id");
        ProductCategory productCategory = productCategoryDaoJdbc.find(productCategoryId);
        Supplier supplier = supplierDaoJdbc.find(supplierId);

        Product newProduct = new Product(name, price, currencyString, description, productCategory, supplier);
        newProduct.setId(resultSet.getInt("product_id"));
        return newProduct;
    }
}
