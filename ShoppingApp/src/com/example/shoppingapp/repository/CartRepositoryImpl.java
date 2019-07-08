package com.example.shoppingapp.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import com.example.shoppingapp.domain.CartEntry;
import com.example.shoppingapp.domain.Product;
import com.example.shoppingapp.domain.User;
import com.example.shoppingapp.exception.DataException;

public class CartRepositoryImpl implements CartRepository {
    private ProductRepository productRepository;

    @Override
    public void addProductInCart(User user, Product product, int quantityFromCart, boolean duplicateFlag)
            throws DataException {
        Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;

        try {
            DbConnection dbConnection = new DbConnectionImpl();

            conn = dbConnection.getConnection();

            String updateQuery = "";
            String selectQuery = "";
            int totalQuantity = 0;

            if (duplicateFlag == true) {
                selectQuery = " SELECT quantity_in_cart FROM carts" + " WHERE id = ?" + " AND product_code = ?";
                ps = conn.prepareStatement(selectQuery);
                ps.setInt(1, user.getId());
                ps.setString(2, product.getProductCode());
                rs = ps.executeQuery();

                while (rs.next()) {
                    totalQuantity = quantityFromCart + rs.getInt("quantity_in_cart");
                }

                updateQuery = " UPDATE carts SET quantity_in_cart = ? WHERE id= ?" + " AND product_code= ?";
                ps1 = conn.prepareStatement(updateQuery);
                ps1.setInt(1, totalQuantity);
                ps1.setInt(2, user.getId());
                ps1.setString(3, product.getProductCode());
                ps1.execute();
            } else {
                updateQuery = " INSERT into carts (id, product_code, quantity_in_cart)" + " values (?, ?, ?)";
                ps2 = conn.prepareStatement(updateQuery);
                ps2.setInt(1, user.getId());
                ps2.setString(2, product.getProductCode());
                ps2.setInt(3, quantityFromCart);
                ps2.execute();
            }
        } catch (SQLException ex) {
            throw new DataException("There's a problem adding a product in cart");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {    /* ignored */
                }
            }

            if (ps2 != null) {
                try {
                    ps2.close();
                } catch (SQLException e) {    /* ignored */
                }
            }

            if (ps1 != null) {
                try {
                    ps.close();
                } catch (SQLException e) {    /* ignored */
                }
            }

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {    /* ignored */
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {    /* ignored */
                }
            }
        }
    }

    @Override
    public void checkOut(User user) throws DataException {
        Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;

        try {
            DbConnection dbConnection = new DbConnectionImpl();

            conn = dbConnection.getConnection();

            String deleteQuery = "DELETE FROM carts WHERE id= ?";
            String updateQuery = " UPDATE inventory"
                                 + " LEFT JOIN carts ON inventory.product_code = carts.product_code"
                                 + " SET inventory.quantity =" + " (inventory.quantity - carts.quantity_in_cart)"
                                 + " WHERE inventory.product_code = carts.product_code" + " AND carts.id= ?";

            ps = conn.prepareStatement(deleteQuery);
            ps1 = conn.prepareStatement(updateQuery);
            ps.setInt(1, user.getId());
            ps1.setInt(1, user.getId());
            ps1.executeUpdate();
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DataException("There's a problem in checking out");
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {    /* ignored */
                }
            }

            if (ps1 != null) {
                try {
                    ps.close();
                } catch (SQLException e) {    /* ignored */
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {    /* ignored */
                }
            }
        }
    }

    @Override
    public void deleteProductInCart(User user, Product product) throws DataException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            DbConnection dbConnection = new DbConnectionImpl();

            conn = dbConnection.getConnection();

            String query = "DELETE FROM carts WHERE id = ? AND product_code = ?";

            ps = conn.prepareStatement(query);
            ps.setInt(1, user.getId());
            ps.setString(2, product.getProductCode());
            ps.executeUpdate();
        } catch (SQLException ex) {}
        finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {    /* ignored */
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {    /* ignored */
                }
            }
        }
    }

    @Override
    public void updateProductInCart(User user, Product product, int quantity) throws DataException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            DbConnection dbConnection = new DbConnectionImpl();

            conn = dbConnection.getConnection();

            String query = " UPDATE carts SET quantity_in_cart = ?" + " WHERE id= ?" + " AND product_code= ?";

            ps = conn.prepareStatement(query);
            ps.setInt(1, quantity);
            ps.setInt(2, user.getId());
            ps.setString(3, product.getProductCode());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DataException("There's a problem updating a product in cart");
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {    /* ignored */
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {    /* ignored */
                }
            }
        }
    }

    @Override
    public CartEntry getCartEntry(User user, Product product) throws DataException {
        CartEntry cartEntry = new CartEntry();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            DbConnection dbConnection = new DbConnectionImpl();

            conn = dbConnection.getConnection();

            String selectQuery = " SELECT product_code, quantity_in_cart" + " FROM carts WHERE id = ?"
                                 + " AND product_code = ?";

            ps = conn.prepareStatement(selectQuery);
            ps.setInt(1, user.getId());
            ps.setString(2, product.getProductCode());
            rs = ps.executeQuery();

            while (rs.next()) {
                Product productToCart = product;

                cartEntry.setQuantityInCart(rs.getInt("quantity_in_cart"));
                cartEntry.setusername(user.getUsername());
                cartEntry.setProduct(productToCart);

                return cartEntry;
            }
        } catch (SQLException ex) {
            throw new DataException("There's a problem fetching cart entry");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {    /* ignored */
                }
            }

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {    /* ignored */
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {    /* ignored */
                }
            }
        }

        return cartEntry;
    }

    @Override
    public List<CartEntry> getCartList(User user) throws DataException {
        List<CartEntry> cartList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            DbConnection dbConnection = new DbConnectionImpl();

            conn = dbConnection.getConnection();

            String selectQuery = " SELECT product_code, quantity_in_cart" + " FROM carts WHERE id = ?";

            ps = conn.prepareStatement(selectQuery);
            ps.setInt(1, user.getId());
            rs = ps.executeQuery();

            while (rs.next()) {
                productRepository = new ProductRepositoryImpl();

                CartEntry cartEntry = new CartEntry();
                Product product = productRepository.getProduct(rs.getString("product_code"));

                cartEntry.setQuantityInCart(rs.getInt("quantity_in_cart"));
                cartEntry.setProduct(product);
                cartList.add(cartEntry);
            }
        } catch (SQLException ex) {
            throw new DataException("There's a problem fetching the cart items");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {    /* ignored */
                }
            }

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {    /* ignored */
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {    /* ignored */
                }
            }
        }

        return cartList;
    }

    @Override
    public boolean isProductExist(Product product, User user) throws DataException {
        boolean productExist = false;
        String productCode = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            DbConnection dbConnection = new DbConnectionImpl();

            conn = dbConnection.getConnection();

            String selectQuery = " SELECT product_code FROM carts" + " WHERE id = ?" + " AND product_code = ?";

            ps = conn.prepareStatement(selectQuery);
            ps.setInt(1, user.getId());
            ps.setString(2, product.getProductCode());
            rs = ps.executeQuery();

            while (rs.next()) {
                productCode = rs.getString("product_code");
            }

            if (productCode != null) {
                productExist = true;
            }
        } catch (SQLException ex) {
            throw new DataException("There's a problem in fetching a product");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {    /* ignored */
                }
            }

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {    /* ignored */
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {    /* ignored */
                }
            }
        }

        return productExist;
    }
}
