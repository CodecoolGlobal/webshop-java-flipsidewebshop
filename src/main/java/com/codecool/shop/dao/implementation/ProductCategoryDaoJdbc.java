package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoJdbc implements ProductCategoryDao {
    private PSQLConnection psqlConnection;
    private static ProductCategoryDaoJdbc instance = null;

    private ProductCategoryDaoJdbc() {}

    public static ProductCategoryDaoJdbc getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoJdbc();
        }
        return instance;
    }

    @Override
    public void add(ProductCategory category) {
        psqlConnection = PSQLConnection.getInstance();
        String sql = "INSERT INTO product_category (name, description, department)" +
                " VALUES(?,?,?)";

        try (Connection conn = psqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, category.getName());
            pstmt.setString(2, category.getDescription());
            pstmt.setString(3, category.getDepartment());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ProductCategory find(int id) {
        psqlConnection = PSQLConnection.getInstance();
        String sql = "SELECT * FROM product_category WHERE category_id=?";

        try (Connection conn = psqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                ProductCategory productCategory = createNewProductCategoryFromSQLResult(resultSet);
                return productCategory;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void remove(int id) {
        psqlConnection = PSQLConnection.getInstance();
        String sql = "DELETE FROM product_category WHERE category_id=?";

        try (Connection conn = psqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ProductCategory> getAll() {
        List<ProductCategory> productCategories = new ArrayList<>();
        psqlConnection = PSQLConnection.getInstance();
        String sql = "SELECT * FROM product_category";

        try (Connection conn = psqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                ProductCategory productCategory = createNewProductCategoryFromSQLResult(resultSet);
                productCategory.setId(resultSet.getInt("category_id"));
                productCategories.add(productCategory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productCategories;
    }

    /**
     *
     * @param productCategoryName
     * @return Takes category name and returns the corresponding ID.
     * If no such category name is present, it returns 0 as ID.
     */
    public int getProductCategoryId(String productCategoryName) {
        int id = 1;
        psqlConnection = PSQLConnection.getInstance();
        String sql = "SELECT * FROM product_category WHERE name=?";

        try (Connection conn = psqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, productCategoryName);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                ProductCategory productCategory = createNewProductCategoryFromSQLResult(resultSet);
                id = productCategory.getId();
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    private ProductCategory createNewProductCategoryFromSQLResult(ResultSet resultSet) throws SQLException {
        ProductCategory productCategory = new ProductCategory(
                resultSet.getString("name"),
                resultSet.getString("department"),
                resultSet.getString("description")
        );

        productCategory.setId(resultSet.getInt("category_id"));
        return productCategory;
    }
}
