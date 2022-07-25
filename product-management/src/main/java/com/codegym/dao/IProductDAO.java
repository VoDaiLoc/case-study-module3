package com.codegym.dao;

import com.codegym.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface IProductDAO {
    Boolean insertProduct(Product product) throws SQLException;

    Product selectProduct(int id);

    List<Product> selectAllProduct();

    Boolean deleteProduct(int id) throws SQLException;

    Boolean updateProduct(Product product) throws ClassNotFoundException,SQLException;

    List<Product> searchProduct(String name);

    List<Product> getNumberPage(int offset, int noOfRecords, String name) throws ClassNotFoundException, SQLException;

    boolean existByProductId(int id);
}
