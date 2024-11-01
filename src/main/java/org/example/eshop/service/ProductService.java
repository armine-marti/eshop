package org.example.eshop.service;

import org.example.eshop.db.DBConnectionProvider;
import org.example.eshop.model.Category;
import org.example.eshop.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

    private Connection connection = DBConnectionProvider.getInstance().getConnection();
    private CategoryService categoryService = new CategoryService();

    public void add(Product product) {
        String sql = "INSERT INTO product (name, description, price, quantity, category_id) VALUES (?, ?, ?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getQuantity());
            preparedStatement.setInt(5, product.getCategory().getId());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Product getProductById(int id) {
        String sql = "SELECT * FROM product WHERE id = " + id;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                Product product = Product.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .price(resultSet.getDouble("price"))
                        .quantity(resultSet.getInt("quantity"))
                        .category(categoryService.getCategoryById(resultSet.getInt("category_id")))
                        .build();
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM product";
        List<Product> products = new ArrayList<Product>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Product product = Product.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .price(resultSet.getDouble("price"))
                        .quantity(resultSet.getInt("quantity"))
                        .category(categoryService.getCategoryById(resultSet.getInt("category_id")))
                        .build();
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public void deleteProduct(int productId) {
        String sql = "DELETE FROM product WHERE id = " + productId;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getSumOfProducts() {
        String sql = "SELECT SUM(quantity) FROM product";
        int productSum = 0;

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                productSum = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productSum;
    }

    public Product getMaxPriceProduct() {
        String sql = "SELECT * FROM product WHERE price = (SELECT MAX(price) FROM product)";
        Product product = null;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                product = Product.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .price(resultSet.getDouble("price"))
                        .quantity(resultSet.getInt("quantity"))
                        .category(categoryService.getCategoryById(resultSet.getInt("category_id")))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public Product getMinPriceProduct() {
        String sql = "SELECT * FROM product WHERE price = (SELECT MIN(price) FROM product)";
        Product product = null;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                product = Product.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .price(resultSet.getDouble("price"))
                        .quantity(resultSet.getInt("quantity"))
                        .category(categoryService.getCategoryById(resultSet.getInt("category_id")))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }


    public double getAvgProductPrice() {
        String sql = "SELECT AVG(price) FROM product";
        double averagePrice = 0;

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                averagePrice = resultSet.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return averagePrice;
    }

    public void updateProduct(Product product) {
        String sql = "UPDATE product SET name = ?, description = ?, price = ?, quantity = ?, category_id = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getQuantity());
            preparedStatement.setInt(5, product.getCategory().getId());
            preparedStatement.setInt(6, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
