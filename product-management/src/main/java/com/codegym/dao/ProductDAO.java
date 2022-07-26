package com.codegym.dao;

import com.codegym.connection.ConnectionMySQL;
import com.codegym.connection.IConnectionMySQL;
import com.codegym.model.Product;
import com.codegym.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements IProductDAO {

    private IConnectionMySQL connectionMySQL = new ConnectionMySQL();

    private static final String INSERT_PRODUCT_SQL = "INSERT INTO product (name, price, quantity, image) VALUES (?, ?, ?, ?);";
    private static final String SELECT_PRODUCT_BY_ID = "select id,name,price,quantity,image from product where id =?";
    private static final String SELECT_ALL_PRODUCT = "select * from product";
    private static final String DELETE_PRODUCT_SQL = "delete from product where id = ?;";
    private static final String UPDATE_PRODUCT_SQL = "update product set name = ?,price= ?, quantity =?, image= ? where id = ?;";

    private static final String SEARCH_BY_NAME_TYPE = "SELECT * FROM user  WHERE  name LIKE ? OR price LIKE ? OR quantity LIKE ? ; ";

    private static String PRODUCT_EXIST_BY_ID = "" +
            "SELECT COUNT(*) AS COUNT " +
            "FROM `product` AS p " +
            "WHERE p.id = ?;";
    private int noOfRecords;

    public int getNoOfRecords(){
        return noOfRecords;
    }
    @Override
    public Boolean insertProduct(Product product) throws SQLException {
        boolean rowInsert = false;
        System.out.println(INSERT_PRODUCT_SQL);
        // try-with-resource statement will auto close the connection.
        try (Connection connection = connectionMySQL.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getPrice());
            preparedStatement.setString(3, product.getQuantity());
            preparedStatement.setString(4, product.getImage());
            rowInsert =  preparedStatement.executeUpdate()>0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowInsert;
    }

    public List<Product> getNumberPage(int offset, int noOfRecords, String name) throws ClassNotFoundException, SQLException {
        Connection connection = connectionMySQL.getConnection();
        System.out.println("numberpage");

        String query = "SELECT SQL_CALC_FOUND_ROWS * FROM product where name LIKE ? OR price LIKE ? OR quantity LIKE ? limit " + offset + "," + noOfRecords;
        List<Product> list = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, '%' + name + '%');
        ps.setString(2, '%' + name + '%');
        ps.setString(3, '%' + name + '%');
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Product product = new Product();
            product.setId(rs.getString("id"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getString("price"));
            product.setQuantity(rs.getString("quantity"));
            product.setImage(rs.getString("image"));
            list.add(product);
        }
        rs = ps.executeQuery("SELECT FOUND_ROWS()");
        if (rs.next()){
            this.noOfRecords = rs.getInt(1);
        }
        connection.close();
        return list;
    }

    @Override
    public boolean existByProductId(int id) {
        boolean exist = false;
        try {
            Connection connection = connectionMySQL.getConnection();
            PreparedStatement statement = connection.prepareCall(PRODUCT_EXIST_BY_ID);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int count = rs.getInt("count");
                if (count > 0) {
                    exist = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }

    @Override
    public Product selectProduct(String id) {
        Product product = null;
        try {
            Connection connection = connectionMySQL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID);
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String price = rs.getString("price");
                String quantity = rs.getString("quantity");
                String image = rs.getString("image");
                product = new Product(id, name, price, quantity, image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public List<Product> selectAllProduct() {
        List<Product> products = new ArrayList<>();
        // Step 1: Establishing a Connection
        try {
            Connection connection = connectionMySQL.getConnection();

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCT);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String price = rs.getString("price");
                String quantity = rs.getString("quantity");
                String image = rs.getString("image");
                products.add(new Product(id, name, price, quantity, image));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Boolean deleteProduct(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = connectionMySQL.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_SQL);) {
            preparedStatement.setInt(1, id);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public Boolean updateProduct(Product product) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = connectionMySQL.getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCT_SQL);) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getPrice());
            statement.setString(3, product.getQuantity());
            statement.setString(4, product.getImage());
            statement.setString(5, product.getId());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
@Override
    public List<Product> searchProduct(String query) {
        List<Product> listProduct = new ArrayList<>();
        try {
            Connection connection = connectionMySQL.getConnection();
            PreparedStatement preparedStatement = connection.prepareCall(SEARCH_BY_NAME_TYPE);
            preparedStatement.setString(1, '%' + query + '%');
            preparedStatement.setString(2, '%' + query + '%');
            preparedStatement.setString(3, '%' + query + '%');
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String price = rs.getString("price");
                String quantity = rs.getString("quantity");
                String image = rs.getString("image");
                listProduct.add(new Product(id, name, price, quantity, image));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listProduct;
    }


    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
