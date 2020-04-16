package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Supplier> getAll() {
        return null;
    }

    public int getSupplierId(String supplierName) {
        return 1;
    }
}
