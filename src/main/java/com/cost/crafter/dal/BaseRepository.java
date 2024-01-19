package com.cost.crafter.dal;

import com.cost.crafter.config.DbConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseRepository {

    // Connection provider class for managing database connections
    private DbConnectionManager connectionManager;

    public BaseRepository(DbConnectionManager connectionProvider) {
        this.connectionManager = connectionProvider;
    }

    // Create operation
    public int create(String insertQuery, Object... parameters) throws Exception {
        int createdId = 0;
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery,
                    Statement.RETURN_GENERATED_KEYS);

            // Set parameters
            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setObject(i + 1, parameters[i]);
            }

            // Execute the query
            preparedStatement.executeUpdate();

            // Return created primary id
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next())
            {
                createdId = rs.getInt(1);
            }
            return createdId;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    // Read operation
    public <T> List<T> read(String selectQuery, RowMapper<T> rowMapper, Object... parameters) throws Exception {
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
            throw new Exception(e.getMessage());
        }

        return result;
    }

    public <T> T readOne(String selectQuery, RowMapper<T> rowMapper, Object... parameters) throws Exception {

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
                    return (rowMapper.mapRow(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

        return null;
    }

    // Update operation
    public int update(String updateQuery, Object... parameters) throws Exception {
        return create(updateQuery, parameters);
    }

    // Delete operation
    public int delete(String deleteQuery, Object... parameters) throws Exception {
        return create(deleteQuery, parameters);
    }

    // Functional interface for mapping rows from ResultSet to objects
    public interface RowMapper<T> {
        T mapRow(ResultSet resultSet) throws SQLException;
    }
}
