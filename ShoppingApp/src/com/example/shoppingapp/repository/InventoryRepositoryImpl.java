package com.example.shoppingapp.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import com.example.shoppingapp.domain.InventoryEntry;
import com.example.shoppingapp.domain.Product;
import com.example.shoppingapp.exception.DataException;

public class InventoryRepositoryImpl implements InventoryRepository {
    private ProductRepository productRepository;

    @Override
    public void addProductQuantity(Product product, int quantity) throws DataException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            DbConnection dbConnection = new DbConnectionImpl();

            conn = dbConnection.getConnection();

            String query = "UPDATE inventory SET quantity = quantity + ? WHERE product_code= ?";

            ps = conn.prepareStatement(query);
            ps.setInt(1, quantity);
            ps.setString(2, product.getProductCode());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DataException("There's a problem updating quantity in inventory");
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* ignored */
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* ignored */
                }
            }
        }
    }

    @Override
    public void updateProductQuantity(Product product, int quantity) throws DataException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            DbConnection dbConnection = new DbConnectionImpl();

            conn = dbConnection.getConnection();

            String query = "UPDATE inventory SET quantity = ? WHERE product_code= ?";

            ps = conn.prepareStatement(query);
            ps.setInt(1, quantity);
            ps.setString(2, product.getProductCode());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DataException("There's a problem updating quantity in inventory");
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* ignored */
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* ignored */
                }
            }
        }
    }

    @Override
    public InventoryEntry getInventoryEntry(String productCode) throws DataException {
        InventoryEntry inventoryEntry = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            DbConnection dbConnection = new DbConnectionImpl();

            conn = dbConnection.getConnection();

            String selectQuery = " SELECT product_code, quantity FROM inventory" + " WHERE product_code = ?";

            ps = conn.prepareStatement(selectQuery);
            ps.setString(1, productCode);
            rs = ps.executeQuery();

            while (rs.next()) {
                productRepository = new ProductRepositoryImpl();
                inventoryEntry = new InventoryEntry();

                Product product = productRepository.getProduct(productCode);

                inventoryEntry.setQuantity(rs.getInt("quantity"));
                inventoryEntry.setProduct(product);
            }
        } catch (SQLException ex) {
            throw new DataException("There's a problem fetching inventory item");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* ignored */
                }
            }

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* ignored */
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* ignored */
                }
            }
        }

        return inventoryEntry;
    }

    @Override
    public List<InventoryEntry> getInventoryList() throws DataException {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;
        List<InventoryEntry> inventoryList = new ArrayList<>();

        try {
            DbConnection dbConnection = new DbConnectionImpl();

            conn = dbConnection.getConnection();

            String selectQuery = "SELECT product_code, quantity FROM inventory";

            s = conn.createStatement();
            rs = s.executeQuery(selectQuery);

            while (rs.next()) {
                productRepository = new ProductRepositoryImpl();

                InventoryEntry inventoryEntry = new InventoryEntry();
                Product product = productRepository.getProduct(rs.getString("product_code"));

                inventoryEntry.setQuantity(rs.getInt("quantity"));
                inventoryEntry.setProduct(product);
                inventoryList.add(inventoryEntry);
            }
        } catch (SQLException ex) {
            throw new DataException("There's a problem fetching inventory");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* ignored */
                }
            }

            if (s != null) {
                try {
                    s.close();
                } catch (SQLException e) { /* ignored */
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* ignored */
                }
            }
        }

        return inventoryList;
    }
}

