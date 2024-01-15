package com.cost.crafter.dal;

import com.cost.crafter.config.DbConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseDAL {

    // Connection provider class for managing database connections
    private DbConnectionManager connectionManager;

    public BaseDAL(DbConnectionManager connectionProvider) {
        this.connectionManager = connectionProvider;
    }

    // Create operation
    public boolean create(String insertQuery, Object... parameters) {

        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            // Set parameters
            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setObject(i + 1, parameters[i]);
            }

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();

            // Return true if at least one row was affected
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Read operation
    public <T> List<T> read(String selectQuery, RowMapper<T> rowMapper, Object... parameters) {
        List<T> result = new ArrayList<>();

        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);

            // Set parameters
            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    preparedStatement.setObject(i + 1, parameters[i]);
                }
            }

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Map the result set to objects using the provided RowMapper
                while (resultSet.next()) {
                    result.add(rowMapper.mapRow(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Update operation
    public boolean update(String updateQuery, Object... parameters) {
        return create(updateQuery, parameters);
    }

    // Delete operation
    public boolean delete(String deleteQuery, Object... parameters) {
        return create(deleteQuery, parameters);
    }

    // Functional interface for mapping rows from ResultSet to objects
    public interface RowMapper<T> {
        T mapRow(ResultSet resultSet) throws SQLException;
    }
}
