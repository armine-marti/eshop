package org.example.eshop.service;

import org.example.eshop.db.DBConnectionProvider;
import org.example.eshop.model.Category;
import org.example.eshop.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {


    private Connection connection = DBConnectionProvider.getInstance().getConnection();


    public void add(Category category) {
        String sql = "INSERT INTO category (name) VALUES (?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, category.getName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                category.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Category> getAllCategories() {
        String sql = "SELECT * FROM category";
        List<Category> categories = new ArrayList<Category>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Category category = Category.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .build();
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public void deleteCategory(int categoryId) {
        String sql = "DELETE FROM category WHERE id = " + categoryId;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Category getCategoryById(int categoryId) {
        String sql = "SELECT * FROM category WHERE id = " + categoryId;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                Category category = Category.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .build();
                return category;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public Category getCategoryByName(String name) {
        String sql = "SELECT * FROM category WHERE name = ?";
        Category category = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                category = Category.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }


    public void updateCategory(Category category) {
        String sql = "UPDATE category SET name = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, category.getName());
            statement.setInt(2, category.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
