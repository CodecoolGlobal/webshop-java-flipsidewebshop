package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoJdbc implements SupplierDao {
    private PSQLConnection psqlConnection;
    private static SupplierDaoJdbc instance = null;

    private SupplierDaoJdbc() {}

    public static SupplierDaoJdbc getInstance() {
        if (instance == null) {
            instance = new SupplierDaoJdbc();
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) {
        psqlConnection = PSQLConnection.getInstance();
        String sql = "INSERT INTO supplier (name, description)" +
                " VALUES(?,?)";

        try (Connection conn = psqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, supplier.getName());
            pstmt.setString(2, supplier.getDescription());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Supplier find(int id) {

        psqlConnection = PSQLConnection.getInstance();
        String sql = "SELECT * FROM supplier WHERE supplier_id=?";

        try (Connection conn = psqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                Supplier supplier = createNewSupplierFromSQLResult(resultSet);
                return supplier;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(int id) {
        psqlConnection = PSQLConnection.getInstance();
        String sql = "DELETE FROM supplier WHERE supplier_id=?";

        try (Connection conn = psqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Supplier> getAll() {
        List<Supplier> suppliers = new ArrayList<>();
        psqlConnection = PSQLConnection.getInstance();
        String sql = "SELECT * FROM supplier";

        try (Connection conn = psqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Supplier supplier = createNewSupplierFromSQLResult(resultSet);
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    /**
     *
     * @param supplierName
     * @return Takes supplier name and returns the corresponding ID. If no such supplier name is present, it returns 0 as ID.
     */
    public int getSupplierId(String supplierName) {
        int id = 1;
        psqlConnection = PSQLConnection.getInstance();
        String sql = "SELECT * FROM supplier WHERE name=?";

        try (Connection conn = psqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, supplierName);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                Supplier supplier = createNewSupplierFromSQLResult(resultSet);
                id = supplier.getId();
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;

    }

    private Supplier createNewSupplierFromSQLResult(ResultSet resultSet) throws SQLException {
        Supplier newSupplier = new Supplier(
                resultSet.getString("name"),
                resultSet.getString("description")
        );
        newSupplier.setId(resultSet.getInt("supplier_id"));
        return newSupplier;
    }
}
