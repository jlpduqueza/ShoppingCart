package com.example.shoppingapp.repository;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import com.example.shoppingapp.domain.Product;
import com.example.shoppingapp.exception.DataException;

public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public Product getProduct(String productCode) throws DataException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Product product = new Product();

        try {
            DbConnection dbConnection = new DbConnectionImpl();

            conn = dbConnection.getConnection();

            String selectQuery = " SELECT product_code, product_description, product_price"
                                 + " FROM products WHERE product_code = ?";

            ps = conn.prepareStatement(selectQuery);
            ps.setString(1, productCode);
            rs = ps.executeQuery();

            while (rs.next()) {
                product.setProductCode(rs.getString("product_code"));
                product.setProductDescription(rs.getString("product_description"));
                product.setPrice(new BigDecimal(rs.getInt("product_price")));
            }
        } catch (SQLException ex) {
            throw new DataException("There's a problem fetching a product");
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

        return product;
    }

    @Override
    public List<Product> getProductList() throws DataException {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;
        List<Product> productList = new ArrayList<>();

        try {
            DbConnection dbConnection = new DbConnectionImpl();

            conn = dbConnection.getConnection();

            String selectQuery = " SELECT product_code, product_description, product_price" + " FROM products";

            s = conn.createStatement();
            rs = s.executeQuery(selectQuery);

            while (rs.next()) {
                Product product = new Product();

                product.setProductCode(rs.getString("product_code"));
                product.setProductDescription(rs.getString("product_description"));
                product.setPrice(new BigDecimal(rs.getInt("product_price")));
                productList.add(product);
            }
        } catch (SQLException ex) {
            throw new DataException("There's a problem fetching list of products");
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

        return productList;
    }
}
